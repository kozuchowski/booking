package com.booking.booking.repository;

import com.booking.booking.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findById(Long id);

    @Query("select t from Tenant t where t.name = :name")
    Tenant findByTenantName(String name);
}
