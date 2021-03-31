package ua.com.foxminded.krailo.university.exception;

public class LessonDateOnHolidayException extends ServiceException {

    public LessonDateOnHolidayException() {
    }

    public LessonDateOnHolidayException(String message) {
	super(message);
    }

    public LessonDateOnHolidayException(Throwable cause) {
	super(cause);
    }

    public LessonDateOnHolidayException(String message, Throwable cause) {
	super(message, cause);
    }

}