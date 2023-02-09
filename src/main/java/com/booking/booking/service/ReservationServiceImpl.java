package com.booking.booking.service;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.dto.ShowReservationDetailsDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



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

        validateReservationDates(resDto);

        Optional<Facility> optionalFacility = facilityRepository.findById(resDto.facilityId);

        if (optionalFacility.isEmpty()){
            throw new NoSuchObjectException("There is no such facility");
        }
        Facility facility = optionalFacility.get();
        Tenant tenant;
        if(tenantRepository.findByTenantName(resDto.tenantName) != null){
            tenant = tenantRepository.findByTenantName(resDto.tenantName);
        }else {
            tenant = new Tenant(resDto.tenantName);
        }


        tenantRepository.save(tenant);

        checkIfVacant(resDto);

        Reservation res = new Reservation(resDto.startDate, resDto.endDate, facility, tenant);

        reservationRepository.save(res);

        return res;
    }



    @Override
    public ShowReservationDetailsDto update(CreateReservationDto resDto, Long reservationsId) {
        Optional<Reservation> optionalRes = reservationRepository.findById(reservationsId);
        LocalDateTime now = LocalDateTime.now();
        Optional<Facility> optionalFacility = facilityRepository.findById(resDto.facilityId);

        if(optionalFacility.isEmpty()){
            throw new NoSuchObjectException("There is no such facility");
        }

        Facility facility = optionalFacility.get();

        if(optionalRes.isEmpty()){
            throw new NoSuchObjectException("There is no such reservation!");
        }
        Reservation res = optionalRes.get();

        if(resDto.startDate.isBefore(now) || resDto.startDate.equals(now)){
            throw new IllegalArgumentException("Reservation already started!");
        }

        validateReservationDates(resDto);

        checkIfVacant(resDto);

        res.setFacility(facility);
        res.setStartDate(resDto.startDate);
        res.setEndDate(resDto.endDate);
        res.setTenancyPeriodInDays();

        reservationRepository.save(res);
        return reservationDtoIntoShowReservationDetailsDto(res);
    }

    @Override
    public List<ShowReservationDetailsDto> getAllReservationsForTenant(String tenantName) {
        Tenant tenant = tenantRepository.findByTenantName(tenantName);
        if(tenant == null){
            throw new NoSuchObjectException("There is no such tenant!");
        }


        return reservationsIntoDtos(reservationRepository.findAllByTenant(tenant));
    }

    @Override
    public List<ShowReservationDetailsDto> getAllReservationsForFacility(Long facilityId) {
        Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);
        if(optionalFacility.isEmpty()){
            throw new NoSuchObjectException("There is no such facility!");
        }
        Facility facility = optionalFacility.get();


        return reservationsIntoDtos(reservationRepository.findAllByFacility(facility));


    }

    @Override
    public ShowReservationDetailsDto getSingleReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(optionalReservation.isEmpty()){
            throw new NoSuchObjectException("There is no such reservation!");
        }
        Reservation reservation = optionalReservation.get();

        return new ShowReservationDetailsDto(reservation.getId(),
                reservation.getTenant().getName(),
                reservation.getFacility().getName(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getTenancyPeriodInDays(),
                reservation.getSummaryPrice());

    }

    @Override
    public List<ShowReservationDetailsDto> reservationsIntoDtos(List<Reservation> reservations) {
        List<ShowReservationDetailsDto> dtoReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtoReservations.add(new ShowReservationDetailsDto(reservation.getId(),
                    reservation.getTenant().getName(),
                    reservation.getFacility().getName(),
                    reservation.getStartDate(),
                    reservation.getEndDate(),
                    reservation.getTenancyPeriodInDays(),
                    reservation.getSummaryPrice()));
        }
        return dtoReservations;
    }

    @Override
    public ShowReservationDetailsDto reservationDtoIntoShowReservationDetailsDto(Reservation r) {
        return new ShowReservationDetailsDto(r.getId(),
                                            r.getTenant().getName(),
                                            r.getFacility().getName(),
                                            r.getStartDate(),
                                            r.getEndDate(),
                                            r.getTenancyPeriodInDays(),
                                            r.getSummaryPrice());
    }

    @Override
    public boolean validateReservationDates(CreateReservationDto resDto) {
        if(resDto.startDate.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Start date must be after current date");
        }

        if(resDto.startDate.isAfter(resDto.endDate ) || resDto.startDate.isEqual(resDto.endDate)){
            throw new IllegalArgumentException("End date must be after start date");
        }
        return true;
    }

    @Override
    public boolean checkIfVacant(CreateReservationDto resDto) {
        List<Reservation> reservations = reservationRepository.findReservationsByFacilityId(resDto.facilityId);
        LocalDateTime starts = resDto.startDate;
        LocalDateTime ends = resDto.endDate;

        for (Reservation r : reservations) {

            if(starts.isEqual(r.getStartDate()) || starts.isAfter(r.getStartDate())
                    && starts.isEqual(r.getEndDate()) || starts.isBefore(r.getEndDate())
                    || ends.isEqual(r.getStartDate()) || ends.isAfter(r.getStartDate())
                    && ends.isEqual(r.getEndDate()) || ends.isBefore(r.getEndDate()) ){

                throw new InvalidPeriodException("This object is not vacant due to this period!");
            }
        }
        return true;
    }
}
