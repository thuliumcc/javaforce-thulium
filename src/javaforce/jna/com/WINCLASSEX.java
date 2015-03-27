package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 * Created : Aug 17, 2013
 */

import com.sun.jna.*;

import java.util.*;

public class WINCLASSEX extends Structure {
  public static class ByReference extends WINCLASSEX implements Structure.ByReference {}
  public static class ByValue extends WINCLASSEX implements Structure.ByValue {}

  public int cbSize;
  public int style;
  public Pointer wndProc;
  public int clsExtra;
  public int wndExtra;
  public Pointer hInstance;
  public Pointer hIcon;
  public Pointer hCursor;
  public Pointer hbrBackground;
  public String lpszMenuName;
  public String lpszClassName;
  public Pointer hIconSm;

  protected List getFieldOrder() {
    return Arrays.asList(new String[] {
        "cbSize", "style", "wndProc", "clsExtra"
      , "wndExtra", "hInstance", "hIcon", "hCursor"
      , "hbrBackground", "lpszMenuName", "lpszClassName", "hIconSm"
    });
  }
}
