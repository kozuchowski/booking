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
@Table(name="TENANTS")
public class Tenant {
    @Id
    private UUID id;
    @NotBlank(message = "Name must not be blank!")
    private String name;

    private LocalDateTime createdOn;

    public Tenant(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.createdOn = LocalDateTime.now();
    }
}
