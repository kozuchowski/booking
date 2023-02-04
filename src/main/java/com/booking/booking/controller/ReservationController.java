package com.booking.booking.controller;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.exception.InvalidPeriodException;
import com.booking.booking.exception.NoSuchObjectException;
import com.booking.booking.model.Reservation;
import com.booking.booking.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    @Autowired
    public ReservationController(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public String createReservation(@Valid @RequestBody CreateReservationDto reservationDto) {

        reservationService.create(reservationDto);

        return "Reservation created";
    }

    @PatchMapping("/reservations/{id}")
    public String changeReservation(@PathVariable Long id,
                                    @RequestBody CreateReservationDto resDto) {
        reservationService.update(resDto, id);
        return "Reservation changed";
    }

    @GetMapping("/reservations/tenant/byId/{reservationId}")
    public Reservation showReservation(@PathVariable Long id) {
        //TODO dto instead of reservation
       return reservationService.getSingleReservation(id);
    }

    @GetMapping("/reservations/tenant/{tenantName}")
    public List<Reservation> showReservationsForTenant(@PathVariable String tenantName) {
        return reservationService.getAllReservationForTenant(tenantName);
    }

    @GetMapping("/reservations/facility/{facilityId}")
    public List<Reservation> showReservationsForFacility(@PathVariable Long facilityId) {
        return reservationService.getAllReservationsForFacility(facilityId);
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
                       NoSuchObjectException.class,
                       IllegalArgumentException.class})
    public String handleAlreadyExistsExceptions(Exception ex) {

        return ex.getMessage();
    }

}
