package ua.com.foxminded.krailo.university.exception;

public class QueryNotExecuteException extends RuntimeException {

    public QueryNotExecuteException() {
    }

    public QueryNotExecuteException(String message) {
	super(message);
    }

    public QueryNotExecuteException(Throwable cause) {
	super(cause);
    }

    public QueryNotExecuteException(String message, Throwable cause) {
	super(message, cause);
    }

}
