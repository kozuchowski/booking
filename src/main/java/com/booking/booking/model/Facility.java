package com.booking.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name="FACILITIES")
public class Facility {
    @Id
    private UUID id;

    private String name;

    private BigDecimal grossPriceInPln;

    private double areaInMeters;

    private String description;

    private LocalDateTime createdOn;

    @ManyToOne
    private Landlord landlord;

    public Facility(String name, BigDecimal grossPriceInPln, double areaInMeters, String description, Landlord landlord) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.grossPriceInPln = grossPriceInPln;
        this.areaInMeters = areaInMeters;
        this.description = description;
        this.createdOn = LocalDateTime.now();
        this.landlord = landlord;
    }
}
