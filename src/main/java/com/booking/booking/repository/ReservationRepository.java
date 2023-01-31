package com.booking.booking.repository;

import com.booking.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying
    @Transactional
    @Query("update Reservation r set r.tenancyPeriod = :#{#res.tenancyPeriod}, r.startDate = :#{#res.startDate}, " +
            "r.endDate = :#{#res.endDate}, r.facility = :#{#res.facility}, r.tenant = :#{#res.tenant}")
    void update(@Param("res") Reservation res);


}
