package com.booking.booking.service;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.dto.ShowReservationDetailsDto;
import com.booking.booking.model.Reservation;
import java.util.List;


public interface ReservationService {
    Reservation create(CreateReservationDto resDto);

    ShowReservationDetailsDto update(CreateReservationDto resDto, Long id);

    List<ShowReservationDetailsDto> getAllReservationsForTenant(String tenantName);

    List<ShowReservationDetailsDto> getAllReservationsForFacility(Long facilityId);

    ShowReservationDetailsDto getSingleReservation(Long id);

    List<ShowReservationDetailsDto> reservationsIntoDtos (List<Reservation> reservations);

    ShowReservationDetailsDto reservationDtoIntoShowReservationDetailsDto(Reservation reservation);

    boolean validateReservationDates(CreateReservationDto reservationDto);

    boolean checkIfVacant(CreateReservationDto reservationDto);

}
