package javaforce.jni;

import com.sun.jna.Platform;

/** Provides simple Native Access.
 *
 * Instead of providing a generic function call like libffi (JNA), this provdes some
 * direct function types for those that are used in JavaForce.
 *
 * @author pquiring
 */

public class JFNative {
  static {
    try {
      if (Platform.is64Bit())
        System.loadLibrary("jfnative64");
      else
        System.loadLibrary("jfnative32");
      loaded = true;
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
  public static boolean loaded;
  public native static void invokeVoid(long ptr);
  public native static void invokeVoid(long ptr, int i1);
  public native static void invokeVoid(long ptr, int i1, int i2);
  public native static void invokeVoid(long ptr, int i1, float f2);
  public native static void invokeVoid(long ptr, int i1, int i2, float[] f3);
  public native static void invokeVoid(long ptr, int i1, int i2[]);
  public native static void invokeVoid(long ptr, int i1, int i2[], int i3);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3);
  public native static void invokeVoid(long ptr, int i1, int i2, int[] i3);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3, float[] f4);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3, int l4);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3, long l4);
  public native static void invokeVoid(long ptr, int i1, int i2, long l3, long l4);
  public native static void invokeVoid(long ptr, int i1, int i2, float f3[], int i4);
  public native static void invokeVoid(long ptr, int i1, long l2, float f3[], int i4);
  public native static void invokeVoid(long ptr, int i1, int i2, short s3[], int i4);
  public native static void invokeVoid(long ptr, int i1, long i2, short s3[], int i4);
  public native static void invokeVoid(long ptr, int i1, int i2, byte s3[], int i4);
  public native static void invokeVoid(long ptr, int i1, long i2, byte s3[], int i4);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3[], int i4);
  public native static void invokeVoid(long ptr, int i1, long i2, int i3[], int i4);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3, int i4, int i5);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3, int i4, int i5, int i6);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3, int i4, int i5, long l6);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3, int i4, int i5, int i6, int i7[]);
  public native static void invokeVoid(long ptr, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9[]);
  public native static void invokeVoid(long ptr, float f1, float f2, float f3, float f4);
  public native static void invokeVoid(long ptr, boolean b1);
  public native static void invokeVoid(long ptr, boolean b1, boolean b2, boolean b3, boolean b4);
  public native static int invokeInt(long ptr);
  public native static int invokeInt(long ptr, int i1);
  public native static int invokeInt(long ptr, int i1, int i2, int i3);
  public native static long invokeLong(long ptr, int i1);
}
