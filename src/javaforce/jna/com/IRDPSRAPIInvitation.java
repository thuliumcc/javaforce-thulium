package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class IRDPSRAPIInvitation extends IDispatch {
  public static class ByReference extends IRDPSRAPIInvitation implements Structure.ByReference {}
  public IRDPSRAPIInvitation() {}
  public IRDPSRAPIInvitation(Pointer pvInstance) {
    super(pvInstance);
  }
  //7 = get_ConnectionString
  public String get_ConnectionString() {
    PointerByReference ref = new PointerByReference();
    int res = invokeInt(7, new Object[] {getPointer(), ref});
    if (res != 0) return null;
    return ref.getValue().getWideString(0);
  }
  //8 = get_GroupName
  //9 = get_Password
  //10 = get_AttendeeLimit
  //11 = put_AttendeeLimit
  //12 = get_Revoked
  //13 = put_Revoked
}
