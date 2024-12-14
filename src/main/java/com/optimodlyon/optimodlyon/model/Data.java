package com.optimodlyon.optimodlyon.model;
import java.util.ArrayList;
import java.util.List;

public class Data {
    Map map;
    List<Intersection> intersections;
    List<Road> roads;
    List<Courier> couriers;
    List<Tour> tours;


    public Data() {
        tours = new ArrayList<>();
        couriers = new ArrayList<>();
        intersections = new ArrayList<>();
        roads = new ArrayList<>();
        map = new Map();
    }

    public Data(Map map, List<Intersection> intersections, List<Road> roads) {
        this.map = map;
        this.intersections = intersections;
        this.roads = roads;
    }

    public Map getMap() {
        return this.map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }

    public void addTour(Tour tour) {
        this.tours.add(tour);
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }


}
