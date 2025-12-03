package com.smartbus.controller;

import com.smartbus.entity.BusLocation;
import com.smartbus.service.BusLocationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bus/location")
@CrossOrigin("*")
public class BusLocationController {

    private final BusLocationService service;

    public BusLocationController(BusLocationService service) {
        this.service = service;
    }

    @PostMapping("/update")
    public BusLocation updateLocation(@RequestBody BusLocation location) {
        return service.updateLocation(location);
    }

    @GetMapping("/track/{busId}")
    public Object trackBus(@PathVariable Long busId) {
        BusLocation data = service.trackBus(busId);

        if (data == null) {
            return new Response(false, "Bus Not Found");
        }

        return new Response(true, data);
    }

    record Response(boolean success, Object data) {}
}

