package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IParseDisplayName extends IUnknown {
  public static class ByReference extends IParseDisplayName implements Structure.ByReference {}
  public IParseDisplayName() {}
  public IParseDisplayName(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = ParseDisplayName
}
