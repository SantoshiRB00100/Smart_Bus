package com.smartbus.service;

import com.smartbus.model.Bus;
import com.smartbus.repository.BusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Bus save(Bus bus) {
        return busRepository.save(bus);
    }

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Optional<Bus> findById(Long id) {
        return busRepository.findById(id);
    }

    // 👇 YOU WERE MISSING THIS
    public Optional<Bus> findByBusCode(String busCode) {
        return busRepository.findByNumber(busCode);
    }
}
