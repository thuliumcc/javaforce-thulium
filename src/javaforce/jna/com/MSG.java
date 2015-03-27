package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 * Created : Aug 17, 2013
 */

import com.sun.jna.*;

import java.util.*;

public class MSG extends Structure {
  public static class ByReference extends MSG implements Structure.ByReference {}
  public static class ByValue extends MSG implements Structure.ByValue {}

  public Pointer hwnd;
  public int msg;
  public Pointer wParam, lParam;
  public int time;
  public int pt_x, pt_y;

  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] {
      "hwnd", "msg", "wParam", "lParam", "time", "pt_x", "pt_y"
    });
  }
}
