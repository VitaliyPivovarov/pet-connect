package api.petpassport.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator implements ConstraintValidator<CustomPasswordValidation, String> {

    private static final short MAX_COUNT = 20;
    private static final short MIN_COUNT = 8;
    private static final String UPPER_CASE = "(.*[A-Z].*)";
    private static final String LOWER_CASE = "(.*[a-z].*)";
    private static final String NUMBERS = "(.*[0-9].*)";
    private static final String SPECIAL_CHARS = "(.*[!@#&()–[{}]:;',?/*~$^+=<>.\"%_|`].*)";

    @Override
    public void initialize(CustomPasswordValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        List<String> passwordExceptions = new ArrayList<>();
        String passwordExceptionStart = "Password must be: ";

        if (password.length() > MAX_COUNT || password.length() < MIN_COUNT) {
            passwordExceptions.add(String.format("be at least %s and at most %s characters", MIN_COUNT, MAX_COUNT));
        }

        if (!password.matches(UPPER_CASE)) {
            passwordExceptions.add("contain one capital letter");
        }

        if (!password.matches(LOWER_CASE)) {
            passwordExceptions.add("contain one lowercase letter");
        }

        if (!password.matches(NUMBERS)) {
            passwordExceptions.add("contain one digit");
        }

        if (!password.matches(SPECIAL_CHARS)) {
            passwordExceptions.add(String.format("contain one special character from %s", SPECIAL_CHARS));
        }

        if (!passwordExceptions.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(passwordExceptionStart.concat(String.join("; ", passwordExceptions)))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
