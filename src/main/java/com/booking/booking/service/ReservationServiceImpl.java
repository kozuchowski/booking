package com.booking.booking.service;

import com.booking.booking.exception.InvalidPeriodException;
import com.booking.booking.model.Facility;
import com.booking.booking.model.Reservation;
import com.booking.booking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    @Override
    public void create(Reservation res) {
        Reservation alreadyExisting = reservationRepository.findByFacilityAndTenancyDates(res);
        if(alreadyExisting != null){
            throw new InvalidPeriodException("This object is not vacant due to this period!");
        }

        reservationRepository.save(res);
    }

    @Override
    public void update(Reservation res) {
        reservationRepository.update(res);
    }

    @Override
    public List<Reservation> getAllReservationForTenant(String tenantName) {
        return reservationRepository.findAllByTenant(tenantName);
    }

    @Override
    public List<Reservation> getAllReservationsForFacility(Facility facility) {
        return null;
    }
}
