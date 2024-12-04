package com.optimodlyon.optimodlyon.utils;

import com.optimodlyon.optimodlyon.model.*;

import java.util.*;

public class TourOptimizer {

    public static class Tour {
        private List<IntersectionModel> intersections;
        private List<RoadModel> roads;

        public Tour(List<IntersectionModel> intersections, List<RoadModel> roads) {
            this.intersections = intersections;
            this.roads = roads;
        }

        public List<IntersectionModel> getIntersections() {
            return intersections;
        }

        public List<RoadModel> getRoads() {
            return roads;
        }
    }

    public static Tour calculateOptimalTour(DeliveryRequestModel deliveryRequest, MapModel map) {
        List<IntersectionModel> tourIntersections = new ArrayList<>();
        List<RoadModel> tourRoads = new ArrayList<>();
        IntersectionModel warehouseLocation = deliveryRequest.getWarehouse().getAddress();
        tourIntersections.add(warehouseLocation);

        List<DeliveryModel> deliveries = deliveryRequest.getDeliveries();
        Set<IntersectionModel> visited = new HashSet<>();
        IntersectionModel currentLocation = warehouseLocation;

        while (visited.size() < deliveries.size() * 2) {
            IntersectionModel nextPoint = null;
            double minDistance = Double.MAX_VALUE;
            List<RoadModel> shortestPath = new ArrayList<>();

            for (DeliveryModel delivery : deliveries) {
                if (!visited.contains(delivery.getOrigin())) {
                    List<RoadModel> path = findShortestPath(currentLocation, delivery.getOrigin(), map);
                    double distance = calculatePathDistance(path);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nextPoint = delivery.getOrigin();
                        shortestPath = path;
                    }
                } else if (!visited.contains(delivery.getDestination()) && visited.contains(delivery.getOrigin())) {
                    List<RoadModel> path = findShortestPath(currentLocation, delivery.getDestination(), map);
                    double distance = calculatePathDistance(path);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nextPoint = delivery.getDestination();
                        shortestPath = path;
                    }
                }
            }

            if (nextPoint != null) {
                tourIntersections.add(nextPoint);
                tourRoads.addAll(shortestPath);
                visited.add(nextPoint);
                currentLocation = nextPoint;
            }
        }

        List<RoadModel> returnPath = findShortestPath(currentLocation, warehouseLocation, map);
        tourIntersections.add(warehouseLocation);
        tourRoads.addAll(returnPath);

        return new Tour(tourIntersections, tourRoads);
    }

    private static List<RoadModel> findShortestPath(IntersectionModel start, IntersectionModel end, MapModel map) {
        Map<IntersectionModel, Double> distances = new HashMap<>();
        Map<IntersectionModel, IntersectionModel> previous = new HashMap<>();
        PriorityQueue<IntersectionModel> queue = new PriorityQueue<>(Comparator.comparing(distances::get));

        for (IntersectionModel intersection : map.getIntersections()) {
            distances.put(intersection, Double.MAX_VALUE);
            previous.put(intersection, null);
        }
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            IntersectionModel current = queue.poll();

            if (current.equals(end)) {
                break;
            }

            for (RoadModel road : map.getRoadsFromIntersection(current)) {
                IntersectionModel neighbor = road.getDestination();
                double newDist = distances.get(current) + road.getLength();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<RoadModel> path = new ArrayList<>();
        for (IntersectionModel at = end; at != null; at = previous.get(at)) {
            IntersectionModel prev = previous.get(at);
            if (prev != null) {
                path.add(0, map.getRoadBetween(prev, at));
            }
        }

        return path;
    }

    private static double calculatePathDistance(List<RoadModel> path) {
        double distance = 0;
        for (RoadModel road : path) {
            distance += road.getLength();
        }
        return distance;
    }
}