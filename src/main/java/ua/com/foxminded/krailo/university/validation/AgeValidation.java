package ua.com.foxminded.krailo.university.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AgeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention (RetentionPolicy.RUNTIME)
public @interface AgeValidation {
    
    String message() default "age constraint";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
