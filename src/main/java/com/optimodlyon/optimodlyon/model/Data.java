package com.optimodlyon.optimodlyon.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {
    static MapModel map;
    List<IntersectionModel> intersections;
    List<RoadModel> roads;
    List <CourierModel> couriers;
    List<TourModel> tours;


    public Data() {
        tours = new ArrayList<>();
    }

    public Data(MapModel map, List<IntersectionModel> intersections, List<RoadModel> roads) {
        this.map = map;
        this.intersections = intersections;
        this.roads = roads;
    }

    public static MapModel getMap() {
        return map;
    }

    public void setMap(MapModel map) {
        this.map = map;
    }

    public List<IntersectionModel> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<IntersectionModel> intersections) {
        this.intersections = intersections;
    }

    public List<RoadModel> getRoads() {
        return roads;
    }

    public void setRoads(List<RoadModel> roads) {
        this.roads = roads;
    }

    public List<CourierModel> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<CourierModel> couriers) {
        this.couriers = couriers;
    }

    public void addTour(TourModel tour) {
        this.tours.add(tour);
    }

    public List<TourModel> getTours() {
        return tours;
    }


}
