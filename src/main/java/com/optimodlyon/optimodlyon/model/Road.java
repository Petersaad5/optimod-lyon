package com.optimodlyon.optimodlyon.model;

public class Road {
    double length;
    Intersection origin;
    Intersection destination;
    String name;

    public Road() {
    }

    public Road(double length, Intersection origin, Intersection destination, String name) {
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

    public Intersection getOrigin() {
        return origin;
    }

    public void setOrigin(Intersection origin) {
        this.origin = origin;
    }

    public Intersection getDestination() {
        return destination;
    }

    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean containsIntersection(Long id) {
        if(origin.getId().equals(id) || destination.getId().equals(id)) {
            return true;
        }
        return false;
    }

    public boolean isOrigin(Long id) {
        return origin.getId().equals(id);
    }

    public boolean isDestination(Long id) {
        return destination.getId().equals(id);
    }

    @Override
    public String toString() {
        return "RoadModel{length=" + length + ", origin=" + origin + ", destination=" + destination + ", name='" + name + "'}";
    }
}
