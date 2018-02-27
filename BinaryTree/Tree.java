import java.util.ArrayList;

/**
 * Created by timothybaba on 2/10/18.
 */
public class Tree {
    public static void main(String[] args) {
        Tree test = new Tree();
        Node node5 = new Node(5); //root
        Node node8 = new Node(8);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node9 = new Node(9);


        node5.left = node8;
        node5.right = node9;
        node9.left = node3;
        node9.right = node2;

        int[] arr = test.store(node5);
        for (Integer item: arr) {
            System.out.print(item + " ");
        }
        System.out.println();

        Node result = test.restore(arr);
        System.out.println(result.data);
        System.out.println(result.left.data);
        System.out.println(result.right.data);
        System.out.println(result.right.left.data);
        System.out.println(result.right.right.data);
    }
    public int[] store(Node root) {
        ArrayList<Entry> list = new ArrayList<>();
        list.add(new Entry(-1, 0));
        store(root, 1, list);

        int[] result = new int[getMaxIndex(list) + 1];
        int index = 0;
        for (Entry item : list) {
            result[item.index] = item.data;
            index++;
        }
        return result;
    }
    public int getMaxIndex(ArrayList<Entry> list){
        int max = Integer.MIN_VALUE;
        for (Entry item: list) {
            if (item.index > max){
                max = item.index;
            }
        }
        return max;
    }


    public void store(Node node, int index, ArrayList<Entry> list) {
        if (node == null) {
            list.add(new Entry(-1, index));
        } else {
            list.add(new Entry(node.data, index));
            store(node.left, index*2, list);
            store(node.right, index*2+1, list);
        }
    }

    public Node restore(int[] arr) {
        Node root = new Node(arr[1]);
        restore(root, 1, arr);
        return root;
    }

    public void restore(Node node, int index, int[] arr) {
        int leftIndex = index*2;
        int rightIndex = index*2 + 1;
        if (arr[leftIndex] != -1) {
            node.left = new Node(arr[leftIndex]);
            restore(node.left, leftIndex, arr);

            node.right = new Node(arr[rightIndex]);
            restore(node.right, rightIndex, arr);
        }
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
