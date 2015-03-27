package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IWebBrowser extends IDispatch {
  public static class ByReference extends IWebBrowser implements Structure.ByReference {}
  public IWebBrowser() {}
  public IWebBrowser(Pointer pvInstance) {
    super(pvInstance);
  }
  //7 = GoBack
  //8 = GoForward
  //9 = GoHome
  public int GoHome() {
    return invokeInt(9, new Object[] {getPointer()});
  }
  //10 = GoSearch
  //11 = Navigate
  public int Navigate(Pointer bstr_url) {
    return invokeInt(11, new Object[] {getPointer(), bstr_url, null, null, null, null});
  }
  //12 = Refresh
  //13 = Refresh2
  //14 = Stop
  //15 = get_Application
  //16 = get_Parent
  //17 = get_Container
  //18 = get_Document
  //19 = get_TopLevelContainer
  //20 = get_Type
  //21 = get_Left
  //22 = put_Left
  public int put_Left(int pos) {
    return invokeInt(22, new Object[] {getPointer(), pos});
  }
  //23 = get_Top
  //24 = put_Top
  public int put_Top(int pos) {
    return invokeInt(24, new Object[] {getPointer(), pos});
  }
  //25 = get_Width
  //26 = put_Width
  public int put_Width(int pos) {
    return invokeInt(26, new Object[] {getPointer(), pos});
  }
  //27 = get_Height
  //28 = put_Height
  public int put_Height(int pos) {
    return invokeInt(28, new Object[] {getPointer(), pos});
  }
  //29 = get_LocationName
  //30 = get_LocationURL
  //31 = get_Busy
}
