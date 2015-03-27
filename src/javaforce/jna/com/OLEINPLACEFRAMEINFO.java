package javaforce.jna.com;

/**
 *
 * @author pquiring
 */

import com.sun.jna.*;
import java.util.*;

public class OLEINPLACEFRAMEINFO extends Structure {
  public OLEINPLACEFRAMEINFO(Pointer ptr) {
    super(ptr);
  }
  public int cb;
  public boolean fMDIApp;
  public Pointer hwndFrame;
  public Pointer haccel;
  public int cAccelEntries;
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] {"cb", "fMDIApp", "hwndFrame", "haccel", "cAccelEntries"});
  }
}
