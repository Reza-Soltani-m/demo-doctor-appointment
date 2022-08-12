package com.blubank.doctorappointment.appointment.service.impl;

import static com.blubank.doctorappointment.base.Utils.*;
import com.blubank.doctorappointment.appointment.dto.request.TakeAppointmentRequest;
import com.blubank.doctorappointment.appointment.model.Appointment;
import com.blubank.doctorappointment.appointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.appointment.service.AppointmentService;
import com.blubank.doctorappointment.base.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    @Value("${appointment.period.in.minutes}")
    private Integer APPOINTMENT_PERIOD;

    private final AppointmentRepository repository;

    @Override
    public List<Appointment> generateDayAppointments(Date startTime, Date endTime) {

        if(startTime.after(endTime))
            throw new ApplicationException(message("error.start.time.is.after.end.time"));

        if(repository.existAppointmentBetweenDates(removeTime(startTime), addDaysToDate(startTime, 1)))
            throw new ApplicationException(message("error.appointment.already.defined.day"));

        Date currentCalculatedTime = startTime;
        List<Appointment> appointments = new ArrayList<>();
        Date calculatedTime = addMinutesToDate(currentCalculatedTime, APPOINTMENT_PERIOD);

        while(!endTime.before(calculatedTime)) {
            Appointment appointment = new Appointment();
            appointment.setStartTime(currentCalculatedTime);
            appointment.setEndTime(calculatedTime);

            appointments.add(appointment);

            currentCalculatedTime = calculatedTime;
            calculatedTime = addMinutesToDate(currentCalculatedTime, APPOINTMENT_PERIOD);
        }

        return repository.saveAll(appointments);
    }

    @Override
    public List<Appointment> appointments(Boolean isTaken, Date date) {
        Date date2 = (date == null) ? null : addDaysToDate(date, 1);
        return repository.findAllAppointments(isTaken, date, date2, null);
    }

    @Override
    public synchronized void deleteAppointment(Integer id) {
        checkAndLoadAppointmentById(id);
        repository.deleteById(id);
    }

    @Override
    public synchronized Appointment takeAppointment(Integer id, TakeAppointmentRequest request) {
        Appointment appointment = checkAndLoadAppointmentById(id);
        appointment.setPatientName(request.getName());
        appointment.setPatientPhoneNumber(request.getPhoneNumber());
        appointment.setTaken(true);

        return repository.save(appointment);
    }

    private Appointment checkAndLoadAppointmentById(Integer id) {
        Appointment appointment = repository.findById(id).orElseThrow(()->new ApplicationException(message("error.appointment.not.found"), HttpStatus.NOT_FOUND));
        if(appointment.isTaken()) throw new ApplicationException(message("error.appointment.is.taken"), HttpStatus.NOT_ACCEPTABLE);

        return appointment;
    }

    @Override
    public List<Appointment> patientAppointments(String phoneNumber) {
        return repository.findAllAppointments(null, null, null, phoneNumber);
    }

}
