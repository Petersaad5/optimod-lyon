package com.optimodlyon.optimodlyon.model;
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

        public RoadModel getRoadByIntersections(IntersectionModel intersection1, IntersectionModel intersection2) {
            for (RoadModel road : roads) {
                if (road.containsIntersection(intersection1.getId()) && road.containsIntersection(intersection2.getId())) {
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
