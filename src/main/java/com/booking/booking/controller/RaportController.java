package com.booking.booking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/raports")
public class RaportController {

    @GetMapping("/facilities/{name}/{starts}/{ends}")
    public String facilityReservationsStatistics(@PathVariable String name,
                                                 @PathVariable LocalDateTime starts,
                                                 @PathVariable LocalDateTime ends) {
        throw new RuntimeException("Not implemented yet");
    }

    @GetMapping("/landlords-facilities-reservations/{starts}/{ends}")
    public List<String> landlordsWithFacilitiesStatistics(@PathVariable LocalDateTime starts,
                                                          @PathVariable LocalDateTime ends) {
        throw new RuntimeException("Not implemented yet");
    }
}
