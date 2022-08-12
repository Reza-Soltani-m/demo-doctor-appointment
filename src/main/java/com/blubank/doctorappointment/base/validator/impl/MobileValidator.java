package com.blubank.doctorappointment.base.validator.impl;

import com.blubank.doctorappointment.base.validator.Mobile;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
    @Override
    public void initialize(Mobile constraint) {
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext cxt) {
        return StringUtils.isEmpty(text) || text.matches("\\d{11}") && text.startsWith("09");
    }

}
