package com.booking.booking.dto;


import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;



public class CreateReservationDto {

    @NotNull
    public Long facilityId;


    public Long tenantId;


    public LocalDateTime startDate;


    public LocalDateTime endDate;
}
