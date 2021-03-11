package ua.com.foxminded.krailo.university.exception;

public class VocationEndDateBeforeStartDateException extends ServiceException {

    public VocationEndDateBeforeStartDateException() {
    }

    public VocationEndDateBeforeStartDateException(String message) {
	super(message);
    }

    public VocationEndDateBeforeStartDateException(Throwable cause) {
	super(cause);
    }

    public VocationEndDateBeforeStartDateException(String message, Throwable cause) {
	super(message, cause);
    }

}
