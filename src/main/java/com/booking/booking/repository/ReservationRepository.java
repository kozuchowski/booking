package com.booking.booking.repository;

import com.booking.booking.model.Facility;
import com.booking.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying
    @Transactional
    @Query("update Reservation r set r.tenancyPeriod = :#{#res.tenancyPeriod}, r.startDate = :#{#res.startDate}, " +
            "r.endDate = :#{#res.endDate}, r.facility = :#{#res.facility}, r.tenant = :#{#res.tenant}")
    void update(@Param("res") Reservation res);

    @Query("select r from  Reservation r where r.tenant.name = ?1")
    List<Reservation> findAllByTenant (String tenantName);

    @Query("select r from Reservation r where r.facility = ?1")
    List<Reservation> findAllByFacility (Facility facility);

    @Query("select r from Reservation r where r.facility = :#{#res.facility} " +
            "and r.startDate >= :#{#res.startDate} and r.endDate <= :#{#res.endDate}")
    Reservation findByFacilityAndTenancyDates(@Param("res") Reservation res);


}
