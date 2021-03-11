package ua.com.foxminded.krailo.university.exception;

public class AudienceNumberNotUniqueException extends ServiceException {

    public AudienceNumberNotUniqueException() {
    }

    public AudienceNumberNotUniqueException(String message) {
	super(message);
    }

    public AudienceNumberNotUniqueException(Throwable cause) {
	super(cause);
    }

    public AudienceNumberNotUniqueException(String message, Throwable cause) {
	super(message, cause);
    }

}
