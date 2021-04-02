package com.jrq.xvgdl.util;

import com.jrq.xvgdl.model.location.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinimumDistanceTest {

    private int[][] mapArray;

    @Before
    public void init() {
        mapArray = get4x5ExampleMapArray();
    }

    @Test
    public void calculateMinimumDistanceJustOneStep() {
        Position position1 = Position.builder().x(0).y(0).z(0).build();
        Position position2 = Position.builder().x(0).y(1).z(0).build();

        List<String> distance = MinimumDistance.calculateMinimumDistance(mapArray, position1, position2);
        assertEquals(1, distance.size() -1);
    }

    @Test
    public void calculateMinimumDistanceNoWallsInTheMiddle() {
        Position position1 = Position.builder().x(0).y(0).z(0).build();
        Position position2 = Position.builder().x(3).y(0).z(0).build();

        List<String> distance = MinimumDistance.calculateMinimumDistance(mapArray, position1, position2);
        assertEquals(3, distance.size() - 1);
    }

    @Test
    public void calculateMinimumDistanceWallsEasy() {
        Position position1 = Position.builder().x(0).y(0).z(0).build();
        Position position2 = Position.builder().x(2).y(2).z(0).build();

        List<String> distance = MinimumDistance.calculateMinimumDistance(mapArray, position1, position2);
        assertEquals(4, distance.size() - 1);
    }

    @Test
    public void calculateMinimumDistanceWallsHard() {
        Position position1 = Position.builder().x(0).y(0).z(0).build();
        Position position2 = Position.builder().x(3).y(3).z(0).build();

        List<String> distance = MinimumDistance.calculateMinimumDistance(mapArray, position1, position2);
        assertEquals(8, distance.size() - 1);
    }

    @Test
    public void calculateMinimumDistanceWallsHardReverse() {
        Position position1 = Position.builder().x(3).y(3).z(0).build();
        Position position2 = Position.builder().x(0).y(0).z(0).build();

        List<String> distance = MinimumDistance.calculateMinimumDistance(mapArray, position1, position2);
        assertEquals(8, distance.size() - 1);
    }

    /**
     *
     * @return a matrix representing this map:
     * O O O X O
     * O X O O O
     * O O O X O
     * O X X O O
     *
     * Where the X are walls and O are empty positions
     */
    private int[][] get4x5ExampleMapArray() {
        return new int[][]{
                    {1, 1, 1, Integer.MAX_VALUE, 1},
                    {1, Integer.MAX_VALUE, 1, 1, 1},
                    {1, 1, 1, Integer.MAX_VALUE, 1},
                    {1, Integer.MAX_VALUE, Integer.MAX_VALUE, 1, 1}
            };
    }
}