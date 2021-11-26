package ua.com.foxminded.krailo.university.validation;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class LessonTimeValidator implements ConstraintValidator<LessonTimeValidation, Object> {

    private String startLessonTime;
    private String endLessonTime;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
	Object lessonStart = new BeanWrapperImpl(value).getPropertyValue(startLessonTime);
	Object lessonEnd = new BeanWrapperImpl(value).getPropertyValue(endLessonTime);

	LocalTime start = (LocalTime) lessonStart;
	LocalTime end = (LocalTime) lessonEnd;
	return start.isBefore(end);
    }

}
