import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;
import java.util.Stack;

@SuppressWarnings("unchecked")
class DeepArrayList<T> implements ArrayListInterface<T>{
    private class DeepArrayIterator implements Iterator<T> {

        private int position = 0;
        private Stack<Iterator> mIterators = new Stack<>();

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (backingArray[position] instanceof Iterable) {
                if (mIterators.isEmpty()) {

                    mIterators.push(((Iterable) backingArray[position]).iterator());
                }
                Iterator currIterator = mIterators.peek();
                if (!currIterator.hasNext()) {
                    throw new NoSuchElementException();
                }
                Object nextItem = currIterator.next();
                if (nextItem instanceof Iterable) {
                    mIterators.push(((Iterable) nextItem).iterator());
                    return next();
                }
                if (!currIterator.hasNext()) {
                    mIterators.pop();
                    if (mIterators.isEmpty()) {
                        ++position;
                    }

                }
                return (T) nextItem;

            }

            return (T) backingArray[position++];
        }

        @Override
        public void remove() {
            DeepArrayList.this.remove(backingArray[position - 1]);
        }
    }


    private class ReverseIterator implements Iterator<T> {

        private int position = size-1;
        private Iterator deepIterator = null;
        @Override
        public boolean hasNext() {
            return position >= 0;
        }

        @Override
        public T next(){
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (backingArray[position] instanceof Iterable) {
                if (backingArray[position] instanceof DeepArrayList) {
                    if(deepIterator == null){
                        deepIterator = (((DeepArrayList) backingArray[position]).reverseIterator());
                    }
                    Iterator currIterator = deepIterator;
                    if (!currIterator.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    Object nextItem = currIterator.next();
                    if (!currIterator.hasNext()) {
                        deepIterator = null;
                        --position;
                    }
                    return (T)nextItem;
                } else {
                    throw new RuntimeException("Unsupported Iterable type. " +
                            "The only supported type is DeepArrayList");
                }

            }
            return (T)backingArray[position--];
        }
        @Override
        public void remove() {
            DeepArrayList.this.remove(backingArray[position - 1]);
        }
    }

    private Object[] backingArray;
    private int size;


    DeepArrayList(){
        this(INITIAL_CAPACITY);
    }

    DeepArrayList(int initialCapacity){
        backingArray = new Object[initialCapacity];
        this.size = 0;
    }

    DeepArrayList(Collection<? extends T> c) {
        this();
        for (T item: c) {
            add(item);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new DeepArrayIterator();
    }

    @Override
    public Iterator<T> reverseIterator() {
        return new ReverseIterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public void trimToSize() {

    }

    @Override
    public boolean add(T data) {
        backingArray[size++] = data;
        //todo
        return false;
    }


    @Override
    public void add(T data, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index is negative");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException(index + " is greater than than"
                    + " the size of the array");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        if (size >= backingArray.length) {
            Object[] temp = new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                if (i < index) {
                    temp[i] = backingArray[i];
                } else if (i == index) {
                    temp[i] = data;
                    temp[i + 1] = backingArray[i];
                } else {
                    temp[i + 1] = backingArray[i];
                }
            }
            size++;
            backingArray = temp;
        } else {
            for (int i = size - 1; i >= index; i--) {
                backingArray[i + 1] = backingArray[i];
            }
            backingArray[index] = data;
            size++;
        }
    }

    @Override
    public boolean remove(Object obj) {
        boolean found = false;
        for(int i=0; i < size - 1; i++){
            if(backingArray[i].equals(obj) && !found){
                found = true;
            }
            if(found) {
                backingArray[i] = backingArray[i + 1];
            }
        }
        if (found) {
            backingArray[--size] = null;
        }
        return found;
    }

    @Override
    public T remove(int index) {
        T removed;
        if (index >= 0 && index < size) {
            removed = (T)backingArray[index];
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[--size] = null;
            return removed;
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds of array of size: " + size);
        }

    }

    @Override
    public T get(int index) {
        //todo
        return null;
    }

    @Override
    public Object[] shallowCopy() {
        //todo
        Object[] new_backing_array = backingArray;
        return new_backing_array;
    }

    @Override
    public Object[] deepCopy() {
        Object[] deepCopy_array = new Object[backingArray.length];
        var arr_length = backingArray.length;
        for(int i = 0; i < arr_length; i++){
            deepCopy_array[i] = backingArray[i];
        }
        return deepCopy_array;
    }

    @Override
    public boolean isEmpty() {
        //todo
        return false;
    }

    @Override
    public int size() {
        //todo
        return size;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object[] getBackingArray() {
        return backingArray;
    }

    @Override
    public String toString() {
        StringBuilder result =  new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            result.append(backingArray[i]);
            if (i == size - 1) {
                result.append("]");
            } else {
                result.append(", ");
            }
        }
        return result.toString();
    }


    /**
     * Compares the specified object with this list for equality. Returns true
     * if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of
     * elements in the two lists are equal. (Two elements e1 and e2 are equal
     * if (e1==null ? e2==null : e1.equals(e2)).) In other words, two lists are
     * defined to be equal if they contain the same elements in the same order.
     * This implementation first checks if the specified object is this list (DeepArrayList). If so,
     * it returns true; if not, it checks if the specified object is a list. If
     * not, it returns false; if so, it iterates over both lists, comparing
     * corresponding pairs of elements. If any comparison returns false, this
     * method returns false. If either iterator runs out of elements before the
     * other it returns false (as the lists are of unequal length); otherwise it
     * returns true when the iterations complete.
     * @param o the object to be compared for equality with this list
     * @return true if the specified object is equal to this list
     */
    @Override
    public boolean equals(Object o) {
        return false;
    }

    /**
     * Returns the hash code value for this list. The hash code of a list is
     * defined to be the result of the following calculation:
     *
     *      int hashCode = 1;
     *      for (E e : list)
     *          hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
     *
     * This ensures that list1.equals(list2) implies that list1.hashCode() ==
     * list2.hashCode() for any two lists, list1 and list2, as required by the
     * general contract of Object.hashCode().
     * @return the hash code value for this list
     */
    @Override
    public int hashCode() {
        return 0;
    }
}