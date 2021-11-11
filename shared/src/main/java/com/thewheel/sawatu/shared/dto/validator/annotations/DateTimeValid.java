package com.thewheel.sawatu.shared.dto.validator.annotations;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface DateTimeValid {
    String message() default "Invalid date. Date must be an ISO instant string";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
