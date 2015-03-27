package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IRDPSRAPIAttendee extends IDispatch {
  public static class ByReference extends IRDPSRAPIAttendee implements Structure.ByReference {}
  public IRDPSRAPIAttendee() {}
  public IRDPSRAPIAttendee(Pointer pvInstance) {
    super(pvInstance);
  }
  //7 = get_Id
  //8 = get_RemoteName
  //9 = get_ControlLevel
  //10 = put_ControlLevel
  public int put_ControlLevel(int lvl) {
    return invokeInt(10, new Object[] {getPointer(), lvl});
  }
  //11 = get_Invitation
  //12 = TerminateConnection
  //13 = get_Flags
  //14 = get_ConnectivityInfo
}
