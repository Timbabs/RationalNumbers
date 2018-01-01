import java.util.*;

public class FindKLargest {
    /**
     * @param arr the input array of Comparable objects
     * @param k the number of elements to return
     * @param <T> a comparable object
     * @return an array of the k largest elements in arr in ascending order
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<? super T>> T[]
                                                findKLargest(T[] arr, int k) {

        if (k <= 0) {
            throw new IllegalArgumentException("K must be a greater than 0");
        }

        if (arr == null) {
            throw new IllegalArgumentException("input array cannot be null");
        }
        PriorityQueue<T> pq = new PriorityQueue<>();

        if (k > arr.length) {
            k = arr.length;
        }

        T[] result = (T[]) new Comparable[k];
        for (T item : arr) {
            if (pq.size() < k || item.compareTo(pq.peek()) > 0) {
                if (pq.size() == k) {
                    pq.poll();
                }
                pq.add(item);
            }
        }
        int index = 0;
        while (!pq.isEmpty()) {
            result[index] = pq.poll();
            index++;
        }

        return result;
    }

    public static void main(String[] args) {
        String[] strArray = {"A", "G", "V", "T"};
        Integer[] intArray = {1, 4, 2, 7};

        Object[] result1= findKLargest(intArray, 2);
        Object[] result2= findKLargest(strArray, 2);
        print(result1);
        print(result2);


    }

    private static void print(Object[] result) {
        System.out.print("Output: [");
        int i = 0;
        for (Object item : result) {
            if (i < result.length - 1) {
                System.out.print(item + ", ");
                i++;
            }
        }
        System.out.println(result[i] + "]");
    }
}

