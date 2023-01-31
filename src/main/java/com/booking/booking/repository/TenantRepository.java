package com.booking.booking.repository;

import com.booking.booking.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
