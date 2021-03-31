package ua.com.foxminded.krailo.university.exception;

public class TeacherOnVocationException extends ServiceException {

    public TeacherOnVocationException() {
    }

    public TeacherOnVocationException(String message) {
	super(message);
    }

    public TeacherOnVocationException(Throwable cause) {
	super(cause);
    }

    public TeacherOnVocationException(String message, Throwable cause) {
	super(message, cause);
    }

}