package com.blubank.doctorappointment.appointment.controller;

import static com.blubank.doctorappointment.base.Utils.*;
import com.blubank.doctorappointment.appointment.dto.response.AppointmentResponse;
import com.blubank.doctorappointment.appointment.dto.response.AppointmentWithPatientInfoResponse;
import com.blubank.doctorappointment.appointment.dto.request.TakeAppointmentRequest;
import com.blubank.doctorappointment.appointment.service.AppointmentService;
import com.blubank.doctorappointment.base.exception.ApplicationException;
import com.blubank.doctorappointment.base.validator.DateFormat;
import com.blubank.doctorappointment.base.validator.Mobile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
@Api(value = "patient services")
public class PatientController {

  private final AppointmentService service;

  @GetMapping("/appointments")
  @ApiOperation(value = "Not Taken Appointments")
  public List<AppointmentResponse> notTakenAppointments(@RequestParam(value = "day", required = false)  @DateFormat(fieldName="day") String day) {
    try {
      Date date = (day == null || day.trim().length() == 0) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(day);
      var result = service.appointments(false, date);
      return result.stream().map(AppointmentResponse::new).parallel().collect(Collectors.toList());
    } catch (ParseException e) {
      throw new ApplicationException(message("error.wrong.data.format"));
    }
  }

  @PutMapping("/take/{id}")
  @ApiOperation(value = "Take an open Appointments")
  public AppointmentWithPatientInfoResponse take(@PathVariable Integer id, @Valid @RequestBody TakeAppointmentRequest request) {
    var result = service.takeAppointment(id, request);
    return new AppointmentWithPatientInfoResponse(result);
  }

  @GetMapping(value = "/patient-appointments/{phoneNumber}")
  @ApiOperation(value = "All Patient Appointments")
  public List<AppointmentResponse> patientAppointments(@PathVariable(value = "phoneNumber") @Mobile String phoneNumber) {
    var result = service.patientAppointments(phoneNumber);
    return result.stream().map(AppointmentResponse::new).parallel().collect(Collectors.toList());
  }

}
