package javaforce.jna.com;

/** Some Variant types
 *
 * see https://msdn.microsoft.com/en-us/library/windows/desktop/ms221170%28v=vs.85%29.aspx
 *
 * @author pquiring
 */

public class VARENUM {
  public static short VT_EMPTY = 0;
  public static short VT_NULL = 1;
  public static short VT_I2 = 2;  //short
  public static short VT_I4 = 3;  //int
  public static short VT_R4 = 4;  //float
  public static short VT_R8 = 5;  //double
  public static short VT_DISPATCH = 9;
  public static short VT_UI4 = 19;  //int
  public static short VT_UI8 = 21;  //long
  public static short VT_INT = 22;  //int
  public static short VT_UINT = 23;  //int
  public static short VT_PTR = 26;  //Pointer
  public static short VT_LPSTR = 30;  //String
  public static short VT_LPWSTR = 31;  //WString
  public static short VT_CLSID = 72;
}
