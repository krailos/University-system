package ua.com.foxminded.krailo.university.exception;

public class GroupCapacityTooBigException extends ServiceException {

    public GroupCapacityTooBigException() {
    }

    public GroupCapacityTooBigException(String message) {
	super(message);
    }

    public GroupCapacityTooBigException(Throwable cause) {
	super(cause);
    }

    public GroupCapacityTooBigException(String message, Throwable cause) {
	super(message, cause);
    }

}
