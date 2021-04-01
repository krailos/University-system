package ua.com.foxminded.krailo.university.exception;

public class LessonDateOnWeekendException extends ServiceException {

    public LessonDateOnWeekendException() {
    }

    public LessonDateOnWeekendException(String message) {
	super(message);
    }

    public LessonDateOnWeekendException(Throwable cause) {
	super(cause);
    }

    public LessonDateOnWeekendException(String message, Throwable cause) {
	super(message, cause);
    }

}
