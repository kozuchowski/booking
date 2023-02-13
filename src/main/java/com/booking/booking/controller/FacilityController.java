package com.booking.booking.controller;

import com.booking.booking.dto.ShowFacilityDto;
import com.booking.booking.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FacilityController {

    private final FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }



    @GetMapping("/facilities")
    public List<ShowFacilityDto> showFacilities() {

        return facilityService.showAllFacilities();
    }
}
