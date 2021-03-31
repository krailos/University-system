package ua.com.foxminded.krailo.university.exception;

public class DaoConstraintViolationException extends DaoException {

    public DaoConstraintViolationException() {
    }

    public DaoConstraintViolationException(String message) {
	super(message);
    }

    public DaoConstraintViolationException(Throwable cause) {
	super(cause);
    }

    public DaoConstraintViolationException(String message, Throwable cause) {
	super(message, cause);
    }

}