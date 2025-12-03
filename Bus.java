package com.smartbus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;
    private String route;

    // 🆕 Live tracking fields
    private double latitude = 12.9716;  // default test location
    private double longitude = 77.5946; // default test location

    private String eta;       // Estimated Time Arrival
    private String status;    // On Time / Running Late

    public Bus() {}

    public Bus(String name, String number, String route) {
        this.name = name;
        this.number = number;
        this.route = route;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getEta() { return eta; }
    public void setEta(String eta) { this.eta = eta; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}


