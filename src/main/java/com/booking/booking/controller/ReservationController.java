package com.booking.booking.controller;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.dto.ShowReservationDetailsDto;
import com.booking.booking.exception.InvalidPeriodException;
import com.booking.booking.exception.NoSuchObjectException;
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
    public ShowReservationDetailsDto createReservation(@Valid @RequestBody CreateReservationDto reservationDto) {

        return reservationService.reservationDtoIntoShowReservationDetailsDto(reservationService.create(reservationDto));
    }

    @PatchMapping("/reservations/{id}")
    public ShowReservationDetailsDto changeReservation(@PathVariable Long id,
                                    @RequestBody CreateReservationDto resDto) {

        return reservationService.update(resDto, id);
    }

    @GetMapping("/reservations/{id}")
    public ShowReservationDetailsDto showReservation(@PathVariable Long id) {
        return reservationService.getSingleReservation(id);
    }

    @GetMapping("/reservations/tenants/{tenantName}")
    public List<ShowReservationDetailsDto> showReservationsForTenant(@PathVariable String tenantName) {
        return reservationService.getAllReservationsForTenant(tenantName);
    }

    @GetMapping("/reservations/facilities/{id}")
    public List<ShowReservationDetailsDto> showReservationsForFacility(@PathVariable Long id) {
        return reservationService.getAllReservationsForFacility(id);
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
