package com.booking.booking.dto;

import java.math.BigDecimal;

public class ShowFacilityDto {

    public Long id;

    public String name;

    public String description;

    public BigDecimal grosspriceInPln;

    public double areaInMeters;

    public ShowFacilityDto(Long id, String name, String description, BigDecimal grosspriceInPln, double areaInMeters) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.grosspriceInPln = grosspriceInPln;
        this.areaInMeters = areaInMeters;
    }
}
