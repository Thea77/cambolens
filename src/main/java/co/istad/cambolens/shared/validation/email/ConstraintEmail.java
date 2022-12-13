package co.istad.cambolens.shared.validation.email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConstraintEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstraintEmail {

    String message() default "Email is already existed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
