package com.booking.booking.service;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.exception.InvalidPeriodException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ReservationServiceIntTest {


    private final ReservationServiceImpl reservationService;
    @Autowired
    ReservationServiceIntTest(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }


    @Test
    void create() {

    }



    @Test
    void update() {
    }

    @Test
    void getAllReservationsForTenant() {
    }

    @Test
    void getAllReservationsForFacility() {
    }

    // Validate reservations dates tests ###################################################

    LocalDateTime validStart = LocalDateTime.of(2024,01, 01, 01, 01, 01, 01);
    LocalDateTime validEnd = LocalDateTime.of(2025,01, 01, 01, 01, 01, 01);
    //invalidDates for checkIfVacant:

    // starts before ends between dates
    LocalDateTime invalidStart1 = LocalDateTime.of(2023,01, 01, 01, 01, 01, 01);
    LocalDateTime invalidEnd1 = LocalDateTime.of(2024,02, 01, 01, 01, 01, 01);
    CreateReservationDto invalidResDtoForCheckIfVacant1 = new CreateReservationDto(2l, "test", invalidStart1, invalidEnd1);

    // starts between  ends between
    LocalDateTime invalidStart2 = LocalDateTime.of(2024,02, 01, 01, 01, 01, 01);
    LocalDateTime invalidEnd2 = LocalDateTime.of(2024,03, 01, 01, 01, 01, 01);
    CreateReservationDto invalidResDtoForCheckIfVacant2 = new CreateReservationDto(2l, "test", invalidStart1, invalidEnd1);

    // starts between  ends after
    LocalDateTime invalidStart3 = LocalDateTime.of(2024,02, 01, 01, 01, 01, 01);
    LocalDateTime invalidEnd3 = LocalDateTime.of(2025,03, 01, 01, 01, 01, 01);
    CreateReservationDto invalidResDtoForCheckIfVacant3 = new CreateReservationDto(2l, "test", invalidStart1, invalidEnd1);

    // starts before  ends after
    LocalDateTime invalidStart4 = LocalDateTime.of(2023,02, 01, 01, 01, 01, 01);
    LocalDateTime invalidEnd4 = LocalDateTime.of(2025,03, 01, 01, 01, 01, 01);
    CreateReservationDto invalidResDtoForCheckIfVacant4 = new CreateReservationDto(2l, "test", invalidStart1, invalidEnd1);

    LocalDateTime earlyStart = LocalDateTime.of(2000,01, 01, 01, 01, 01, 01);
    LocalDateTime earlyEnd = LocalDateTime.of(2000,01, 01, 01, 01, 01, 01);
    CreateReservationDto validResDto = new CreateReservationDto(2l, "test", validStart, validEnd);

    // validateReservationDatesDtos
    CreateReservationDto invalidResDto1 = new CreateReservationDto(2l, "test", validStart, earlyEnd);
    CreateReservationDto invalidResDto2 = new CreateReservationDto(2l, "test", earlyStart, validEnd);
    CreateReservationDto invalidResDto3 = new CreateReservationDto(2l, "test", earlyStart, earlyEnd);

    @Test
    void reservationDatesShouldBeValid(){

        assertTrue(reservationService.isDateAfterCurrentDate(validStart));
        assertFalse(reservationService.isDateAfterCurrentDate(earlyEnd));

    }

    @Test
    void shouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,
                () -> {
                    reservationService.validateReservationDates(invalidResDto1);
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    reservationService.validateReservationDates(invalidResDto2);
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    reservationService.validateReservationDates(invalidResDto3);
                });
    }

    @Test
    void shouldReturnTrueIfDatesAreValid() {
        reservationService.validateReservationDates(validResDto);
    }

    @Test
    void shouldThrowInvalidPeriodException(){
        reservationService.create(validResDto);
        assertThrows(InvalidPeriodException.class,
                () -> {
                    reservationService.checkIfVacant(validResDto);
                });
        assertThrows(InvalidPeriodException.class,
                () -> {
                    reservationService.checkIfVacant(invalidResDtoForCheckIfVacant1);
                });
        assertThrows(InvalidPeriodException.class,
                () -> {
                    reservationService.checkIfVacant(invalidResDtoForCheckIfVacant2);
                });
        assertThrows(InvalidPeriodException.class,
                () -> {
                    reservationService.checkIfVacant(invalidResDtoForCheckIfVacant3);
                });
        assertThrows(InvalidPeriodException.class,
                () -> {
                    reservationService.checkIfVacant(invalidResDtoForCheckIfVacant4);
                });
   }

   @Test
   void shouldReturnTrueIfFacilityIsVacant(){
        reservationService.checkIfVacant(validResDto);
   }
}