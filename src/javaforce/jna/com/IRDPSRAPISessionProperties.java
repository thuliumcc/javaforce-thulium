package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class IRDPSRAPISessionProperties extends IDispatch {
  public static class ByReference extends IRDPSRAPISessionProperties implements Structure.ByReference {}
  public IRDPSRAPISessionProperties() {}
  public IRDPSRAPISessionProperties(Pointer pvInstance) {
    super(pvInstance);
  }
  //7 = get_Property
  public int get_Property(Pointer bstr_name, VARIANT variant) {
    return invokeInt(7, new Object[] {getPointer(), bstr_name, variant});
  }
  //8 = put_Property
  public int put_Property(Pointer bstr_name, VARIANT.ByValue variant) {
    return invokeInt(8, new Object[] {getPointer(), bstr_name, variant});
  }
}
