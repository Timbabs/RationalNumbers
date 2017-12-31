package Util;

/**
 * Created by timothybaba on 12/31/17.
 */

public class Entry<U, V> {
    private U key;
    private V value;

    public Entry(U key, V value) {
        this.key = key;
        this.value = value;
    }
    public U getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }
}