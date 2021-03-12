package ua.com.foxminded.krailo.university.exception;

public class VocationPeriodTooLongException extends ServiceException {

    public VocationPeriodTooLongException() {
    }

    public VocationPeriodTooLongException(String message) {
	super(message);
    }

    public VocationPeriodTooLongException(Throwable cause) {
	super(cause);
    }

    public VocationPeriodTooLongException(String message, Throwable cause) {
	super(message, cause);
    }

}
