package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IOleContainer extends IParseDisplayName {
  public static class ByReference extends IOleContainer implements Structure.ByReference {}
  public IOleContainer() {}
  public IOleContainer(Pointer pvInstance) {
    super(pvInstance);
  }
  //4 = EnumObjects
  //5 = LockContainer
}
