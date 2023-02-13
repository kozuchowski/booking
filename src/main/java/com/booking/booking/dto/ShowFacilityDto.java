package com.booking.booking.dto;

import java.math.BigDecimal;

public class ShowFacilityDto {

    public String name;

    public String description;

    public BigDecimal grosspriceInPln;

    public double areaInMeters;

    public ShowFacilityDto(String name, String description, BigDecimal grosspriceInPln, double areaInMeters) {
        this.name = name;
        this.description = description;
        this.grosspriceInPln = grosspriceInPln;
        this.areaInMeters = areaInMeters;
    }
}
