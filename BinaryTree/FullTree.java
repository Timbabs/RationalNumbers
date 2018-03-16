/**
 * Created by timothybaba on 2/10/18.
 */
import java.util.*;

public class FullTree {
    public static void main(String[] args) {
        FullTree test = new FullTree();
        Node root = new Node(5);
        root.left = new Node(8);
        root.right = new Node(9);
        root.left.left = new Node(7);
        root.left.right = new Node(6);
        root.right.left = new Node(3);
        root.right.right = new Node(2);

        System.out.println("\nTree\n"+root.data);
        System.out.println(root.left.data);
        System.out.println(root.right.data);
        System.out.println(root.left.left.data);
        System.out.println(root.left.right.data);
        System.out.println(root.right.left.data);
        System.out.println(root.right.right.data);


        int[] arr = test.serialize(root);
        System.out.println("\nSerialized Tree");
        for (Integer item: arr) {
            System.out.print(item + " ");
        }
        System.out.println();

        Node result = test.deserialize(arr);
        System.out.println("\nDeserialized Tree\n"+result.data);
        System.out.println(result.left.data);
        System.out.println(result.right.data);
        System.out.println(result.left.left.data);
        System.out.println(result.left.right.data);
        System.out.println(result.right.left.data);
        System.out.println(result.right.right.data);
    }

    private static class Container {
        int data;
        Container (int x) {
            this.data = x;
        }
    }

    public int[] serialize(Node node) {
        int size = getSize(node);
        int[] result = new int[size];

        //option1
        serialize(node, result, new Container(0));

        //option2
        // serialize(node, 0, size, result);

        //option3
        // ArrayList<Integer> list = new ArrayList<>();
        // serialize(node, list);
        // int[] result = new int[list.size()];
        // int index = 0;
        // for (Integer item: list) {
        //     result[index] = item;
        //     index++;
        // }

        return result;
    }

    public int getSize(Node node){
        if (node == null) {
            return 0;
        }
        return 1 + getSize(node.left) + getSize(node.right);
    }


   //option1
    public void serialize(Node node, int[] result, Container size) {
        if (node != null) {
            serialize(node.left, result, size);
            result[size.data] = node.data;
            int val = size.data;
            size.data = ++val;
            serialize(node.right, result, size);
        }

   }

   //option2
   public void serialize(Node node, int leftIndex, int rightIndex, int[] result) {
        if (node != null) {
            int index = (leftIndex + rightIndex)/2;
            result[index] = node.data;
            serialize(node.left, leftIndex, index, result);
            serialize(node.right, index, rightIndex, result);
        }

   }

   //option3
   public void serialize(Node node, ArrayList<Integer> list) {
        if (node != null) {
            serialize(node.left, list);
            list.add(node.data);
            serialize(node.right, list);
        }

   }




    public Node deserialize(int[] arr) {
        return deserialize(arr, 0, arr.length);
    }

    private Node deserialize(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex != rightIndex) {
            int index = (leftIndex + rightIndex)/2;
            Node node = new Node(arr[index]);
            node.left = deserialize(arr, leftIndex, index);
            if (node.left != null) {
                node.right = deserialize(arr, index, rightIndex);
            } else {
                node.right = null;
            }
            return node;
        }
        return null;
    }

    private static class Node {
        int data;
        Node left;
        Node right;
        Node (int val) {
            this(val, null, null);
        }

        Node (int val, Node rightx, Node leftx) {
            data = val;
            left = leftx;
            right = rightx;
        }
    }

    private static class Entry {
        int data;
        int index;
        Entry (int datax, int indexx){
            data = datax;
            index = indexx;
        }
    }
}
