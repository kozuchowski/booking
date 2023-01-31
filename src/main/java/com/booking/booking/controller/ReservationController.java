package com.booking.booking.controller;

import com.booking.booking.model.Reservation;
import com.booking.booking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reservations")
    public String createReservation() {

        Reservation reservation = new Reservation(34543);

        reservationRepository.save(reservation);

        return "reservation created";
    }
}
