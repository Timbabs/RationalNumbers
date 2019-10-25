import java.util.Iterator;
import java.util.Stack;

class SomeDataStructure implements Iterable<Integer>{
    private Object[] value;
    private int size, position;
    private Stack<Iterator<SomeDataStructure>> mTterators;

    SomeDataStructure(int size){
        value = new Object[size];
        this.size = 0;
        position = 0;
        mTterators = new Stack<>();
    }
    void add(int num){
        value[size++] = num;
    }
    void add(SomeDataStructure num){
        value[size++] = num;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator iterator(){
        return new Iterator() {
            @Override
            public boolean hasNext() {
                return position < size;
            }
            // [1, 3, 5, [0, 2, 4, 6, 8], 7]
            //1, 3, 5, 0,
            @Override
            public Object next(){
                if (hasNext()) {
                    if (value[position] instanceof Integer) {
                        return value[position++];
                    }

                    if(mTterators.isEmpty()){
                        if (value[position] instanceof SomeDataStructure) {
                            mTterators.push(((SomeDataStructure)value[position]).iterator());
                        }
                    }
                    Iterator currIterator = mTterators.peek();
                    if (currIterator.hasNext()) {
                        Object nextItem = currIterator.next();
                        if (!currIterator.hasNext()) {
                            mTterators.pop();
                            if (mTterators.isEmpty()) {
                                ++position;
                            }

                        }
                        return nextItem;
                    } else {
                        return null;
                    }


                } else{

                    return null;
                }
            }
        };
    }
}

class Main{
    public static void main(String[] args) {
        SomeDataStructure obj1 = new SomeDataStructure(2);
        obj1.add(4);
        obj1.add(6);
        SomeDataStructure obj = new SomeDataStructure(5);
        obj.add(0);
        obj.add(2);
        obj.add(obj1);
        obj.add(8);
        SomeDataStructure sub = new SomeDataStructure(5);
        sub.add(1);
        sub.add(3);
        sub.add(5);
        sub.add(obj);
        sub.add(7);


        System.out.println("\n------------------------------------\nFor Loop");

//        for(Iterator it = sub.iterator(); it.hasNext();){
//            System.out.print(it.next()+" ");
//        }
        for (Object item : sub) {
            System.out.print(item + " ");
        }
    }
}



