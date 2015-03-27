package javaforce.jna.com;

import com.sun.jna.*;
import com.sun.jna.ptr.*;

/**
 *
 * @author pquiring
 */
public class IEnumMoniker extends IUnknown {
  public static class ByReference extends IEnumMoniker implements Structure.ByReference {}
  public IEnumMoniker() {}
  public IEnumMoniker(Pointer pvInstance) {
    super(pvInstance);
  }
  //4 methods (3-6)
  public int Next(int celt, PointerByReference rgelt, IntByReference pint) {
    return invokeInt(3, new Object[] { getPointer(), celt, rgelt, pint });
  }
  public int Skip(int celt) {
    return invokeInt(4, new Object[] { getPointer(), celt });
  }
  public int Reset() {
    return invokeInt(5, new Object[] { getPointer() });
  }
  public int Clone(PointerByReference ienummoniker) {
    return invokeInt(6, new Object[] { getPointer(), ienummoniker });
  }
}
