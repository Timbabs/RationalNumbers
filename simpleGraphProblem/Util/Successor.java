package Util;

import java.util.*;

/**
 * Created by timothybaba on 12/31/17.
 */
public abstract class Successor<U> {

    public U successorNode(List<U> neighbors, Set<U> visited) {

        int i = 0;
        U nextNode = null;
        while (i < neighbors.size()) {
            if (!visited.contains(neighbors.get(i))) {
                nextNode = neighbors.get(i);
                break;
            }
            i++;
        }
        return nextNode;
    }

    public int getCost(int curr, int next) {
        return (next - curr) * (next - curr);
    }
 }
