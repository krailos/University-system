package ua.com.foxminded.krailo.university.exception;

public class GroupNameNotUniqueException extends ServiceException {

    public GroupNameNotUniqueException() {
    }

    public GroupNameNotUniqueException(String message) {
	super(message);
    }

    public GroupNameNotUniqueException(Throwable cause) {
	super(cause);
    }

    public GroupNameNotUniqueException(String message, Throwable cause) {
	super(message, cause);
    }

}
