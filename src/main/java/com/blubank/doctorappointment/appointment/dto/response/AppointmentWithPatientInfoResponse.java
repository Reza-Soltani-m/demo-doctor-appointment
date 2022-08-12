package com.blubank.doctorappointment.appointment.dto.response;

import com.blubank.doctorappointment.appointment.model.Appointment;
import lombok.Data;

@Data
public class AppointmentWithPatientInfoResponse extends AppointmentResponse {
    private String patientPhoneNumber;
    private String patientName;

    public AppointmentWithPatientInfoResponse(Appointment appointment) {
        super(appointment);
        this.patientPhoneNumber = appointment.getPatientPhoneNumber();
        this.patientName = appointment.getPatientName();
    }
}
