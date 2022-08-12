package com.blubank.doctorappointment.base.validator;

import com.blubank.doctorappointment.base.validator.impl.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mobile {
    String message() default "mobile format is not correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
