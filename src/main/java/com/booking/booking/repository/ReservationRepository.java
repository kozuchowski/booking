package com.booking.booking.repository;

import com.booking.booking.dto.CreateReservationDto;
import com.booking.booking.model.Facility;
import com.booking.booking.model.Reservation;
import com.booking.booking.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);

    List<Reservation> findAllByTenant (Tenant tenant);

    List<Reservation> findAllByFacility (Facility facility);

    @Query("select r from Reservation r where r.facility.name = :#{#resDto.facilityName} " +
            "and r.startDate between :#{#resDto.startDate} and :#{#resDto.endDate} " +
            "or r.endDate between :#{#resDto.startDate} and :#{#resDto.endDate} ")
    Reservation findByFacilityAndTenancyDates( CreateReservationDto resDto);


}
