package iit.unimiskolc.FRI.exception;

public class MtmtConfigurationException extends RuntimeException {
    public MtmtConfigurationException(final String message) {
        super(message);
    }

    public MtmtConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
