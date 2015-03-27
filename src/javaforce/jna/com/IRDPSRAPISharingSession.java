package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 * See : https://msdn.microsoft.com/en-us/library/aa373307(v=vs.85).aspx
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class IRDPSRAPISharingSession extends IDispatch {
  public static class ByReference extends IRDPSRAPISharingSession implements Structure.ByReference {}
  public IRDPSRAPISharingSession() {}
  public IRDPSRAPISharingSession(Pointer pvInstance) {
    super(pvInstance);
  }
  //7 = Open
  public int Open() {
    return invokeInt(7, new Object[] {getPointer()});
  }
  //8 = Close
  public int Close() {
    return invokeInt(8, new Object[] {getPointer()});
  }
  //9 = put_ColorDepth
  //10 = get_ColorDepth
  public int get_ColorDepth(IntByReference depth) {
    return invokeInt(10, new Object[] {getPointer(), depth});
  }
  //11 = get_Properties
  public int get_Properties(PointerByReference iRDPSRAPISessionProperties) {
    return invokeInt(11, new Object[] {getPointer(), iRDPSRAPISessionProperties});
  }
  //12 = get_Attendees
  //13 = get_Invitations
  public int get_Invitations(PointerByReference ref) {
    return invokeInt(13, new Object[] {getPointer(), ref});
  }
  //14 = get_ApplicationFilter
  //15 = get_VirtualChannelManager
  //16 = Pause
  //17 = Resume
  //18 = ConnectToClient
  //19 = SetDesktopSharedRect
  //20 = GetDesktopSharedRect
  public int GetDesktopSharedRect(IntByReference left, IntByReference top, IntByReference right, IntByReference bottom) {
    return invokeInt(20, new Object[] {getPointer(), left, top, right, bottom});
  }
}
