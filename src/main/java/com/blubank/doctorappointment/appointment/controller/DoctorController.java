package com.blubank.doctorappointment.appointment.controller;

import static com.blubank.doctorappointment.base.Utils.*;
import com.blubank.doctorappointment.appointment.dto.request.AddAppointmentRequest;
import com.blubank.doctorappointment.appointment.dto.response.AppointmentResponse;
import com.blubank.doctorappointment.appointment.dto.response.AppointmentWithPatientInfoResponse;
import com.blubank.doctorappointment.appointment.service.AppointmentService;
import com.blubank.doctorappointment.base.exception.ApplicationException;
import com.blubank.doctorappointment.base.validator.DateFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
@Api(value = "doctor services")
@Service
public class DoctorController {

  private final AppointmentService service;

  @PostMapping("/add")
  @ApiOperation(value = "add open times")
  public List<AppointmentResponse> add(@Valid @RequestBody AddAppointmentRequest req) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      Date startDateTime = dateFormat.parse(req.getDay() + " " + req.getStartTime());
      Date endDateTime = dateFormat.parse(req.getDay() + " " + req.getEndTime());

      var result = service.generateDayAppointments(startDateTime, endDateTime);

      return result.stream().map(AppointmentResponse::new).parallel().collect(Collectors.toList());
    } catch (ParseException e) {
      throw new ApplicationException(message("error.wrong.data.format"));
    }
  }

  @GetMapping("/appointments")
  @ApiOperation(value = "Appointments")
  public List<AppointmentResponse> appointments(@RequestParam(value = "isTaken", required = false) Boolean isTaken,
                                                @RequestParam(value = "day", required = false) @DateFormat(fieldName="day") String day) {
    try {
      Date date = (day == null || day.trim().length() == 0) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(day);
      var result = service.appointments(isTaken, date);
      return result.stream().map(AppointmentWithPatientInfoResponse::new).parallel().collect(Collectors.toList());
    } catch (ParseException e) {
      throw new ApplicationException(message("error.wrong.data.format"));
    }
  }

  @DeleteMapping("/delete/{id}")
  @ApiOperation(value = "Delete Appointment")
  public void delete(@PathVariable Integer id) {
      service.deleteAppointment(id);
  }

}
