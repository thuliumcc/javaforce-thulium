package javaforce.gl;

/**
 * OpenGL binding for Java (JNA)
 *
 * @author pquiring
 *
 * Created : Sept 16, 2013
 *
 * Notes:
 *   - only support OpenGL 2.0 or better (1.x not supported)
 *   - only call GL functions from the EDT (event dispatching thread)
 *     - shared resources are used making it not thread safe
 *   - doesn't work on Ubuntu unless you install 'libgl1-mesa-dev' package
 *      - the GL.so is not in the ld path until this package installs???
 *   - Supports Windows, Linux and MacOSX.VI or better (aka SnowLeopard)
 *   - if there are functions or constants missing feel free to add them
 *     - add constants to end of "GL Constants" list
 *     - add function prototype to GLFuncs class and actual function at end of file
 *     - open a bug report and I will add it
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;
import com.sun.jna.win32.*;
import java.awt.*;
import java.lang.reflect.*;
import java.util.*;

import javaforce.*;
import javaforce.jna.objc.*;
import static javaforce.jna.objc.NSOpenGLPixelFormat.*;

public class GL {
  private static Pointer malloc(int size) {
    return new Pointer(Native.malloc(size));
  }

  private static void free(Pointer ptr) {
    Native.free(Pointer.nativeValue(ptr));
  }

  public GL(GLInterface iface) {
    this.iface = iface;
  }

  //Windows
  public static interface User32 extends StdCallLibrary {
    public Pointer GetDC(Pointer hwnd);
    public void ReleaseDC(Pointer hwnd, Pointer hdc);
    //test
    public Pointer SetWindowsHookExA(int idHook, Callback proc, Pointer hInst, int threadId);
    public int CallNextHookEx(Pointer hHook, int code, Pointer wParam, Pointer lParam);
  }
  private static User32 user32;
  public static interface Gdi32 extends StdCallLibrary {
    public void SwapBuffers(Pointer hdc);
    public int ChoosePixelFormat(Pointer hdc, PIXELFORMATDESCRIPTOR pfd);
    public int SetPixelFormat(Pointer hdc, int pixelFormat, PIXELFORMATDESCRIPTOR pfd);
  }
  private static Gdi32 gdi32;
  public static interface WGL extends StdCallLibrary {
    public Pointer wglCreateContext(Pointer hdc);
    public int wglDeleteContext(Pointer hglrc);
    public int wglMakeCurrent(Pointer hdc, Pointer hglrc);
    public Pointer wglGetProcAddress(String name);
  }
  private static WGL wgl;

  public static class PIXELFORMATDESCRIPTOR extends Structure {
    public short nSize, nVersion;
    public int dwFlags;
    public byte iPixelType, cColorBits;
    public byte ignored[] = new byte[13];
    public byte cDepthBits;
    public byte cStencilBits;
    public byte cAuxBits;
    public byte iLayerType;
    public byte ignored2;
    public int ignored3[] = new int[3];

    protected java.util.List getFieldOrder() {
      return Arrays.asList(new String[] {"nSize", "nVersion", "dwFlags", "iPixelType", "cColorBits"
        , "ignored", "cDepthBits", "cStencilBits", "cAuxBits", "iLayerType", "ignored2", "ignored3"
      });
    }
  }

  //Linux/X11 stuff
  public static interface X11 extends Library {
    public Pointer XOpenDisplay(Pointer res);
    public void XCloseDisplay(Pointer xd);
  }
  private static X11 x11;
  public static interface GLX extends Library {
    public Pointer glXCreateContext(Pointer xd, Pointer vi, Pointer shareList, int directRender);
    public int glXDestroyContext(Pointer ctx);
    public int glXMakeCurrent(Pointer xd, int win, Pointer ctx);
    public Pointer glXGetProcAddress(String name);
    public Pointer glXChooseVisual(Pointer xd, int res, int atts[]);
    public void glXSwapBuffers(Pointer wd, int win);
  }
  private static GLX glx;

  /** Must call once per process. */
  public static boolean init() {
    try {
      if (Platform.isWindows()) {
        if (wgl != null) return true;
        user32 = (User32)Native.loadLibrary("user32", User32.class);
        gdi32 = (Gdi32)Native.loadLibrary("gdi32", Gdi32.class);
        wgl = (WGL)Native.loadLibrary("opengl32", WGL.class);
        os = OS.WINDOWS;
      } else if (Platform.isMac()) {
        if (!ObjectiveC.init()) return false;
        os = OS.MAC;
      } else {
        if (x11 != null) return true;
        x11 = (X11)Native.loadLibrary("X11", X11.class);
        glx = (GLX)Native.loadLibrary("GL", GLX.class);
        os = OS.LINUX;
      }
    } catch (Throwable t) {
      JFLog.log(t);
      return false;
    }
    return true;
  }

  /** Creates an OpenGL context in a Canvas.
   */
  public static GL createComponent(Canvas c, GLInterface iface, GL shared) {
    GL gl = new GL(iface);
    if (os == OS.WINDOWS) {
      gl.whwnd = Native.getComponentPointer(c);
      createWindows(gl, shared);
      getWindowsAPI();
    } else if (os == OS.MAC) {
      if (!createMac(gl, c, shared)) return null;
      getMacAPI();
    } else {
      gl.x11id = (int)Native.getComponentID(c);
      createLinux(gl, shared);
      getLinuxAPI();
    }
    return gl;
  }

  /** Creates an OpenGL context that renders to offscreen image.
   */
  static GL createOffscreen(Window w, Component c, GLInterface iface, GL shared) {
    GL gl = new GL(iface);
    if (os == OS.WINDOWS) {
      gl.whwnd = Native.getWindowPointer(w);
      createWindows(gl, shared);
      getWindowsAPI();
    } else if (os == OS.MAC) {
      if (!createMac(gl, w, shared)) return null;
      getMacAPI();
    } else {
      gl.x11id = (int)Native.getWindowID(w);
      createLinux(gl, shared);
      getLinuxAPI();
    }
    gl.createOffscreen(c.getWidth(), c.getHeight());
    return gl;
  }

  //windows private constants
  private static final int PFD_DRAW_TO_WINDOW = 0x04;
  private static final int PFD_SUPPORT_OPENGL = 0x20;
  private static final int PFD_DOUBLEBUFFER = 0x01;
  private static final int PFD_TYPE_RGBA = 0x00;
  private static final int PFD_MAIN_PLANE = 0x00;

  private static void createWindows(GL gl, GL shared) {
    //gl.whwnd already obtained
    gl.whdc = user32.GetDC(gl.whwnd);
    PIXELFORMATDESCRIPTOR pfd = new PIXELFORMATDESCRIPTOR();
    pfd.nSize = (short)pfd.size();
    pfd.nVersion = 1;
    pfd.dwFlags = PFD_DRAW_TO_WINDOW | PFD_SUPPORT_OPENGL | PFD_DOUBLEBUFFER;
    pfd.iPixelType = PFD_TYPE_RGBA;
    pfd.cColorBits = 24;
    pfd.cDepthBits = 16;
    pfd.cStencilBits = 8;
    pfd.iLayerType = PFD_MAIN_PLANE;
    int pixelFormat = gdi32.ChoosePixelFormat(gl.whdc, pfd);
    gdi32.SetPixelFormat(gl.whdc, pixelFormat, pfd);
    if (shared == null) {
      gl.wctx = wgl.wglCreateContext(gl.whdc);
    } else {
      gl.wctx = (Pointer)shared.wctx;
    }
    wgl.wglMakeCurrent(gl.whdc, gl.wctx);
  }

  private static void getWindowsAPI() {
    if (api != null) return;
    api = new GLFuncs();
    NativeLibrary glLibrary = NativeLibrary.getInstance("opengl32");
    try {
      Field fields[] = api.getClass().getFields();
      for(int a=0;a<fields.length;a++) {
        String name = fields[a].getName();
        Pointer ptr = wgl.wglGetProcAddress(name);
        if (ptr == null) {
          //OpenGL 1.1 function
          try {
            fields[a].set(api, glLibrary.getFunction(name));
          } catch (Throwable t) {
            JFLog.log("OpenGL:Warning:Function not found:" + name);
          }
        } else {
          //OpenGL 2.0+ function
          fields[a].set(api, Function.getFunction(ptr));
        }
      }
    } catch (Exception e) {
      JFLog.log(e);
    }
  }

  private static synchronized boolean createMac(GL gl, Component c, GL shared) {
    final GL _gl = gl;
    final GL _shared = shared;
    final Component _c = c;

    NSAutoreleasePool pool = new NSAutoreleasePool();
    pool.alloc();
    pool.init();

    _gl.nsobj = new MyNSObject();
    _gl.nsobj.register(new Runnable() {public void run() {
      _gl.nsview = new NSView();
      _gl.nsview.obj = Native.getComponentPointer(_c);

      if (_shared == null) {
        NSOpenGLPixelFormat fmt = new NSOpenGLPixelFormat();
        fmt.alloc();
        fmt.initWithAttributes(new int[] {
          NSOpenGLPFAWindow,
    //      NSOpenGLPFAAccelerated,  //is not available on my test system
          NSOpenGLPFADoubleBuffer,
          NSOpenGLPFAColorSize,24,
          NSOpenGLPFADepthSize,16,
            0  //zero terminate list
          }
        );
        if (fmt.obj == null) {
          JFLog.log("NSOpenGLPixelFormat initWithAttributes failed");
          return;
        }
        _gl.nsopenglcontext = new NSOpenGLContext();
        _gl.nsopenglcontext.alloc();
        _gl.nsopenglcontext.initWithFormat(fmt);
      } else {
        _gl.nsopenglcontext = _shared.nsopenglcontext;
      }
      _gl.nsopenglcontext.setView(_gl.nsview);
      _gl.nsopenglcontext.makeCurrentContext();
      synchronized(nslock) {
        nslock.notify();
      }
    }});
    _gl.nsobj.alloc();
    _gl.nsobj.init();
    synchronized(nslock) {
      _gl.nsobj.performSelectorOnMainThread(ObjectiveC.getSelector("callback"), null, false);
      try { nslock.wait(); } catch (Exception e) {}
    }
    _gl.nsobj.setRunnable(new Runnable() {public void run() {
      _gl.render_mac();
    }});

    pool.release();
    return true;
  }

  private static void getMacAPI() {
    if (api != null) return;
    api = new GLFuncs();
    NativeLibrary glLibrary = NativeLibrary.getInstance("OpenGL");
    try {
      Field fields[] = api.getClass().getFields();
      for(int a=0;a<fields.length;a++) {
        String name = fields[a].getName();
        try {
          fields[a].set(api, glLibrary.getFunction(name));
        } catch (Throwable t) {
          JFLog.log("OpenGL:Warning:Function not found:" + name);
        }
      }
    } catch (Exception e) {
      JFLog.log(e);
    }
  }

  private static final int GLX_RGBA = 4;
  private static final int GLX_DEPTH_SIZE = 12;
  private static final int GLX_DOUBLEBUFFER = 5;
  private static final int None = 0;

  private static void createLinux(GL gl, GL shared) {
    //x11id is already obtained
    gl.xd = x11.XOpenDisplay(null);
    int atts[] = new int[] {GLX_RGBA, GLX_DEPTH_SIZE, 32, GLX_DOUBLEBUFFER, None};
    gl.xvi = glx.glXChooseVisual(gl.xd, 0, atts);
    if (shared == null) {
      gl.xctx = glx.glXCreateContext(gl.xd, gl.xvi, null, GL_TRUE);
    } else {
      gl.xctx = shared.xctx;
    }
    glx.glXMakeCurrent(gl.xd, gl.x11id, gl.xctx);
  }

  private static void getLinuxAPI() {
    if (api != null) return;
    api = new GLFuncs();
    NativeLibrary glLibrary = NativeLibrary.getInstance("GL");
    try {
      Field fields[] = api.getClass().getFields();
      for(int a=0;a<fields.length;a++) {
        String name = fields[a].getName();
        Pointer ptr = glx.glXGetProcAddress(name);
        if (ptr == null) {
          //OpenGL 1.1 function
          try {
            fields[a].set(api, glLibrary.getFunction(name));
          } catch (Throwable t) {
            JFLog.log("OpenGL:Warning:Function not found:" + name);
          }
        } else {
          //OpenGL 2.0+ function
          fields[a].set(api, Function.getFunction(ptr));
        }
      }
    } catch (Exception e) {
      JFLog.log(e);
    }
  }

  //offscreen data
  private int os_fb, os_clr_rb, os_depth_rb;
  private int os_width, os_height;
  private int os_px[], os_fpx[];  //pixels, flipped pixels
  private JFImage os_img;  //basically a BufferedImage

  /** Get offscreen buffer in a java.awt.Image */
  public Image getOffscreen() {
    //TODO : the image is trippled here - need to optimize!!!
    glReadPixels(0, 0, os_width, os_height, GL_BGRA, GL_UNSIGNED_BYTE, os_px);
    //invert and fix alpha (pixel by pixel - slow - OUCH!)
    //OpenGL makes black pixels transparent which causes unwanted trailing effects
    int src = (os_height - 1) * os_width;
    int dst = 0;
    for(int y=0;y<os_height;y++) {
      for(int x=0;x<os_width;x++) {
        os_fpx[dst++] = os_px[src++] | 0xff000000;
      }
      src -= os_width * 2;
    }
    os_img.putPixels(os_fpx, 0, 0, os_width, os_height, 0);
    return os_img.getImage();
  }

  /** Get offscreen buffer pixels (leaving alpha channel as is)
   * Pixels that are not rendered to are usually transparent.
   */
  public int[] getOffscreenPixels() {
    glReadPixels(0, 0, os_width, os_height, GL_BGRA, GL_UNSIGNED_BYTE, os_px);
    //invert and fix alpha (pixel by pixel - slow - OUCH!)
    //OpenGL makes black pixels transparent which causes unwanted trailing effects
    int src = (os_height - 1) * os_width;
    int dst = 0;
    for(int y=0;y<os_height;y++) {
      System.arraycopy(os_px, src, os_fpx, dst, os_width);
      src -= os_width;
      dst += os_width;
    }
    return os_fpx;
  }

  /** Resize offscreen buffer dimensions. */
  public void resizeOffscreen(int width, int height) {
    os_width = width;
    os_height = height;
    os_px = new int[os_width * os_height];
    os_fpx = new int[os_width * os_height];
    os_img = new JFImage(os_width, os_height);

    int ids[] = {os_clr_rb, os_depth_rb};
    glDeleteRenderbuffers(2, ids);

    glGenRenderbuffers(1, ids);
    os_clr_rb = ids[0];
    glBindRenderbuffer(GL_RENDERBUFFER, os_clr_rb);
    glRenderbufferStorage(GL_RENDERBUFFER, GL_RGBA, width, height);
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, os_clr_rb);

    glGenRenderbuffers(1, ids);
    os_depth_rb = ids[0];
    glBindRenderbuffer(GL_RENDERBUFFER, os_depth_rb);
    glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, os_depth_rb);
  }

  /** Creates an offscreen buffer to where rendering is directly to. */
  public void createOffscreen(int width, int height) {
    int ids[] = new int[1];

    glGenFramebuffers(1, ids);
    os_fb = ids[0];
    glBindFramebuffer(GL_FRAMEBUFFER, os_fb);

    glGenRenderbuffers(1, ids);
    os_clr_rb = ids[0];
    glBindRenderbuffer(GL_RENDERBUFFER, os_clr_rb);
    glRenderbufferStorage(GL_RENDERBUFFER, GL_RGBA, width, height);
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, os_clr_rb);

    glGenRenderbuffers(1, ids);
    os_depth_rb = ids[0];
    glBindRenderbuffer(GL_RENDERBUFFER, os_depth_rb);
    glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, os_depth_rb);

    glBindFramebuffer(GL_FRAMEBUFFER, os_fb);

    os_width = width;
    os_height = height;
    os_px = new int[os_width * os_height];
    os_fpx = new int[os_width * os_height];
    os_img = new JFImage(os_width, os_height);
  }

  public void destroy() {
    if (os == OS.WINDOWS) {
      wgl.wglMakeCurrent(null, null);
      wgl.wglDeleteContext(wctx);
      wctx = null;
      user32.ReleaseDC(whwnd, whdc);
      whdc = null;
    } else if (os == OS.MAC) {
      if (nsopenglcontext != null) {
        nsopenglcontext.clearCurrentContext();
        nsopenglcontext.dealloc();
        nsopenglcontext = null;
      }
      if (nsobj != null) {
        nsobj.dealloc();
        nsobj = null;
      }
    } else {
      glx.glXMakeCurrent(xd, 0, null);
      glx.glXDestroyContext(xctx);
      xctx = null;
      x11.XCloseDisplay(xd);
      xd = null;
    }
  }

  /** Makes all calls to gl apply to this window. */
  public void makeCurrent() {
    //NOTE:This func is called directly by some projects that use offscreen rendering
    if (os == OS.WINDOWS) {
      wgl.wglMakeCurrent(whdc, wctx);
    } else if (os == OS.MAC) {
      nsopenglcontext.setView(nsview);
      nsopenglcontext.makeCurrentContext();
    } else {
      glx.glXMakeCurrent(xd, x11id, xctx);
    }
  }

  /** Makes your rendering visible. */
  public void swap() {
    if (os == OS.WINDOWS) {
      gdi32.SwapBuffers(whdc);
    } else if (os == OS.MAC) {
      nsopenglcontext.flushBuffer();
    } else {
      glx.glXSwapBuffers(xd, x11id);
    }
  }

  private void lock() {
    if (os == OS.MAC) {
      nsview.lockFocus();
    }
  }

  private void unlock() {
    if (os == OS.MAC) {
      nsview.unlockFocus();
    }
  }

  private void render_mac() {
    lock();
    makeCurrent();
    iface.render(this);
    unlock();
  }

  void render() {
    if (os == OS.MAC) {
      NSAutoreleasePool pool = new NSAutoreleasePool();
      pool.alloc();
      pool.init();
      nsobj.performSelectorOnMainThread(ObjectiveC.getSelector("callback"), null, false);
      pool.release();
    } else {
      makeCurrent();
      iface.render(this);
    }
  }

  //common data
  private GLInterface iface;
  private enum OS {WINDOWS, LINUX, MAC};
  private static OS os;

  //Windows data
  private Pointer wctx, whwnd, whdc;

  //Linux data
  private int x11id;
  private Pointer xd, xvi, xctx;

  //MAC data
  private NSView nsview;
  private NSOpenGLContext nsopenglcontext;
  private MyNSObject nsobj;
  private static Object nslock = new Object();

  //GL constants
  public static final int GL_VERSION = 0x1F02;
  public static final int GL_MAX_TEXTURE_SIZE = 0x0D33;
  public static final int GL_CW = 0x900;
  public static final int GL_CCW = 0x0901;
  public static final int GL_CULL_FACE = 0x0b44;
  public static final int GL_BLEND = 0x0be2;
  public static final int GL_DEPTH_TEST = 0x0b71;

  public static final int GL_NEVER = 0x0200;
  public static final int GL_LESS = 0x0201;
  public static final int GL_EQUAL = 0x0202;
  public static final int GL_LEQUAL = 0x0203;
  public static final int GL_GREATER = 0x0204;
  public static final int GL_NOTEQUAL = 0x0205;
  public static final int GL_GEQUAL = 0x0206;
  public static final int GL_ALWAYS = 0x0207;

  public static final int GL_SRC_COLOR = 0x0300;
  public static final int GL_ONE_MINUS_SRC_COLOR = 0x0301;
  public static final int GL_SRC_ALPHA = 0x0302;
  public static final int GL_ONE_MINUS_SRC_ALPHA = 0x0303;
  public static final int GL_DST_ALPHA = 0x0304;
  public static final int GL_ONE_MINUS_DST_ALPHA = 0x0305;
  public static final int GL_DST_COLOR = 0x0306;
  public static final int GL_ONE_MINUS_DST_COLOR = 0x0307;
  public static final int GL_SRC_ALPHA_SATURATE = 0x0308;

  public static final int GL_UNPACK_ALIGNMENT = 0x0cf5;
  public static final int GL_TEXTURE_2D = 0x0de1;
  public static final int GL_TEXTURE_WRAP_S = 0x2802;
  public static final int GL_TEXTURE_WRAP_T = 0x2803;
  public static final int GL_REPEAT = 0x2901;
  public static final int GL_TEXTURE_MAG_FILTER = 0x2800;
  public static final int GL_TEXTURE_MIN_FILTER = 0x2801;
  public static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
  public static final int GL_NEAREST = 0x2600;
  public static final int GL_TEXTURE_ENV = 0x2300;
  public static final int GL_TEXTURE_ENV_MODE = 0x2200;
  public static final int GL_MODULATE = 0x2100;
  public static final int GL_RGBA = 0x1908;
  public static final int GL_BGRA = 0x80e1;
  public static final int GL_COLOR_BUFFER_BIT = 0x4000;
  public static final int GL_DEPTH_BUFFER_BIT= 0x0100;
  public static final int GL_STENCIL_BUFFER_BIT = 0x0400;
  public static final int GL_STENCIL_TEST = 0x0B90;
  public static final int GL_ARRAY_BUFFER = 0x8892;
  public static final int GL_STATIC_DRAW = 0x88e4;
  public static final int GL_STREAM_DRAW = 0x88e0;
  public static final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;
  public static final int GL_FLOAT = 0x1406;
  public static final int GL_FALSE = 0;
  public static final int GL_TRUE = 1;
  public static final int GL_ZERO = 0;
  public static final int GL_ONE = 1;
  public static final int GL_UNSIGNED_BYTE = 0x1401;
  public static final int GL_UNSIGNED_SHORT = 0x1403;
  public static final int GL_UNSIGNED_INT = 0x1405;

  public static final int GL_POINTS = 0x0000;
  public static final int GL_LINES = 0x0001;
  public static final int GL_LINE_LOOP = 0x0002;
  public static final int GL_LINE_STRIP = 0x0003;
  public static final int GL_TRIANGLES = 0x0004;
  public static final int GL_TRIANGLE_STRIP = 0x0005;
  public static final int GL_TRIANGLE_FAN = 0x0006;
  public static final int GL_QUADS = 0x0007;
  public static final int GL_QUAD_STRIP = 0x0008;
  public static final int GL_POLYGON = 0x0009;

  public static final int GL_FRAGMENT_SHADER = 0x8b30;
  public static final int GL_VERTEX_SHADER = 0x8b31;
  public static final int GL_TEXTURE0 = 0x84c0;
  public static final int GL_FRAMEBUFFER = 0x8d40;
  public static final int GL_READ_FRAMEBUFFER = 0x8ca8;
  public static final int GL_DRAW_FRAMEBUFFER = 0x8ca9;
  public static final int GL_COLOR_ATTACHMENT0 = 0x8ce0;
  public static final int GL_DEPTH_COMPONENT16 = 0x81a5;
  public static final int GL_DEPTH_COMPONENT24 = 0x81a6;
  public static final int GL_DEPTH_COMPONENT32 = 0x81a7;
  public static final int GL_DEPTH_ATTACHMENT = 0x8d00;
  public static final int GL_RENDERBUFFER = 0x8d41;

  //glCullFace constants
  public static final int GL_FRONT = 0x0404;
  public static final int GL_BACK = 0x0405;
  public static final int GL_FRONT_AND_BACK = 0x0408;

  /** Returns OpenGL version. ie: {3,3,0} */
  public int[] getVersion() {
    String str = glGetString(GL_VERSION);
    if (str == null) {
      JFLog.log("Error:glGetString returned NULL");
      return new int[] {0,0};
    }
    int idx = str.indexOf(" ");
    if (idx != -1) str = str.substring(0, idx);
    String parts[] = str.split("[.]");
    int ret[] = new int[parts.length];
    for(int a=0;a<parts.length;a++) {
      ret[a] = Integer.valueOf(parts[a]);
    }
    return ret;
  }

  public static String getJNAVersion() {
    return Native.VERSION + ":" + Native.VERSION_NATIVE;
  }

  public void printError(String msg) {
    int err;
    do {
      err = glGetError();
      System.out.println(msg + "=" + String.format("%x", err));
    } while (err != 0);
  }

  public void printError() {
    printError("err");
  }

  /** Clears viewport */
  public void clear(int clr, int width, int height) {
    float r = (clr & 0xff0000) >> 16;
    r /= 256.0f;
    float g = (clr & 0xff00) >> 8;
    g /= 256.0f;
    float b = (clr & 0xff);
    b /= 256.0f;
    glViewport(0, 0, width, height);
    glClearColor(r, g, b, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT);
    swap();
  }

  private static class GLFuncs {
    public Function glActiveTexture;
    public Function glAttachShader;
    public Function glBindBuffer;
    public Function glBindFramebuffer;
    public Function glBindRenderbuffer;
    public Function glBindTexture;
    public Function glBlendFunc;
    public Function glBufferData;
    public Function glClear;
    public Function glClearColor;
    public Function glColorMask;
    public Function glCompileShader;
    public Function glCreateProgram;
    public Function glCreateShader;
    public Function glCullFace;
    public Function glDepthFunc;
    public Function glDepthMask;
    public Function glDeleteBuffers;
    public Function glDeleteFramebuffers;
    public Function glDeleteRenderbuffers;
    public Function glDeleteTextures;
    public Function glDisable;
    public Function glDrawElements;
    public Function glEnable;
    public Function glEnableVertexAttribArray;
    public Function glFlush;
    public Function glFramebufferTexture2D;
    public Function glFramebufferRenderbuffer;
    public Function glFrontFace;
    public Function glGetAttribLocation;
    public Function glGetError;
    public Function glGetProgramInfoLog;
    public Function glGetShaderInfoLog;
    public Function glGetString;
    public Function glGetIntegerv;
    public Function glGenBuffers;
    public Function glGenFramebuffers;
    public Function glGenRenderbuffers;
    public Function glGenTextures;
    public Function glGetUniformLocation;
    public Function glLinkProgram;
    public Function glPixelStorei;
    public Function glReadPixels;
    public Function glRenderbufferStorage;
    public Function glShaderSource;
    public Function glStencilFunc;
    public Function glStencilMask;
    public Function glStencilOp;
    public Function glTexImage2D;
    public Function glTexParameteri;
    public Function glUseProgram;
    public Function glUniformMatrix4fv;
    public Function glUniform3fv;
    public Function glUniform2fv;
    public Function glUniform1f;
    public Function glUniform3iv;
    public Function glUniform2iv;
    public Function glUniform1i;
    public Function glVertexAttribPointer;
    public Function glViewport;
  }
  private static GLFuncs api;

  private Object a1[] = new Object[1];
  private Object a2[] = new Object[2];
  private Object a3[] = new Object[3];
  private Object a4[] = new Object[4];
  private Object a5[] = new Object[5];
  private Object a6[] = new Object[6];
  private Object a7[] = new Object[7];
//  private Object a8[] = new Object[8];
  private Object a9[] = new Object[9];

  public void glActiveTexture(int i1) {
    a1[0] = i1;
    api.glActiveTexture.invoke(a1);
  }
  public void glAttachShader(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glAttachShader.invoke(a2);
  }
  public void glBindBuffer(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBindBuffer.invoke(a2);
  }
  public void glBindFramebuffer(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBindFramebuffer.invoke(a2);
  }
  public void glBindRenderbuffer(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBindRenderbuffer.invoke(a2);
  }
  public void glBindTexture(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBindTexture.invoke(a2);
  }
  public void glBlendFunc(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBlendFunc.invoke(a2);
  }
  //NOTE : GLsizeiptr is 32 or 64 bits - use a Pointer
  public void glBufferData(int i1, int i2, float i3[], int i4) {
    a4[0] = i1;
    a4[1] = new Pointer(i2);
    a4[2] = i3;
    a4[3] = i4;
    api.glBufferData.invoke(a4);
  }
  public void glBufferData(int i1, int i2, short i3[], int i4) {
    a4[0] = i1;
    a4[1] = new Pointer(i2);
    a4[2] = i3;
    a4[3] = i4;
    api.glBufferData.invoke(a4);
  }
  public void glBufferData(int i1, int i2, int i3[], int i4) {
    a4[0] = i1;
    a4[1] = new Pointer(i2);
    a4[2] = i3;
    a4[3] = i4;
    api.glBufferData.invoke(a4);
  }
  public void glBufferData(int i1, int i2, byte i3[], int i4) {
    a4[0] = i1;
    a4[1] = new Pointer(i2);
    a4[2] = i3;
    a4[3] = i4;
    api.glBufferData.invoke(a4);
  }
  public void glClear(int flags) {
    a1[0] = flags;
    api.glClear.invoke(a1);
  }
  public void glClearColor(float r, float g, float b, float a) {
    a4[0] = r;
    a4[1] = g;
    a4[2] = b;
    a4[3] = a;
    api.glClearColor.invoke(a4);
  }
  public void glColorMask(boolean r, boolean g, boolean b, boolean a) {
    a4[0] = r;
    a4[1] = g;
    a4[2] = b;
    a4[3] = a;
    api.glColorMask.invoke(a4);
  }
  public void glCompileShader(int id) {
    a1[0] = id;
    api.glCompileShader.invoke(a1);
  }
  public int glCreateProgram() {
    return api.glCreateProgram.invokeInt(null);
  }
  public int glCreateShader(int type) {
    a1[0] = type;
    return api.glCreateShader.invokeInt(a1);
  }
  public void glCullFace(int id) {
    a1[0] = id;
    api.glCullFace.invoke(a1);
  }
  public void glDeleteBuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glDeleteBuffers.invoke(a2);
  }
  public void glDeleteFramebuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glDeleteFramebuffers.invoke(a2);
  }
  public void glDeleteRenderbuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glDeleteRenderbuffers.invoke(a2);
  }
  public void glDeleteTextures(int i1, int i2[], int i3) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = i3;
    api.glDeleteTextures.invoke(a3);
  }
  public void glDrawElements(int i1, int i2, int i3, int i4) {
    a4[0] = i1;
    a4[1] = i2;
    a4[2] = i3;
    a4[3] = new Pointer(i4);
    api.glDrawElements.invoke(a4);
  }
  public void glDepthFunc(int i1) {
    a1[0] = i1;
    api.glDepthFunc.invoke(a1);
  }
  public void glDisable(int id) {
    a1[0] = id;
    api.glDisable.invoke(a1);
  }
  public void glDepthMask(boolean state) {
    a1[0] = state;
    api.glDepthMask.invoke(a1);
  }
  public void glEnable(int id) {
    a1[0] = id;
    api.glEnable.invoke(a1);
  }
  public void glEnableVertexAttribArray(int id) {
    a1[0] = id;
    api.glEnableVertexAttribArray.invoke(a1);
  }
  public void glFlush() {
    api.glFlush.invoke(null);
  }
  public void glFramebufferTexture2D(int i1, int i2, int i3, int i4, int i5) {
    a5[0] = i1;
    a5[1] = i2;
    a5[2] = i3;
    a5[3] = i4;
    a5[4] = i5;
    api.glFramebufferTexture2D.invoke(a5);
  }
  public void glFramebufferRenderbuffer(int i1, int i2, int i3, int i4) {
    a4[0] = i1;
    a4[1] = i2;
    a4[2] = i3;
    a4[3] = i4;
    api.glFramebufferRenderbuffer.invoke(a4);
  }
  public void glFrontFace(int id) {
    a1[0] = id;
    api.glFrontFace.invoke(a1);
  }
  public int glGetAttribLocation(int i1, String str) {
    a2[0] = i1;
    a2[1] = str;
    return api.glGetAttribLocation.invokeInt(a2);
  }
  public int glGetError() {
    return api.glGetError.invokeInt(null);
  }
  public String glGetProgramInfoLog(int id) {
    IntByReference len = new IntByReference();
    len.setValue(1024);
    Pointer str = malloc(1024);
    a4[0] = id;
    a4[1] = 1024;
    a4[2] = len;
    a4[3] = str;
    api.glGetProgramInfoLog.invoke(a4);
    String ret = str.getString(0);
    free(str);
    return ret;
  }
  public String glGetShaderInfoLog(int id) {
    IntByReference len = new IntByReference();
    len.setValue(1024);
    Pointer str = malloc(1024);
    a4[0] = id;
    a4[1] = 1024;
    a4[2] = len;
    a4[3] = str;
    api.glGetShaderInfoLog.invoke(a4);
    String ret = str.getString(0);
    free(str);
    return ret;
  }
  public String glGetString(int type) {
    a1[0] = type;
    return api.glGetString.invokeString(a1, false);
  }
  public void glGetIntegerv(int type, int i[]) {
    a2[0] = type;
    a2[1] = i;
    api.glGetIntegerv.invoke(a2);
  }
  public void glGenBuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glGenBuffers.invoke(a2);
  }
  public void glGenFramebuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glGenFramebuffers.invoke(a2);
  }
  public void glGenRenderbuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glGenRenderbuffers.invoke(a2);
  }
  public void glGenTextures(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glGenTextures.invoke(a2);
  }
  public int glGetUniformLocation(int i1, String str) {
    a2[0] = i1;
    a2[1] = str;
    return api.glGetUniformLocation.invokeInt(a2);
  }
  public void glLinkProgram(int id) {
    a1[0] = id;
    api.glLinkProgram.invoke(a1);
  }
  public void glPixelStorei(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glPixelStorei.invoke(a2);
  }
  public void glReadPixels(int i1, int i2, int i3, int i4, int i5, int i6, int px[]) {
    a7[0] = i1;
    a7[1] = i2;
    a7[2] = i3;
    a7[3] = i4;
    a7[4] = i5;
    a7[5] = i6;
    a7[6] = px;
    api.glReadPixels.invoke(a7);
  }
  public void glRenderbufferStorage(int i1, int i2, int i3, int i4) {
    a4[0] = i1;
    a4[1] = i2;
    a4[2] = i3;
    a4[3] = i4;
    api.glRenderbufferStorage.invoke(a4);
  }
  public int glShaderSource(int type, int count, String src[], int src_lengths[]) {
    a4[0] = type;
    a4[1] = count;
    a4[2] = src;
    a4[3] = src_lengths;
    return api.glShaderSource.invokeInt(a4);
  }
  public int glStencilFunc(int func, int ref, int mask) {
    a3[0] = func;
    a3[1] = ref;
    a3[2] = mask;
    return api.glStencilFunc.invokeInt(a3);
  }
  public int glStencilMask(int mask) {
    a1[0] = mask;
    return api.glStencilMask.invokeInt(a1);
  }
  public int glStencilOp(int sfail, int dpfail, int dppass) {
    a3[0] = sfail;
    a3[1] = dpfail;
    a3[2] = dppass;
    return api.glStencilOp.invokeInt(a3);
  }
  public void glTexImage2D(int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int px[]) {
    a9[0] = i1;
    a9[1] = i2;
    a9[2] = i3;
    a9[3] = i4;
    a9[4] = i5;
    a9[5] = i6;
    a9[6] = i7;
    a9[7] = i8;
    a9[8] = px;
    api.glTexImage2D.invoke(a9);
  }
  public void glTexParameteri(int i1, int i2, int i3) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = i3;
    api.glTexParameteri.invoke(a3);
  }
  public void glUseProgram(int id) {
    a1[0] = id;
    api.glUseProgram.invoke(a1);
  }
  public void glUniformMatrix4fv(int i1, int i2, int i3, float m[]) {
    a4[0] = i1;
    a4[1] = i2;
    a4[2] = i3;
    a4[3] = m;
    api.glUniformMatrix4fv.invoke(a4);
  }
  public void glUniform3fv(int i1, int i2, float f[]) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = f;
    api.glUniform3fv.invoke(a3);
  }
  public void glUniform2fv(int i1, int i2, float f[]) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = f;
    api.glUniform2fv.invoke(a3);
  }
  public void glUniform1f(int i1, float f) {
    a2[0] = i1;
    a2[1] = f;
    api.glUniform1f.invoke(a2);
  }
  public void glUniform3iv(int i1, int i2, int v[]) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = v;
    api.glUniform3iv.invoke(a3);
  }
  public void glUniform2iv(int i1, int i2, int v[]) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = v;
    api.glUniform2iv.invoke(a3);
  }
  public void glUniform1i(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glUniform1i.invoke(a2);
  }
  public void glVertexAttribPointer(int i1, int i2, int i3, int i4, int i5, int i6) {
    a6[0] = i1;
    a6[1] = i2;
    a6[2] = i3;
    a6[3] = i4;
    a6[4] = i5;
    a6[5] = new Pointer(i6);
    api.glVertexAttribPointer.invoke(a6);
  }
  public void glViewport(int x,int y,int w,int h) {
    a4[0] = x;
    a4[1] = y;
    a4[2] = w;
    a4[3] = h;
    api.glViewport.invoke(a4);
  }
}
