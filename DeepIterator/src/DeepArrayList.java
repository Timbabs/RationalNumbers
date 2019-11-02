import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
            throw new UnsupportedOperationException();
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
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public void trimToSize() {
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
        //todo
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
    public Object shallowCopy() {
        //todo
        return null;
    }

    @Override
    public Object deepCopy() {
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