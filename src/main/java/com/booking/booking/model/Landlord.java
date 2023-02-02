package com.booking.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name="LANDLORDS")
public class Landlord {
    @Id
    private UUID id;

    private String name;

    private LocalDateTime createdOn;

    @ManyToOne
    private Facility facility;

    public Landlord(String name, Facility facility) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.createdOn = LocalDateTime.now();
        this.facility = facility;
    }
}
