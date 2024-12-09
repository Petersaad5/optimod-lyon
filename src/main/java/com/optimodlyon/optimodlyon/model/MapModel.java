package com.optimodlyon.optimodlyon.model;
import java.util.ArrayList;
import java.util.List;

    public class MapModel {
        int id;
        List<IntersectionModel> intersections;
        List<RoadModel> roads;

        public MapModel() {
        }

        public MapModel(int id, List<IntersectionModel> intersections, List<RoadModel> roads) {
            this.id = id;
            this.intersections = intersections;
            this.roads = roads;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
        public List<RoadModel> getRoadsFromIntersection(IntersectionModel intersection) {
            List<RoadModel> result = new ArrayList<>();
            for (RoadModel road : roads) {
                if (road.getOrigin().equals(intersection)) {
                    result.add(road);
                }
            }
            return result;
        }

        public RoadModel getRoadBetween(IntersectionModel origin, IntersectionModel destination) {
            for (RoadModel road : roads) {
                if (road.getOrigin().equals(origin) && road.getDestination().equals(destination)|| road.getOrigin().equals(destination) && road.getDestination().equals(origin)) {
                    return road;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "MapModel{id=" + id + ", intersections=" + intersections + ", roads=" + roads + "}";
        }
}
