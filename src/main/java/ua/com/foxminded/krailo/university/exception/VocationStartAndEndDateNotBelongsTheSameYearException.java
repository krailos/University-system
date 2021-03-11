package ua.com.foxminded.krailo.university.exception;

public class VocationStartAndEndDateNotBelongsTheSameYearException extends ServiceException {

    public VocationStartAndEndDateNotBelongsTheSameYearException() {
    }

    public VocationStartAndEndDateNotBelongsTheSameYearException(String message) {
	super(message);
    }

    public VocationStartAndEndDateNotBelongsTheSameYearException(Throwable cause) {
	super(cause);
    }

    public VocationStartAndEndDateNotBelongsTheSameYearException(String message, Throwable cause) {
	super(message, cause);
    }

}
