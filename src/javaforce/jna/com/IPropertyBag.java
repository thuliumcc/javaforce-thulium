package javaforce.jna.com;

import com.sun.jna.*;

/**
 *
 * @author pquiring
 */
public class IPropertyBag extends IUnknown {
  public static class ByReference extends IPropertyBag implements Structure.ByReference {}
  public IPropertyBag() {}
  public IPropertyBag(Pointer pvInstance) {
    super(pvInstance);
  }
  public int Read(WString name, VARIANT var, Pointer iErrorLog) {
    return invokeInt(3, new Object[] { getPointer(), name, var, iErrorLog });
  }
  public int Write(WString name, VARIANT var) {
    return invokeInt(4, new Object[] { getPointer(), name, var });
  }
}
