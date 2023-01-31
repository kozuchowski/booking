package com.booking.booking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name="RESERVATIONS")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private int tenancyPeriod;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    private Facility facility;

    @ManyToOne
    private Tenant tenant;
}
