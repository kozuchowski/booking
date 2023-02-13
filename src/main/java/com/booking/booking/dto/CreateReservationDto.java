package com.booking.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
public class CreateReservationDto {

    @NotNull
    public Long facilityId;

    @NotBlank(message = "Name must not be blank")
    public String tenantName;

    @NotNull
    public LocalDateTime startDate;

    @NotNull
    public LocalDateTime endDate;
}
