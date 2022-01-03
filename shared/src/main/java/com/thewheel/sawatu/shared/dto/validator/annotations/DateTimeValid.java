package com.thewheel.sawatu.shared.dto.validator.annotations;

import com.thewheel.sawatu.shared.dto.validator.processors.DateTimeValidProcessor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = DateTimeValidProcessor.class)
public @interface DateTimeValid {
    String message() default "Invalid date. Date must be an ISO instant string";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
