package com.blubank.doctorappointment.appointment.dto.request;

import com.blubank.doctorappointment.base.validator.DateFormat;
import com.blubank.doctorappointment.base.validator.TimeFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddAppointmentRequest {

    @NotNull
    @DateFormat
    String day;

    @NotNull
    @TimeFormat
    String startTime;

    @NotNull
    @TimeFormat
    String endTime;
}
