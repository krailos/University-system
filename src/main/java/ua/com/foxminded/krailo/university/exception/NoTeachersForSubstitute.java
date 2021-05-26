package ua.com.foxminded.krailo.university.exception;

public class NoTeachersForSubstitute extends ServiceException {

    public NoTeachersForSubstitute() {
    }

    public NoTeachersForSubstitute(String message) {
	super(message);
    }

    public NoTeachersForSubstitute(Throwable cause) {
	super(cause);
    }

    public NoTeachersForSubstitute(String message, Throwable cause) {
	super(message, cause);
    }

}
