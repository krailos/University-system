package ua.com.foxminded.krailo.university.exception;

public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException() {
    }

    public ConstraintViolationException(String message) {
	super(message);
    }

    public ConstraintViolationException(Throwable cause) {
	super(cause);
    }

    public ConstraintViolationException(String message, Throwable cause) {
	super(message, cause);
    }

}
