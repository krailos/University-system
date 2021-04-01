package ua.com.foxminded.krailo.university.exception;

public class NotUniqueNameException extends ServiceException {

    public NotUniqueNameException() {
    }

    public NotUniqueNameException(String message) {
	super(message);
    }

    public NotUniqueNameException(Throwable cause) {
	super(cause);
    }

    public NotUniqueNameException(String message, Throwable cause) {
	super(message, cause);
    }

}
