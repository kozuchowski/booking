package com.booking.booking.service;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.model.Facility;
import com.booking.booking.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    Reservation create(CreateReservationDto resDto);

    void update(CreateReservationDto resDto, UUID id);

    List<Reservation> getAllReservationForTenant(String tenantName);

    List<Reservation> getAllReservationsForFacility(UUID facilityId);

    Reservation getSingleReservation(UUID id);

}
