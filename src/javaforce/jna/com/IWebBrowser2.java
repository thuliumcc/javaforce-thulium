package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IWebBrowser2 extends IWebBrowserApp {
  public static class ByReference extends IWebBrowser2 implements Structure.ByReference {}
  public IWebBrowser2() {}
  public IWebBrowser2(Pointer pvInstance) {
    super(pvInstance);
  }
  //52 = Navigate2
  //53 = QueryStatusWB
  //54 = ExecWB
  //55 = ShowBrowserBar
  //56 = get_ReadyState
  //57 = get_Offline
  //58 = put_Offline
  //59 = get_Silent
  //60 = put_Silent
  //61 = get_RegisterAsBrowser
  //62 = put_RegisterAsBrowser
  //63 = get_RegisterAsDropTarget
  //64 = put_RegisterAsDropTarget
  //65 = get_TheaterMode
  //66 = put_TheaterMode
  //67 = get_AddressBar
  //68 = put_AddressBar
  //69 = get_Resizable
  //70 = put_Resizable
}
