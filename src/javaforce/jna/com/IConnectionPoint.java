package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class IConnectionPoint extends IUnknown {
  public static class ByReference extends IConnectionPoint implements Structure.ByReference {}
  public IConnectionPoint() {}
  public IConnectionPoint(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = GetConnectionInterface
  //4 = GetConnectionPointContainer
  //5 = Advise
  public int Advise(Pointer iUnknown, IntByReference tokenRef) {
    return invokeInt(5, new Object[] {getPointer(), iUnknown, tokenRef});
  }
  //6 = Unadvise
  public int Unadvise(int token) {
    return invokeInt(6, new Object[] {getPointer(), token});
  }
  //7 = EnumConnections
}
