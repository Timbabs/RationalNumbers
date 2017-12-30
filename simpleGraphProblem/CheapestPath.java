/**
 * Created by timothybaba on 11/6/17.
 */

import java.util.*;

import static java.util.Arrays.asList;
public class shortestPath {
}


/*
# There are 10 people at a wizard meetup.
# Each wizard has levels 0 - 9 (the index of the input) and
# knows a few other wizards there.
# Your job is to find the cheapest way for wizard 0 to meet wizard 9.
# Introductions have a cost that equals the square of the difference in levels.

# Goal: Level 0 wizard wants to meet level 9 using the fewest possible magic points.
# Cost: square of difference of levels
# The index of the array represents the level (0-9)
# the value is an array with the index of the other people each person knows.
# Note that relationships are one directional (e.g. 2 can introduce you to 3 but not vice versa)
# e.g. Min cost: 23 Min path: [0, 1, 4, 6, 9]
 */

class Solution {
    List<List<Integer>> wizards = asList(
            asList(1, 2, 3), // Wizard 0
            asList(8, 6, 4), // Wizard 1
            asList(7, 8, 3), // Wizard 2...
            asList(8, 1),
            asList(6),
            asList(8, 7),
            asList(9, 4),
            asList(4, 6),
            asList(1),
            asList(1, 4)
    );

    public LinkedList<Integer>  dfs() {
        LinkedList<Integer> result = new LinkedList<>();
        Stack<Entry<Integer, LinkedList<Integer>>> myStack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        int start = 0;
        int goal = 9;
        visited.add(start);

        myStack.push(new Entry<>(start, new LinkedList<>(Arrays.asList(start))));
        while (!myStack.isEmpty()) {
            Entry<Integer, LinkedList<Integer>> currEntry = myStack.peek();
            Integer currNode = currEntry.getKey();

            List<Integer> neighbors =  wizards.get(currNode);

            Integer minVertex = null;
            Integer minCost = Integer.MAX_VALUE;

            for (Integer s: neighbors) {
                int cost = getCost(currNode, s);
                if (cost < minCost && !visited.contains(s)) {
                    minCost  = cost;
                    minVertex = s;
                }
            }
            if (minVertex != null) {
                LinkedList<Integer> list = new LinkedList<>(currEntry.getValue());
                list.add(minVertex);
                if (minVertex == goal) {
                    return list;
                }
                visited.add(minVertex);
                myStack.push(new Entry<>(minVertex, list));
            } else {
                myStack.pop();
            }
        }
        return result;
    }

    public int getCost(int curr, int next) {
        return (next - curr) * (next - curr);
    }


    private class Entry<U, V> {
        U key;
        V value;

        public Entry(U key, V value) {
            this.key = key;
            this.value = value;
        }
         public U getKey() {
            return this.key;
         }

        public V getValue() {
            return this.value;
        }
    }

}

class Check {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.dfs());
    }
}