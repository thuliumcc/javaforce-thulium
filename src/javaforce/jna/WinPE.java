package javaforce.jna;

/** Tool to add resources to Windows PE exe files.
 *
 * Based on WinRun4J source code (converted to Java).
 *
 * @author pquiring
 */

import com.sun.jna.*;
import com.sun.jna.win32.*;

import java.lang.reflect.*;
import java.util.*;
import java.io.*;

import javaforce.*;
import javaforce.jna.com.*;

public class WinPE {
  public static interface KERNEL extends StdCallLibrary {
    Pointer BeginUpdateResourceA(String exeFile, boolean clearResources);
    boolean EndUpdateResourceA(Pointer handle, boolean discardChanges);
    boolean UpdateResourceA(Pointer handle, Pointer type, Pointer name, short lang, Pointer data, int size);
  }
  private static KERNEL kernel;
  public static class ICONENTRY extends Structure {
    public byte width, height, clrCnt, reserved;
    public short planes, bitCnt;
    public int bytesInRes, imageOffset;
    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
  }
  public static class ICONHEADER extends Structure {
    public short res, type, count;
    public ICONENTRY icons[];
    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
  }
  public static class RGBQUAD extends Structure {
    public byte b,g,r,res;
    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
  }
  public static class ICONIMAGE extends Structure {
    public BITMAPINFOHEADER header;
    public RGBQUAD colors;
    public byte[] xors_ands;
    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
  }
  public static class GRPICONENTRY extends Structure {
    public byte width;
    public byte height;
    public byte colourCount;
    public byte reserved;
    public byte planes;
    public byte bitCount;
    public short bytesInRes;
    public short bytesInRes2;
    public short reserved2;
    public short id;
    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
  }
  public static class GRPICONHEADER extends Structure {
    public short res, type, count;
    public GRPICONENTRY entries[];
    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
  }
  private static Pointer malloc(int size) {
    return new Pointer(Native.malloc(size));
  }
  private static void free(Pointer ptr) {
    Native.free(Pointer.nativeValue(ptr));
  }
  public static void usage() {
    System.out.println("WinPE : Add resources to Windows EXE PE files");
    System.out.println("Usage : WinPE exeFile file(s)");
    System.out.println("Supported : .ico .manifest .cfg");
    System.exit(0);
  }
  private static final int RT_ICON = 3;
  private static final int RT_GROUP_ICON = 11 + 3;
  private static final int RT_STRING = 6;  //too complex
  private static final int RT_RCDATA = 10;  //raw data (used to store .cfg file)
  private static final int RT_MANIFEST = 24;

  private static final short EN_US = 0x409;  //MAKELANGID(LANG_ENGLISH, SUBLANG_ENGLISH_US)

  public static void main(String args[]) {
    if (!Platform.isWindows()) {System.out.println("For windows only"); return;}
    if (args == null || args.length < 2) usage();
    try {
      kernel = (KERNEL) Native.loadLibrary("kernel32", KERNEL.class);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    String exeFile = args[0];
    for(int a=1;a<args.length;a++) {
      String file = args[a];
      if (file.endsWith(".ico")) {
        addIcon(exeFile, file);
      }
      else if (file.endsWith(".manifest")) {
        addManifest(exeFile, file);
      }
      else if (file.endsWith(".cfg")) {
        addStrings(exeFile, file);
      }
      else {
        System.out.println("Unsupported file:" + file);
      }
    }
  }

  public static void addIcon(String exeFile, String icoFile) {
    try {
      //LoadIcon
      GRPICONHEADER grp = new GRPICONHEADER();
      ICONHEADER ih = new ICONHEADER();
      ICONIMAGE ii[];
      RandomAccessFile fis = new RandomAccessFile(icoFile, "r");
      byte tmp[] = new byte[6];
      if (!JF.readAll(fis, tmp, 0, 6)) throw new Exception("ico read error");
      ih.res = (short)LE.getuint16(tmp, 0);
      ih.type = (short)LE.getuint16(tmp, 2);
      ih.count = (short)LE.getuint16(tmp, 4);
//      System.out.println("count=" + ih.count);
      ih.icons = new ICONENTRY[ih.count];
      ih.write();
      ii = new ICONIMAGE[ih.count];
      for(int a=0;a<ih.count;a++) {
        ih.icons[a] = new ICONENTRY();
        int size = ih.icons[a].size();
        byte icon[] = new byte[size];
        if (!JF.readAll(fis, icon, 0, size)) throw new Exception("ico read error");
        ih.icons[a].write();
        ih.icons[a].getPointer().write(0, icon, 0, size);
        ih.icons[a].read();
      }
      for(int a=0;a<ih.count;a++) {
        ii[a] = new ICONIMAGE();
        fis.seek(ih.icons[a].imageOffset);
        int size = ih.icons[a].bytesInRes;
        byte data[] = new byte[size];
        if (!JF.readAll(fis, data, 0, size)) throw new Exception("ico read error");
        ii[a].xors_ands = new byte[size - ii[a].header.size() - ii[a].colors.size()];
        ii[a].write();
        ii[a].getPointer().write(0, data, 0, size);
        ii[a].read();
      }
      fis.close();
      //convert to resource format
      grp.entries = new GRPICONENTRY[ih.count];
      grp.res = 0;
      grp.type = 1;
      grp.count = ih.count;
      for(int a=0;a<ih.count;a++) {
        grp.entries[a] = new GRPICONENTRY();
        ICONENTRY ie = ih.icons[a];
        GRPICONENTRY ge = grp.entries[a];
        ge.bitCount = 0;
        ge.bytesInRes = ie.bitCnt;
        ge.bytesInRes2 = (short)ie.bytesInRes;
        ge.colourCount = ie.clrCnt;
        ge.height = ie.height;
        ge.id = (short)(a+1);
        ge.planes = (byte)ie.planes;
        ge.reserved = ie.reserved;
        ge.width = ie.width;
        ge.reserved2 = 0;
      }
      grp.write();

      //Begin
      Pointer exe = kernel.BeginUpdateResourceA(exeFile, false);
      if (exe == null) {
        System.out.println("Unable to open exe");
        return;
      }

      //add icon group
      kernel.UpdateResourceA(exe, new Pointer(RT_GROUP_ICON), new Pointer(1)
        , EN_US, grp.getPointer(), grp.size());

      //add icon(s)
      for(int a=0;a<ih.count;a++) {
        kernel.UpdateResourceA(exe, new Pointer(RT_ICON), new Pointer(a+1)
        , EN_US, ii[a].getPointer(), ii[a].size());
      }

      //end
      kernel.EndUpdateResourceA(exe, false);

      System.out.println("Added:" + icoFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void addManifest(String exeFile, String manifestFile) {
    try {
      RandomAccessFile fis = new RandomAccessFile(manifestFile, "r");
      int fs = (int)fis.length();
      byte tmp[] = new byte[fs];
      if (!JF.readAll(fis, tmp, 0, fs)) throw new Exception("read error");
      fis.close();
      Pointer p = malloc(fs);
      p.write(0, tmp, 0, fs);

      //Begin
      Pointer exe = kernel.BeginUpdateResourceA(exeFile, false);
      if (exe == null) {
        System.out.println("Unable to open exe");
        return;
      }

      //add manifest
      kernel.UpdateResourceA(exe, new Pointer(RT_MANIFEST), new Pointer(1)
        , EN_US, p, fs);

      //end
      kernel.EndUpdateResourceA(exe, false);

      free(p);

      System.out.println("Added:" + manifestFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void addStrings(String exeFile, String strFile) {
    try {
      RandomAccessFile fis = new RandomAccessFile(strFile, "r");
      int fs = (int)fis.length();
      byte tmp[] = new byte[fs];
      if (!JF.readAll(fis, tmp, 0, fs)) throw new Exception("read error");
      fis.close();
      Pointer p = malloc(fs);
      p.write(0, tmp, 0, fs);

      //Begin
      Pointer exe = kernel.BeginUpdateResourceA(exeFile, false);
      if (exe == null) {
        System.out.println("Unable to open exe");
        return;
      }

      //add manifest
      kernel.UpdateResourceA(exe, new Pointer(RT_RCDATA), new Pointer(1)
        , EN_US, p, fs);

      //end
      kernel.EndUpdateResourceA(exe, false);

      free(p);

      System.out.println("Added:" + strFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
