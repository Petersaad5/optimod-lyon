package com.optimodlyon.optimodlyon.utils;

import com.optimodlyon.optimodlyon.model.DeliveryRequestModel;
import com.optimodlyon.optimodlyon.model.IntersectionModel;
import com.optimodlyon.optimodlyon.model.MapModel;
import com.optimodlyon.optimodlyon.model.RoadModel;

import java.util.*;

public class TSP {
    private static final int INF = Integer.MAX_VALUE;

    public static MapModel tsp(DeliveryRequestModel deliveryRequest, MapModel map) {
        int n = map.getIntersections().size();
        double[][] cost = new double[n][n];

        // Create cost matrix from MapModel
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    cost[i][j] = 0;
                } else {
                    RoadModel road = map.getRoadByIntersections(map.getIntersections().get(i), map.getIntersections().get(j));
                    cost[i][j] = (road != null) ? road.getLength() : INF;
                }
            }
        }

        // Solve TSP
        return solveTSP(n, cost, map);
    }

    public static MapModel solveTSP(int n, double[][] cost, MapModel map) {
        // Initialize variables
        boolean[][] x = new boolean[n][n];
        double bestCost = INF;
        List<Integer> bestTour = new ArrayList<>();

        // Branch-and-Cut framework
        branchAndCut(n, cost, x, 0, new ArrayList<>(), 0, bestTour, bestCost);

        // Create and return the result MapModel
        MapModel result = new MapModel();
        result.setCost(bestCost);
        List<IntersectionModel> tourIntersections = new ArrayList<>();
        for (int index : bestTour) {
            tourIntersections.add(map.getIntersections().get(index));
        }
        result.setIntersections(tourIntersections);
        return result;
    }

    private static void branchAndCut(int n, double[][] cost, boolean[][] x, int depth, List<Integer> currentTour, double currentCost, List<Integer> bestTour, double bestCost) {
        if (depth == n) {
            // Check if the tour is valid and update the best tour
            if (isValidTour(n, x)) {
                if (currentCost < bestCost) {
                    bestCost = currentCost;
                    bestTour.clear();
                    bestTour.addAll(currentTour);
                }
            }
            return;
        }

        // Branching
        for (int i = 0; i < n; i++) {
            if (!currentTour.contains(i)) {
                currentTour.add(i);
                for (int j = 0; j < n; j++) {
                    if (i != j && !currentTour.contains(j)) {
                        x[i][j] = true;
                        branchAndCut(n, cost, x, depth + 1, currentTour, currentCost + cost[i][j], bestTour, bestCost);
                        x[i][j] = false;
                    }
                }
                currentTour.remove(currentTour.size() - 1);
            }
        }
    }

    private static boolean isValidTour(int n, boolean[][] x) {
        // Check degree constraints
        for (int i = 0; i < n; i++) {
            int outDegree = 0;
            int inDegree = 0;
            for (int j = 0; j < n; j++) {
                if (x[i][j]) outDegree++;
                if (x[j][i]) inDegree++;
            }
            if (outDegree != 1 || inDegree != 1) return false;
        }

        // Check for subtours
        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                if (count > 1) return false;
                dfs(i, x, visited);
            }
        }
        return true;
    }

    private static void dfs(int node, boolean[][] x, boolean[] visited) {
        visited[node] = true;
        for (int i = 0; i < x.length; i++) {
            if (x[node][i] && !visited[i]) {
                dfs(i, x, visited);
            }
        }
    }
}