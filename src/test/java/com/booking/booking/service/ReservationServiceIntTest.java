package com.booking.booking.service;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.exception.InvalidPeriodException;
import com.booking.booking.exception.NoSuchObjectException;
import com.booking.booking.model.Facility;
import com.booking.booking.model.Landlord;
import com.booking.booking.model.Reservation;
import com.booking.booking.model.Tenant;
import com.booking.booking.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ReservationServiceIntTest {


    private final ReservationServiceImpl reservationService;

    private final LandlordRepository landlordRepository;

    private final FacilityRepository facilityRepository;

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceIntTest(ReservationServiceImpl reservationService, LandlordRepository landlordRepository,
                                     FacilityRepository facilityRepository, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.landlordRepository = landlordRepository;
        this.facilityRepository = facilityRepository;
        this.reservationRepository = reservationRepository;
    }



    // Create reservation #######################################
    @Test
    void shouldThrowNoSuchObjectException(){
        assertThrows(NoSuchObjectException.class,
                () -> {
                    reservationService.create(new CreateReservationDto(-1l, "test",
                            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)));
                });
    }



    @Test
    void shouldCreateValidReservation() {
        Landlord landlord = new Landlord("test", 0l);
        landlordRepository.save(landlord);
        Facility facility = new Facility(UUID.randomUUID().toString(),1l, 1, "test", landlord);
        facilityRepository.save(facility);
        Reservation res = reservationService.create(new CreateReservationDto(
                        facilityRepository.findByName(facility.getName()).getId(), "test",
                        LocalDateTime.now().plusYears(1000), LocalDateTime.now().plusYears(1001)));

        assertNotNull(res);
        assertNotNull(res.getFacility());
        assertNotNull(res.getTenant());

        reservationRepository.deleteById(res.getId());

    }

    // Update reservation #######################################

    @Test
    void shouldThrowNoSuchObjectExceptionForFacility() {
        Landlord landlord = new Landlord("test", 0l);
        landlordRepository.save(landlord);
        Facility facility = new Facility("test",1l, 1, "test", landlord);
        facilityRepository.save(facility);
        CreateReservationDto resDto = new CreateReservationDto(2l, "test",
                LocalDateTime.now().plusYears(1000), LocalDateTime.now().plusYears(1001));
        Reservation res = reservationService.create(resDto);

        assertThrows(NoSuchObjectException.class,
                () -> {
                    reservationService.update(resDto, -1l);
                });

        reservationRepository.deleteById(res.getId());
    }
    @Test
    void shouldThrowNoSuchObjectExceptionForReservation() {
        Landlord landlord = new Landlord("test", 0l);
        landlordRepository.save(landlord);
        Facility facility = new Facility("test",1l, 1, "test", landlord);
        facilityRepository.save(facility);
        CreateReservationDto resDto = new CreateReservationDto(2l, "test",
                LocalDateTime.now().plusYears(1000), LocalDateTime.now().plusYears(1001));
        assertThrows(NoSuchObjectException.class,
                () -> {
                    reservationService.update(resDto, -1l);
                });
    }
    //TODO Check test for update
    @Test
    void shouldUpdateReservationDetails() {
        Landlord landlord = new Landlord("test", -1l);
        landlordRepository.save(landlord);
        Facility facility = new Facility("test",1l, 1, "test", landlord);
        facilityRepository.save(facility);

        CreateReservationDto resDto = new CreateReservationDto(facility.getId(), "test",
                LocalDateTime.now().plusYears(1000), LocalDateTime.now().plusYears(1001));

        Reservation res = reservationService.create(resDto);

        LocalDateTime newStart = LocalDateTime.now().plusYears(2000);
        LocalDateTime newEnd = LocalDateTime.now().plusYears(2001);
        reservationService.update(new CreateReservationDto(facility.getId(), "newTest", newStart, newEnd), res.getId());

        Reservation updatedReservation = reservationRepository.findById(res.getId()).get();

        System.out.println(updatedReservation.getStartDate());
        System.out.println(updatedReservation.getEndDate());
        System.out.println(updatedReservation.getTenant().getName());

        assertTrue(updatedReservation.getTenant().getName().equals("newTest"));
        assertTrue(updatedReservation.getStartDate().isEqual(newStart));
        assertTrue(updatedReservation.getEndDate().isEqual(newEnd));


        reservationRepository.delete(res);
        facilityRepository.delete(facility);
        landlordRepository.delete(landlord);
    }

    // List of reservations for tenant ###################################################

    @Test
    void shouldThrowNoSuchObjectExceptionForTenant() {
        assertThrows(NoSuchObjectException.class,
                () -> {
                    reservationService.getAllReservationsForTenant(UUID.randomUUID().toString());
                });
    }

    @Test
    void listOfReservationsShouldNotBeEmpty() {
        Tenant tenant = new Tenant(UUID.randomUUID().toString());
        tenantRepository.save(tenant);

        Landlord landlord = new Landlord("test", -1l);
        landlordRepository.save(landlord);

        Facility facility1 = new Facility("test", 1l, 1l, "test", landlord);
        Facility facility2 = new Facility("test2", 1l, 1l, "test", landlord);
        facilityRepository.save(facility1);
        facilityRepository.save(facility2);

        CreateReservationDto resDto1 = new CreateReservationDto(facility1.getId(), tenant.getName(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10));

        CreateReservationDto resDto2 = new CreateReservationDto(facility2.getId(), tenant.getName(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10));

        Reservation res1 = reservationService.create(resDto1);
        Reservation res2 = reservationService.create(resDto2);

        assertTrue(!reservationService.getAllReservationsForTenant(tenant.getName()).isEmpty());

        reservationRepository.delete(res1);
        reservationRepository.delete(res2);
        facilityRepository.delete(facility1);
        facilityRepository.delete(facility2);
        landlordRepository.delete(landlord);
        tenantRepository.delete(tenant);


    }

    // List of reservations for facility ###################################################

    @Test
    void shouldThrowExceptionForNoFacility() {
        assertThrows(NoSuchObjectException.class,
                () -> {
                    reservationService.getAllReservationsForFacility(-1l);
                });
    }

    @Test
    void listOfReservationsForFacilityShoudNotBeEmpty() {
        Tenant tenant = new Tenant(UUID.randomUUID().toString());
        tenantRepository.save(tenant);

        Landlord landlord = new Landlord("test", -1l);
        landlordRepository.save(landlord);

        Facility facility = new Facility("test", 1l, 1l, "test", landlord);
        facilityRepository.save(facility);


        CreateReservationDto resDto1 = new CreateReservationDto(facility.getId(), tenant.getName(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10));

        CreateReservationDto resDto2 = new CreateReservationDto(facility.getId(), tenant.getName(),
                LocalDateTime.now().plusDays(11), LocalDateTime.now().plusDays(15));

        Reservation res1 = reservationService.create(resDto1);
        Reservation res2 = reservationService.create(resDto2);

        assertTrue(!reservationService.getAllReservationsForFacility(facility.getId()).isEmpty());

        reservationRepository.delete(res1);
        reservationRepository.delete(res2);
        facilityRepository.delete(facility);
        landlordRepository.delete(landlord);
        tenantRepository.delete(tenant);

    }



    @Test
    void getAllReservationsForFacility() {
    }

    // Validate reservations dates ###################################################

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
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private ReportRepository reportRepository;


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