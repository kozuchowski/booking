package com.booking.booking.repository;

import com.booking.booking.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
