package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class IOleWindow extends IUnknown {
  public static class ByReference extends IOleWindow implements Structure.ByReference {}
  public IOleWindow() {}
  public IOleWindow(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = GetWindow
  public int GetWindow(PointerByReference hwnd) {
    return invokeInt(3, new Object[] {getPointer(), hwnd});
  }
  //4 = ContextSensitiveHelp
}
