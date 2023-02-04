package com.booking.booking.controller;

import com.booking.booking.model.Facility;
import com.booking.booking.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FacilityController {

    private final FacilityRepository fr;

    @Autowired
    public FacilityController(FacilityRepository fr) {
        this.fr = fr;
    }

    @GetMapping("/facilities")
    public List<Facility> showFacilities() {
        //TODO sort by name, dto instead of reservation
        return fr.findAll();
    }
}
