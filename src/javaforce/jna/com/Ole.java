package javaforce.jna.com;

/**
 * Ole Library
 *
 * @author pquiring
 *
 * Created : Aug 17, 2013
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;
import com.sun.jna.win32.*;

import javaforce.jna.com.Guid.*;

public interface Ole extends StdCallLibrary {
  public int CoInitialize(Pointer reserved);
  public int CoInitializeEx(Pointer reserved, int dwCoInit);
  public static int COINIT_MULTITHREADED      = 0x0;
  public static int COINIT_APARTMENTTHREADED  = 0x2;
  public static int COINIT_DISABLE_OLE1DDE    = 0x4;
  public static int COINIT_SPEED_OVER_MEMORY  = 0x8;
  public int CoCreateInstance(CLSID clsid, Pointer pUnkOuter, int dwClsContext, IID riid, PointerByReference ppv);
  public static int CLSCTX_INPROC_SERVER = 0x1;
  public static int CLSCTX_INPROC_HANDLER = 0x2;
  public static int CLSCTX_LOCAL_SERVER = 0x4;
  public static int CLSCTX_REMOTE_SERVER = 0x10;
  public static int CLSCTX_ALL = CLSCTX_INPROC_SERVER | CLSCTX_INPROC_HANDLER | CLSCTX_LOCAL_SERVER | CLSCTX_REMOTE_SERVER;
  public int CoUninitialize();
  public int CLSIDFromProgID(String progId, CLSID clsid);
  public int CoTaskMemFree(Pointer pv);

  public int OleInitialize(Pointer res);
  public int OleSetContainedObject(Pointer iUnknown, boolean contained);
}
