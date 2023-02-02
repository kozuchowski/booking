package com.booking.booking.service;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.exception.InvalidPeriodException;
import com.booking.booking.exception.NoSuchObjectException;
import com.booking.booking.model.Facility;
import com.booking.booking.model.Reservation;
import com.booking.booking.model.Tenant;
import com.booking.booking.repository.FacilityRepository;
import com.booking.booking.repository.ReservationRepository;
import com.booking.booking.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;

    private final FacilityRepository facilityRepository;

    private final TenantRepository tenantRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, FacilityRepository facilityRepository, TenantRepository tenantRepository) {
        this.reservationRepository = reservationRepository;
        this.facilityRepository = facilityRepository;
        this.tenantRepository = tenantRepository;
    }





    @Override
    public Reservation create(CreateReservationDto resDto) {


        Optional<Facility> facilityOptional = facilityRepository.findById(resDto.facilityId);
        if(facilityOptional.isEmpty()){
            throw new NoSuchObjectException("There is no such facility!");
        }
        Facility facility = facilityOptional.get();

        Optional<Tenant> tenantOptional = tenantRepository.findById(resDto.tenantId);
        if(tenantOptional.isEmpty()){
            throw new NoSuchObjectException("There is no such tenant");
        }
        Tenant tenant = tenantOptional.get();

        Reservation alreadyExisting = reservationRepository.findByFacilityAndTenancyDates(resDto);
        if(alreadyExisting != null){
            throw new InvalidPeriodException("This object is not vacant due to this period!");
        }

        Reservation res = new Reservation(resDto.startDate, resDto.endDate, facility, tenant);

        reservationRepository.save(res);

        return res;
    }



    @Override
    public void update(CreateReservationDto resDto, UUID id) {
        Optional<Reservation> optionalRes = reservationRepository.findById(id);
        LocalDateTime now = LocalDateTime.now();

        if(optionalRes.isEmpty()){
            throw new NoSuchObjectException("There is no such reservation!");
        }
        Reservation res = optionalRes.get();

        if(resDto.startDate.isAfter(now) || resDto.startDate.equals(now)){
            throw new IllegalArgumentException("Reservation already started!");
        }

        res.setStartDate(resDto.startDate);
        res.setEndDate(resDto.endDate);
        res.setTenancyPeriodInDays();

        reservationRepository.save(res);
    }

    @Override
    public List<Reservation> getAllReservationForTenant(String tenantName) {
        Tenant tenant = tenantRepository.findByTenantName(tenantName);
        if(tenant == null){
            throw new NoSuchObjectException("There is no such tenant!");
        }

        return reservationRepository.findAllByTenant(tenant);
    }

    @Override
    public List<Reservation> getAllReservationsForFacility(UUID facilityId) {
        Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);
        if(optionalFacility.isEmpty()){
            throw new NoSuchObjectException("There is no such facility!");
        }
        Facility facility = optionalFacility.get();

        return reservationRepository.findAllByFacility(facility);
    }

    @Override
    public Reservation getSingleReservation(UUID id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(optionalReservation.isEmpty()){
            throw new NoSuchObjectException("There is no such reservation!");
        }
        Reservation res = optionalReservation.get();
        return res;
    }
}
