package api.petpassport.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
public @interface CustomPasswordValidation {
    String message() default "Invalid password!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
