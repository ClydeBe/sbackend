package com.thewheel.sawatu.shared.dto.validator.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thewheel.sawatu.shared.dto.validator.annotations.JsonValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JsonValidProcessor implements ConstraintValidator<JsonValid, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void initialize(JsonValid constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            mapper.readTree(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
