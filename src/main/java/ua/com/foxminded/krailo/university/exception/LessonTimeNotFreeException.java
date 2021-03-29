package ua.com.foxminded.krailo.university.exception;

public class LessonTimeNotFreeException extends ServiceException {

    public LessonTimeNotFreeException() {
    }

    public LessonTimeNotFreeException(String message) {
	super(message);
    }

    public LessonTimeNotFreeException(Throwable cause) {
	super(cause);
    }

    public LessonTimeNotFreeException(String message, Throwable cause) {
	super(message, cause);
    }

}
