package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 * Created : Aug 19, 2013
 */

import com.sun.jna.*;

public class IFilterGraph extends IUnknown {
  public static class ByReference extends IFilterGraph implements Structure.ByReference {}
  public IFilterGraph() {}
  public IFilterGraph(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = AddFilter
  public int AddFilter(Pointer iBaseFilter, WString pName) {
    return invokeInt(3, new Object[] { getPointer(), iBaseFilter, pName });
  }
  //4 = RemoveFilter
  //5 = EnumFilters
  //6 = FindFilterByName
  //7 = ConnectDirect
  //8 = Reconnect
  //9 = Disconnect
  //10 = SetDefaultSyncSource
}
