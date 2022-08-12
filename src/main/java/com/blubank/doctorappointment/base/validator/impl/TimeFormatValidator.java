package com.blubank.doctorappointment.base.validator.impl;

import com.blubank.doctorappointment.base.validator.TimeFormat;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeFormatValidator implements ConstraintValidator<TimeFormat, String> {
    @Override
    public void initialize(TimeFormat constraint) {
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext cxt) {
        return StringUtils.isEmpty(text) || text.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
    }

}
