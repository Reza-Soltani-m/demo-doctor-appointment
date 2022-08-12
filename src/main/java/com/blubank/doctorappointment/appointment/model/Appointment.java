package com.blubank.doctorappointment.appointment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date startTime;

    @Column(nullable = false)
    private Date endTime;

    @Column()
    private boolean isTaken;

    @Column(length = 15)
    private String patientName;

    @Column(length = 11)
    private String patientPhoneNumber;

    @Version
    private Integer version;
}
