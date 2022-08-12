package com.blubank.doctorappointment.appointment.dto.request;

import com.blubank.doctorappointment.base.validator.Mobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class TakeAppointmentRequest {
    @NotNull
    @Mobile
    String phoneNumber;

    @NotNull
    @Length(min = 3, max = 15)
    String name;
}
