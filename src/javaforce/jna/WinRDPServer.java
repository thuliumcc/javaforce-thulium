package javaforce.jna;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

import javaforce.*;
import javaforce.jna.com.*;

public class WinRDPServer {
  private static Ole ole;
  private static OleAut oleaut;

  public static interface RDPListener {
    public void onAttendeeConnect();
    public void onAttendeeDisconnect();
  }

  private RDPListener listener;

  private static Pointer malloc(int size) {
    return new Pointer(Native.malloc(size));
  }

  private static void free(Pointer ptr) {
    Native.free(Pointer.nativeValue(ptr));
  }

  private boolean init() {
    try {
      if (ole == null) {
        ole = (Ole)Native.loadLibrary("ole32", Ole.class);
        if (ole == null) return false;
      }
      if (oleaut == null) {
        oleaut = (OleAut)Native.loadLibrary("oleaut32", OleAut.class);
        if (oleaut == null) return false;
      }
      return comInit();
    } catch (Throwable t) {
      JFLog.log(t);
    }
    return false;
  }

  private static boolean uninit() {
    return comUnInit();
  }

  private static boolean comInit() {
    int res = ole.CoInitializeEx(null, ole.COINIT_APARTMENTTHREADED);
    if (res != HRESULT.S_OK && res != HRESULT.S_FALSE) return false;
    return true;
  }
  private static boolean comUnInit() {
    ole.CoUninitialize();
    return true;
  }

  private Pointer bstr_alloc(String str) {
    return oleaut.SysAllocString(new WString(str));
  }

  private void bstr_free(Pointer bstr) {
    oleaut.SysFreeString(bstr);
  }

  private void getPort() throws Exception {
    int res;
    Pointer bstr_port = bstr_alloc("PortId");
    VARIANT gPort = new VARIANT();
    res = rdpProps.get_Property(bstr_port, gPort);
    System.out.println("port=" + gPort.data.i32);
    bstr_free(bstr_port);
    if (res != 0) throw new Exception("get_Property() failed");

    Pointer bstr_protocol = bstr_alloc("PortProtocol");
    res = rdpProps.get_Property(bstr_protocol, gPort);
    System.out.println("protcol=" + gPort.data.i32);
    bstr_free(bstr_protocol);
    if (res != 0) throw new Exception("put_Property() failed");
  }

  /** This is not accepted ??? */
  private void setPort(int port) throws Exception {
    int res;
    Pointer bstr_port = bstr_alloc("PortId");
    VARIANT.ByValue vPort = new VARIANT.ByValue();
    vPort.vt = VARENUM.VT_I4;
    vPort.data.i32 = port;
    vPort.write();
    res = rdpProps.put_Property(bstr_port, vPort);
    bstr_free(bstr_port);
    if (res != 0) throw new Exception("put_Property() failed");

    Pointer bstr_protocol = bstr_alloc("PortProtocol");
    VARIANT.ByValue vProtocol = new VARIANT.ByValue();
    vProtocol.vt = VARENUM.VT_I4;
    vProtocol.data.i32 = 2;  //2=AF_INET (23=AF_INET6)
    vProtocol.write();
    res = rdpProps.put_Property(bstr_protocol, vProtocol);
    bstr_free(bstr_protocol);
    if (res != 0) throw new Exception("put_Property() failed");
  }

  private static final int DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_CONNECTED = 301;
  private static final int DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_DISCONNECTED = 302;
  private static final int DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_UPDATE = 303;
  private static final int DISPID_RDPSRAPI_EVENT_ON_ERROR = 304;
  private static final int DISPID_RDPSRAPI_EVENT_ON_VIEWER_CONNECTED = 305;
  private static final int DISPID_RDPSRAPI_EVENT_ON_VIEWER_DISCONNECTED = 306;
  private static final int DISPID_RDPSRAPI_EVENT_ON_VIEWER_AUTHENTICATED = 307;
  private static final int DISPID_RDPSRAPI_EVENT_ON_VIEWER_CONNECTFAILED = 308;
  private static final int DISPID_RDPSRAPI_EVENT_ON_CTRLLEVEL_CHANGE_REQUEST = 309;
  private static final int DISPID_RDPSRAPI_EVENT_ON_GRAPHICS_STREAM_PAUSED = 310;
  private static final int DISPID_RDPSRAPI_EVENT_ON_GRAPHICS_STREAM_RESUMED = 311;
  private static final int DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_JOIN = 312;
  private static final int DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_LEAVE = 313;
  private static final int DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_DATARECEIVED = 314;
  private static final int DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_SENDCOMPLETED = 315;
  private static final int DISPID_RDPSRAPI_EVENT_ON_APPLICATION_OPEN = 316;
  private static final int DISPID_RDPSRAPI_EVENT_ON_APPLICATION_CLOSE = 317;
  private static final int DISPID_RDPSRAPI_EVENT_ON_APPLICATION_UPDATE = 318;
  private static final int DISPID_RDPSRAPI_EVENT_ON_WINDOW_OPEN = 319;
  private static final int DISPID_RDPSRAPI_EVENT_ON_WINDOW_CLOSE = 320;
  private static final int DISPID_RDPSRAPI_EVENT_ON_WINDOW_UPDATE = 321;
  private static final int DISPID_RDPSRAPI_EVENT_ON_APPFILTER_UPDATE = 322;
  private static final int DISPID_RDPSRAPI_EVENT_ON_SHARED_RECT_CHANGED = 323;
  private static final int DISPID_RDPSRAPI_EVENT_ON_FOCUSRELEASED = 324;
  private static final int DISPID_RDPSRAPI_EVENT_ON_SHARED_DESKTOP_SETTINGS_CHANGED = 325;

  private String GetRDPSessionEventString(int id)
  {
    switch (id)
    {
      case DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_CONNECTED:
        return "OnAttendeeConnected";
      case DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_DISCONNECTED:
        return "OnAttendeeDisconnected";
      case DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_UPDATE:
        return "OnAttendeeUpdate";
      case DISPID_RDPSRAPI_EVENT_ON_ERROR:
        return "OnError";
      case DISPID_RDPSRAPI_EVENT_ON_VIEWER_CONNECTED:
        return "OnConnectionEstablished";
      case DISPID_RDPSRAPI_EVENT_ON_VIEWER_DISCONNECTED:
        return "OnConnectionTerminated";
      case DISPID_RDPSRAPI_EVENT_ON_VIEWER_AUTHENTICATED:
        return "OnConnectionAuthenticated";
      case DISPID_RDPSRAPI_EVENT_ON_VIEWER_CONNECTFAILED:
        return "OnConnectionFailed";
      case DISPID_RDPSRAPI_EVENT_ON_CTRLLEVEL_CHANGE_REQUEST:
        return "OnControlLevelChangeRequest";
      case DISPID_RDPSRAPI_EVENT_ON_GRAPHICS_STREAM_PAUSED:
        return "OnGraphicsStreamPaused";
      case DISPID_RDPSRAPI_EVENT_ON_GRAPHICS_STREAM_RESUMED:
        return "OnGraphicsStreamResumed";
      case DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_JOIN:
        return "OnChannelJoin";
      case DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_LEAVE:
        return "OnChannelLeave";
      case DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_DATARECEIVED:
        return "OnChannelDataReceived";
      case DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_SENDCOMPLETED:
        return "OnChannelDataSent";
      case DISPID_RDPSRAPI_EVENT_ON_APPLICATION_OPEN:
        return "OnApplicationOpen";
      case DISPID_RDPSRAPI_EVENT_ON_APPLICATION_CLOSE:
        return "OnApplicationClose";
      case DISPID_RDPSRAPI_EVENT_ON_APPLICATION_UPDATE:
        return "OnApplicationUpdate";
      case DISPID_RDPSRAPI_EVENT_ON_WINDOW_OPEN:
        return "OnWindowOpen";
      case DISPID_RDPSRAPI_EVENT_ON_WINDOW_CLOSE:
        return "OnWindowClose";
      case DISPID_RDPSRAPI_EVENT_ON_WINDOW_UPDATE:
        return "OnWindowUpdate";
      case DISPID_RDPSRAPI_EVENT_ON_APPFILTER_UPDATE:
        return "OnAppFilterUpdate";
      case DISPID_RDPSRAPI_EVENT_ON_SHARED_RECT_CHANGED:
        return "OnSharedRectChanged";
      case DISPID_RDPSRAPI_EVENT_ON_FOCUSRELEASED:
        return "OnFocusReleased";
      case DISPID_RDPSRAPI_EVENT_ON_SHARED_DESKTOP_SETTINGS_CHANGED:
        return "OnSharedDesktopSettingsChanged";
  //    case DISPID_RDPAPI_EVENT_ON_BOUNDING_RECT_CHANGED:
  //      return "OnViewingSizeChanged";
    }
    return "OnUnknown";
  }

  public void setListener(RDPListener listener) {
    this.listener = listener;
  }

  public boolean start(String auth, String group, String pass, int numAttend, int port) {
    this.auth = auth;
    this.group = group;
    this.pass = pass;
    this.numAttend = numAttend;
    this.port = port;

    new Thread() {
      public void run() {
        start2();
      }
    }.start();
    return true;
  }

	private static final int CTRL_LEVEL_INVALID = 0;
	private static final int CTRL_LEVEL_NONE = 1;
	private static final int CTRL_LEVEL_VIEW = 2;
	private static final int CTRL_LEVEL_INTERACTIVE = 3;

  private void start2() {
    try {
      PointerByReference ref = new PointerByReference();

      if (!init()) throw new Exception("OleInit failed");

      if (!WinWindow.init()) throw new Exception("WinWindow.init() failed");
      ww = new WinWindow();
      ww.create(WinWindow.HWND_MESSAGE, false);

      qicb = new Callback() {
        //QueryInterface
        public int callback(Pointer _this, Pointer _refiid, PointerByReference ppvObject) {
          if (ppvObject == null) {
            return HRESULT.E_POINTER;
          }
          Guid.GUID refiid = new Guid.GUID(_refiid);
//          System.out.println("QI:refiid=" + refiid + ",ppv=" + ppvObject);
          if (refiid.equals(Guid.IID_IUnknown)) {
//            System.out.println(" returning IUnknown");
            ppvObject.setValue(myDispatch.getPointer());
            myDispatch.AddRef();
            return HRESULT.S_OK;
          }

          if (refiid.equals(Guid.IID_IDispatch)) {
//            System.out.println(" returning IDispatch");
            ppvObject.setValue(myDispatch.getPointer());
            myDispatch.AddRef();
            return HRESULT.S_OK;
          }

          if (refiid.equals(Guid.DIID__IRDPSessionEvents)) {
//            System.out.println(" returning DIID__IRDPSessionEvents");
            ppvObject.setValue(myDispatch.getPointer());
            myDispatch.AddRef();
            return HRESULT.S_OK;
          }

          String iface = Guid.find(refiid);
          System.out.println("QueryInterface:Unknown IID=" + iface);
          ppvObject.setValue(null);
          return HRESULT.E_NOINTERFACE;
        }
      };

      qi = InterfaceBuilder.callbackToPointer(qicb);

      myDispatch = new InterfaceBuilder();
      myDispatch.alloc(7, "IDispatch");
      myDispatch.setMethod(0, qi);
      myDispatch.setMethod(3, new Callback() {
        //GetTypeInfoCount
        public int callback(Pointer _this) {
          System.out.println("IDispatch.GetTypeInfoCount");
          return HRESULT.E_NOTIMPL;
        }
      });
      myDispatch.setMethod(4, new Callback() {
        //GetTypeInfo
        public int callback(Pointer _this) {
          System.out.println("IDispatch.GetTypeInfo");
          return HRESULT.E_NOTIMPL;
        }
      });
      myDispatch.setMethod(5, new Callback() {
        //GetIDsOfNames
        public int callback(Pointer _this) {
          System.out.println("IDispatch.GetIDsOfNames");
          return HRESULT.E_NOTIMPL;
        }
      });
      myDispatch.setMethod(6, new Callback() {
        //Invoke
        public int callback(Pointer _this, int dispIdMember, Pointer riif
          , int lcid, int flags, Pointer dispParams, Pointer variantResult
          , Pointer excepInfo, IntByReference argErr
        )
        {
          Guid.IID iif = new Guid.IID(riif);
          System.out.println("IDispatch.Invoke:" + dispIdMember + ":" + GetRDPSessionEventString(dispIdMember));
          int res;
          VARIANT vr = new VARIANT();
          PointerByReference ref = new PointerByReference();

          switch (dispIdMember)
          {
            case DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_CONNECTED:
              {
                int level;
                IDispatch pDispatch;
                IRDPSRAPIAttendee pAttendee;

                vr.vt = VARENUM.VT_DISPATCH;
                vr.data.ptr = null;

                res = oleaut.DispGetParam(dispParams, 0, VARENUM.VT_DISPATCH, vr, argErr);

                if (res != 0)
                {
                  System.out.println("DispGetParam failed");
                  return res;
                }

                pDispatch = new IDispatch(vr.data.ptr);

                res = pDispatch.QueryInterface(Guid.IID_IRDPSRAPIAttendee, ref);

                if (res != 0)
                {
                  System.out.println("IDispatch::QueryInterface(IRDPSRAPIAttendee) failed");
                  return res;
                }

                pAttendee = new IRDPSRAPIAttendee(ref.getValue());

                //level = CTRL_LEVEL_VIEW;
                level = CTRL_LEVEL_INTERACTIVE;

                res = pAttendee.put_ControlLevel(level);

                if (res != 0)
                {
                  System.out.println("IRDPSRAPIAttendee::put_ControlLevel() failed");
                  return res;
                }

                pAttendee.Release();

                if (listener != null) {
                  listener.onAttendeeConnect();
                }
              }
              break;

            case DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_DISCONNECTED:
              if (listener != null) {
                listener.onAttendeeDisconnect();
              }
              break;

            case DISPID_RDPSRAPI_EVENT_ON_ATTENDEE_UPDATE:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_ERROR:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_VIEWER_CONNECTED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_VIEWER_DISCONNECTED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_VIEWER_AUTHENTICATED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_VIEWER_CONNECTFAILED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_CTRLLEVEL_CHANGE_REQUEST:
              {
                int level;
                IDispatch pDispatch;
                IRDPSRAPIAttendee pAttendee;

                vr.vt = VARENUM.VT_INT;
                vr.data.ptr = null;

                res = oleaut.DispGetParam(dispParams, 1, VARENUM.VT_INT, vr, argErr);

                if (res != 0)
                {
                  System.out.println("DispGetParam(1, VT_INT) failed");
                  return res;
                }

                level = vr.data.i32;

                vr.vt = VARENUM.VT_DISPATCH;
                vr.data.ptr = null;

                res = oleaut.DispGetParam(dispParams, 0, VARENUM.VT_DISPATCH, vr, argErr);

                if (res != 0)
                {
                  System.out.println("DispGetParam(0, VT_DISPATCH) failed");
                  return res;
                }

                pDispatch = new IDispatch(vr.data.ptr);

                res = pDispatch.QueryInterface(Guid.IID_IRDPSRAPIAttendee, ref);

                if (res != 0)
                {
                  System.out.println("IDispatch::QueryInterface(IRDPSRAPIAttendee) failed");
                  return res;
                }

                pAttendee = new IRDPSRAPIAttendee(ref.getValue());

                res = pAttendee.put_ControlLevel(level);

                if (res != 0)
                {
                  System.out.println("IRDPSRAPIAttendee::put_ControlLevel() failed");
                  return res;
                }

                pAttendee.Release();
              }
              break;

            case DISPID_RDPSRAPI_EVENT_ON_GRAPHICS_STREAM_PAUSED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_GRAPHICS_STREAM_RESUMED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_JOIN:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_LEAVE:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_DATARECEIVED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_VIRTUAL_CHANNEL_SENDCOMPLETED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_APPLICATION_OPEN:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_APPLICATION_CLOSE:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_APPLICATION_UPDATE:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_WINDOW_OPEN:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_WINDOW_CLOSE:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_WINDOW_UPDATE:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_APPFILTER_UPDATE:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_SHARED_RECT_CHANGED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_FOCUSRELEASED:
              break;

            case DISPID_RDPSRAPI_EVENT_ON_SHARED_DESKTOP_SETTINGS_CHANGED:
              break;

//            case DISPID_RDPAPI_EVENT_ON_BOUNDING_RECT_CHANGED:
//              break;
          }

          return HRESULT.S_OK;
        }
      });

      int res = ole.CoCreateInstance(Guid.CLSID_RDPSession
        , null, Ole.CLSCTX_ALL
        , Guid.IID_IUnknown, ref);
      if (res != 0) throw new Exception("CoCreateInstance failed");
      rdpUnknown = new IUnknown(ref.getValue());

      res = rdpUnknown.QueryInterface(Guid.IID_IRDPSRAPISharingSession, ref);
      if (res != 0) throw new Exception("QueryInterface(IConnectionPointContainer) failed");
      rdpSession = new IRDPSRAPISharingSession(ref.getValue());

      res = rdpSession.QueryInterface(Guid.IID_IConnectionPointContainer, ref);
      if (res != 0) throw new Exception("QueryInterface(IConnectionPointContainer) failed");
      rdpCPC = new IConnectionPointContainer(ref.getValue());

      rdpCPC.FindConnectionPoint(Guid.DIID__IRDPSessionEvents, ref);
      if (res != 0) throw new Exception("FindConnectionPoint failed");
      rdpCP = new IConnectionPoint(ref.getValue());

      IntByReference tokenref = new IntByReference();
      res = rdpCP.Advise(myDispatch.getPointer(), tokenref);
      if (res != 0) throw new Exception("IConnectionPoint.Advise() failed");
      token = tokenref.getValue();

      res = rdpSession.get_Properties(ref);
      if (res != 0) throw new Exception("RDPSession.get_Properties() failed");
      rdpProps = new IRDPSRAPISessionProperties(ref.getValue());

      IntByReference left = new IntByReference();
      IntByReference top = new IntByReference();
      IntByReference right = new IntByReference();
      IntByReference bottom = new IntByReference();

      res = rdpSession.GetDesktopSharedRect(left, top, right, bottom);
      if (res != 0) throw new Exception("GetDesktopSharedRect failed");
/*
      getPort();
      setPort(port);
      getPort();
*/
      res = rdpSession.Open();
      if (res != 0) throw new Exception("RDPSession.Open() Failed");

      IntByReference depth = new IntByReference();
      res = rdpSession.get_ColorDepth(depth);
      if (res != 0) throw new Exception("RDPSession.get_ColorDepth() Failed");

      res = rdpSession.get_Invitations(ref);
      if (res != 0) throw new Exception("RDPSession.get_Invitations() Failed");
      rdpIM = new IRDPSRAPIInvitationManager(ref.getValue());

      Pointer bstr_auth = bstr_alloc(auth);
      Pointer bstr_group = bstr_alloc(group);
      Pointer bstr_pass = bstr_alloc(pass);
      rdpI = rdpIM.CreateInvitation(bstr_auth, bstr_group, bstr_pass, numAttend);
      bstr_free(bstr_auth);
      bstr_free(bstr_group);
      bstr_free(bstr_pass);
      if (rdpI == null) throw new Exception("RDPSession.CreateInvitation() Failed");

      connectionString = rdpI.get_ConnectionString();

      System.out.println("RDP ConnectionString=" + connectionString);

      active = true;
      while (active) {
        ww.processMessage();
      }

      stop2();
      return;
    } catch (Exception e) {
      e.printStackTrace();
      stop2();
      return;
    }
  }

  public boolean stop() {
    active = false;
    ww.PostMessage();
    return true;
  }

  private void stop2() {
    if (rdpSession != null) {
      rdpSession.Close();
      rdpSession.Release();
      rdpSession = null;
    }
    if (rdpUnknown != null) {
      rdpUnknown.Release();
      rdpUnknown = null;
    }
    if (rdpIM != null) {
      rdpIM.Release();
      rdpIM = null;
    }
    if (rdpI != null) {
      rdpI.Release();
      rdpI = null;
    }
    if (rdpProps != null) {
      rdpProps.Release();
      rdpProps = null;
    }
    if (rdpCPC != null) {
      rdpCPC.Release();
      rdpCPC = null;
    }
    if (rdpCP != null) {
      rdpCP.Unadvise(token);
      rdpCP.Release();
      rdpCP = null;
    }
    if (ww != null) {
      ww.dispose();
      ww = null;
    }
  }

  public String getConnectionString() {
    while (connectionString == null) {
      JF.sleep(100);
    }
    return connectionString;
  }

  private String auth, group, pass;
  private int numAttend, port;
  private WinWindow ww;
  private boolean active;

  private IUnknown rdpUnknown;
  private IRDPSRAPISharingSession rdpSession;
  private IRDPSRAPIInvitationManager rdpIM;
  private IRDPSRAPIInvitation rdpI;
  private IRDPSRAPISessionProperties rdpProps;
  private IConnectionPointContainer rdpCPC;
  private IConnectionPoint rdpCP;

  private Callback qicb;  //QueryInterface Callback
  private Pointer qi;  //QueryInterface function ptr
  private int token;  //Advise token
  private InterfaceBuilder myDispatch;

  private String connectionString;
}
