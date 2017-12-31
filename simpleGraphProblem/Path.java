/**
 * Created by timothybaba on 11/6/17.
 */

import Util.*;

import java.util.*;


class Path<U> {

    public LinkedList<U>  dfsApproach(Graph<U> graph, U start, U goal) {
        LinkedList<U> result = new LinkedList<>();
        Stack<Entry<U, LinkedList<U>>> myStack = new Stack<>();
        Set<U> visited = new HashSet<>();

        visited.add(start);

        myStack.push(new Entry<>(start, new LinkedList<>(Arrays.asList(start))));
        while (!myStack.isEmpty()) {
            Entry<U, LinkedList<U>> currEntry = myStack.peek();
            U currNode = currEntry.getKey();

            List<U> neighbors =  graph.getAdjList().get(currNode);


            U nextNode = getNextNode(neighbors, visited, currNode);
            if (nextNode != null) {
                LinkedList<U> list = new LinkedList<>(currEntry.getValue());
                list.add(nextNode);
                if (nextNode == goal) {
                    return list;
                }
                visited.add(nextNode);
                myStack.push(new Entry<>(nextNode, list));
            } else {
                myStack.pop();
            }
        }
        return result;
    }

    private U getNextNode(List<U> neighbors, Set<U> visited, U currentNode) {

        if (currentNode instanceof Integer) {
            Successor<U> successor = new Successor<U>() {
                @Override
                public U successorNode(List<U> neighbors, Set<U> visited) {
                    U minVertex = null;
                    Integer minCost = Integer.MAX_VALUE;

                    for (U s: neighbors) {
                        int cost = getCost((Integer) currentNode, (Integer)s);
                        if (cost < minCost && !visited.contains(s)) {
                            minCost  = cost;
                            minVertex =  s;
                        }
                    }
                    return minVertex;
                }
            };
            return successor.successorNode(neighbors, visited);

        }
        Successor<U> successor = new Successor<U>() {};
        return successor.successorNode(neighbors, visited);
    }
}
