package com.smartbus.service;

import com.smartbus.entity.BusLocation;
import com.smartbus.repository.BusLocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BusLocationService {

    private final BusLocationRepository repository;
    private final Random random = new Random();

    public BusLocationService(BusLocationRepository repository) {
        this.repository = repository;
    }

    // Save or update manually entered location
    public BusLocation updateLocation(BusLocation location) {

        // Default route setup
        if (location.getRouteFrom() == null || location.getRouteFrom().isBlank())
            location.setRouteFrom("Majestic");

        if (location.getRouteTo() == null || location.getRouteTo().isBlank())
            location.setRouteTo("Whitefield");

        // Default ETA if empty
        if (location.getEta() == null || location.getEta().isBlank())
            location.setEta("15 min");

        // Default status
        if (location.getStatus() == null || location.getStatus().isBlank())
            location.setStatus("On Time");

        return repository.save(location);
    }


    // Main realtime simulation logic
    public BusLocation trackBus(Long busId) {

        Optional<BusLocation> optional = repository.findByBusId(busId);

        if (optional.isPresent()) {
            BusLocation bus = optional.get();

            // Move location slightly (simulate bus movement)
            bus.setLatitude(bus.getLatitude() + (random.nextDouble() - 0.5) * 0.0007);
            bus.setLongitude(bus.getLongitude() + (random.nextDouble() - 0.5) * 0.0007);

            // Fix missing route only ONCE
            if (bus.getRouteFrom() == null || bus.getRouteFrom().isBlank())
                bus.setRouteFrom("Majestic");

            if (bus.getRouteTo() == null || bus.getRouteTo().isBlank())
                bus.setRouteTo("Whitefield");


            // ---- ETA Logic ----
            int eta;
            try {
                eta = Integer.parseInt(bus.getEta().replaceAll("\\D", ""));
            } catch (Exception e) {
                eta = 15;
            }

            // Decrease but never below 0
            if (eta > 0) eta--;

            bus.setEta(eta + " min");


            // ---- Status Conditions ----
            if (eta == 0) bus.setStatus("Arrived");
            else if (eta <= 5) bus.setStatus("On Time");
            else bus.setStatus("Running Late");


            return repository.save(bus);
        }

        return null;
    }


    // For frontend calls tracking all markers
    public List<BusLocation> trackAllBuses() {
        List<BusLocation> buses = repository.findAll();
        buses.forEach(bus -> trackBus(bus.getBusId()));
        return buses;
    }
}







