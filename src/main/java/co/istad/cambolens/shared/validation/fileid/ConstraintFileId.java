package co.istad.cambolens.shared.validation.fileid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ConstraintFileIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstraintFileId {
    
    String message() default "File ID does not exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
