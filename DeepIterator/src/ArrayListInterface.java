import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * This interface describes the public methods needed for our custom
 * DeepArrayList, a generic array-backed list data structure.
 *
 * The expected Big-O for each method is provided in the docs.
 *
 *
 * @author Programmers Club
 * @version 1
 */
@SuppressWarnings("unchecked")
interface ArrayListInterface<T> extends Iterable<T>{

    /**
     * The initial capacity of the array list.
     */
    int INITIAL_CAPACITY = 10;

    /**
     * @param c the Comparator used to compare list elements. A null value
     * indicates that the elements' natural ordering should be used
     */
    default void sort(Comparator<? super T> c) {
        sortHelper(this, c);
    }

    private void sortHelper(Object obj, Comparator<? super T> c) {
        if (obj instanceof DeepArrayList) {
            DeepArrayList deepList = (DeepArrayList)obj;
            for (int i = 0; i <  deepList.size(); i++) {
                Object item = deepList.get(i);
                if (item instanceof Iterable) {
                    sortHelper(item, c);
                }
            }
            deepList.trimToSize();
            mergeSort((T[])deepList.getBackingArray(), c, 0, deepList.size() - 1);

        } else {
            for (Object item : (Iterable)obj) {
                if (item instanceof Iterable) {
                    sortHelper(item, c);
                }
            }
            if (obj instanceof List) {
                T[] listArray = ((T[])((List)obj).toArray());

                mergeSort(listArray, c, 0, listArray.length - 1);

                int index = 0;
                for (T item : listArray) {
                    ((List)obj).set(index++, item);
                }
            }
        }
    }

    private void mergeSort(T[] arr, Comparator<? super T> comparator,
                                      int minIndex, int maxIndex) {
        if (minIndex < maxIndex) {
            int midIndex = (minIndex + maxIndex) / 2;
            mergeSort(arr, comparator, minIndex, midIndex);
            mergeSort(arr, comparator, midIndex + 1, maxIndex);
            mergeSortedArray(arr, comparator, minIndex, midIndex, maxIndex);
        }
    }

    private int compare(Comparator<? super T> c, T obj1, T obj2) {
        if (obj1 instanceof Iterable) {
            if (obj1 instanceof DeepArrayList) {
                Iterator itr = ((DeepArrayList)obj1).iterator();
                if (itr.hasNext()) {
                    obj1 = (T) itr.next();
                }
            }
            else if (obj1 instanceof Collection) {
                Iterator itr = new DeepArrayList(((Collection) obj1)).iterator();
                if (itr.hasNext()) {
                    obj1 = (T)itr.next();
                }
            }
        }
        if (obj2 instanceof Iterable) {
            if (obj2 instanceof DeepArrayList) {
                Iterator itr = ((DeepArrayList)obj2).iterator();
                if (itr.hasNext()) {
                    obj2 = (T) itr.next();
                }
            }
            else if (obj2 instanceof Collection) {
                //System.out.println("i'm also here too");
                Iterator itr = new DeepArrayList(((Collection) obj2)).iterator();
                if (itr.hasNext()) {
                    obj2 = (T)itr.next();
                }
            }
        }
        if (!(obj1 instanceof Iterable) && !(obj2 instanceof Iterable)) {
            if (c != null) {
                return c.compare(obj1, obj2);
            } else {
                return ((Comparable)obj1).compareTo(obj2);
            }
        } else {
            return -1;
        }

    }

    private void mergeSortedArray(T[] arr, Comparator<? super T> comparator,
                                  int minIndex, int midIndex, int maxIndex) {
        T[] temp = (T[]) new Object[(maxIndex-minIndex) + 1];

        int firstArrayMinIndex = minIndex;
        int secondArrayMinIndex = midIndex + 1;
        int currentTempIndex = 0;

        while (firstArrayMinIndex <= midIndex && secondArrayMinIndex <= maxIndex) {
            if (compare(comparator, arr[firstArrayMinIndex], arr[secondArrayMinIndex]) <= 0) {
                temp[currentTempIndex] = arr[firstArrayMinIndex++];
            } else {
                temp[currentTempIndex] = arr[secondArrayMinIndex++];
            }
            currentTempIndex++;
        }

        while (firstArrayMinIndex <= midIndex) {
            temp[currentTempIndex++] = arr[firstArrayMinIndex++];
        }

        while (secondArrayMinIndex <= maxIndex) {
            temp[currentTempIndex++] = arr[secondArrayMinIndex++];
        }

        currentTempIndex = 0;
        for (int index = minIndex; index <= maxIndex;) {
            arr[index++] = temp[currentTempIndex++];
        }
    }

    /**
     *
     * @param list An array of iterables to merge
     * @return the merged list
     */
    default Iterable<T> merge(Iterable<T>...list) {
        Iterable merged = new DeepArrayList();
        for(Iterable obj: list){
            if (obj instanceof DeepArrayList) {
                DeepArrayList deepList = (DeepArrayList)obj;
                for (int i = 0; i < deepList.size(); i++) {
                    ((DeepArrayList)merged).add(deepList.get(i));
                }
            } else  {
                for(Object value: obj){
                    ((DeepArrayList)merged).add(value);
                }
            }

        }
        return merged;
    }

    /**
     * @return Items in the reverse order
     */
    Iterator<T> reverseIterator();

    /**
     * @param o the element to find
     * @return Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     *  @throws java.lang.IllegalArgumentException if passed in data is null.
     */
    int lastIndexOf(Object o);

    /**
     * Trims the capacity of this deepArrayList instance to be the list's
     * current size.
     */
    void trimToSize();

    /**
     * Add the given data to the back of our array list.
     *
     * Must be amortized O(1).
     *
     * @param data whether the data was added successfully or not.
     */
    boolean add(T data);

    /**
     * Adds the element to the index specified.
     *
     * This add may require elements to be shifted.
     * Adding to index {@code size} should be amortized O(1),
     * all other adds are O(n).
     *
     * @param index The index where you want the new element.
     * @param data Any object of type T.
     * @throws java.lang.IndexOutOfBoundsException if index is negative
     * or index > size.
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    void add(T data, int index);

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.
     *
     * If the list is empty, return {@code null}.
     * Must be O(1).
     *
     * @return whether the data was removed successfully.
     */
    boolean remove(Object o);

    /**
     * Removes and returns the element at index.
     *
     * Remember that this remove may require elements to be shifted.
     * This method should be O(1) for index {@code size}, and O(n) in all other
     * cases.
     *
     * @param index The index of the element
     * @return The object that was formerly at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size.
     */
    T remove(int index);


    /**
     * Returns the element at the given index.
     *
     * Must be O(1).
     *
     * @param index The index of the element
     * @return The data stored at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size.
     */
    T get(int index);

    /**
     * Returns a shallow copy of this DeepArrayList instance.
     **/
    Object[] shallowCopy();

    /**
     * Returns a deep copy of this DeepArrayList instance.
     **/
    Object[] deepCopy();

    /**
     * Return a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    boolean isEmpty();

    /**
     * Return the size of the list as an integer.
     *
     * Must be O(1).
     *
     * @return The size of the list.
     */
    int size();

    /**
     * Clear the list. Reset the backing array to a new array of the initial
     * capacity.
     *
     * Must be O(1).
     */
    void clear();

    /**
     * Return the backing array for this list.
     *
     * Must be O(1).
     *
     * @return the backing array for this list
     */
    Object[] getBackingArray();
}
