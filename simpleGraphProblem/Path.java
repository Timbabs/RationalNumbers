import java.util.*;
public class Path {
    static Graph graph;
    static Node[] nodes;
    public static void main(String[] args) {
        nodes = new Node[10];
        nodes[0] = new Node("A", "Z");
        nodes[1] = new Node("A", "T");
        nodes[2] = new Node("A", "S");
        nodes[3] = new Node("Z", "O");
        nodes[4] = new Node("R", "P");
        nodes[5] = new Node("R", "C");
        nodes[6] = new Node("S", "R");
        nodes[7] = new Node("S", "F");
        nodes[8] = new Node("P", "B");
        nodes[9] = new Node("O", "S");

        graph = new Graph(10, nodes);
        System.out.println(dfs());

    }

    public static LinkedList<String> dfs() {
       LinkedList<String> result =  new LinkedList<>();
        String start = "A";
        String goal = "B";
        Stack<Entry<String, LinkedList<String>>> myStack = new Stack<>();
        Set<String> visited = new HashSet<>();
        visited.add(start);
        LinkedList<String> list = new LinkedList<>();
        list.add(start);
        myStack.push(new Entry<>(start, list));

        while (!myStack.isEmpty()) {
            Entry<String, LinkedList<String>> currStackItem = myStack.peek();
            String currNode = currStackItem.getKey();

            LinkedList<String> neighbors = graph.g.get(currNode);
            int i = 0;
            String nextNode = null;
            while (i < neighbors.size()) {
                if (!visited.contains(neighbors.get(i))) {
                    nextNode = neighbors.get(i);
                    break;
                }
                i++;
            }
            if (nextNode != null) {
                LinkedList<String> newList = new LinkedList<>(currStackItem.getValue());
                newList.add(nextNode);
                if (nextNode == goal) {
                    return newList;
                }
                myStack.push(new Entry<>(nextNode, newList));
                visited.add(nextNode);

            } else {
                myStack.pop();
            }
        }
        return result;
    }

    private static class Graph {
        int Vertices;
        HashMap<String, LinkedList<String>> g = new HashMap<>();

        public Graph(int V, Node[] nodes) {
            this.Vertices = V;
            for (Node n: nodes) {
                this.addEdge(n.u, n.v);
            }
        }

        public void addEdge(String u, String v) {
            LinkedList<String> temp = g.getOrDefault(u, new LinkedList<>());

            temp.add(v);
            g.put(u, temp);
        }

    }

    private static class Node {
        String u;
        String v;

        public Node(String u, String v) {
            this.u = u;
            this.v = v;
        }
    }

    private static class Entry<U, V> {
        private U str;
        private V list;

        public Entry(U str,  V list) {
            this.str = str;
            this.list = list;
        }

        public U getKey() {
            return this.str;
        }

        public V getValue() {
            return this.list;
        }

    }
}