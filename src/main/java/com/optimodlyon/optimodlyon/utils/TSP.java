package com.optimodlyon.optimodlyon.utils;

import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.model.Map;

import java.util.*;

public class TSP {

    // counter
    private static int dijkstraCalls = 0;
    private static int dijsktraCallsMemo = 0;

    // memoization matrix for dijkstra with LRU cache
    private static final int MAX_MEMO_SIZE = 1000;
    private static final java.util.Map<String, Map> memo = new LinkedHashMap<String, Map>(MAX_MEMO_SIZE, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<String, Map> eldest) {
            return size() > MAX_MEMO_SIZE;
        }
    };

//    public static List<List<IntersectionModel>> generatePermutations(DeliveryRequestModel deliveryRequest) {
//        List<DeliveryModel> deliveries = deliveryRequest.getDeliveries();
//        List<List<IntersectionModel>> permutations = new ArrayList<>();
//        IntersectionModel warehouseIntersection = deliveryRequest.getWarehouse().getAddress();
//
//        // Step 1: Create a list of pickup and delivery pairs
//        List<IntersectionModel> allPoints = new ArrayList<>();
//        for (DeliveryModel delivery : deliveries) {
//            allPoints.add(delivery.getOrigin()); // Pickup point
//            allPoints.add(delivery.getDestination()); // Delivery point
//        }
//
//        // Step 2: Backtracking to generate valid permutations
//        backtrack(permutations, new ArrayList<>(), allPoints, new HashSet<>(), new HashSet<>());
//        return permutations;
//    }

    public static List<List<Intersection>> generatePertinentPermutations(DeliveryRequest deliveryRequest) {
        List<Delivery> deliveries = deliveryRequest.getDeliveries();
        List<List<Intersection>> permutations = new ArrayList<>();
        Intersection warehouseIntersection = deliveryRequest.getWarehouse().getAddress();

        // Step 1: Create a list of pickup and delivery pairs
        List<Intersection> allPoints = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            allPoints.add(delivery.getOrigin()); // Pickup point
            allPoints.add(delivery.getDestination()); // Delivery point
        }

        // Step 2: Generate permutations using nearest neighbor calculateEuclidienDistance
        List<Intersection> currentPermutation = new ArrayList<>();
        Set<Intersection> usedPoints = new HashSet<>();
        currentPermutation.add(warehouseIntersection);
        usedPoints.add(warehouseIntersection);

        while (currentPermutation.size() < allPoints.size() + 1) {
            Intersection lastPoint = currentPermutation.get(currentPermutation.size() - 1);
            Intersection nextPoint = findNearestNeighbor(lastPoint, allPoints, usedPoints);
            if (nextPoint != null) {
                currentPermutation.add(nextPoint);
                usedPoints.add(nextPoint);
            } else {
                break;
            }
        }

        // Add the warehouse to the end of the permutation
        currentPermutation.add(warehouseIntersection);
        permutations.add(currentPermutation);

        return permutations;
    }

    private static Intersection findNearestNeighbor(Intersection current, List<Intersection> allPoints, Set<Intersection> usedPoints) {
        Intersection nearestNeighbor = null;
        double shortestDistance = Double.POSITIVE_INFINITY;

        for (Intersection point : allPoints) {
            if (!usedPoints.contains(point)) {
                double distance = calculateEuclidienDistance(current, point);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestNeighbor = point;
                }
            }
        }

        return nearestNeighbor;
    }

    private static void backtrack(
            List<List<Intersection>> permutations,
            List<Intersection> currentPermutation,
            List<Intersection> allPoints,
            Set<Intersection> usedPickups,
            Set<Intersection> usedPoints
    ) {
        // Base case: when the current permutation includes all points
        if (currentPermutation.size() == allPoints.size()) {
            permutations.add(new ArrayList<>(currentPermutation));
            return;
        }

        for (Intersection point : allPoints) {
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

    private static boolean isPickupPoint(Intersection point, List<Intersection> allPoints) {
        // A point is a pickup point if it appears at an even index (0, 2, 4, ...)
        return allPoints.indexOf(point) % 2 == 0;
    }

    private static boolean isDeliveryPoint(Intersection point, List<Intersection> allPoints) {
        // A point is a delivery point if it appears at an odd index (1, 3, 5, ...)
        return !isPickupPoint(point, allPoints);
    }

    private static Intersection getPickupPoint(Intersection deliveryPoint, List<Intersection> allPoints) {
        // For a delivery point at index i, its pickup point is at index i-1
        int deliveryIndex = allPoints.indexOf(deliveryPoint);
        return allPoints.get(deliveryIndex - 1);
    }


    public static Map aStar(Map map, Intersection start, Intersection end) {
        String key = start.getId() + "-" + end.getId();
        dijkstraCalls++;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        dijsktraCallsMemo++;

        // Initialize the distance of each intersection to infinity
        java.util.Map<Intersection, Double> gScore = new HashMap<>();
        for (Intersection intersection : map.getIntersections()) {
            gScore.put(intersection, Double.POSITIVE_INFINITY);
        }
        gScore.put(start, 0.0);

        // Initialize the calculateEuclidienDistance score of each intersection to infinity
        java.util.Map<Intersection, Double> fScore = new HashMap<>();
        for (Intersection intersection : map.getIntersections()) {
            fScore.put(intersection, Double.POSITIVE_INFINITY);
        }
        fScore.put(start, calculateEuclidienDistance(start, end));

        // Initialize the previous intersection of each intersection to null
        java.util.Map<Intersection, Intersection> previous = new HashMap<>();
        for (Intersection intersection : map.getIntersections()) {
            previous.put(intersection, null);
        }

        // Initialize the set of open intersections
        PriorityQueue<Intersection> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(start);

        // While there are open intersections
        while (!openSet.isEmpty()) {
            Intersection current = openSet.poll();

            // If the current intersection is the end intersection, stop
            if (current.equals(end)) {
                break;
            }

            // Update the distance of each neighbor of the current intersection
            for (Road road : map.getRoads()) {
                if (road.containsIntersection(current.getId())) {
                    Intersection neighbor = road.isOrigin(current.getId()) ? road.getDestination() : road.getOrigin();
                    double tentativeGScore = gScore.get(current) + road.getLength();
                    if (tentativeGScore < gScore.get(neighbor)) {
                        previous.put(neighbor, current);
                        gScore.put(neighbor, tentativeGScore);
                        fScore.put(neighbor, tentativeGScore + calculateEuclidienDistance(neighbor, end));
                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }

        // Reconstruct the shortest path
        List<Intersection> path = new ArrayList<>();
        List<Road> roads = new ArrayList<>();
        for (Intersection intersection = end; intersection != null; intersection = previous.get(intersection)) {
            path.add(intersection);
            // reconstruct the road
            if (previous.get(intersection) != null) {
                roads.add(map.getRoadByIntersections(intersection, previous.get(intersection)));
            }
        }
        Collections.reverse(path);
        Map result = new Map(-1, path, roads);
        memo.put(key, result);
        return result;
    }

    private static double calculateEuclidienDistance(Intersection a, Intersection b) {
        // Example calculateEuclidienDistance: Euclidean distance (assuming coordinates are available)
        double dx = a.getLongitude() - b.getLongitude();
        double dy = a.getLatitude() - b.getLatitude();
        return Math.sqrt(dx * dx + dy * dy);
    }

//    public static MapModel dijkstra(MapModel map, IntersectionModel start, IntersectionModel end) {
//        String key = start.getId() + "-" + end.getId();
//        dijkstraCalls++;
//        if (memo.containsKey(key)) {
//            return memo.get(key);
//        }
//
//        dijsktraCallsMemo++;
//
//        // Initialize the distance of each intersection to infinity
//        Map<IntersectionModel, Double> distance = new HashMap<>();
//        for (IntersectionModel intersection : map.getIntersections()) {
//            distance.put(intersection, Double.POSITIVE_INFINITY);
//        }
//        distance.put(start, 0.0);
//
//        // Initialize the previous intersection of each intersection to null
//        Map<IntersectionModel, IntersectionModel> previous = new HashMap<>();
//        for (IntersectionModel intersection : map.getIntersections()) {
//            previous.put(intersection, null);
//        }
//
//        // Initialize the set of unvisited intersections
//        Set<IntersectionModel> unvisited = new HashSet<>(map.getIntersections());
//
//        // While there are unvisited intersections
//        while (!unvisited.isEmpty()) {
//            // Find the intersection with the smallest distance
//            IntersectionModel current = null;
//            for (IntersectionModel intersection : unvisited) {
//                if (current == null || distance.get(intersection) < distance.get(current)) {
//                    current = intersection;
//                }
//            }
//
//            // Remove the current intersection from the set of unvisited intersections
//            unvisited.remove(current);
//
//            // If the current intersection is the end intersection, stop
//            if (current.equals(end)) {
//                break;
//            }
//
//            // Update the distance of each neighbor of the current intersection
//            for (RoadModel road : map.getRoads()) {
//                if (road.containsIntersection(current.getId())) {
//                    IntersectionModel neighbor = road.isOrigin(current.getId()) ? road.getDestination() : road.getOrigin();
//                    double newDistance = distance.get(current) + road.getLength();
//                    if (newDistance < distance.get(neighbor)) {
//                        distance.put(neighbor, newDistance);
//                        previous.put(neighbor, current);
//                    }
//                }
//            }
//        }
//
//        // Reconstruct the shortest path
//        List<IntersectionModel> path = new ArrayList<>();
//        List<RoadModel> roads = new ArrayList<>();
//        for (IntersectionModel intersection = end; intersection != null; intersection = previous.get(intersection)) {
//            path.add(intersection);
//            // reconstruct the road
//            if (previous.get(intersection) != null) {
//                roads.add(map.getRoadByIntersections(intersection, previous.get(intersection)));
//            }
//        }
//        Collections.reverse(path);
//        MapModel result = new MapModel(-1, path, roads);
//        memo.put(key, result);
//        return result;
//    }

    public static double calculateDistance(Map map) {
        double distance = 0;
        for (Road road : map.getRoads()) {
            distance += road.getLength();
        }
        return distance;
    }
//
//    public static MapModel tsp (DeliveryRequestModel deliveryRequest, MapModel map) {
//        List<List<IntersectionModel>> permutations = generatePermutations(deliveryRequest);
//        MapModel bestMap = new MapModel(-1, new ArrayList<>(), new ArrayList<>());
//        double bestDistance = Double.POSITIVE_INFINITY; // The distance of the best path found so far
//        for (List<IntersectionModel> permutation : permutations) {
//            MapModel tempBestMap = new MapModel(1, new ArrayList<>(), new ArrayList<>());
//            // add warehouse to the beginning and end of the permutation
//            permutation.add(0, deliveryRequest.getWarehouse().getAddress());
//            permutation.add(deliveryRequest.getWarehouse().getAddress());
//            double distance = 0;
//            for (int i = 0; i < permutation.size() - 1; i++) {
//                IntersectionModel current = permutation.get(i);
//                IntersectionModel next = permutation.get(i+1);
//                MapModel subMap = dijkstra(map, current, next);
//                tempBestMap.addMap(subMap);
//                distance += calculateDistance(subMap);
//                // calculateEuclidienDistance
//                if (distance > bestDistance) {
//                    break;
//                }
//            }
//            if(distance < bestDistance) {
//                bestMap = tempBestMap;
//                bestDistance = distance;
//            }
//        }
//        System.out.println("Dijkstra calls: " + dijkstraCalls);
//        System.out.println("Dijkstra calls with memoization: " + dijsktraCallsMemo);
//        return bestMap;
//    }


    public static Map tsp(DeliveryRequest deliveryRequest, Map map) {
        long startTime = System.currentTimeMillis();
        List<List<Intersection>> permutations = generatePertinentPermutations(deliveryRequest);
        Map bestMap = new Map(-1, new ArrayList<>(), new ArrayList<>());
        double bestDistance = Double.POSITIVE_INFINITY; // The distance of the best path found so far
        for (List<Intersection> permutation : permutations) {
            Map tempBestMap = new Map(-1, new ArrayList<>(), new ArrayList<>());
            // add warehouse to the beginning and end of the permutation
            permutation.add(0, deliveryRequest.getWarehouse().getAddress());
            permutation.add(deliveryRequest.getWarehouse().getAddress());
            double distance = 0;
            for (int i = 0; i < permutation.size() - 1; i++) {
                Intersection current = permutation.get(i);
                Intersection next = permutation.get(i + 1);
                Map subMap = aStar(map, current, next);
                tempBestMap.addMap(subMap);
                distance += calculateDistance(subMap);
                // calculateEuclidienDistance
                if (distance > bestDistance) {
                    break;
                }
            }
            if (distance < bestDistance) {
                bestMap = tempBestMap;
                bestDistance = distance;
            }
        }
        System.out.println("A* calls: " + dijkstraCalls);
        System.out.println("A* calls with memoization: " + dijsktraCallsMemo);
        System.out.println("Time taken for TSP: " + (System.currentTimeMillis() - startTime) + "ms");
        return bestMap;
    }
}
