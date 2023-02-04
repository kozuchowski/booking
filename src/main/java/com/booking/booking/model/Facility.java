package com.booking.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Name must not be blank!")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal grossPriceInPln;

    @DecimalMin(value = "0.0", inclusive = false)
    private double areaInMeters;
    @NotBlank(message = "Description must not be blank!")
    private String description;

    private LocalDateTime createdOn;

    @ManyToOne
    private Landlord landlord;

    public Facility(String name, Long grossPriceInPln, double areaInMeters, String description, Landlord landlord) {
        this.name = name;
        this.grossPriceInPln = BigDecimal.valueOf(grossPriceInPln);
        this.areaInMeters = areaInMeters;
        this.description = description;
        this.createdOn = LocalDateTime.now();
        this.landlord = landlord;
    }
}
