package com.thewheel.sawatu.shared.dto.validator.processors;

import com.thewheel.sawatu.shared.dto.validator.annotations.PeriodsValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PeriodsValidProcessor implements ConstraintValidator<PeriodsValid, List<Integer>> {

    @Override
    public void initialize(PeriodsValid constraintAnnotation) {}

    @Override
    public boolean isValid(List<Integer> value, ConstraintValidatorContext context) {
        if(value == null) return false;
        return  value.stream().allMatch(period -> 0 <= period && period < 24);
    }
}
