package com.booking.booking.repository;

import com.booking.booking.model.Facility;
import com.booking.booking.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    Optional<Facility> findById(UUID id);

    List<Facility> findAllByLandlord(Landlord landlord);
}
