package com.optimodlyon.optimodlyon.utils;

import com.optimodlyon.optimodlyon.model.*;

import java.text.DateFormat;
import java.util.*;

public class TSP {

    public static List<List<IntersectionModel>> generatePermutations(DeliveryRequestModel deliveryRequest) {
        List<DeliveryModel> deliveries = deliveryRequest.getDeliveries();
        List<List<IntersectionModel>> permutations = new ArrayList<>();
        IntersectionModel warehouseIntersection = deliveryRequest.getWarehouse().getAddress();

        // Step 1: Create a list of pickup and delivery pairs
        List<IntersectionModel> allPoints = new ArrayList<>();
        for (DeliveryModel delivery : deliveries) {
            allPoints.add(delivery.getOrigin()); // Pickup point
            allPoints.add(delivery.getDestination()); // Delivery point
        }

        // Step 2: Backtracking to generate valid permutations
        backtrack(permutations, new ArrayList<>(), allPoints, new HashSet<>(), new HashSet<>());
        return permutations;
    }

    private static void backtrack(
            List<List<IntersectionModel>> permutations,
            List<IntersectionModel> currentPermutation,
            List<IntersectionModel> allPoints,
            Set<IntersectionModel> usedPickups,
            Set<IntersectionModel> usedPoints
    ) {
        // Base case: when the current permutation includes all points
        if (currentPermutation.size() == allPoints.size()) {
            permutations.add(new ArrayList<>(currentPermutation));
            return;
        }

        for (IntersectionModel point : allPoints) {
            // If already used, skip
            if (usedPoints.contains(point)) continue;

            // If it's a delivery point, ensure its pickup is already included
            if (isDeliveryPoint(point, allPoints) && !usedPickups.contains(getPickupPoint(point, allPoints))) {
                continue;
            }

            // Mark the point as used and add it to the current permutation
            usedPoints.add(point);
            if (isPickupPoint(point, allPoints)) {
                usedPickups.add(point);
            }

            currentPermutation.add(point);

            // Recurse
            backtrack(permutations, currentPermutation, allPoints, usedPickups, usedPoints);

            // Backtrack
            currentPermutation.remove(currentPermutation.size() - 1);
            usedPoints.remove(point);
            if (isPickupPoint(point, allPoints)) {
                usedPickups.remove(point);
            }
        }
    }

    private static boolean isPickupPoint(IntersectionModel point, List<IntersectionModel> allPoints) {
        // A point is a pickup point if it appears at an even index (0, 2, 4, ...)
        return allPoints.indexOf(point) % 2 == 0;
    }

    private static boolean isDeliveryPoint(IntersectionModel point, List<IntersectionModel> allPoints) {
        // A point is a delivery point if it appears at an odd index (1, 3, 5, ...)
        return !isPickupPoint(point, allPoints);
    }

    private static IntersectionModel getPickupPoint(IntersectionModel deliveryPoint, List<IntersectionModel> allPoints) {
        // For a delivery point at index i, its pickup point is at index i-1
        int deliveryIndex = allPoints.indexOf(deliveryPoint);
        return allPoints.get(deliveryIndex - 1);
    }

    // dijkstra algorithm
    public static List<IntersectionModel> dijkstra(MapModel map, IntersectionModel start, IntersectionModel end) {
        // Initialize the distance of each intersection to infinity
        Map<IntersectionModel, Double> distance = new HashMap<>();
        for (IntersectionModel intersection : map.getIntersections()) {
            distance.put(intersection, Double.POSITIVE_INFINITY);
        }
        distance.put(start, 0.0);

        // Initialize the previous intersection of each intersection to null
        Map<IntersectionModel, IntersectionModel> previous = new HashMap<>();
        for (IntersectionModel intersection : map.getIntersections()) {
            previous.put(intersection, null);
        }

        // Initialize the set of unvisited intersections
        Set<IntersectionModel> unvisited = new HashSet<>(map.getIntersections());

        // While there are unvisited intersections
        while (!unvisited.isEmpty()) {
            // Find the intersection with the smallest distance
            IntersectionModel current = null;
            for (IntersectionModel intersection : unvisited) {
                if (current == null || distance.get(intersection) < distance.get(current)) {
                    current = intersection;
                }
            }

            // Remove the current intersection from the set of unvisited intersections
            unvisited.remove(current);

            // If the current intersection is the end intersection, stop
            if (current.equals(end)) {
                break;
            }

            // Update the distance of each neighbor of the current intersection
            for (RoadModel road : map.getRoads()) {
                if (road.containsIntersection(current.getId())) {
                    IntersectionModel neighbor = road.isOrigin(current.getId()) ? road.getDestination() : road.getOrigin();
                    double newDistance = distance.get(current) + road.getLength();
                    if (newDistance < distance.get(neighbor)) {
                        distance.put(neighbor, newDistance);
                        previous.put(neighbor, current);
                    }
                }
            }
        }

        // Reconstruct the shortest path
        List<IntersectionModel> path = new ArrayList<>();
        for (IntersectionModel intersection = end; intersection != null; intersection = previous.get(intersection)) {
            path.add(intersection);
        }
        Collections.reverse(path);
        return path;
    }

    public static double calculateDistance(List<IntersectionModel> path, MapModel map) {
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            IntersectionModel current = path.get(i);
            IntersectionModel next = path.get(i + 1);
            for (RoadModel road : map.getRoads()) {
                if (road.containsIntersection(current.getId()) && road.containsIntersection(next.getId())) {
                    distance += road.getLength();
                    break;
                }
            }
        }
        return distance;
    }

    public static MapModel tsp (DeliveryRequestModel deliveryRequest, MapModel map) {
        List<List<IntersectionModel>> permutations = generatePermutations(deliveryRequest);
        List<IntersectionModel> bestPath = null; // The best path found so far
        List<RoadModel> bestRoads = null;
        MapModel bestMap = new MapModel(1, bestPath, bestRoads);
        double bestDistance = Double.POSITIVE_INFINITY; // The distance of the best path found so far
        List<RoadModel> tempBestRoad = new ArrayList<>();
        for (List<IntersectionModel> permutation : permutations) {
            // add warehouse to the beginning and end of the permutation
            permutation.add(0, deliveryRequest.getWarehouse().getAddress());
            permutation.add(deliveryRequest.getWarehouse().getAddress());
            double distance = 0;
            tempBestRoad.clear();
            for (int i = 0; i < permutation.size() - 1; i++) {
                IntersectionModel current = permutation.get(i);
                IntersectionModel next = permutation.get(i+1);
                List<IntersectionModel> bestRoute = dijkstra(map, current, next);
                for (int j = 0; j < bestRoute.size() - 1; j++) {
                    tempBestRoad.add(map.getRoadByIntersections(bestRoute.get(j), bestRoute.get(j+1)));
                }
                distance += calculateDistance(bestRoute, map);
                // heuristic
                if (distance > bestDistance) {
                    break;
                }
            }
            if(distance < bestDistance) {
                bestDistance = distance;
                bestPath = permutation;
                bestRoads = tempBestRoad;
                bestMap.setIntersections(bestPath);
                bestMap.setRoads(bestRoads);
            }
        }
        return bestMap;
    }

    // test main method
//    public static void main(String[] args) {
//        // Create a delivery request
//        IntersectionModel warehouse = new IntersectionModel(0L, 0, 0);
//        IntersectionModel pickup1 = new IntersectionModel(1L, 1, 1);
//        IntersectionModel delivery1 = new IntersectionModel(2L, 2, 2);
//        IntersectionModel pickup2 = new IntersectionModel(3L, 3, 3);
//        IntersectionModel delivery2 = new IntersectionModel(4L, 4, 4);
//        IntersectionModel pickup3 = new IntersectionModel(5L, 5, 5);
//        IntersectionModel delivery3 = new IntersectionModel(6L, 6, 6);
//        DeliveryModel deliveryModel1 = new DeliveryModel(10, 5, delivery1, pickup1);
//        DeliveryModel deliveryModel2 = new DeliveryModel(15, 5, delivery2, pickup2);
//        DeliveryModel deliveryModel3 = new DeliveryModel(20, 5, delivery3, pickup3);
//        List<DeliveryModel> deliveries = new ArrayList<>();
//        deliveries.add(deliveryModel1);
//        deliveries.add(deliveryModel2);
//        deliveries.add(deliveryModel3);
//        Date date = new Date(122, 11, 9);
//        WarehouseModel warehouseM = new WarehouseModel(date, warehouse);
//        DeliveryRequestModel deliveryRequest = new DeliveryRequestModel(1L, warehouseM, deliveries);
//
//        // Print delivery request
//        System.out.println("Delivery request:");
//        System.out.println(deliveryRequest);
//
//        // Generate permutations
//        List<List<IntersectionModel>> permutations = generatePermutations(deliveryRequest);
//
//        // Print permutations
//        System.out.println(permutations.size());
//
//        // Create a map
//
//
//
//    }
}