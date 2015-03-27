package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 * Created : Aug 19, 2013
 */

import com.sun.jna.*;

public class IOleClientSite extends IUnknown {
  public static class ByReference extends IOleClientSite implements Structure.ByReference {}
  public IOleClientSite() {}
  public IOleClientSite(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = SaveObject
  //4 = GetMoniker
  //5 = GetContainer
  //6 = ShowObject
  //7 = OnShowWindow
  //8 = RequestNewObjectLayout
}
