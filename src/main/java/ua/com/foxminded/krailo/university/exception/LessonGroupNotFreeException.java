package ua.com.foxminded.krailo.university.exception;

public class LessonGroupNotFreeException extends ServiceException {

    public LessonGroupNotFreeException() {
    }

    public LessonGroupNotFreeException(String message) {
	super(message);
    }

    public LessonGroupNotFreeException(Throwable cause) {
	super(cause);
    }

    public LessonGroupNotFreeException(String message, Throwable cause) {
	super(message, cause);
    }

}
