package com.booking.booking.dto;

import java.math.BigDecimal;


public interface LandlordAllFacilitiesReportDto {

     Long getLandlordId();

     String getLandlordName();

     int getRentedFacilitiesCount();

     int getFacilityTenantsCount();

     BigDecimal getTotalLandlordIncome();




}
