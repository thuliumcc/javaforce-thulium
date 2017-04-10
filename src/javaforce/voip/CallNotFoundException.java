package javaforce.voip;


public class CallNotFoundException extends SipFailureException {
    public CallNotFoundException() {
        super(481, "CALL NOT FOUND");
    }
}
