
package ua.com.foxminded.krailo.university.exception;

public class VocationPeriodNotFreeException extends ServiceException {

    public VocationPeriodNotFreeException() {
    }

    public VocationPeriodNotFreeException(String message) {
	super(message);
    }

    public VocationPeriodNotFreeException(Throwable cause) {
	super(cause);
    }

    public VocationPeriodNotFreeException(String message, Throwable cause) {
	super(message, cause);
    }

}