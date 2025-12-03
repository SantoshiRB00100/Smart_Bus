package com.smartbus.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bus_locations")
public class BusLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long busId;
    private double latitude;
    private double longitude;
    private String eta;
    private String status;
    private String routeFrom;
    private String routeTo;

    // ---------------- Getters & Setters ----------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBusId() { return busId; }
    public void setBusId(Long busId) { this.busId = busId; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getEta() { return eta; }
    public void setEta(String eta) { this.eta = eta; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRouteFrom() { return routeFrom; }
    public void setRouteFrom(String routeFrom) { this.routeFrom = routeFrom; }

    public String getRouteTo() { return routeTo; }
    public void setRouteTo(String routeTo) { this.routeTo = routeTo; }
}




