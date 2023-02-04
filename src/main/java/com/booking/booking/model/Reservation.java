package com.booking.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name="RESERVATIONS")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private long tenancyPeriodInDays;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdOn;

    @ManyToOne
    private Facility facility;

    @ManyToOne
    private Tenant tenant;

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, Facility facility, Tenant tenant) {
        this.tenancyPeriodInDays = ChronoUnit.DAYS.between(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdOn = LocalDateTime.now();
        this.facility = facility;
        this.tenant = tenant;
    }

    public void setTenancyPeriodInDays() {
        this.tenancyPeriodInDays = ChronoUnit.DAYS.between(startDate, endDate);
    }
}
