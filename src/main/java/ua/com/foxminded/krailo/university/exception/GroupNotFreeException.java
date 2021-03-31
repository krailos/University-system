package ua.com.foxminded.krailo.university.exception;

public class GroupNotFreeException extends ServiceException {

    public GroupNotFreeException() {
    }

    public GroupNotFreeException(String message) {
	super(message);
    }

    public GroupNotFreeException(Throwable cause) {
	super(cause);
    }

    public GroupNotFreeException(String message, Throwable cause) {
	super(message, cause);
    }

}