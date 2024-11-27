package com.optimodlyon.optimodlyon.model;

public class IntersectionModel {
    Integer id;
    double latitude;
    double longitude;
    // Default constructor
    public IntersectionModel() {
    }

    // Parameterized constructor
    public IntersectionModel(Integer id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
