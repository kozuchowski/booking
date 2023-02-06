package com.booking.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name="FACILITIES")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Name must not be blank!")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal grossPricePerDayInPln;

    @DecimalMin(value = "0.0", inclusive = false)
    private double areaInMeters;
    @NotBlank(message = "Description must not be blank!")
    private String description;

    private LocalDateTime createdOn;

    @ManyToOne
    private Landlord landlord;

    public Facility(String name, Long grossPricePerDayInPln, double areaInMeters, String description, Landlord landlord) {
        this.name = name;
        this.grossPricePerDayInPln = BigDecimal.valueOf(grossPricePerDayInPln);
        this.areaInMeters = areaInMeters;
        this.description = description;
        this.createdOn = LocalDateTime.now();
        this.landlord = landlord;
    }
}
