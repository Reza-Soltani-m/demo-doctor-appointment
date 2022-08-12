package com.blubank.doctorappointment;

import com.blubank.doctorappointment.appointment.controller.DoctorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DoctorAppointmentApplicationTests {

	@Autowired
	private DoctorController controller;

	@Test
	void contextLoads() {
		System.out.println(controller.appointments(null, null));
	}

}
