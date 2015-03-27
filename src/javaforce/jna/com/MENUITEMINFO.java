package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 */

import com.sun.jna.*;

import java.util.*;

public class MENUITEMINFO extends Structure {
  public static class ByReference extends MENUITEMINFO implements Structure.ByReference {}
  public static class ByValue extends MENUITEMINFO implements Structure.ByValue {}

  public int cbSize, fMask, fType, fState, wId;
  public Pointer subMenu, bmpChecked, bmpUnchecked, itemData;
  public String typeData;
  public int cch;
  public Pointer bmpItem;

  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] {
      "cbSize", "fMask", "fType", "fState",
      "wId", "subMenu", "bmpChecked", "bmpUnchecked",
      "itemData", "typeData", "cch", "bmpItem",
    });
  }
}
