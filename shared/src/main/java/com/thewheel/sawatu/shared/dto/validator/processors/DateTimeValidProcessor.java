package com.thewheel.sawatu.shared.dto.validator.processors;

import com.thewheel.sawatu.shared.dto.validator.annotations.DateTimeValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeValidProcessor implements ConstraintValidator<DateTimeValid, LocalDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    @Override
    public void initialize(DateTimeValid constraintAnnotation) {}

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if(value == null) return false;
        try {
            LocalDateTime.parse(value.toString(), formatter);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }
}
