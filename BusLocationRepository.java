package com.smartbus.repository;

import com.smartbus.entity.BusLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusLocationRepository extends JpaRepository<BusLocation, Long> {
    Optional<BusLocation> findByBusId(Long busId);
}





