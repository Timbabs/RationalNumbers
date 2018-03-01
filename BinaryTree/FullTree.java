import java.util.ArrayList;

/**
 * Created by timothybaba on 2/10/18.
 */
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

        // Node result = test.deserialize(arr);
        // System.out.println("\nDeserialized Tree\n"+result.data);
        // System.out.println(result.left.data);
        // System.out.println(result.right.data);
        // System.out.println(result.left.left.data);
        // System.out.println(result.left.right.data);
        // System.out.println(result.right.left.data);
        // System.out.println(result.right.right.data);
    }
    public int[] serialize(Node node) {
        int size = getSize(node);
        int[] result = new int[size];
        serialize(node, 0, size, result);
        return result;
    }
    public int getSize(Node node){
        if (node == null) {
            return 0;
        }
        return 1 + getSize(node.left) + getSize(node.right);
    }


    public void serialize(Node node, int leftIndex, int rightIndex, int[] result) {
        if (node != null) {
            int index = (leftIndex + rightIndex)/2;
            result[index] = node.data;
            serialize(node.left, leftIndex, index, result);
            serialize(node.right, index, rightIndex, result);
        }
    }

    public Node deserialize(int[] arr) {
        // TODO
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
