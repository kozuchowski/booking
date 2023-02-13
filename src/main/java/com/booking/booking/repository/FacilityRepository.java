package com.booking.booking.repository;

import com.booking.booking.model.Facility;
import com.booking.booking.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    Optional<Facility> findById(Long id);

    Facility findByName(String name);

    List<Facility> findAllByLandlord(Landlord landlord);

    List<Facility> findAll();

}
