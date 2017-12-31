import Util.Edge;
import Util.Graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        Edge<String>[] edges = new Edge[10];
        edges[0] = new Edge("A", "Z");
        edges[1] = new Edge("A", "T");
        edges[2] = new Edge("A", "S");
        edges[3] = new Edge("Z", "O");
        edges[4] = new Edge("R", "P");
        edges[5] = new Edge("R", "C");
        edges[6] = new Edge("S", "R");
        edges[7] = new Edge("S", "F");
        edges[8] = new Edge("P", "B");
        edges[9] = new Edge("O", "S");

        Map<Integer, List<Integer>> adjList = new HashMap<>();
        adjList.put(0, Arrays.asList(1, 2, 3));
        adjList.put(1, Arrays.asList(8, 6, 4));
        adjList.put(2, Arrays.asList(7, 8, 3));
        adjList.put(3, Arrays.asList(8, 1));
        adjList.put(4, Arrays.asList(6));
        adjList.put(5, Arrays.asList(8, 7));
        adjList.put(6, Arrays.asList(9, 4));
        adjList.put(7, Arrays.asList(4, 6));
        adjList.put(8, Arrays.asList(1));
        adjList.put(9, Arrays.asList(1, 4));

        Graph<String> graph1 = new Graph<>(edges);
        Graph<Integer> graph2 = new Graph<>(adjList);
        System.out.println(new Path<String>().dfsApproach(graph1, "A", "B"));
        System.out.println(new Path<Integer>().dfsApproach(graph2, 0, 9));


    }

}