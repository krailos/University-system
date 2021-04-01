package ua.com.foxminded.krailo.university.exception;

public class AudienceOverflowException extends ServiceException {

    public AudienceOverflowException() {
    }

    public AudienceOverflowException(String message) {
	super(message);
    }

    public AudienceOverflowException(Throwable cause) {
	super(cause);
    }

    public AudienceOverflowException(String message, Throwable cause) {
	super(message, cause);
    }

}
