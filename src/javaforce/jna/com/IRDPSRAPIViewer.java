package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

public class IRDPSRAPIViewer extends IDispatch {
  public static class ByReference extends IRDPSRAPIViewer implements Structure.ByReference {}
  public IRDPSRAPIViewer() {}
  public IRDPSRAPIViewer(Pointer pvInstance) {
    super(pvInstance);
  }
  //7 = Connect
  public int Connect(Pointer connString, Pointer name, Pointer password) {
    return invokeInt(7, new Object[] {getPointer(), connString, name, password});
  }
  //8 = Disconnect
  //9 = get_Attendees
  //10 = get_Invitations
  //11 = get_ApplicationFilter
  //12 = get_VirtualChannelManager
  //13 = put_SmartSizing
  //14 = get_SmartSizing
  //15 = RequestControl
  //16 = put_DisconnectedText
  //17 = get_DisconnectedText
  //18 = RequestColorDepthChange
  //19 = get_Properties
  //20 = StartReverseConnectListener
}
