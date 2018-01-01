import java.util.*;

public class SumToK {

    /**
     * @param arr the array to find pairs within
     * @param k the desired sum of pairs to find
     * @return the number of pairs present in arr that sum up to k
     */
    public static int countAllPairs(int[] arr, int k) {
        if (arr == null) {
            return 0;
        }
        HashMap<Integer, Integer> myMap = new HashMap<>();
        int count = 0;
        for (int item: arr) {
            if (!myMap.containsKey(item)) {
                int val = myMap.getOrDefault(k - item, 0);
                myMap.put((k - item), val + 1);
            } else {
                int val = myMap.get(item) - 1;
                if (val == 0) {
                    myMap.remove(item);
                } else {
                    myMap.put(item, val);
                }
                count++;
            }
        }
        return count;
    }

    public static void main(String[] arr) {
        int[] newArray = new int[arr.length - 1];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = Integer.parseInt(arr[i]);
        }
        int k = Integer.parseInt(arr[arr.length - 1]);
        System.out.print("input: Array = [");
        int i;
        for (i = 0; i < newArray.length - 1; i++) {
            System.out.print(newArray[i] + " ");
        }
        System.out.print(newArray[i] + "]; ");
        System.out.println("k = " + k);
        System.out.println("Output: " + countAllPairs(newArray, k));
    }
}