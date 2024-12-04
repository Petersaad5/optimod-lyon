package com.optimodlyon.optimodlyon.utils;

import com.optimodlyon.optimodlyon.model.*;

import java.util.*;

public class TSP {

    public static Map<Long, Map<Long, Double>> createGraph(MapModel map, List<DeliveryRequestModel> deliveries) {
        // input: map, list of delivery requests
        // output: graph representing the shortest paths between all pairs of points.
        // The graph is represented as a map of maps, where the outer map maps each point to a map of distances to other points.
        Map<Long, Map<Long, Double>> graph = new HashMap<>();

        // Get all unique points (pickup and delivery)
        Set<Long> points = new HashSet<>();
        for (DeliveryRequestModel delivery : deliveries) {
            points.add(delivery.getOriginId());
            points.add(delivery.getDestinationId());
        }

        // Calculate shortest paths between all pairs of points
        for (Long point : points) {
            graph.put(point, dijkstra(map, point, points));
        }

        return graph;
    }

    public static List<Long> solveTSP(Map<Long, Map<Long, Double>> graph, List<DeliveryRequestModel> deliveries) {
        List<Long> order = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        Long current = deliveries.get(0).getOriginId(); // Start from the first pickup point

        while (visited.size() < graph.size()) {
            order.add(current);
            visited.add(current);

            // Find the next closest point that hasn't been visited yet
            Double minDistance = Double.MAX_VALUE;
            Long nextPoint = null;
            for (Map.Entry<Long, Double> entry : graph.get(current).entrySet()) {
                if (!visited.contains(entry.getKey()) && entry.getValue() < minDistance) {
                    minDistance = entry.getValue();
                    nextPoint = entry.getKey();
                }
            }

            if (nextPoint != null) {
                current = nextPoint;
            } else {
                break;
            }
        }

        return order;
    }

    private static Map<Long, Double> dijkstra(MapModel map, Long start, Set<Long> points) {
        Map<Long, Double> distances = new HashMap<>();
        PriorityQueue<IntersectionModel> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Map<Long, IntersectionModel> intersections = new HashMap<>();

        for (IntersectionModel intersection : map.getIntersections()) {
            intersections.put(intersection.getId(), intersection);
            distances.put(intersection.getId(), Double.MAX_VALUE);
        }

        distances.put(start, 0.0);
        queue.add(intersections.get(start));

        while (!queue.isEmpty()) {
            IntersectionModel current = queue.poll();
            for (RoadModel road : map.getRoads()) {
                if (road.getOrigin().getId().equals(current.getId())) {
                    IntersectionModel neighbor = road.getDestination();
                    double newDist = distances.get(current.getId()) + road.getLength();
                    if (newDist < distances.get(neighbor.getId())) {
                        distances.put(neighbor.getId(), newDist);
                        queue.add(neighbor);
                    }
                }
            }
        }

        // Filter distances to only include the points of interest
        distances.keySet().retainAll(points);
        return distances;
    }

    // main method for testing
    public static void main(String[] args) {
        // Parse the plan
        Parser.parsePlan("src/public/xml/petitPlan.xml");
        MapModel map = Parser.data.getMap();

        // Parse the delivery requests
        Parser.parseDemande("src/public/xml/demandePetit4.xml");
        List<DeliveryRequestModel> deliveries = Parser.data.getDeliveryRequests();

        // Create the graph
        Map<Long, Map<Long, Double>> graph = createGraph(map, deliveries);

        // Solve the TSP
        List<Long> order = solveTSP(graph, deliveries);

        System.out.println(order);
    }
}

