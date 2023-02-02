package com.booking.booking.controller;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.exception.InvalidPeriodException;
import com.booking.booking.exception.NoSuchObjectException;
import com.booking.booking.model.Reservation;
import com.booking.booking.repository.ReservationRepository;
import com.booking.booking.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    @Autowired
    public ReservationController(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public UUID createReservation(@Valid @RequestBody CreateReservationDto reservationDto) {

        Reservation res = reservationService.create(reservationDto);

        return res.getId();
    }

    @PatchMapping("/reservations/{id}")
    public String changeReservation() {

        return "Reservation changed";
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidPeriodException.class,
            NoSuchObjectException.class})
    public String handleAlreadyExistsExceptions(Exception ex) {

        return ex.getMessage();
    }

}
