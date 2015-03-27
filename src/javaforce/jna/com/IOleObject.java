package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class IOleObject extends IUnknown {
  public static class ByReference extends IOleObject implements Structure.ByReference {}
  public IOleObject() {}
  public IOleObject(Pointer pvInstance) {
    super(pvInstance);
  }
  //3 = SetClientSite
  public int SetClientSite(Pointer iOleClientSite) {
    return invokeInt(3, new Object[] {getPointer(), iOleClientSite});
  }
  //4 = GetClientSite
  //5 = SetHostNames
  public int SetHostNames(WString szContainerApp, WString szContainerObj) {
    return invokeInt(5, new Object[] {getPointer(), szContainerApp, szContainerObj});
  }
  //6 = Close
  public int Close(int saveOption) {
    return invokeInt(6, new Object[] {getPointer(), saveOption});
  }
  //7 = SetMoniker
  //8 = GetMoniker
  //9 = InitFromData
  //10 = GetClipboardData
  //11 = DoVerb
  public int DoVerb(int verb, Pointer lpmsg, Pointer iOleClientSite, int index, Pointer hwnd, RECT rect) {
    return invokeInt(11, new Object[] {getPointer(), verb, lpmsg, iOleClientSite, index, hwnd, rect});
  }
  //12 = EnumVerbs
  //13 = Update
  //14 = IsUpToDate
  //15 = GetUserClassID
  //16 = GetUserType
  //17 = SetExtent
  //18 = GetExtent
  //19 = Advise
  public int Advise(IAdviseSink sink, IntByReference token) {
    return invokeInt(19, new Object[] {getPointer(), sink.getPointer(), token});
  }
  //20 = Unadvise
  public int Unadvise(int token) {
    return invokeInt(20, new Object[] {getPointer(), token});
  }
  //21 = EnumAdvise
  //22 = GetMiscStatus
  //23 = SetColorScheme

  //DoVerb verb's
  public final static int OLEIVERB_PRIMARY = 0;
  public final static int OLEIVERB_SHOW = -1;
  public final static int OLEIVERB_OPEN = -2;
  public final static int OLEIVERB_HIDE = -3;
  public final static int OLEIVERB_UIACTIVATE = -4;
  public final static int OLEIVERB_INPLACEACTIVATE = -5;
  public final static int OLEIVERB_DISCARDUNDOSTATE = -6;

  public final static int OLECLOSE_SAVEIFDIRTY = 0;
}
