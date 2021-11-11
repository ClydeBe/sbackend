package com.thewheel.sawatu.shared.dto.validator.annotations;


import com.thewheel.sawatu.shared.dto.validator.processors.JsonValidProcessor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = JsonValidProcessor.class)
public @interface JsonValid {
    String message() default "String value is not a valid JSON";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
