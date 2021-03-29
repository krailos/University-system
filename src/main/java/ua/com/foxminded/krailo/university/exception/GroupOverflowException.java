package ua.com.foxminded.krailo.university.exception;

public class GroupOverflowException extends ServiceException {

    public GroupOverflowException() {
    }

    public GroupOverflowException(String message) {
	super(message);
    }

    public GroupOverflowException(Throwable cause) {
	super(cause);
    }

    public GroupOverflowException(String message, Throwable cause) {
	super(message, cause);
    }

}
