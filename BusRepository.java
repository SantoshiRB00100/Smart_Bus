package com.smartbus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartbus.model.Bus;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {
    Optional<Bus> findByNumber(String number); // Bus number used as BusCode
}


