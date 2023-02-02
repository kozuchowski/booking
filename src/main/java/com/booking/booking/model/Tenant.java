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
@Table(name="TENANTS")
public class Tenant {
    @Id
    private UUID id;

    private String name;

    private LocalDateTime createdOn;

    public Tenant(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.createdOn = LocalDateTime.now();
    }
}
