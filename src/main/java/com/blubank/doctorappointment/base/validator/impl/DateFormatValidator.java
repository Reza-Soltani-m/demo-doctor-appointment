package com.blubank.doctorappointment.base.validator.impl;

import com.blubank.doctorappointment.base.validator.DateFormat;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    @Override
    public void initialize(DateFormat constraint) {
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext cxt) {
        return StringUtils.isEmpty(text) || text.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$");
    }

}
