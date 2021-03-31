package ua.com.foxminded.krailo.university.exception;

public class AudienceNotFreeException extends ServiceException {

    public AudienceNotFreeException() {
    }

    public AudienceNotFreeException(String message) {
	super(message);
    }

    public AudienceNotFreeException(Throwable cause) {
	super(cause);
    }

    public AudienceNotFreeException(String message, Throwable cause) {
	super(message, cause);
    }

}