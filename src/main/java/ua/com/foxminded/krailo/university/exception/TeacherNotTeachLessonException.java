package ua.com.foxminded.krailo.university.exception;

public class TeacherNotTeachLessonException extends ServiceException {

    public TeacherNotTeachLessonException() {
    }

    public TeacherNotTeachLessonException(String message) {
	super(message);
    }

    public TeacherNotTeachLessonException(Throwable cause) {
	super(cause);
    }

    public TeacherNotTeachLessonException(String message, Throwable cause) {
	super(message, cause);
    }

}
