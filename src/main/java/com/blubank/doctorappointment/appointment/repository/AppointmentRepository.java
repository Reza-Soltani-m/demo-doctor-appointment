package com.blubank.doctorappointment.appointment.repository;

import com.blubank.doctorappointment.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("select CASE WHEN COUNT(a) > 0 THEN true ELSE false END from Appointment a where a.startTime >= :d1 and a.startTime < :d2 ")
    Boolean existAppointmentBetweenDates(Date d1, Date d2);

    @Query("from Appointment a where ((:isTaken is null) or (a.isTaken is null or a.isTaken = :isTaken)) and " +
            "(:d1 is null or (a.startTime >= :d1 and a.startTime < :d2)) and " +
            "(:phoneNumber is null or a.patientPhoneNumber = :phoneNumber)")
    List<Appointment> findAllAppointments(Boolean isTaken, Date d1, Date d2, String phoneNumber);
}
