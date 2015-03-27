package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IOleInPlaceObject extends IOleWindow {
  public static class ByReference extends IOleInPlaceObject implements Structure.ByReference {}
  public IOleInPlaceObject() {}
  public IOleInPlaceObject(Pointer pvInstance) {
    super(pvInstance);
  }
  //5 = InPlaceDeactivate
  //6 = UIDeactivate
  //7 = SetObjectRects
  public int SetObjectRects(RECT pos, RECT clip) {
    return invokeInt(7, new Object[] {getPointer(), pos, clip});
  }
  //8 = ReactivateAndUndo
}
