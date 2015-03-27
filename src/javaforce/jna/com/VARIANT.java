package javaforce.jna.com;

/**
 *
 * @author pquiring
 *
 * Created : Aug 17, 2013
 */

import com.sun.jna.*;

import java.util.*;

public class VARIANT extends Structure {
  public static class ByReference extends VARIANT implements Structure.ByReference {}
  public static class ByValue extends VARIANT implements Structure.ByValue {}
  public short vt;  //see VARENUM
  public short r2, r3, r4;  //reserved
  public static class Data extends Union {
    public byte i8;
    public short i16;
    public int i32;
    public long i64;
    public Pointer string;
    public Pointer ptr;
  }
  public Data data;
  @Override
  protected List getFieldOrder() {
    return Arrays.asList(new String[] {"vt", "r2", "r3", "r4", "data"});
  }
}
