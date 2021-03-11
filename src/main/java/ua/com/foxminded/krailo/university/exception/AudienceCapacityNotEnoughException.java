package ua.com.foxminded.krailo.university.exception;

public class AudienceCapacityNotEnoughException extends ServiceException {

    public AudienceCapacityNotEnoughException() {
    }

    public AudienceCapacityNotEnoughException(String message) {
	super(message);
    }

    public AudienceCapacityNotEnoughException(Throwable cause) {
	super(cause);
    }

    public AudienceCapacityNotEnoughException(String message, Throwable cause) {
	super(message, cause);
    }

}
