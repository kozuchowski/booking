package com.booking.booking.dto;


import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;



public class CreateReservationDto {

    @NotNull
    public Long facilityId;

    @NotNull
    public Long tenantId;

    @NotNull
    public LocalDateTime startDate;

    @NotNull
    public LocalDateTime endDate;
}
