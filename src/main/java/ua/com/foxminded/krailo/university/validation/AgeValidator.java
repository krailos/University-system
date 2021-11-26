package ua.com.foxminded.krailo.university.validation;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ua.com.foxminded.krailo.university.config.UniversityConfigProperties;

public class AgeValidator implements 
ConstraintValidator<AgeValidation, LocalDate> {
    
    private UniversityConfigProperties universityConfigProperties;
        
    public AgeValidator(UniversityConfigProperties universityConfigProperties) {
	this.universityConfigProperties = universityConfigProperties;
    }


    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
	 return Period.between(date, LocalDate.now()).getYears() > universityConfigProperties.getAgeConstraint();	 
    }

}
