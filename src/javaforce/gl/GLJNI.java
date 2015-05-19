package javaforce.gl;

/** JNI Access to OpenGL functions for better performance.
 *
 * A few String related functions still use JNA (easier and low frequency).
 *
 * @author pquiring
 */

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import static javaforce.gl.GL.malloc;

import javaforce.jni.*;

public class GLJNI extends GL {

  private static boolean x64;

  static {
    x64 = Platform.is64Bit();
  }

  public GLJNI(GLInterface iface) {
    this.iface = iface;
  }

  public void glActiveTexture(int i1) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glActiveTexture), i1);
  }

  public void glAttachShader(int i1, int i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glAttachShader), i1, i2);
  }

  public void glBindBuffer(int i1, int i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glBindBuffer), i1, i2);
  }

  public void glBindFramebuffer(int i1, int i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glBindFramebuffer), i1, i2);
  }

  public void glBindRenderbuffer(int i1, int i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glBindRenderbuffer), i1, i2);
  }

  public void glBindTexture(int i1, int i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glBindTexture), i1, i2);
  }

  public void glBlendFunc(int i1, int i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glBlendFunc), i1, i2);
  }

  public void glBufferData(int i1, int i2, float[] f3, int i4) {
    if (x64)
      JFNative.invokeVoid(Pointer.nativeValue(api.glBufferData), i1, (long)i2, f3, i4);
    else
      JFNative.invokeVoid(Pointer.nativeValue(api.glBufferData), i1, i2, f3, i4);
  }

  public void glBufferData(int i1, int i2, short[] s3, int i4) {
    if (x64)
      JFNative.invokeVoid(Pointer.nativeValue(api.glBufferData), i1, (long)i2, s3, i4);
    else
      JFNative.invokeVoid(Pointer.nativeValue(api.glBufferData), i1, i2, s3, i4);
  }

  public void glBufferData(int i1, int i2, int[] i3, int i4) {
    if (x64)
      JFNative.invokeVoid(Pointer.nativeValue(api.glBufferData), i1, (long)i2, i3, i4);
    else
      JFNative.invokeVoid(Pointer.nativeValue(api.glBufferData), i1, i2, i3, i4);
  }

  public void glBufferData(int i1, int i2, byte[] i3, int i4) {
    if (x64)
      JFNative.invokeVoid(Pointer.nativeValue(api.glBufferData), i1, (long)i2, i3, i4);
    else
      JFNative.invokeVoid(Pointer.nativeValue(api.glBufferData), i1, i2, i3, i4);
  }

  public void glClear(int flags) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glClear), flags);
  }

  public void glClearColor(float r, float g, float b, float a) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glClearColor), r, g, b, a);
  }

  public void glColorMask(boolean r, boolean g, boolean b, boolean a) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glColorMask), r, g, b, a);
  }

  public void glCompileShader(int id) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glCompileShader), id);
  }

  public int glCreateProgram() {
    return JFNative.invokeInt(Pointer.nativeValue(api.glCreateProgram));
  }

  public int glCreateShader(int type) {
    return JFNative.invokeInt(Pointer.nativeValue(api.glCreateShader), type);
  }

  public void glCullFace(int id) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glCullFace), id);
  }

  public void glDeleteBuffers(int i1, int[] i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glDeleteBuffers), i1, i2);
  }

  public void glDeleteFramebuffers(int i1, int[] i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glDeleteFramebuffers), i1, i2);
  }

  public void glDeleteRenderbuffers(int i1, int[] i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glDeleteRenderbuffers), i1, i2);
  }

  public void glDeleteTextures(int i1, int[] i2, int i3) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glDeleteTextures), i1, i2, i3);
  }

  public void glDrawElements(int i1, int i2, int i3, int i4) {
    if (x64)
      JFNative.invokeVoid(Pointer.nativeValue(api.glDrawElements), i1, i2, i3, (long)i4);
    else
      JFNative.invokeVoid(Pointer.nativeValue(api.glDrawElements), i1, i2, i3, i4);
  }

  public void glDepthFunc(int i1) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glDepthFunc), i1);
  }

  public void glDisable(int id) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glDisable), id);
  }

  public void glDepthMask(boolean state) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glDepthMask), state);
  }

  public void glEnable(int id) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glEnable), id);
  }

  public void glEnableVertexAttribArray(int id) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glEnableVertexAttribArray), id);
  }

  public void glFlush() {
    JFNative.invokeVoid(Pointer.nativeValue(api.glFlush));
  }

  public void glFramebufferTexture2D(int i1, int i2, int i3, int i4, int i5) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glFramebufferTexture2D), i1, i2, i3, i4, i5);
  }

  public void glFramebufferRenderbuffer(int i1, int i2, int i3, int i4) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glFramebufferRenderbuffer), i1, i2, i3, i4);
  }

  public void glFrontFace(int id) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glFrontFace), id);
  }

  public int glGetAttribLocation(int i1, String str) {
    a2[0] = i1;
    a2[1] = str;
    return api.glGetAttribLocation.invokeInt(a2);
  }

  public int glGetError() {
    return JFNative.invokeInt(Pointer.nativeValue(api.glGetError));
  }

  public String glGetProgramInfoLog(int id) {
    IntByReference len = new IntByReference();
    Pointer lenptr = len.getPointer();
    len.setValue(1024);
    Pointer str = malloc(1024);
    if (x64)
      JFNative.invokeVoid(Pointer.nativeValue(api.glGetProgramInfoLog), id, 1024, Pointer.nativeValue(lenptr), Pointer.nativeValue(str));
    else
      JFNative.invokeVoid(Pointer.nativeValue(api.glGetProgramInfoLog), id, 1024, (int)Pointer.nativeValue(lenptr), (int)Pointer.nativeValue(str));
    String ret = str.getString(0);
    free(str);
    return ret;
  }

  public String glGetShaderInfoLog(int id) {
    IntByReference len = new IntByReference();
    Pointer lenptr = len.getPointer();
    len.setValue(1024);
    Pointer str = malloc(1024);
    if (x64)
      JFNative.invokeVoid(Pointer.nativeValue(api.glGetShaderInfoLog), id, 1024, Pointer.nativeValue(lenptr), Pointer.nativeValue(str));
    else
      JFNative.invokeVoid(Pointer.nativeValue(api.glGetShaderInfoLog), id, 1024, (int)Pointer.nativeValue(lenptr), (int)Pointer.nativeValue(str));
    String ret = str.getString(0);
    free(str);
    return ret;
  }

  public String glGetString(int type) {
    Pointer ptr;
    long peer;
    if (x64)
      peer = JFNative.invokeLong(Pointer.nativeValue(api.glGetString), type);
    else
      peer = JFNative.invokeInt(Pointer.nativeValue(api.glGetString), type);
    ptr = new Pointer(peer);
    return ptr.getString(0);
  }

  public void glGetIntegerv(int type, int[] i) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glGetIntegerv), type, i);
  }

  public void glGenBuffers(int i1, int[] i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glGenBuffers), i1, i2);
  }

  public void glGenFramebuffers(int i1, int[] i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glGenFramebuffers), i1, i2);
  }

  public void glGenRenderbuffers(int i1, int[] i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glGenRenderbuffers), i1, i2);
  }

  public void glGenTextures(int i1, int[] i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glGenTextures), i1, i2);
  }

  public int glGetUniformLocation(int i1, String str) {
    a2[0] = i1;
    a2[1] = str;
    return api.glGetUniformLocation.invokeInt(a2);
  }

  public void glLinkProgram(int id) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glLinkProgram), id);
  }

  public void glPixelStorei(int i1, int i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glPixelStorei), i1, i2);
  }

  public void glReadPixels(int i1, int i2, int i3, int i4, int i5, int i6, int[] px) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glReadPixels), i1, i2, i3, i4, i5, i6, px);
  }

  public void glRenderbufferStorage(int i1, int i2, int i3, int i4) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glRenderbufferStorage), i1, i2, i3, i4);
  }

  public int glShaderSource(int type, int count, String[] src, int[] src_lengths) {
    a4[0] = type;
    a4[1] = count;
    a4[2] = src;
    a4[3] = src_lengths;
    return api.glShaderSource.invokeInt(a4);
  }

  public int glStencilFunc(int func, int ref, int mask) {
    return JFNative.invokeInt(Pointer.nativeValue(api.glStencilFunc), func, ref, mask);
  }

  public int glStencilMask(int mask) {
    return JFNative.invokeInt(Pointer.nativeValue(api.glStencilMask), mask);
  }

  public int glStencilOp(int sfail, int dpfail, int dppass) {
    return JFNative.invokeInt(Pointer.nativeValue(api.glStencilOp), sfail, dpfail, dppass);
  }

  public void glTexImage2D(int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int[] px) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glTexImage2D), i1, i2, i3, i4, i5, i6, i7, i8, px);
  }

  public void glTexParameteri(int i1, int i2, int i3) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glTexParameteri), i1, i2, i3);
  }

  public void glUseProgram(int id) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glUseProgram), id);
  }

  public void glUniformMatrix4fv(int i1, int i2, int i3, float[] f4) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glUniformMatrix4fv), i1, i2, i3, f4);
  }

  public void glUniform3fv(int i1, int i2, float[] f3) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glUniform3fv), i1, i2, f3);
  }

  public void glUniform2fv(int i1, int i2, float[] f3) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glUniform2fv), i1, i2, f3);
  }

  public void glUniform1f(int i1, float f2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glUniform1f), i1, f2);
  }

  public void glUniform3iv(int i1, int i2, int[] i3) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glUniform3iv), i1, i2, i3);
  }

  public void glUniform2iv(int i1, int i2, int[] i3) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glUniform2iv), i1, i2, i3);
  }

  public void glUniform1i(int i1, int i2) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glUniform1i), i1, i2);
  }

  public void glVertexAttribPointer(int i1, int i2, int i3, int i4, int i5, int i6) {
    if (x64)
      JFNative.invokeVoid(Pointer.nativeValue(api.glVertexAttribPointer), i1, i2, i3, i4, i5, (long)i6);
    else
      JFNative.invokeVoid(Pointer.nativeValue(api.glVertexAttribPointer), i1, i2, i3, i4, i5, i6);
  }

  public void glViewport(int x, int y, int w, int h) {
    JFNative.invokeVoid(Pointer.nativeValue(api.glViewport), x, y, w, h);
  }
}
