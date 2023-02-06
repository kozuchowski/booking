package com.booking.booking.service;

import com.booking.booking.dto.ShowFacilityDto;
import com.booking.booking.model.Facility;

import java.util.List;

public interface FacilityService {
    List<ShowFacilityDto> showAllFacilities();
}
