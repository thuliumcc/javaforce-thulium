package javaforce.jna;

/**
 *
 * Embed an ActiveX WebBrowser in a window.
 *
 * @author pquiring
 *
 */

import java.awt.*;

import com.sun.jna.*;
import com.sun.jna.ptr.*;
import java.awt.event.*;

import javaforce.*;
import javaforce.jna.com.*;

public class WinWebBrowser {
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

  //clsid = Guid.CLSID_WebBrowser | Guid.CLSID_MozillaBrowser
  public boolean create(Guid.CLSID clsid, Canvas comp) {
    this.clsid = clsid;
    this.canvas = comp;
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
            myOleClientSite.AddRef();
            return HRESULT.S_OK;
          }

          if (refiid.equals(Guid.IID_IDispatch)) {
//            System.out.println(" returning IDispatch");
            ppvObject.setValue(myDispatch.getPointer());
            myDispatch.AddRef();
            return HRESULT.S_OK;
          }

          if (refiid.equals(Guid.IID_IOleClientSite)) {
//            System.out.println(" returning IOleClientSite");
            ppvObject.setValue(myOleClientSite.getPointer());
            myOleClientSite.AddRef();
            return HRESULT.S_OK;
          }
          if (refiid.equals(Guid.IID_IOleInPlaceSite)) {
//            System.out.println(" returning IOleInPlaceSite");
            ppvObject.setValue(myOleInPlaceSite.getPointer());
            myOleInPlaceSite.AddRef();
            return HRESULT.S_OK;
          }
          if (refiid.equals(Guid.IID_IDocHostUIHandler)) {
//            System.out.println(" returning IDocHostUIHandler");
            ppvObject.setValue(myDocHost.getPointer());
            myDocHost.AddRef();
            return HRESULT.S_OK;
          }
//          String iface = Guid.find(refiid);
//          System.out.println("QueryInterface:Unknown IID=" + iface);
          ppvObject.setValue(null);
          return HRESULT.E_NOINTERFACE;
        }
      };

      qi = InterfaceBuilder.callbackToPointer(qicb);

      //create client site
      myOleClientSite = new InterfaceBuilder();
      myOleClientSite.alloc(9, "IOleClientSite");
      myOleClientSite.setMethod(0, qi);
      myOleClientSite.setMethod(5, new Callback() {
        //5 = GetContainer
        public int callback(Pointer _this, PointerByReference iOleContainer) {
          System.out.println("GetContainer");
          iOleContainer.setValue(null);
          return HRESULT.E_NOINTERFACE;
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
      myOleInPlaceSite.setMethod(0, qi);
      myOleInPlaceSite.setMethod(3, new Callback() {
        //3 = GetWindow
        public int callback(Pointer _this, PointerByReference hwnd) {
          System.out.println("GetWindow (site)");
          hwnd.setValue(child_hwnd);
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

      myDocHost = new InterfaceBuilder();
      myDocHost.alloc(18, "IDocHostUIHandler");
      myDocHost.setMethod(0, qi);
      myDocHost.setMethod(4, new Callback() {
        //GetHostInfo
        public int callback(Pointer _this, Pointer pInfo) {
          System.out.println("GetHostInfo");
          DOCHOSTUIINFO info = new DOCHOSTUIINFO(pInfo);
          info.read();
          info.cbSize = info.size();
          info.dwFlags = 4;  //DOCHOSTUIFLAG_NO3DBORDER;
          info.dwDoubleClick = 0; //DOCHOSTUIDBLCLK_DEFAULT;
          info.write();
          return HRESULT.S_OK;
        }
      });

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
          System.out.println("IDispatch.Invoke:" + dispIdMember + "," + iif);
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

/*
      myOleInPlaceUIWindow = new InterfaceBuilder();
      myOleInPlaceUIWindow.alloc(9, "IOleInPlaceUIWindow");
      myOleInPlaceUIWindow.setMethod(0, qi);
*/

      //create ActiveX object
      int res = ole.CoCreateInstance(clsid
        , null
        , Ole.CLSCTX_INPROC_SERVER | Ole.CLSCTX_INPROC_HANDLER
        , Guid.IID_IUnknown
        , ref);
      if (res != 0) throw new Exception("CoCreateInstance failed!");
      webUnknown = new IUnknown(ref.getValue());

      //get interfaces
      res = webUnknown.QueryInterface(Guid.IID_IOleObject, ref);
      if (res != 0) throw new Exception("QueryInterface(IOleObject) failed");
      webOO = new IOleObject(ref.getValue());

      res = webUnknown.QueryInterface(Guid.IID_IWebBrowser2, ref);
      if (res != 0) throw new Exception("QueryInterface(IWebBrowser2) failed");
      webBrowser = new IWebBrowser2(ref.getValue());

      res = webUnknown.QueryInterface(Guid.IID_IConnectionPointContainer, ref);
      if (res != 0) throw new Exception("QueryInterface(IConnectionPointContainer) failed");
      webCPC = new IConnectionPointContainer(ref.getValue());

      res = webUnknown.QueryInterface(Guid.IID_IOleInPlaceObject, ref);
      if (res != 0) throw new Exception("QueryInterface(IID_IOleInPlaceObject) failed");
      webOIPO = new IOleInPlaceObject(ref.getValue());

      res = webCPC.FindConnectionPoint(Guid.DIID_DWebBrowserEvents2, ref);
      if (res != 0) throw new Exception("FindConnnectionPointer(DWebBrowserEvents2) failed");
      webCP = new IConnectionPoint(ref.getValue());

      //get events
      IntByReference tokenref = new IntByReference();
      webCP.Advise(myDispatch.getPointer(), tokenref);
      token = tokenref.getValue();

      //embed OleObject
      res = webOO.SetClientSite(myOleClientSite.getPointer());
      if (res != 0) throw new Exception("IOleObject.SetClientSite() failed");

      res = webOO.SetHostNames(new WString("My Host Name"), null);
      if (res != 0) throw new Exception("IOleObject.SetHostNames() failed");

      res = ole.OleSetContainedObject(webOO.getPointer(), true);
      if (res != 0) throw new Exception("Ole.OleSetContainedObject() failed");

      res = webOO.DoVerb(IOleObject.OLEIVERB_SHOW, null
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

      show();

      moved();

      navigate("http://google.com");

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

  private void dispose2() {
    if (webOO != null) {
      webOO.Close(IOleObject.OLECLOSE_SAVEIFDIRTY);
    }
    if (webCP != null) {
      webCP.Unadvise(token);
      webCP.Release();
      webCP = null;
    }
    if (webCPC != null) {
      webCPC.Release();
      webCPC = null;
    }
    if (webBrowser == null) {
      webBrowser.Release();
      webBrowser = null;
    }
    if (webOO != null) {
      webOO.Release();
      webOO = null;
    }
    if (webUnknown != null) {
      webUnknown.Release();
      webUnknown = null;
    }
    if (child_hwnd != null) {
      ww.dispose();
      child_hwnd = null;
    }
  }

  public void show() {
    webBrowser.put_Visible(true);
  }

  public void hide() {
    webBrowser.put_Visible(false);
  }

  public void navigate(String url) {
    Pointer bstr_url = bstr_alloc(url);
    webBrowser.Navigate(bstr_url);
    bstr_free(bstr_url);
  }

  private void moved() {
    RECT rect = getRECT();
    ww.setPos(0, 0, rect.right, rect.bottom);
    webOIPO.SetObjectRects(rect, rect);
    webBrowser.put_Left(0);
    webBrowser.put_Top(0);
    webBrowser.put_Width(rect.right);
    webBrowser.put_Height(rect.bottom);
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
  private Guid.CLSID clsid;
  private Canvas canvas;
  private Pointer canvas_hwnd, child_hwnd;
  private WinWindow ww;  //child window
  private Callback qicb;  //QueryInterface Callback
  private Pointer qi;  //QueryInterface function ptr
  private int token;  //Advise token

  //other side
  private IUnknown webUnknown;
  private IWebBrowser2 webBrowser;
  private IOleObject webOO;
  private IConnectionPointContainer webCPC;
  private IConnectionPoint webCP;
  private IOleInPlaceObject webOIPO;

  //this side
  private InterfaceBuilder myDispatch;
  private InterfaceBuilder myDocHost;
  private InterfaceBuilder myOleClientSite;
  private InterfaceBuilder myOleInPlaceSite;
//  private InterfaceBuilder myOleInPlaceUIWindow;
  private InterfaceBuilder myOleInPlaceFrame;
}
