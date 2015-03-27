package javaforce.jna;

/** Linux PTY support.
 *
 * @author pquiring
 *
 * Created : Jan 17, 2014
 */

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import com.sun.jna.*;
import com.sun.jna.ptr.*;

import javaforce.*;

public class LnxPty {

  public static class termios extends Structure {
    public int c_iflag;
    public int c_oflag;
    public int c_cflag;
    public int c_lflag;
    public byte c_line;
    public byte c_cc[] = new byte[32];
    public int c_ispeed;
    public int c_ospeed;

    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
    public termios() {}
    public termios(Pointer ptr) {
      super(ptr);
    }
  }

  public static class winsize extends Structure {
    public short ws_row;
    public short ws_col;
    public short ws_xpixel;
    public short ws_ypixel;

    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
    public winsize() {}
    public winsize(Pointer ptr) {
      super(ptr);
    }
  }

  public static class timeval extends Structure {
    public NativeLong tv_sec, tv_usec;

    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
    public timeval() {}
    public timeval(Pointer ptr) {
      super(ptr);
    }
  }

  public static class fd_set extends Structure {
    public byte[] bits = new byte[128];  //must be 1024 bits

    protected List getFieldOrder() {
      return JF.makeFieldList(getClass());
    }
    public fd_set() {}
    public fd_set(Pointer ptr) {
      super(ptr);
    }
  }

  private void FD_ZERO(fd_set set) {
    for(int a=0;a<set.bits.length;a++) {
      set.bits[a] = 0;
    }
  }

  private void FD_SET(fd_set set, int fd) {
    int pos = fd / 8;
    int bit = 1 << (fd % 8);
    set.bits[pos] |= bit;
  }

  private void FD_CLR(fd_set set, int fd) {
    int pos = fd / 8;
    int bit = 1 << (fd % 8);
    set.bits[pos] &= (0xff - bit);
  }

  private boolean FD_ISSET(fd_set set, int fd) {
    int pos = fd / 8;
    int bit = 1 << (fd % 8);
    return (set.bits[pos] & bit) != 0;
  }

  public interface C extends Library {
    public int fork();
    public int posix_openpt(int o_flgs);
    public String ptsname(int fd);
    public int grantpt(int fd);
    public int unlockpt(int fd);
    public int ioctl(int fd, int cmd, Pointer args);
    public int execvpe(String cmd, String argv[], String env[]);
    public int open(String name, int flgs);
    public int close(int fd);
    public int tcgetattr(int fd, termios attrs);
    public int tcsetattr(int fd, int optacts, termios attrs);
    public void tcflush(int fd, int code);
    public int dup2(int fd, int fd2);
    public int signal(int sig, Pointer handler);
    public int read(int fd, Pointer buf, int bufsiz);
    public int write(int fd, Pointer buf, int bufsiz);
    public int setsid();
    public int select(int nfds, Pointer read_set, Pointer write_set, Pointer error_set, timeval timeout);
    public int fcntl(int fd, int cmd, int arg);
    public int waitpid(int pid, IntByReference status, int opts);
  }

  public interface Util extends Library {
    public int forkpty(IntByReference master, Pointer name, Pointer termios, Pointer winsize);
  }

  private static C c;
  private static Util util;

  private static final int O_RDWR = 02;
  private static final int O_NOCTTY = 0400;
  private static final int O_NONBLOCK = 04000;
  //FNDELAY -> O_NDELAY -> O_NONBLOCK
  private static final int FNDELAY = 04000;
  private static final int SIGINT = 2;
  private static final int SIGQUIT = 3;
  private static final int SIGCHLD = 17;
  private static final Pointer SIG_DFL = null;
  private static final int STDIN_FILENO = 0;
  private static final int STDOUT_FILENO = 1;
  private static final int STDERR_FILENO = 2;
  private static final int VERASE = 2;
  private static final int IUTF8 = 040000;
  private static final int IXON = 02000;
  private static final int TIOCSWINSZ = 0x5414;
  private static final int FIONREAD = 0x541b;
  private static final int TCSANOW = 0;
  private static final int VMIN = 6;
  private static final int VTIME = 5;
  private static final int ICANON = 02;
  private static final int F_GETFL = 3;
  private static final int F_SETFL = 4;
  private static final int CS8 = 060;
  private static final int CREAD = 0200;
  private static final int CLOCAL = 04000;
  private static final int TCIFLUSH = 0;

  public static boolean init() {
    if (c != null) return true;
    try {
      c = (C)Native.loadLibrary("c", C.class);
      util = (Util)Native.loadLibrary("util", Util.class);
      return true;
    } catch (Throwable t) {
      JFLog.log(t);
      return false;
    }
  }

  /** Spawns cmd with args and env (both must be null terminated arrays) and returns new pty. */
  public static LnxPty exec(String cmd, String args[], String env[]) {
    LnxPty pty = new LnxPty();
    if (!pty.fork(cmd, args, env)) return null;
    return pty;
  }

  /** Spawns cmd with args and env (both must be null terminated arrays).
   * Captures all output (stdout and stderr) and returns it.
   * Does not return until child process exits. */
  public static String execOutput(String cmd, String args[], String env[]) {
    LnxPty pty = new LnxPty();
    if (!pty.fork(cmd, args, env)) return null;
    StringBuilder sb = new StringBuilder();
    byte data[] = new byte[1024];
    while (true) {
      int read = pty.read(data);
      if (read == -1) break;
      sb.append(new String(data, 0, read));
    }
    pty.close();
    return sb.toString();
  }

  public boolean isClosed() {
    return closed;
  }

  private int master = -1;
  private boolean closed = false;
  private long writeBuf, readBuf;
//  private termios orgattrs;

  private int pid;
  private Process p;

  /** Spawns a new process with a new pty.
   * Note:args, env MUST be null terminated.
   */
  private boolean fork(String cmd, String args[], String env[]) {
    return fork_nofork(cmd,args,env);
  }

  /** This method uses Java to fork but the child process starts in LnxPty.main()
   *
   * Works 100% of the time.
   */
  private boolean fork_nofork(String cmd, String args[], String env[]) {
    String slaveName;
    master = c.posix_openpt(O_RDWR | O_NOCTTY);
    if (master == -1) return false;
    slaveName = c.ptsname(master);
    if (slaveName == null) {
      JFLog.log("LnxPty:slave pty == null");
      return false;
    }
    if (c.grantpt(master) != 0) {
      JFLog.log("LnxPty:grantpt() failed");
      return false;
    }
    if (c.unlockpt(master) != 0) {
      JFLog.log("LnxPty:unlockpt() failed");
      return false;
    }

    ArrayList<String> cmdline = new ArrayList<String>();
    cmdline.add("java");
    cmdline.add("-cp");
    cmdline.add("/usr/share/java/javaforce.jar:/usr/share/java/jna.jar");
    cmdline.add("javaforce.jna.LnxPty");
    cmdline.add(slaveName);
    cmdline.add(cmd);
    cmdline.add("" + (args.length-1));  //# args
    for(int a=0;a<args.length;a++) {
      if (args[a] == null) break;
      cmdline.add(args[a]);
    }
    for(int a=0;a<env.length;a++) {
      if (env[a] == null) break;
      cmdline.add(env[a]);
    }
    String cl[] = cmdline.toArray(new String[0]);
/*
    for(int a=0;a<cl.length;a++) {
      JFLog.log("cmd=" + cl[a]);
    }
*/
    try {
      ProcessBuilder pb = new ProcessBuilder(cl);
      String user = System.getenv("USER");
      if (user != null) {
        if (user.equals("root")) {
          pb.directory(new File("/root"));
        } else {
          pb.directory(new File("/home/" + user));
        }
      }
      p = pb.start();
    } catch (Exception e) {
      JFLog.log(e);
      return false;
    }

    writeBuf = Native.malloc(1024);
    readBuf = Native.malloc(1024);
    new Thread() {
      public void run() {
        try {p.waitFor();} catch (Exception e) {}
        close();
      }
    }.start();
    return true;
  }

  /** This is the child process for fork_nofork() implementation.
   */
  public static void main(String args[]) {
    if (args == null || args.length < 3) {
      System.out.println("Usage : LnxPty slaveName, cmd, #args, [args...], [env...]");
      return;
    }
    init();

    String slaveName = args[0];
    String cmd = args[1];
    int noArgs = JF.atoi(args[2]);
    int p = 3;
    ArrayList<String> process_args = new ArrayList<String>();
    ArrayList<String> process_env = new ArrayList<String>();
    for(int a=0;a<noArgs;a++) {
      process_args.add(args[p++]);
    }
    while (p < args.length) {
      process_env.add(args[p++]);
    }

    termios attrs = new termios();

    try {
      int slave = c.open(slaveName, O_RDWR);  //should open this in child process
      if (slave == -1) {
        System.out.println("LnxPty:unable to open slave pty");
        System.exit(0);
      }
      if (c.setsid() == -1) {
        System.out.println("LnxPty:unable to setsid");
        System.exit(0);
      }
      c.tcgetattr(slave, attrs);
      // Assume input is UTF-8; this allows character-erase to be correctly performed in cooked mode.
      attrs.c_iflag |= IUTF8;
      // Humans don't need XON/XOFF flow control of output, and it only serves to confuse those who accidentally hit ^S or ^Q, so turn it off.
      attrs.c_iflag &= ~IXON;
      // ???
      attrs.c_cc[VERASE] = 127;
      c.tcsetattr(slave, TCSANOW, attrs);
      c.dup2(slave, STDIN_FILENO);
      c.dup2(slave, STDOUT_FILENO);
      c.dup2(slave, STDERR_FILENO);
      c.signal(SIGINT, SIG_DFL);
      c.signal(SIGQUIT, SIG_DFL);
      c.signal(SIGCHLD, SIG_DFL);
      c.execvpe(cmd, process_args.toArray(new String[0]), process_env.toArray(new String[0]));
      System.exit(0);  //should not happen
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  /** This method uses ProcessBuilder to execute bash directly
   * but uses redirectInput/Output/Error(File) to redirect stdin/out/err to the pty.
   *
   * I can not call setsid() in the new process so bash is complaining about ioctl
   * issues and the pty is not valid.
   *
   */
  private boolean fork_java(String cmd, String args[], String env[]) {
    ProcessBuilder pb = new ProcessBuilder();
    master = c.posix_openpt(O_RDWR | O_NOCTTY);
    if (master == -1) return false;
    String slaveName = c.ptsname(master);
    if (slaveName == null) {
      JFLog.log("LnxPty:slave pty == null");
      return false;
    }
    if (c.grantpt(master) != 0) {
      JFLog.log("LnxPty:grantpt() failed");
      return false;
    }
    if (c.unlockpt(master) != 0) {
      JFLog.log("LnxPty:unlockpt() failed");
      return false;
    }
    File slaveFile = new File(slaveName);
    ArrayList<String> cmdlist = new ArrayList<String>();
    cmdlist.add(cmd);
    for(int a=1;a<args.length;a++) {
      if (args[a] == null) break;
      cmdlist.add(args[a]);
    }
    writeBuf = Native.malloc(1024);
    readBuf = Native.malloc(1024);
    try {
      pb.command(cmdlist);
      for(int a=0;a<env.length;a++) {
        if (env[a] == null) break;
        int idx = env[a].indexOf("=");
        if (idx == -1) continue;  //bad env
        String key = env[a].substring(0, idx);
        String val = env[a].substring(idx+1);
        pb.environment().put(key, val);
      }
      pb.redirectInput(slaveFile);
      pb.redirectOutput(slaveFile);
      pb.redirectError(slaveFile);
      pb.directory(new File("/home/" + System.getenv("USER")));
      p = pb.start();
      new Thread() {
        public void run() {
          try { p.waitFor(); } catch (Exception e) { JFLog.log(e); }
          p = null;
          close();
        }
      }.start();
    } catch (Exception e) {
      JFLog.log(e);
      return false;
    }
    return true;
  }

  /** This method uses the forkpty() from the util library.
   *
   * Fails 30% of the time.
   */
  private boolean fork_forkpty(String cmd, String args[], String env[]) {
    IntByReference masterRef = new IntByReference();
//    termios attrs = new termios();
//    winsize size = new winsize();
    pid = util.forkpty(masterRef, null, null, null);  //attrs.getPointer(), size.getPointer());
    if (pid == 0) {
      //child process (slave)
      c.execvpe(cmd, args, env);  //searches path for cmd
      System.exit(0);
    }
    //parent process (master)
    master = masterRef.getValue();
    writeBuf = Native.malloc(1024);
    readBuf = Native.malloc(1024);
    new Thread() {
      public void run() {
        c.waitpid(pid, new IntByReference(), 0);
        close();
      }
    }.start();
    return true;
  }

  /** This method uses just the c library.
   *
   * Fails 30% of the time.
   */
  private boolean fork_old(String cmd, String args[], String env[]) {
    String slaveName;
    master = c.posix_openpt(O_RDWR | O_NOCTTY);
    if (master == -1) return false;
    slaveName = c.ptsname(master);
    if (slaveName == null) {
      JFLog.log("LnxPty:slave pty == null");
      return false;
    }
    if (c.grantpt(master) != 0) {
      JFLog.log("LnxPty:grantpt() failed");
      return false;
    }
    if (c.unlockpt(master) != 0) {
      JFLog.log("LnxPty:unlockpt() failed");
      return false;
    }

    termios attrs = new termios();

    int slave = c.open(slaveName, O_RDWR);  //should open this in child process
    if (slave == -1) {
      JFLog.log("LnxPty:unable to open slave pty");
      return false;
    }
    final int pid = c.fork();
    if (pid < 0) {
      JFLog.log("LnxPty:fork() failed");
      return false;
    }
    if (pid == 0) {
      //run child (try and do as little here)
      if (c.setsid() == -1) System.exit(0);
      c.close(master);  //close master fd in child process
      c.tcgetattr(slave, attrs);
      // Assume input is UTF-8; this allows character-erase to be correctly performed in cooked mode.
      attrs.c_iflag |= IUTF8;
      // Humans don't need XON/XOFF flow control of output, and it only serves to confuse those who accidentally hit ^S or ^Q, so turn it off.
      attrs.c_iflag &= ~IXON;
      // ???
      attrs.c_cc[VERASE] = 127;
      c.tcsetattr(slave, TCSANOW, attrs);
      c.dup2(slave, STDIN_FILENO);
      c.dup2(slave, STDOUT_FILENO);
      c.dup2(slave, STDERR_FILENO);
      c.signal(SIGINT, SIG_DFL);
      c.signal(SIGQUIT, SIG_DFL);
      c.signal(SIGCHLD, SIG_DFL);
      c.execvpe(cmd, args, env);  //searches path for cmd
      System.exit(0);
      return false;  //should never happen
    } else {
      c.close(slave);  //should open in child process
      writeBuf = Native.malloc(1024);
      readBuf = Native.malloc(1024);
      new Thread() {
        public void run() {
          c.waitpid(pid, new IntByReference(), 0);
          close();
        }
      }.start();
      return true;
    }
  }

  /** Frees resources */
  public synchronized void close() {
    if (closed) return;
    if (master != -1) {
      c.close(master);
//      master = -1;  //do NOT clear it - reader thread may still be running
    }
    if (writeBuf != 0) {
      Native.free(writeBuf);
      writeBuf = 0;
    }
    if (readBuf != 0) {
      Native.free(readBuf);
      readBuf = 0;
    }
    closed = true;
  }

  /** Writes to child process (max 1024 bytes) */
  public void write(byte buf[]) {
    Pointer p = new Pointer(writeBuf);
    p.write(0, buf, 0, buf.length);
    c.write(master, p, buf.length);
  }

  /** Reads from child process (max 1024 bytes) */
  public int read(byte buf[]) {
    if (closed) return -1;
    timeval timeout = new timeval();
    timeout.tv_sec = new NativeLong(1);  //1 full second
    timeout.tv_usec = new NativeLong(0);
    fd_set read_set = new fd_set();
    FD_SET(read_set, master);
    read_set.write();

    fd_set error_set = new fd_set();
    FD_SET(error_set, master);
    error_set.write();

    int res = c.select(master+1, read_set.getPointer(), null, error_set.getPointer(), timeout);
    if (res == -1) {
      //select error
      return -1;
    }
    if (res == 0) {
      //timeout
      return 0;
    }
    read_set.read();
    error_set.read();

    if (closed) {
      return -1;
    }

    if (FD_ISSET(error_set, master)) {
      return -1;
    }
    if (FD_ISSET(read_set, master)) {
      Pointer p = new Pointer(readBuf);
      int read = c.read(master, p, buf.length);
      if (read > 0) {
        p.read(0, buf, 0, read);
      }
      return read;
    }
    JFLog.log("LnxPty:select() : unknown reason");
    return -1;
  }

  /** Sets the size of the pty */
  public void setSize(int x, int y) {
    winsize size = new winsize();
    size.ws_row = (short)y;
    size.ws_col = (short)x;
    size.ws_xpixel = (short)(x*8);
    size.ws_ypixel = (short)(y*8);
    size.write();
    c.ioctl(master, TIOCSWINSZ, size.getPointer());
  }

  /** Returns current processes environment variables plus those passed in extra.
   * extra overrides current variables.
   */
  public static String[] makeEnvironment(String extra[]) {
    ArrayList<String> env = new ArrayList<String>();
    Map<String, String> old = System.getenv();
    Set<String> set = old.keySet();
    String keys[] = (String[])set.toArray(new String[0]);
    for(int a=0;a<keys.length;a++) {
      String key = keys[a];
      env.add(key + "=" + old.get(key));
    }
    if (extra != null) {
      for(int a=0;a<extra.length;a++) {
        int idx = extra[a].indexOf("=");
        if (idx == -1) continue;  //bad string
        String key = extra[a].substring(0, idx+1);
        int cnt = env.size();
        boolean found = false;
        for(int b=0;b<cnt;b++) {
          if (env.get(b).startsWith(key)) {
            env.set(b, extra[a]);
            found = true;
            break;
          }
        }
        if (!found) {
          env.add(extra[a]);
        }
      }
    }
    env.add(null);
    return env.toArray(new String[0]);
  }
  public static String[] makeEnvironment() {
    return makeEnvironment(null);
  }
}
