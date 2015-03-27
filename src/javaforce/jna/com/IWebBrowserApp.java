package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IWebBrowserApp extends IWebBrowser {
  public static class ByReference extends IWebBrowserApp implements Structure.ByReference {}
  public IWebBrowserApp() {}
  public IWebBrowserApp(Pointer pvInstance) {
    super(pvInstance);
  }
  //32 = Quit
  //33 = ClientToWindow
  //34 = PutProperty
  //35 = GetProperty
  //36 = get_Name
  //37 = get_HWND
  //38 = get_FullName
  //39 = get_Path
  //40 = get_Visible
  //41 = put_Visible
  public int put_Visible(boolean state) {
    return invokeInt(41, new Object[] {getPointer(), state});
  }
  //42 = get_StatusBar
  //43 = put_StatusBar
  //44 = get_StatusText
  //45 = put_StatusText
  //46 = get_ToolBar
  //47 = put_ToolBar
  //48 = get_MenuBar
  //49 = put_MenuBar
  //50 = get_FullScreen
  //51 = put_FullScreen
}
