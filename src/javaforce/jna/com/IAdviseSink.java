package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IAdviseSink extends IUnknown {
  public static class ByReference extends IAdviseSink implements Structure.ByReference {}
  public IAdviseSink() {}
  public IAdviseSink(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = ???
}
