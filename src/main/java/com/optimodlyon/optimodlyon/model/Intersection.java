package com.optimodlyon.optimodlyon.model;

public class Intersection {
    Long id;
    double latitude;
    double longitude;
    // Default constructor
    public Intersection() {
    }

    // Parameterized constructor
    public Intersection(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Intersection getIntersectionById(Long id) {
        return this;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "IntersectionModel{id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + "}";
    }
}