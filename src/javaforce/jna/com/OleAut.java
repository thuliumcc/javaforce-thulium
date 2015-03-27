package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 * Created : Aug 17, 2013
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;
import com.sun.jna.win32.*;

public interface OleAut extends StdCallLibrary {
  public int VariantInit(VARIANT pVar);
  public int VariantClear(VARIANT pVar);
  public Pointer SysAllocString(WString psz);  //returns BSTR
  public void SysFreeString(Pointer bstr);  //frees BSTR
  public int DispGetParam(
    Pointer pdispparams,
    int position,
    int vtTarg,  //VARTYPE or VARENUM
    VARIANT pvarResult,
    IntByReference puArgErr
  );
}
