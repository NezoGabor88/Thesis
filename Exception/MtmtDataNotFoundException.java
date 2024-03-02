package iit.unimiskolc.FRI.exception;

public class MtmtDataNotFoundException extends RuntimeException {
    public MtmtDataNotFoundException(final String message) {
        super(message);
    }

    public MtmtDataNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
