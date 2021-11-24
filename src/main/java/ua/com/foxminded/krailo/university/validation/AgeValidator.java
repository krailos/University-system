package ua.com.foxminded.krailo.university.validation;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeValidator implements 
ConstraintValidator<AgeValidation, LocalDate> {
    
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
	 return Period.between(date, LocalDate.now()).getYears() > 14;	 
    }

}
