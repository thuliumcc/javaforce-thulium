package javaforce.jna.com;

/**
 * msvcrt Library
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;
import com.sun.jna.win32.*;

public interface msvcrt extends StdCallLibrary {
  Pointer memcpy(Callback dst, Callback src, int size);  //this is used just to evaluate callbacks
}
