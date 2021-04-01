package ua.com.foxminded.krailo.university.exception;

public class VocationPeriodNotSameYearException extends ServiceException {

    public VocationPeriodNotSameYearException() {
    }

    public VocationPeriodNotSameYearException(String message) {
	super(message);
    }

    public VocationPeriodNotSameYearException(Throwable cause) {
	super(cause);
    }

    public VocationPeriodNotSameYearException(String message, Throwable cause) {
	super(message, cause);
    }

}
