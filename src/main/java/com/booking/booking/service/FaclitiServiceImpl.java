package com.booking.booking.service;

import com.booking.booking.dto.ShowFacilityDto;
import com.booking.booking.model.Facility;
import com.booking.booking.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FaclitiServiceImpl implements FacilityService{

    private final FacilityRepository fr;

    @Autowired
    public FaclitiServiceImpl(FacilityRepository fr) {
        this.fr = fr;
    }

    @Override
    public List<ShowFacilityDto> showAllFacilities() {
        List<Facility> facilities = fr.findAll();
        List<ShowFacilityDto> facilitiDtos = new ArrayList<>();
        for (Facility facility : facilities) {
            var facilityDto = new ShowFacilityDto(facility.getName(),
                                                    facility.getDescription(),
                                                    facility.getGrossPricePerDayInPln(),
                                                    facility.getAreaInMeters());
            facilitiDtos.add(facilityDto);
        }

        return facilitiDtos;
    }
}
