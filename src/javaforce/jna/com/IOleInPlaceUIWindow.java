package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IOleInPlaceUIWindow extends IOleWindow {
  public static class ByReference extends IOleInPlaceUIWindow implements Structure.ByReference {}
  public IOleInPlaceUIWindow() {}
  public IOleInPlaceUIWindow(Pointer pvInstance) {
    super(pvInstance);
  }
  //5 = GetBorder
  //6 = RequestBorderSpace
  //7 = SetBorderSpace
  //8 = SetActiveObject
}
