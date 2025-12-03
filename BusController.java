package com.smartbus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartbus.model.Bus;
import com.smartbus.service.BusService;
import com.smartbus.entity.BusLocation;
import com.smartbus.service.BusLocationService;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bus")
public class BusController {

    private final BusService busService;
    private final BusLocationService locationService;

    public BusController(BusService busService, BusLocationService locationService) {
        this.busService = busService;
        this.locationService = locationService;
    }

    // ------------------------ ADD BUS ------------------------
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addBus(@RequestBody Bus bus) {
        Bus saved = busService.save(bus);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("bus", saved);

        return ResponseEntity.created(URI.create("/api/bus/" + saved.getId())).body(response);
    }

    // ------------------------ GET ALL ------------------------
    @GetMapping("/all")
    public List<Bus> getAll() {
        return busService.findAll();
    }

    // ------------------------ GET BY CODE ------------------------
    @GetMapping("/{busCode}")
    public ResponseEntity<Map<String, Object>> getByCode(@PathVariable String busCode) {
        Map<String, Object> response = new HashMap<>();

        return busService.findByBusCode(busCode).map(bus -> {
            response.put("success", true);
            response.put("bus", bus);
            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            response.put("success", false);
            response.put("message", "❌ Bus Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }

    // ------------------------ TRACK LIVE ------------------------
    @GetMapping("/track/{busCode}")
    public ResponseEntity<Map<String, Object>> trackBus(@PathVariable String busCode) {
        Map<String, Object> response = new HashMap<>();

        return busService.findByBusCode(busCode).map(bus -> {

            // get updated live location
            BusLocation location = locationService.trackBus(bus.getId());

            // ---- ETA DECREMENT LOGIC ----
            String etaStr = bus.getEta().replace("minutes", "").trim();
            int remainingEta = Integer.parseInt(etaStr);

            remainingEta = Math.max(0, remainingEta - 1);

            // ---- STATUS HANDLING ----
            if (remainingEta == 0) {
                bus.setStatus("Arrived");
            } else if (remainingEta <= 5) {
                bus.setStatus("Arriving Soon");
            } else if (remainingEta > 10) {
                bus.setStatus("Running Late");
            } else {
                bus.setStatus("On The Way");
            }

            bus.setEta(remainingEta + " minutes");

            // update live location fields
            location.setEta(bus.getEta());
            location.setStatus(bus.getStatus());

            // Splitting route "City → College"
            if (bus.getRoute().contains("→")) {
                String[] routeParts = bus.getRoute().split("→");
                location.setRouteFrom(routeParts[0].trim());
                location.setRouteTo(routeParts[1].trim());
            }

            response.put("success", true);
            response.put("message", "🚍 Live bus tracking updated");
            response.put("bus", location);

            return ResponseEntity.ok(response);

        }).orElseGet(() -> {
            response.put("success", false);
            response.put("message", "❌ Bus Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }

    // ------------------------ TRACK ALL ------------------------
    @GetMapping("/track/all")
    public ResponseEntity<Map<String, Object>> trackAllBuses() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("buses", locationService.trackAllBuses());
        return ResponseEntity.ok(response);
    }
}



