package javaforce.jna.com;

/**
 * Used to create an OLE Object in memory.
 *
 * IUnknown::AddRef and Release and defined and track the reference count.
 * Any other function will print an "Undefined Function Called" error to stdout.
 *
 * @author pquiring
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class InterfaceBuilder {
  private static Pointer malloc(int size) {
    return new Pointer(Native.malloc(size));
  }

  private static void free(Pointer ptr) {
    Native.free(Pointer.nativeValue(ptr));
  }

  private PointerByReference _this;
  private Pointer _vtable;
  private Callback callbacks[];
  private static msvcrt crt;
  private Callback cb_unknown;
  private Pointer p_unknown;
  private String name;

  private int refCnt;  //is tracked but does nothing

  private void init(int numMethods) {
    for(int a=0;a<numMethods;a++) {
      final int aa = a;
      if (a == 1) {
        //add Ref
        setMethod(a, new Callback() {
          public int callback(Pointer _this) {
            return AddRef();
          }
        });
      }
      else if (a == 2) {
        setMethod(a, new Callback() {
          public int callback(Pointer _this) {
            return Release();
          }
        });
      } else {
        setMethod(a, new Callback() {
          public int callback(Pointer _this) {
            System.out.println("Undefined Function " + aa + " called on interface " + name);
            return HRESULT.S_OK;  //or E_NOTIMPL
          }
        });
      }
    }
  }

  private static boolean debug = false;

  public void alloc(int numMethods, String name) {
    if (debug) numMethods += 2;
    this.name = name;
    _vtable = malloc(numMethods * Pointer.SIZE);
    _this = new PointerByReference();
    _this.setValue(_vtable);
    callbacks = new Callback[numMethods];
    init(numMethods);
  }

  public void free() {
    if (_vtable != null) {
      free(_vtable);
      _vtable = null;
    }
  }

  public void finalize() {
    free();
  }

  public int AddRef() {
    refCnt++;
//    System.out.println("AddRef:" + name + ":" + refCnt);
    return 1;  //refCnt;
  }

  public int Release() {
    refCnt--;
//    System.out.println("Release:" + name + ":" + refCnt);
    return 1;  //refCnt;
  }

  public static Pointer callbackToPointer(Callback callback) {
    //ugly hack to get Pointer from Callback
    if (crt == null) {
      crt = (msvcrt)Native.loadLibrary("msvcrt", msvcrt.class);
    }
    return crt.memcpy(callback, callback, 0);
  }

  public void setMethod(int index, Pointer callback) {
    _vtable.setPointer(index * Pointer.SIZE, callback);
  }

  public void setMethod(int index, Callback callback) {
    callbacks[index] = callback;  //hold ref to avoid GC'd
    _vtable.setPointer(index * Pointer.SIZE, callbackToPointer(callback));
  }

  public Pointer getMethod(int index) {
    return _vtable.getPointer(index * Pointer.SIZE);
  }

  /** Returns the 'this' pointer. */
  public Pointer getPointer() {
    return _this.getPointer();  //NOT getValue() - need the reference
  }
}
