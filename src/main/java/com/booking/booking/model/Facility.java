package com.booking.booking.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name="FACILITIES")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private BigDecimal price;

    private double area;

    private String description;

    @ManyToOne
    private Landlord landlord;

}