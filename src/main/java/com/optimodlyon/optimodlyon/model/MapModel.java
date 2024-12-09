package com.optimodlyon.optimodlyon.model;
import java.util.List;

    public class MapModel {
        int id;
        List<IntersectionModel> intersections;
        List<RoadModel> roads;
        private double cost;

        public MapModel() {
        }

        public MapModel(int id, List<IntersectionModel> intersections, List<RoadModel> roads) {
            this.id = id;
            this.intersections = intersections;
            this.roads = roads;
        }

        public MapModel(double cost) {
            this.cost = cost;
        }

        public MapModel(List<RoadModel> roads, List<IntersectionModel> intersections, int id, double cost) {
            this.roads = roads;
            this.intersections = intersections;
            this.id = id;
            this.cost = cost;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
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

        public void addMap(MapModel map) {
            for (IntersectionModel intersection : map.getIntersections()) {
                this.intersections.add(intersection);
            }
            for (RoadModel road : map.getRoads()) {
                this.roads.add(road);
            }
        }

        @Override
        public String toString() {
            return "MapModel{id=" + id + ", intersections=" + intersections + ", roads=" + roads + "}";
        }
}
