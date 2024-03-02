package iit.unimiskolc.FRI.exception;

public class FriDataServiceException extends RuntimeException {


    public FriDataServiceException(final String message) {
        super(message);
    }

    public FriDataServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
