package ua.com.foxminded.krailo.university.exception;

public class SpecialityNameNotUniqueException extends ServiceException {

    public SpecialityNameNotUniqueException() {
    }

    public SpecialityNameNotUniqueException(String message) {
	super(message);
    }

    public SpecialityNameNotUniqueException(Throwable cause) {
	super(cause);
    }

    public SpecialityNameNotUniqueException(String message, Throwable cause) {
	super(message, cause);
    }

}
