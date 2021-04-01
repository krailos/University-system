package ua.com.foxminded.krailo.university.exception;

public class TeacherNotFreeException extends ServiceException {

    public TeacherNotFreeException() {
    }

    public TeacherNotFreeException(String message) {
	super(message);
    }

    public TeacherNotFreeException(Throwable cause) {
	super(cause);
    }

    public TeacherNotFreeException(String message, Throwable cause) {
	super(message, cause);
    }

}
