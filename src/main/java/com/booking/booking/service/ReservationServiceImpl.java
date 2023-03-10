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
import org.springframework.transaction.annotation.Transactional;

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
    public ShowReservationDetailsDto update(CreateReservationDto resDto, Long reservationId) {
        Optional<Reservation> optionalRes = reservationRepository.findById(reservationId);
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

        if(!now.isBefore(res.getStartDate())){
            throw new IllegalArgumentException("Reservation already started!");
        }

        validateReservationDates(resDto);

        // TODO validation should not include dates in current reservation
        res.setStartDate(LocalDateTime.of(0,1,1,1,1,1,0));
        res.setEndDate(LocalDateTime.of(0,1,1,1,1,1,1));

        reservationRepository.saveAndFlush(res);

        checkIfVacant(resDto);


        res.setFacility(facility);
        res.setStartDate(resDto.startDate);
        res.setEndDate(resDto.endDate);
        res.setTenancyPeriodInDays();
        res.setSummaryPrice();

        reservationRepository.saveAndFlush(res);
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

        if(!isDateAfterCurrentDate(resDto.startDate)){
            throw new IllegalArgumentException("Start date must be after current date");
        }

        if(!isEndDateAfterStartDate(resDto.startDate, resDto.endDate)){
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
               && ends.isEqual(r.getEndDate()) || ends.isBefore(r.getEndDate())
               ||starts.isBefore(r.getStartDate()) && ends.isAfter(r.getEndDate())){

                throw new InvalidPeriodException("This object is not vacant due to this period!");
            }
        }

        return true;
    }

    public boolean isDateAfterCurrentDate(LocalDateTime date) {
        if(date.isAfter(LocalDateTime.now())){
            return true;
        }
        return false;
    }

    public boolean isEndDateAfterStartDate(LocalDateTime starts, LocalDateTime ends) {
        if(ends.isAfter(starts)){
            return true;
        }
        return false;
    }

//    public boolean checkIfDatesCover (LocalDateTime starts, LocalDateTime ends,
//                                      LocalDateTime newStart, LocalDateTime newEnd){
//
//        if(newStart.isEqual(newStart) || newStart.isAfter(starts)
//           && newStart.isEqual(starts) || newStart.isBefore(ends)
//           || newEnd.isEqual(starts) || newEnd.isAfter(starts)
//           && newEnd.isEqual(ends) || newEnd.isBefore(ends)
//           ||newStart.isBefore(starts) && newEnd.isAfter(ends)) {
//            return true;
//        }
//        return false;
//    }

}
