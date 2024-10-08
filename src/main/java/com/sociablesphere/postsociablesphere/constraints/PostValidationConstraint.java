package com.sociablesphere.postsociablesphere.constraints;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PostValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PostValidationConstraint {
    String message() default "Invalid post configuration.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
