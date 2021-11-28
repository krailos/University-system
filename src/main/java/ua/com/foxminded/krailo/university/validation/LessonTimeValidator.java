package ua.com.foxminded.krailo.university.validation;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ua.com.foxminded.krailo.university.model.LessonTime;

public class LessonTimeValidator implements ConstraintValidator<LessonTimeValidation, LessonTime> {

    @Override
    public boolean isValid(LessonTime value, ConstraintValidatorContext context) {
	LocalTime start = value.getStartTime();
	LocalTime end = value.getEndTime();
	return start.isBefore(end);
    }

}
