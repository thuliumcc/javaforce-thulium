package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

import java.util.*;

public class DOCHOSTUIINFO extends Structure {
  public static class ByReference extends DOCHOSTUIINFO implements Structure.ByReference {}
  public static class ByValue extends DOCHOSTUIINFO implements Structure.ByValue {}
  public DOCHOSTUIINFO() {}
  public DOCHOSTUIINFO(Pointer ptr) {
    super(ptr);
  }
  public int cbSize;
  public int dwFlags;
  public int dwDoubleClick;
  public Pointer pchHostCss;
  public Pointer pchHostNS;
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] {"cbSize", "dwFlags", "dwDoubleClick", "pchHostCss", "pchHostNS"});
  }
}
