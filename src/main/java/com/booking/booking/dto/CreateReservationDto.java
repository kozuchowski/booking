package com.booking.booking.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class CreateReservationDto {

    @NotBlank(message = "Facility name must not be blank")
    public String facilityName;

    @NotBlank(message = "Name must not be blank")
    public String tenantName;

    @NotNull
    public LocalDateTime startDate;

    @NotNull
    public LocalDateTime endDate;
}
