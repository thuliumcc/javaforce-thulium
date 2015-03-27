package javaforce.jna;

/**
 * Windows Window - for creating Child windows only
 *  (used for ActiveX child windows)
 *
 * @author pquiring
 *
 * Created : Jan 16, 2014
 */

import java.lang.reflect.*;
import java.util.*;

import com.sun.jna.*;
import com.sun.jna.win32.*;

import javaforce.*;
import javaforce.jna.com.*;

public class WinWindow {
  private static Pointer malloc(int size) {
    return new Pointer(Native.malloc(size));
  }

  private static void free(Pointer ptr) {
    Native.free(Pointer.nativeValue(ptr));
  }

  public interface User extends StdCallLibrary {
    public int RegisterClassExA(WINCLASSEX wc);
    public Pointer CreateWindowExA(
      int styleEx, String className, String winName
      ,int style, int x, int y, int width, int height
      ,Pointer parent, Pointer menu, Pointer hInstance, Pointer lParam
    );
    public boolean ShowWindow(Pointer hwnd, int cmd);
    public int DefWindowProcA(Pointer hwnd, int msg, Pointer lParam, Pointer wParam);
    public void SetWindowPos(Pointer hwnd, Pointer pos, int x,int y,int cx,int cy, int flags);
    public boolean GetMessageA(MSG msg, Pointer hwnd, int min, int max);
    public void TranslateMessage(MSG msg);
    public void DispatchMessageA(MSG msg);
    public boolean PostMessageA(Pointer hwnd, int msg, Pointer wParam, Pointer lParam);
    public boolean GetClientRect(Pointer hwnd, RECT rect);
    public Pointer GetParent(Pointer hwnd);
    public boolean DestroyWindow(Pointer hwnd);
  }

  public interface Kernel extends StdCallLibrary {
    public Pointer GetModuleHandleA(String name);
    public int GetLastError();
  }

  public static User user;
  public static Kernel kernel;

  public static boolean init() {
    if (user != null) return true;
    try {
      user = (User)Native.loadLibrary("user32", User.class);
      kernel = (Kernel)Native.loadLibrary("kernel32", Kernel.class);
      return true;
    } catch (Throwable t) {
      JFLog.log(t);
      return false;
    }
  }

  private StdCallLibrary.StdCallCallback wpcb;
  private Pointer wp, hwnd;

  private int cnt;

  public static Pointer HWND_MESSAGE = new Pointer(-3);

  public Pointer create(Pointer parent_hwnd, boolean visible) {
    if (hwnd != null) return hwnd;

    wpcb = new StdCallLibrary.StdCallCallback() {
      public int callback(Pointer hwnd, int msg, Pointer wParam, Pointer lParam) {
        int ret = user.DefWindowProcA(hwnd, msg, wParam, lParam);
        return ret;
      }
    };

    wp = InterfaceBuilder.callbackToPointer(wpcb);

    String clsName = "JFWindowClass_" + cnt++;

    Pointer hInstance = kernel.GetModuleHandleA(null);

    WINCLASSEX cls = new WINCLASSEX();
    cls.cbSize = cls.size();
    cls.lpszClassName = clsName;
    cls.wndProc = wp;
    cls.hInstance = hInstance;

    int atom = user.RegisterClassExA(cls);

    int style = 0x40000000;  //WS_CHILD
    if (visible) style |= 0x10000000;  //WS_VISIBLE
    hwnd = user.CreateWindowExA(0, clsName, "Window"
      , style
      , 0, 0, 1, 1
      , parent_hwnd, null, hInstance, null);

    return hwnd;
  }

  public void setPos(int x,int y,int width,int height) {
    user.SetWindowPos(hwnd, null, x, y, width, height
      , 0x0004 | 0x0010 | 0x0040 | 0x0002);
    //SWP_NOZORDER|SWP_NOACTIVATE|SWP_SHOWWINDOW|SWP_NOMOVE
  }

  public void show() {
    user.ShowWindow(hwnd, 9);  //9=SW_RESTORE
  }

  public void dispose() {
    if (hwnd != null) {
      user.DestroyWindow(hwnd);
      hwnd = null;
    }
  }

  public void processMessage() {
    MSG msg = new MSG();
    user.GetMessageA(msg, null, 0, 0);
    user.TranslateMessage(msg);
    user.DispatchMessageA(msg);
  }

  public void PostMessage() {
    user.PostMessageA(hwnd, 0x400, null, null);  //0x400 = WM_USER
  }

  private static List makeFieldList(Class cls) {
    //This "assumes" compiler places fields in order as defined (some don't)
    ArrayList<String> list = new ArrayList<String>();
    Field fields[] = cls.getFields();
    for(int a=0;a<fields.length;a++) {
      String name = fields[a].getName();
      if (name.startsWith("ALIGN_")) continue;  //field of Structure
      list.add(name);
    }
    return list;
  }
}
