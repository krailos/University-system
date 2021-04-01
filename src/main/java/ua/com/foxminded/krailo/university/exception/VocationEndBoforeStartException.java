package ua.com.foxminded.krailo.university.exception;

public class VocationEndBoforeStartException extends ServiceException {

    public VocationEndBoforeStartException() {
    }

    public VocationEndBoforeStartException(String message) {
	super(message);
    }

    public VocationEndBoforeStartException(Throwable cause) {
	super(cause);
    }

    public VocationEndBoforeStartException(String message, Throwable cause) {
	super(message, cause);
    }

}
