package com.booking.booking.controller;

import com.booking.booking.dto.FacilityReportDto;
import com.booking.booking.dto.LandlordAllFacilitiesReportDto;
import com.booking.booking.dto.ReportDatesDto;
import com.booking.booking.repository.ReportRepository;
import com.booking.booking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/raports")
public class ReportController {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @PostMapping("/facilities/{facilityId}")
    public FacilityReportDto facilityReservationsStatistics(@PathVariable Long facilityId,
                                                            @RequestBody ReportDatesDto datesDto){
        return reportRepository.findAllReservationsDaysCountWithReservationsCount(facilityId, datesDto.starts, datesDto.ends);
    }

    @PostMapping("/landlords-facilities-reservations")
    public List<LandlordAllFacilitiesReportDto> landlordsWithFacilitiesStatistics(@RequestBody ReportDatesDto reportDto) {
        return reportRepository.findReservedFacilitiesCountWithReservationsPerFacilityCountAndFacilitiesIncome(reportDto.starts,reportDto.ends);
    }
}
