package com.booking.booking.service;

import com.booking.booking.model.Facility;
import com.booking.booking.model.Reservation;

import java.util.List;

public interface ReservationService {
    void create(Reservation res);

    void update(Reservation res);

    List<Reservation> getAllReservationForTenant(String tenantName);

    List<Reservation> getAllReservationsForFacility(Facility facility);

}
