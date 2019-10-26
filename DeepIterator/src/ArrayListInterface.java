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
public interface ArrayListInterface<T> extends Iterable<T>{

    /**
     * The initial capacity of the array list.
     */
    public static final int INITIAL_CAPACITY = 10;


    /**
     * Add the given data to the back of our array list.
     *
     * Must be amortized O(1).
     *
     * @param data whether the data was added successfully or not.
     */
    public boolean add(T data);

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
    public void add(T data, int index);

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.
     *
     * If the list is empty, return {@code null}.
     * Must be O(1).
     *
     * @return whether the data was removed successfully.
     */
    public boolean remove(Object o);

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
    public T remove(int index);


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
    public T get(int index);

    /**
     * Returns a shallow copy of this DeepArrayList instance.
     **/
    public Object shallowCopy();

    /**
     * Returns a deep copy of this DeepArrayList instance.
     **/
    public Object deepCopy();

    /**
     * Return a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty();

    /**
     * Return the size of the list as an integer.
     *
     * Must be O(1).
     *
     * @return The size of the list.
     */
    public int size();

    /**
     * Clear the list. Reset the backing array to a new array of the initial
     * capacity.
     *
     * Must be O(1).
     */
    public void clear();

    /**
     * Return the backing array for this list.
     *
     * Must be O(1).
     *
     * @return the backing array for this list
     */
    public Object[] getBackingArray();


}
