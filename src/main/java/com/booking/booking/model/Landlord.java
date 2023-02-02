package com.booking.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "Name must not be blank!")
    private String name;

    private LocalDateTime createdOn;


    public Landlord(String name, Facility facility) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.createdOn = LocalDateTime.now();
    }
}
