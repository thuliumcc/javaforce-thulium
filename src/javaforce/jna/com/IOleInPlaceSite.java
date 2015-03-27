package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IOleInPlaceSite extends IOleWindow {
  public static class ByReference extends IOleInPlaceSite implements Structure.ByReference {}
  public IOleInPlaceSite() {}
  public IOleInPlaceSite(Pointer pvInstance) {
    super(pvInstance);
  }
  //5 = CanInPlaceActivate
  //6 = OnInPlaceActivate
  //7 = OnUIActivate
  //8 = GetWindowContext
  //9 = Scroll
  //10 = OnUIDeactivate
  //11 = OnInPlaceDeactivate
  //12 = DiscardUndoState
  //13 = DeactivateAndUndo
  //14 = OnPosRectChange
}
