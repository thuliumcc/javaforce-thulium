package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class IRDPSRAPIInvitationManager extends IDispatch {
  public static class ByReference extends IRDPSRAPIInvitationManager implements Structure.ByReference {}
  public IRDPSRAPIInvitationManager() {}
  public IRDPSRAPIInvitationManager(Pointer pvInstance) {
    super(pvInstance);
  }
  //7 = get__NewEnum
  //8 = get_Item
  //9 = get_Count
  //10 = CreateInvitation
  public IRDPSRAPIInvitation CreateInvitation(Pointer authString, Pointer groupName, Pointer password, int attendLimit) {
    PointerByReference ref = new PointerByReference();
    int res = invokeInt(10, new Object[] {getPointer(), authString, groupName, password, attendLimit, ref});
    if (res != 0) {
      System.out.println("IRDPSRAPIInvitationManager.CreateInvitation() = " + Long.toString(res & 0xffffffffL, 16));
      return null;
    }
    return new IRDPSRAPIInvitation(ref.getValue());
  }
/*
  public IRDPSRAPIInvitation CreateInvitation(String authString, String groupName, String password, int attendLimit) {
    PointerByReference ref = new PointerByReference();
    int res = invokeInt(10, new Object[] {getPointer(), new BSTR(authString).getPointer(), new BSTR(groupName).getPointer(), new BSTR(password).getPointer(), attendLimit, ref});
    if (res != 0) {
      System.out.println("IRDPSRAPIInvitationManager.CreateInvitation() = " + Long.toString(res & 0xffffffffL, 16));
      return null;
    }
    return new IRDPSRAPIInvitation(ref.getValue());
  }*/
}
