package com.jrq.xvgdl.util;

import com.jrq.xvgdl.model.location.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Given two positions in the game map matrix representation,
 * this algorithm will return the minimum distance between those two points
 * taking into account the walls of the map.
 * This algorithm considers a two dimensional map.
 */
@Slf4j
public class MinimumDistance {

    private static Position destination;
    private static int searchRadius;
    private static List<String> solution = new ArrayList<>();

    public static List<String> calculateMinimumDistance(int [][] distances, Position p1, Position p2) {
        initialize(p2, Integer.MAX_VALUE);
        return calculateMinimumDistance(distances, p1, p2, searchRadius);
    }

    public static List<String> calculateMinimumDistanceFixedRadius(int [][] distances, Position p1, Position p2, int radius) {
        initialize(p2, radius);
        calculateMinimumDistanceRecursive(new ArrayList<>(), distances, p1.getX(), p1.getY());
        return solution;
    }

    public static List<String> calculateMinimumDistance(int [][] distances, Position p1, Position p2, int radius) {
        initialize(p2, radius);
        while (solution.isEmpty()) {
            calculateMinimumDistanceRecursive(new ArrayList<>(), distances, p1.getX(), p1.getY());
            searchRadius++;
        }
        return solution;
    }

    private static void initialize(Position position, int radius) {
        searchRadius = radius;
        destination = position;
        solution = new ArrayList<>();
    }

    private static void calculateMinimumDistanceRecursive(List<String> alreadyVisited, int[][] distances, int x, int y) {

        // Base case, radius exceeded
        if (radiusExceeded(x, y)) {
            return;
        }
        // Base case, solution is worst than current best
        if (alreadyVisited.size() >= solution.size() && !solution.isEmpty()) {
            return;
        }
        // Base case, out of the map
        if (x < 0 || x == distances.length ||
            y < 0 || y == distances[x].length) {
            return;
        }
        // Base case, wall found
        if (distances[x][y] == Integer.MAX_VALUE) {
            return;
        }
        // Base case, get to destination
        if (destination.getX() == x &&
                destination.getY() == y) {
            alreadyVisited.add(x + "," + y);
            if (alreadyVisited.size() < solution.size() || solution.isEmpty()) solution = alreadyVisited;
            return;
        }

        // Mark Node as visited
        alreadyVisited.add(x + "," + y);

        // Check all possible neighbours
        if (!alreadyVisited.contains((x+1) + "," + y)) {
            calculateMinimumDistanceRecursive(new ArrayList<>(alreadyVisited), distances, x + 1, y);
        }
        if (!alreadyVisited.contains((x-1) + "," + y)) {
            calculateMinimumDistanceRecursive(new ArrayList<>(alreadyVisited), distances, x - 1, y);
        }
        if (!alreadyVisited.contains(x + "," + (y+1))) {
            calculateMinimumDistanceRecursive(new ArrayList<>(alreadyVisited), distances, x, y+1);
        }
        if (!alreadyVisited.contains(x + "," + (y-1))) {
            calculateMinimumDistanceRecursive(new ArrayList<>(alreadyVisited), distances, x, y - 1);
        }
    }

    private static boolean radiusExceeded(int x, int y) {
        return Math.abs(x - destination.getX()) > searchRadius ||
                Math.abs(y - destination.getY()) > searchRadius;
    }
}