package javaforce;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class JFLog {
    private static final Logger logger = Logger.getLogger("JavaForce");

    public static boolean init(String filename, boolean stdout) {
        return true;
    }

    public static boolean init(int id, String filename, boolean stdout) {
        return true;
    }

    public static boolean append(String filename, boolean stdout) {
        return true;
    }

    public static boolean trace(String msg) {
        return log(msg, Level.TRACE);
    }

    public static boolean log(String msg) {
        return log(msg, Level.DEBUG);
    }

    private static boolean log(String msg, Level level) {
        logger.log(JFLog.class.getName(), level, msg, null);
        return true;
    }

    public static boolean log(Throwable t) {
        logger.warn(t.getMessage(), t);
        return true;
    }

    public static void setEnabled(boolean state) {
    }
}
