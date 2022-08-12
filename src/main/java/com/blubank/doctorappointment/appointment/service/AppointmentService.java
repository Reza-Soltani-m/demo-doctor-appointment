package com.blubank.doctorappointment.appointment.service;

import com.blubank.doctorappointment.appointment.dto.request.TakeAppointmentRequest;
import com.blubank.doctorappointment.appointment.model.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    List<Appointment> generateDayAppointments(Date startTime, Date endTime);

    List<Appointment> appointments(Boolean isTaken, Date date);

    void deleteAppointment(Integer id);

    Appointment takeAppointment(Integer id, TakeAppointmentRequest request);

    List<Appointment> patientAppointments(String phoneNumber);

}
