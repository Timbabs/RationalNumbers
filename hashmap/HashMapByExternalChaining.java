import Util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.LinkedList;

/**
 * Implementation of HashMap using external chaining as collision
 * resolution strategy.
 *
 * @author Timothy Baba
 */
public class HashMapByExternalChaining<K, V> implements HashMapInterface<K, V> {

    private LinkedList<MapEntry<K, V>>[] backingTable;
    private int size;


    @SuppressWarnings("unchecked")
    public HashMapByExternalChaining() {
        this(HashMapInterface.INITIAL_CAPACITY);
    }

    /**
     * @param initialCapacity initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public HashMapByExternalChaining(int initialCapacity) {
        backingTable = (LinkedList<MapEntry<K, V>>[])
                new LinkedList[initialCapacity];
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        V result = null;
        if (key == null || value == null) {
            throw new IllegalArgumentException(
                    key == null ? "key is null" : "value is null");
        }
        MapEntry<K, V> newEntry =  new MapEntry<>(key, value);
        if ((double) (size + 1) / backingTable.length
                > HashMapInterface.MAX_LOAD_FACTOR) {
            resizeBackingTable((2 * backingTable.length) + 1);
        }

        int index = Math.abs(key.hashCode()) % backingTable.length;

        if (backingTable[index] == null) {
            backingTable[index] =  new LinkedList<>();
            backingTable[index].addFirst(newEntry);
            size++;
            return result;
        } else {
            Iterator<MapEntry<K, V>> myIterator
                    = backingTable[index].iterator();
            boolean contained = false;
            while (myIterator.hasNext() && !contained) {
                MapEntry<K, V> current = myIterator.next();
                if (current.getKey().equals(key)) {
                    result = current.getValue();
                    current.setValue(value);
                    contained = true;
                }
            }
            if (!contained) {
                backingTable[index].addFirst(newEntry);
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
        int index = Math.abs(key.hashCode()) % backingTable.length;
        boolean contained = false;
        if (backingTable[index] != null) {
            Iterator<MapEntry<K, V>> myIterator
                    = backingTable[index].iterator();
            while (myIterator.hasNext() && !contained) {
                MapEntry<K, V> current = myIterator.next();
                if (current.getKey().equals(key)) {
                    result = current.getValue();
                    myIterator.remove();
                    size--;
                    contained = true;
                }
            }
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
        int index = Math.abs(key.hashCode()) % backingTable.length;

        if (backingTable[index] != null) {
            Iterator<MapEntry<K, V>> myIterator
                    = backingTable[index].iterator();
            while (myIterator.hasNext()) {
                MapEntry<K, V> current = myIterator.next();
                if (current.getKey().equals(key)) {
                    return current.getValue();
                }
            }
        }

        throw new NoSuchElementException("The entered ket is not in the map");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }
        int index = Math.abs(key.hashCode()) % backingTable.length;
        if (backingTable[index] != null) {
            Iterator<MapEntry<K, V>> myIterator
                    = backingTable[index].iterator();
            while (myIterator.hasNext()) {
                MapEntry<K, V> current = myIterator.next();
                if (current.getKey().equals(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        backingTable = (LinkedList<MapEntry<K, V>>[])
                new LinkedList[HashMapInterface.INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> mySet = new HashSet<>();
        for (int i = 0; i < backingTable.length; i++) {
            if (backingTable[i] != null) {
                Iterator<MapEntry<K, V>> myIterator
                        = backingTable[i].iterator();
                while (myIterator.hasNext()) {
                    mySet.add(myIterator.next().getKey());
                }
            }
        }

        return mySet;
    }

    @Override
    public List<V> values() {
        List<V> myList = new ArrayList<>();
        for (int i = 0; i < backingTable.length; i++) {
            if (backingTable[i] != null) {
                Iterator<MapEntry<K, V>> myIterator
                        = backingTable[i].iterator();
                while (myIterator.hasNext()) {
                    myList.add(myIterator.next().getValue());
                }
            }
        }
        return myList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException(
                    "Passed in length should be a positive number");
        }
        LinkedList<MapEntry<K, V>>[] temp = (LinkedList<MapEntry<K, V>>[])
                new LinkedList[length];

        for (int i = 0; i < backingTable.length; i++) {
            if (backingTable[i] != null) {
                Iterator<MapEntry<K, V>> backArrayIterator =
                        backingTable[i].iterator();
                while (backArrayIterator.hasNext()) {
                    MapEntry<K, V> current = backArrayIterator.next();
                    int index = Math.abs(current.getKey().hashCode()) % length;

                    if (temp[index] == null) {
                        temp[index] = new LinkedList<>();
                        temp[index].addFirst(current);
                    } else  {
                        temp[index].addFirst(current);
                    }
                }
            }
        }
        backingTable = temp;
    }

}
