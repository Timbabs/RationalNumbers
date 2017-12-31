package Util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Graph<U> {
    private Map<U, List<U>> adjList = new HashMap<>();

    public Graph(Edge<U>[] edges) {

        for (Edge<U> edge: edges) {
            this.addEdge(edge.head, edge.tail);
        }
    }
    public Graph(Map<U, List<U>> adjList) {
        this.adjList = adjList;
    }

    private void addEdge(U u, U v) {
        List<U> temp = adjList.getOrDefault(u, new LinkedList<>());

        temp.add(v);
        adjList.put(u, temp);
    }

    public Map<U, List<U>> getAdjList() {
        return adjList;
    }

}


