package com.booking.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShowReservationDetailsDto {

    public Long reservationId;

    public String tenantName;

    public String facilityName;

    public LocalDateTime starts;

    public LocalDateTime ends;

    public long tenancyPeriodInDays;

    public BigDecimal totalGrossPriceInPln;

    public ShowReservationDetailsDto(Long reservationId, String tenantName, String facilityName, LocalDateTime starts, LocalDateTime ends, long tenancyPeriodInDays, BigDecimal totalGrossPriceInPln) {
        this.reservationId = reservationId;
        this.tenantName = tenantName;
        this.facilityName = facilityName;
        this.starts = starts;
        this.ends = ends;
        this.tenancyPeriodInDays = tenancyPeriodInDays;
        this.totalGrossPriceInPln = totalGrossPriceInPln;
    }
}
