package com.booking.booking.repository;

import com.booking.booking.model.Facility;
import com.booking.booking.model.Reservation;
import com.booking.booking.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);

    List<Reservation> findAllByTenant (Tenant tenant);


    List<Reservation> findAllByFacility (Facility facility);


    @Query("select r from Reservation r where r.facility.id = :id")
    List<Reservation> findReservationsByFacilityId(Long id);


}
