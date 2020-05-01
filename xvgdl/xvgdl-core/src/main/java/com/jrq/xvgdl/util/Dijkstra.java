package com.jrq.xvgdl.util;

public class Dijkstra {

    public static void dijkstra(int[][] graph, int sourceVertex){
        int vertexCount = graph.length;
        boolean[] visitedVertex = new boolean[vertexCount];
        int[] distance = new int[vertexCount];

        for (int i = 0; i < vertexCount; i++){
            visitedVertex[i] = false;
            distance[i] = Integer.MAX_VALUE;
        }

        distance[sourceVertex] = 0; // distance of source vertex to itself is zero
        for (int i = 0; i < vertexCount; i++){
            //find the neighbouring unvisited vertex having  minimum distance from source vertex
            //for the first time u will be the source vertex and the distance array will be updated with the neighbouring vertex distance of source vertex
            int u = findMinDistance(distance, visitedVertex);
            //u is the row and v is the column
            visitedVertex[u] = true;
            //now update all the neighbour vertex distances
            for (int v =0 ; v < vertexCount; v++){
                //graph[u][v] != 0 -> there should be a direct edge
                if(!visitedVertex[v] && graph[u][v] != 0 && (distance[u] + graph[u][v] < distance[v])){
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }
        for (int i = 0; i < distance.length; i++){
            System.out.println(String.format("Distance from source vertex %s to vertex %s is %s", sourceVertex, i, distance[i]));
        }

    }

    private static int findMinDistance(int[] distance, boolean[] visitedVertex) {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceVertex = -1;
        for (int i =0; i < distance.length; i++){
            //the vertex should not be visited and the distance should be the minimum.
            //this is similar to finding the min element of an array
            if(!visitedVertex[i] && distance[i] < minDistance){
                minDistance = distance[i];
                minDistanceVertex = i;
            }
        }
        return minDistanceVertex;
    }

    public static void main(String[] args) {
        int graph[][] = new int[][] { { 0, 4, 8, 0, 0 },
                { 4, 0, 2, 5, 0 },
                { 8, 2, 0, 5, 9},
                { 0, 5, 5, 0, 4 },
                { 0, 0, 9, 4, 0 } };
        Dijkstra t = new Dijkstra();
        t.dijkstra(graph, 0);
    }
}
