package com.booking.booking.repository;

import com.booking.booking.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {

}
