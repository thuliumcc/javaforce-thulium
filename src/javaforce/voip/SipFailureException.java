package javaforce.voip;

public class SipFailureException extends RuntimeException {
    private int code;
    private String reason;

    public SipFailureException(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
