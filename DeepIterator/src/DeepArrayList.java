import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;


@SuppressWarnings("unchecked")
class DeepArrayList<T> implements ArrayListInterface<T>{
    private T[] data = (T[])new Object[INITIAL_CAPACITY];
    private class DeepArrayIterator implements Iterator<T> {

        private int position = 0;
        private Stack<Iterator> mIterators = new Stack<>();
        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next(){
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Object currentItem = backingArray[position];
            if (currentItem instanceof Iterable) {
                if(mIterators.isEmpty()){

                    mIterators.push(((Iterable)backingArray[position]).iterator());
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
                return (T)nextItem;

            }

            return (T)backingArray[position++];
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
        return null;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null){
            for (int i = size-1; i >=0; i--)
                if (data[i]==null)
                    return i;

        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(data[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public void trimToSize() {
        if (size != data.length){
            T[] newData = (T[])(new Object[size]);
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
        //implementing trim to size
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
    public boolean remove(Object o) {
        //todo
        return false;
    }

    @Override
    public T remove(int index) {
        //todo
        return null;
    }

    @Override
    public T get(int index) {
        //todo
        return null;
    }

    @Override
    public Object[] shallowCopy() {
        //todo
        return null;
    }

    @Override
    public Object[] deepCopy() {
        //todo
        return null;
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

//    @Override
//    public boolean equals(Object o) {
//
//    }
//
//    @Override
//    public int hashCode() {
//
//    }
}