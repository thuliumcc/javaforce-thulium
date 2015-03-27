package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IMarshal extends IUnknown {
  public static class ByReference extends IMarshal implements Structure.ByReference {}
  public IMarshal() {}
  public IMarshal(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = GetUnmarshalClass
  //4 = GetMarshalSizeMax
  //5 = MarshalInterface
  //6 = UnmarshalInterface
  //7 = ReleaseMarshalData
  //8 = DisconnectObject
}
