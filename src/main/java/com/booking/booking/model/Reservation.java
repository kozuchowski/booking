package com.booking.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


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

    private int tenancyPeriod;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    private Facility facility;

    @ManyToOne
    private Tenant tenant;


    public Reservation(int tenancyPeriod) {
        this.tenancyPeriod = tenancyPeriod;
    }
}
