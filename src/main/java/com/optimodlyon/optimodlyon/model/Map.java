package com.optimodlyon.optimodlyon.model;
import java.util.List;

    public class Map {
        int id;
        List<Intersection> intersections;
        List<Road> roads;

        public Map() {
        }

        public Map(int id, List<Intersection> intersections, List<Road> roads) {
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

        public Road getRoadByIntersections(Intersection intersection1, Intersection intersection2) {
            for (Road road : roads) {
                if (road.containsIntersection(intersection1.getId()) && road.containsIntersection(intersection2.getId())) {
                    return road;
                }
            }
            return null;
        }

        public void addMap(Map map) {
            for (Intersection intersection : map.getIntersections()) {
                this.intersections.add(intersection);
            }
            for (Road road : map.getRoads()) {
                this.roads.add(road);
            }
        }

        @Override
        public String toString() {
            return "MapModel{id=" + id + ", intersections=" + intersections + ", roads=" + roads + "}";
        }
}
