package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IOleInPlaceFrame extends IOleInPlaceUIWindow {
  public static class ByReference extends IOleInPlaceFrame implements Structure.ByReference {}
  public IOleInPlaceFrame() {}
  public IOleInPlaceFrame(Pointer pvInstance) {
    super(pvInstance);
  }
  //9 = InsertMenus
  //10 = SetMenu
  //11 = RemoveMenus
  //12 = SetStatusText
  //13 = EnableModeless
  //14 = TranslateAccelerator
}
