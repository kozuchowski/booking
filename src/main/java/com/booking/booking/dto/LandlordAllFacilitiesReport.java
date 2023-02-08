package com.booking.booking.dto;

import java.math.BigDecimal;
import java.util.Map;

public class LandlordAllFacilitiesReport {

    public Long landlordId;

    public String landlordName;

    public int rentedFacilitiesCount;

    public Map<Long, Long> reservationsPerFacilityCount;


    public Map<Long, BigDecimal> facilityIncome;

    public BigDecimal totalLandlordIncome;




}
