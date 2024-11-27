package com.optimodlyon.optimodlyon.model;
import java.util.List;

public class Data {
    MapModel map;
    List<IntersectionModel> intersections;
    List<RoadModel> roads;

    public Data() {
    }

    public Data(MapModel map, List<IntersectionModel> intersections, List<RoadModel> roads) {
        this.map = map;
        this.intersections = intersections;
        this.roads = roads;
    }

    public MapModel getMap() {
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


}
