package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class IConnectionPointContainer extends IUnknown {
  public static class ByReference extends IConnectionPointContainer implements Structure.ByReference {}
  public IConnectionPointContainer() {}
  public IConnectionPointContainer(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = EnumConnectionPoints
  //4 = FindConnectionPoint
  public int FindConnectionPoint(Guid.IID riid, PointerByReference ref) {
    return invokeInt(4, new Object[] {getPointer(), riid, ref});
  }
}
