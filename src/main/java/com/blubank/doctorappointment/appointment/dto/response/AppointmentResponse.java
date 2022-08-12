package com.blubank.doctorappointment.appointment.dto.response;

import com.blubank.doctorappointment.appointment.model.Appointment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class AppointmentResponse {
    private Integer id;
    private String day;
    private String startTime;
    private String endTime;

    public AppointmentResponse(Appointment appointment) {
        this.id = appointment.getId();
        this.day = new SimpleDateFormat("yyyy-MM-dd").format(appointment.getStartTime());
        this.startTime = new SimpleDateFormat("HH:mm").format(appointment.getStartTime());
        this.endTime = new SimpleDateFormat("HH:mm").format(appointment.getEndTime());
    }
}
