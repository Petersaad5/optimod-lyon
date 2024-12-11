package com.optimodlyon.optimodlyon.utils;

import com.optimodlyon.optimodlyon.model.*;

import java.text.DateFormat;
import java.util.*;

public class TSP {

    // counter
    private static int dijkstraCalls = 0;
    private static int dijsktraCallsMemo = 0;

    // memoization matrix for dijkstra
    private static final Map<String, MapModel> memo = new HashMap<>();

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

    public static MapModel dijkstra(MapModel map, IntersectionModel start, IntersectionModel end) {
        String key = start.getId() + "-" + end.getId();
        dijkstraCalls++;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        dijsktraCallsMemo++;

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
        List<RoadModel> roads = new ArrayList<>();
        for (IntersectionModel intersection = end; intersection != null; intersection = previous.get(intersection)) {
            path.add(intersection);
            // reconstruct the road
            if (previous.get(intersection) != null) {
                roads.add(map.getRoadByIntersections(intersection, previous.get(intersection)));
            }
        }
        Collections.reverse(path);
        MapModel result = new MapModel(-1, path, roads);
        memo.put(key, result);
        return result;
    }

    public static double calculateDistance(MapModel map) {
        double distance = 0;
        for (RoadModel road : map.getRoads()) {
            distance += road.getLength();
        }
        return distance;
    }

    public static MapModel tsp (DeliveryRequestModel deliveryRequest, MapModel map) {
        // calculate the time taken for the tsp algorithm
        long startTime = System.currentTimeMillis();
        List<List<IntersectionModel>> permutations = generatePermutations(deliveryRequest);
        MapModel bestMap = new MapModel(-1, null, null);
        double bestDistance = Double.POSITIVE_INFINITY; // The distance of the best path found so far
        for (List<IntersectionModel> permutation : permutations) {
            MapModel tempBestMap = new MapModel(1, new ArrayList<>() , new ArrayList<>());
            // add warehouse to the beginning and end of the permutation
            permutation.add(0, deliveryRequest.getWarehouse().getAddress());
            permutation.add(deliveryRequest.getWarehouse().getAddress());
            double distance = 0;
            for (int i = 0; i < permutation.size() - 1; i++) {
                IntersectionModel current = permutation.get(i);
                IntersectionModel next = permutation.get(i+1);
                MapModel subMap = dijkstra(map, current, next);
                tempBestMap.addMap(subMap);
                distance += calculateDistance(subMap);
                // heuristic
                if (distance > bestDistance) {
                    break;
                }
            }
            if(distance < bestDistance) {
                bestMap = tempBestMap;
                bestDistance = distance;
            }
        }
        System.out.println("Dijkstra calls: " + dijkstraCalls);
        System.out.println("Dijkstra calls with memoization: " + dijsktraCallsMemo);
        System.out.println("Time taken for TSP: " + (System.currentTimeMillis() - startTime) + "ms");
        return bestMap;
    }

}