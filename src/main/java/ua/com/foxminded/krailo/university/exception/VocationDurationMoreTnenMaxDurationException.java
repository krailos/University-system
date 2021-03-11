package ua.com.foxminded.krailo.university.exception;

public class VocationDurationMoreTnenMaxDurationException extends ServiceException {

    public VocationDurationMoreTnenMaxDurationException() {
    }

    public VocationDurationMoreTnenMaxDurationException(String message) {
	super(message);
    }

    public VocationDurationMoreTnenMaxDurationException(Throwable cause) {
	super(cause);
    }

    public VocationDurationMoreTnenMaxDurationException(String message, Throwable cause) {
	super(message, cause);
    }

}
