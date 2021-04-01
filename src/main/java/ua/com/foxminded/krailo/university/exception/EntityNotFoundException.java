package ua.com.foxminded.krailo.university.exception;

public class EntityNotFoundException extends DaoException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
	super(message);
    }

    public EntityNotFoundException(Throwable cause) {
	super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause) {
	super(message, cause);
    }

}
