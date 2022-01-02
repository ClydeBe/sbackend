package com.thewheel.sawatu.shared.dto.validator.annotations;

import com.thewheel.sawatu.shared.dto.validator.processors.PeriodsValidProcessor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD })
@Constraint(validatedBy = PeriodsValidProcessor.class)
public @interface PeriodsValid {

    String message() default "Periods must be within 0 and 23";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
