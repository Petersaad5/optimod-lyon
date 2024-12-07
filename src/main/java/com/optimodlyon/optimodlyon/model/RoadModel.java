package com.optimodlyon.optimodlyon.model;

public class RoadModel {
    double length;
    IntersectionModel origin;
    IntersectionModel destination;
    String name;

    public RoadModel() {
    }

    public RoadModel(double length, IntersectionModel origin, IntersectionModel destination, String name) {
        this.length = length;
        this.origin = origin;
        this.destination = destination;
        this.name = name;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public IntersectionModel getOrigin() {
        return origin;
    }

    public void setOrigin(IntersectionModel origin) {
        this.origin = origin;
    }

    public IntersectionModel getDestination() {
        return destination;
    }

    public void setDestination(IntersectionModel destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoadModel{length=" + length + ", origin=" + origin + ", destination=" + destination + ", name='" + name + "'}";
    }
}
