package javaforce;

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

    public static boolean log(String msg) {
        return log(0, msg);
    }

    private static boolean log(int id, String msg) {
        logger.log(JFLog.class.getName(), Level.DEBUG, msg, null);
        return true;
    }

    public static boolean log(Throwable t) {
        return log(0, t);
    }

    private static boolean log(int id, Throwable t) {
        logger.warn(t.getMessage(), t);
        return true;
    }

    public static void setEnabled(boolean state) {
    }
}
