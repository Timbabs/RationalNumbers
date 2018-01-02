import Util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Implementation of HashMap using linear probing as collision
 * resolution strategy.
 *
 * @author Timothy Baba
 */
public class HashMapByLinearProbing<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMapByLinearProbing() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public HashMapByLinearProbing(int initialCapacity) {
        table = new MapEntry[initialCapacity];
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        V result = null;
        if (key == null || value == null) {
            throw new IllegalArgumentException(
                    key == null ? "key is null" : "value is null");
        }
        MapEntry<K, V> newEntry = new MapEntry<>(key, value);
        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable((2 * table.length) + 1);
        }
        int index = Math.abs(key.hashCode()) % table.length;
        int noOfIterations = 0;
        boolean isContained = false;
        boolean added = false;
        boolean stop = false;
        Integer deletedIndex = null;

        if (size == 0) {
            table[index] = newEntry;
            size++;
        } else {
            while (noOfIterations < table.length && !isContained && !added
                    && !stop) {
                MapEntry<K, V> current = table[index];
                if (current == null) {
                    if  (deletedIndex == null) {
                        table[index] = newEntry;
                        size++;
                        added = true;
                    } else {
                        table[deletedIndex] = newEntry;
                        size++;
                        stop = true;
                    }

                } else {

                    if (!current.isRemoved()) {

                        if (current.getKey().equals(key)) {
                            result = current.getValue();
                            current.setValue(value);
                            isContained = true;
                        }
                    } else {
                        if (deletedIndex == null) {
                            deletedIndex = index;
                        }
                    }

                }
                noOfIterations++;
                index = ++index % table.length;
            }
            if (noOfIterations == table.length && deletedIndex != null) {
                table[deletedIndex] = newEntry;
                size++;
            }
        }

        return result;

    }

    @Override
    public V remove(K key) {
        V result = null;
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        boolean contained = false;
        int noOfIterations = 0;
        while (noOfIterations < table.length && !contained && table[index]
                != null) {
            if (!table[index].isRemoved()) {
                if (table[index].getKey().equals(key)) {
                    result = table[index].getValue();
                    contained = true;
                    table[index].setRemoved(true);
                    size--;
                }
            }
            noOfIterations++;
            index = ++index % table.length;
        }
        if (!contained) {
            throw new NoSuchElementException("The entered key does not exist");
        } else {
            return result;
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        int noOfIterations = 0;
        while (noOfIterations < table.length && table[index] != null) {
            if (table[index].getKey().equals(key)
                    && !table[index].isRemoved()) {
                return table[index].getValue();
            }
            index = ++index % table.length;
            noOfIterations++;
        }

        throw new NoSuchElementException("The key " + key + "is not contained"
                + " int the map");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        int noOfIterations = 0;
        while (noOfIterations < table.length && table[index] != null) {
            if (!table[index].isRemoved()
                    && table[index].getKey().equals(key)) {
                return true;
            }
            index = ++index % table.length;
            noOfIterations++;
        }

        return false;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> mySet = new HashSet<>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                mySet.add(entry.getKey());
            }

        }
        return mySet;
    }

    @Override
    public List<V> values() {
        List<V> myList = new ArrayList<>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                myList.add(entry.getValue());
            }

        }
        return myList;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException(length <= 0 ? "length is less "
                    + " than 0" : "length is less than size");
        }
        MapEntry<K, V>[] temp = new MapEntry[length];
        for (MapEntry<K, V> item : table) {
            if (!(item == null)) {
                if (!item.isRemoved()) {
                    int index =
                            Math.abs(item.getKey().hashCode()) % temp.length;
                    MapEntry<K, V> toAdd = temp[index];
                    while (toAdd != null) {
                        index = ++index % length;
                        toAdd = temp[index];
                    }
                    temp[index] = item;
                }
            }

        }
        table = temp;
    }
}
