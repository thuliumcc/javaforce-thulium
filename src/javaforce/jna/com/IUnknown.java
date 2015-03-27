package javaforce.jna.com;

/**
 * IUnknown interface
 *
 * @author pquiring
 *
 * Created : Aug 17, 2013
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

import javaforce.jna.com.Guid.*;

public class IUnknown extends PointerType {
  public static class ByReference extends IUnknown implements Structure.ByReference {}
  public IUnknown() {}
  public IUnknown(Pointer pvInstance) {
    super(pvInstance);
  }

  /** Invokes a COM function. Note: args[0] MUST be the 'this' pointer (ie: getPointer()) */
  public int invokeInt(int idx, Object args[]) {
    //cpp_this = getPointer()
    //vtbl = cpp_this.getPointer(0);
    //funcPtr = vtbl.getPointer(idx * Pointer.SIZE)
    return Function.getFunction(getPointer().getPointer(0).getPointer(idx * Pointer.SIZE)).invokeInt(args);
  }

  //NOTE : Functions are numbered as defined in the header files (MSDN lists them in alphabetical order)

  //0 = QueryInterface
  public int QueryInterface(IID riid, PointerByReference ppvObject) {
    return invokeInt(0, new Object[] { getPointer(), riid, ppvObject });
  }
  //1 = AddRef
  public int AddRef() {
    return invokeInt(1, new Object[] { getPointer() });
  }
  //2 = Release
  public int Release() {
    return invokeInt(2, new Object[] { getPointer() });
  }
}
