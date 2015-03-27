package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IDocHostUIHandler extends IUnknown {
  public static class ByReference extends IDocHostUIHandler implements Structure.ByReference {}
  public IDocHostUIHandler() {}
  public IDocHostUIHandler(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = ShowContextMenu
  //4 = GetHostInfo
  //5 = ShowUI
  //6 = HideUI
  //7 = UpdateUI
  //8 = EnableModeless
  //9 = OnDocWindowActivate
  //10 = OnFrameWindowActivate
  //11 = ResizeBorder
  //12 = TranslateAccelerator
  //13 = GetOptionKeyPath
  //14 = GetDropTarget
  //15 = GetExternal
  //16 = TranslateUrl
  //17 = FilterDataObject
}
