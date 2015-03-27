package javaforce.jna;

/**
 *
 * @author pquiring
 *
 */

import java.awt.*;

import com.sun.jna.*;
import com.sun.jna.ptr.*;
import java.awt.event.ComponentAdapter;

import javaforce.*;
import javaforce.jna.com.*;

public class WinRDPClient {
  private static Ole ole;
  private static OleAut oleaut;

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

  private boolean uninit() {
    return comUnInit();
  }

  private boolean comInit() {
    int res = ole.CoInitializeEx(null, ole.COINIT_APARTMENTTHREADED);
    if (res != 0) return false;
    return true;
  }
  private boolean comUnInit() {
    ole.CoUninitialize();
    return true;
  }

  private Pointer bstr_alloc(String str) {
    return oleaut.SysAllocString(new WString(str));
  }

  private void bstr_free(Pointer bstr) {
    oleaut.SysFreeString(bstr);
  }

  public boolean create(String connectionString, String username, String pass, Canvas canvas) {
    this.canvas = canvas;
    this.connectionString = connectionString;
    this.username = username;
    this.pass = pass;
    new Thread() {
      public void run() {
        create2();
      }
    }.start();
    return true;
  }

  private void create2() {
    try {
      PointerByReference ref = new PointerByReference();

      if (!init()) throw new Exception("Ole init failed");

      canvas_hwnd = Native.getComponentPointer(canvas);
      ww = new WinWindow();
      if (!WinWindow.init()) throw new Exception("WinWindow init failed");
      child_hwnd = ww.create(canvas_hwnd, true);
      if (child_hwnd == null) throw new Exception("WinWindow.create() failed");

      cb = new Callback() {
        //QueryInterface
        public int callback(Pointer _this, Pointer _refiid, PointerByReference ppvObject) {
          Guid.GUID refiid = new Guid.GUID(_refiid);
//          System.out.println("QI:refiid=" + refiid + ",ppv=" + ppvObject);
          if (refiid.equals(Guid.IID_IUnknown)) {
            ppvObject.setValue(myOleClientSite.getPointer());
            myOleClientSite.AddRef();
            return HRESULT.S_OK;
          }
          if (refiid.equals(Guid.IID_IDispatch)) {
            ppvObject.setValue(myDispatch.getPointer());
            myDispatch.AddRef();
            return HRESULT.S_OK;
          }
          if (refiid.equals(Guid.IID_IOleClientSite) ||
              refiid.equals(Guid.IID_IUnknown))
          {
            ppvObject.setValue(myOleClientSite.getPointer());
            myOleClientSite.AddRef();
            return HRESULT.S_OK;
          }
          if (refiid.equals(Guid.IID_IOleInPlaceSite)) {
            ppvObject.setValue(myOleInPlaceSite.getPointer());
            myOleInPlaceSite.AddRef();
            return HRESULT.S_OK;
          }
//          String iface = Guid.find(refiid);
//          System.out.println(" name=" + iface);
          return HRESULT.E_NOINTERFACE;
        }
      };
      qi = InterfaceBuilder.callbackToPointer(cb);

      //create client site
      myOleClientSite = new InterfaceBuilder();
      myOleClientSite.alloc(9, "IOleClientSite");
      myOleClientSite.setMethod(0, qi);
      myOleClientSite.setMethod(5, new Callback() {
        //5 = GetContainer
        public int callback(Pointer _this, PointerByReference iOleContainer) {
          System.out.println("GetContainer");
          iOleContainer.setValue(myOleContainer.getPointer());
          return 0;
        }
      });
      myOleClientSite.setMethod(6, new Callback() {
        //6 = ShowObject
        public int callback(Pointer _this) {
          System.out.println("ShowObject");
          return 0;
        }
      });

      myOleInPlaceSite = new InterfaceBuilder();
      myOleInPlaceSite.alloc(15, "IOleInPlaceSite");
      myOleInPlaceSite.setMethod(0, qi);  //QueryInterface
      myOleInPlaceSite.setMethod(3, new Callback() {
        //3 = GetWindow
        public int callback(Pointer _this, PointerByReference hwndref) {
          System.out.println("GetWindow (site)");
          hwndref.setValue(child_hwnd);
          return 0;
        }
      });
      myOleInPlaceSite.setMethod(5, new Callback() {
        //5 = CanInPlaceActivate
        public int callback(Pointer _this) {
          System.out.println("CanInPlaceActivate");
          return 0;
        }
      });
      myOleInPlaceSite.setMethod(6, new Callback() {
        //6 = OnInPlaceActivate
        public int callback(Pointer _this) {
          System.out.println("OnInPlaceActivate");
          return 0;
        }
      });
      myOleInPlaceSite.setMethod(7, new Callback() {
        //7 = OnUIActivate
        public int callback(Pointer _this) {
          System.out.println("OnUIActivate");
          return 0;
        }
      });
      myOleInPlaceSite.setMethod(8, new Callback() {
        //8 = GetWindowContext
        public int callback(Pointer _this, PointerByReference iOleInPlaceFrame
          , PointerByReference iOleInPlaceUIWindow, RECT pos, RECT clip
          , Pointer iOleInPlaceFrameInfo)
        {
          System.out.println("GetWindowContext");
          iOleInPlaceFrame.setValue(myOleInPlaceFrame.getPointer());
          iOleInPlaceUIWindow.setValue(null);  //myOleInPlaceUIWindow.getPointer()

          RECT r = getRECT();

/*
          pos.left = r.left;
          pos.top = r.top;
          pos.right = r.right;
          pos.bottom = r.bottom;

          clip.left = r.left;
          clip.top = r.top;
          clip.right = r.right;
          clip.bottom = r.bottom;
*/

          OLEINPLACEFRAMEINFO info = new OLEINPLACEFRAMEINFO(iOleInPlaceFrameInfo);
          info.read();
          info.fMDIApp = false;
          info.hwndFrame = child_hwnd;
          info.haccel = null;
          info.cAccelEntries = 0;
          info.write();

          return 0;
        }
      });
      myOleInPlaceSite.setMethod(10, new Callback() {
        //10 = OnUIDeactivate
        public int callback(Pointer _this, boolean undoable) {
          System.out.println("OnUIDeactivate");
          return 0;
        }
      });
      myOleInPlaceSite.setMethod(11, new Callback() {
        //11 = OnInPlaceDeactivate
        public int callback(Pointer _this) {
          System.out.println("OnInPlaceDeactivate");
          return 0;
        }
      });
      myOleInPlaceSite.setMethod(14, new Callback() {
        //14 = OnPosRectChange
        public int callback(Pointer _this, Pointer pRect) {
          System.out.println("OnPosRectChange");
//TODO

/*
  IOleObject *browserObject;
	IOleInPlaceObject *inplace;

	// We need to get the browser's IOleInPlaceObject object so we can call its SetObjectRects
	// function.
	browserObject = *((IOleObject **)((char *)This - sizeof(IOleObject *) - sizeof(IOleClientSite)));
	if (!browserObject->lpVtbl->QueryInterface(browserObject, &IID_IOleInPlaceObject, (void**)&inplace))
	{
		// Give the browser the dimensions of where it can draw.
		inplace->lpVtbl->SetObjectRects(inplace, lprcPosRect, lprcPosRect);
		inplace->lpVtbl->Release(inplace);
	}
*/

          return 0;
        }
      });

      myOleContainer = new InterfaceBuilder();
      myOleContainer.alloc(6, "IOleContainer");
      myOleContainer.setMethod(0, qi);

      myDispatch = new InterfaceBuilder();
      myDispatch.alloc(7, "IDispatch");
      myDispatch.setMethod(0, qi);  //QueryInterface
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
        public int callback(Pointer _this) {
          System.out.println("IDispatch.Invoke");
          //TODO
          return HRESULT.S_OK;
        }
      });

      myOleInPlaceFrame = new InterfaceBuilder();
      myOleInPlaceFrame.alloc(15, "IOleInPlaceFrame");
      myOleInPlaceFrame.setMethod(0, qi);
      myOleInPlaceFrame.setMethod(3, new Callback() {
        //3 = GetWindow
        public int callback(Pointer _this, PointerByReference hwnd) {
          System.out.println("GetWindow (frame)");
          hwnd.setValue(child_hwnd);
          return 0;
        }
      });
      myOleInPlaceFrame.setMethod(8, new Callback() {
        //8 = SetActiveObject
        public int callback(Pointer _this, Pointer iOleInPlaceActiveObject, Pointer objName) {
          System.out.println("SetActiveObject");
          return 0;
        }
      });
      myOleInPlaceFrame.setMethod(10, new Callback() {
        //10 = SetMenu
        public int callback(Pointer _this, Pointer hmenuShared, Pointer holemenu, Pointer hwndActiveObject) {
          System.out.println("SetMenu");
          return 0;
        }
      });
      myOleInPlaceFrame.setMethod(13, new Callback() {
        //10 = EnableModeless
        public int callback(Pointer _this, Pointer hmenuShared, Pointer holemenu, Pointer hwndActiveObject) {
          System.out.println("EnableModeless");
          return 0;
        }
      });

      //create AX object
      int res = ole.CoCreateInstance(Guid.CLSID_RDPViewer
        , null
        , Ole.CLSCTX_INPROC_SERVER | Ole.CLSCTX_INPROC_HANDLER
        , Guid.IID_IUnknown
        , ref);
      if (res != 0) throw new Exception("CoCreateInstance failed!");
      rdpUnknown = new IUnknown(ref.getValue());

      //get AX interfaces
      res = rdpUnknown.QueryInterface(Guid.IID_IRDPSRAPIViewer, ref);
      if (res != 0) throw new Exception("RDPClient does not support IID_IRDPSRAPIViewer");
      rdpViewer = new IRDPSRAPIViewer(ref.getValue());

      res = rdpUnknown.QueryInterface(Guid.IID_IOleObject, ref);
      if (res != 0) throw new Exception("RDPClient does not support IOleObject");
      rdpOO = new IOleObject(ref.getValue());

      res = rdpUnknown.QueryInterface(Guid.IID_IOleInPlaceObject, ref);
      if (res != 0) throw new Exception("QueryInterface(IID_IOleInPlaceObject) failed");
      rdpOIPO = new IOleInPlaceObject(ref.getValue());

      //embed object
      res = rdpOO.SetClientSite(myOleClientSite.getPointer());
      if (res != 0) throw new Exception("IOleObject.SetClientSite() failed");

      res = ole.OleSetContainedObject(rdpOO.getPointer(), true);
      if (res != 0) throw new Exception("Ole.OleSetContainedObject() failed");

      res = rdpOO.DoVerb(IOleObject.OLEIVERB_SHOW, null
        , myOleClientSite.getPointer(), -1, child_hwnd, getRECT());
      if (res != 0) throw new Exception("IOleObject.DoVerb() failed");

      canvas.addComponentListener(new ComponentAdapter() {
        public void componentResized(java.awt.event.ComponentEvent evt) {
          moved();
        }
        public void componentMoved(java.awt.event.ComponentEvent evt) {
          moved();
        }
      });

      moved();

      System.out.println("cs=" + connectionString);

      //now connect to server
      Pointer bstr_cs = bstr_alloc(connectionString);
      Pointer bstr_name = bstr_alloc(username);
      Pointer bstr_pass = bstr_alloc(pass);
      res = rdpViewer.Connect(bstr_cs, bstr_name, bstr_pass);
      bstr_free(bstr_cs);
      bstr_free(bstr_name);
      bstr_free(bstr_pass);
      if (res != 0) throw new Exception("RDPClient.Connect() Failed");

      //now process message queue for child window
      active = true;
      while (active) {
        ww.processMessage();
      }
      dispose2();
    } catch (Exception e) {
      e.printStackTrace();
      dispose2();
    }
  }

  public boolean dispose() {
    active = false;
    ww.PostMessage();  //wake processMessage() loop
    return true;
  }

  private boolean dispose2() {
    if (rdpOO != null) {
      rdpOO.Close(IOleObject.OLECLOSE_SAVEIFDIRTY);
    }
    if (rdpViewer != null) {
      rdpViewer.Release();
      rdpViewer = null;
    }
    if (rdpOO != null) {
      rdpOO.Release();
      rdpOO = null;
    }
    if (rdpUnknown != null) {
      rdpUnknown.Release();
      rdpUnknown = null;
    }
    if (child_hwnd != null) {
      ww.dispose();
      child_hwnd = null;
    }
    return true;
  }

  private void moved() {
    RECT rect = getRECT();
    ww.setPos(0, 0, rect.right, rect.bottom);
    rdpOIPO.SetObjectRects(rect, rect);
  }

  private RECT getRECT() {
    Rectangle cr = canvas.getBounds();
    RECT rect = new RECT();
    rect.top = 0;
    rect.left = 0;
    rect.right = cr.width;
    rect.bottom = cr.height;
    return rect;
  }

  private boolean active;
  private Callback cb;
  private Pointer qi;
  private Canvas canvas;
  private String connectionString, username, pass;
  private Pointer canvas_hwnd, child_hwnd;
  private WinWindow ww;

  //activeX object
  private IUnknown rdpUnknown;
  private IRDPSRAPIViewer rdpViewer;
  private IOleObject rdpOO;
  private IOleInPlaceObject rdpOIPO;

  //this side
  private InterfaceBuilder myOleClientSite;
  private InterfaceBuilder myOleContainer;
  private InterfaceBuilder myOleInPlaceSite;
  private InterfaceBuilder myDispatch;
  private InterfaceBuilder myOleInPlaceFrame;
}
