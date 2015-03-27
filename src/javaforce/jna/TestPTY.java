package javaforce.jna;

import javaforce.JF;

/** Test LnxPty function
 *
 * @author pquiring
 */

public class TestPTY {
  public static void main(String args[]) {
    LnxPty.init();
    byte buf[] = new byte[1024];
    while (true) {
      LnxPty pty = LnxPty.exec("bash", new String[] {"bash", "-i" ,"-l"}, new String[] {});
      pty.write("ls\n".getBytes());
      int read = pty.read(buf);
      System.out.print(new String(buf,0,read));
      pty.write("exit\n".getBytes());
      while (!pty.isClosed()) {
        JF.sleep(100);
      }
    }
  }
}
