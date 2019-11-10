import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Sample JUnit tests for our DeepArrayList.
 * @version 1
 */
@SuppressWarnings("unchecked")
public class DeepArrayListTests {

    static class ComparatorPlus<T extends Comparable<? super T>>  implements Comparator<T> {
        public int compare(T v1, T v2) {
            return v1.compareTo(v2);
        }
    }

    private static final int TIMEOUT = 200;

    private ArrayListInterface<String> stringList;
    private ArrayListInterface<Integer> integerList;
    private ArrayListInterface genericList;
    private ComparatorPlus<String> stringComparator;
    private ComparatorPlus<Integer> integerComparator;

    @Before
    public void setUp() {
        stringList = new DeepArrayList<>();
        integerList = new DeepArrayList<>();
        genericList = new DeepArrayList();
//        stringComparator = new ComparatorPlus<>();
//        integerComparator = new ComparatorPlus<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAddStrings() {
        assertEquals(0, stringList.size());

        stringList.add("0a"); //0a
        stringList.add("1a"); //0a 1a
        stringList.add("2a"); //0a 1a 2a
        stringList.add("3a"); //0a 1a 2a 3a

        assertEquals(4, stringList.size());
        //System.out.println("inputList: " + stringList);

        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        assertArrayEquals(expected, stringList.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddNestedList() {
        assertEquals(0, stringList.size());

        genericList.add(1);
        genericList.add(2);
        genericList.add(Arrays.asList(3, 4));
        genericList.add(5);
        genericList.add(6);

        assertEquals(5, genericList.size());
        //System.out.println("inputList: " + genericList);

        Object[] expanded = new Object[ArrayListInterface.INITIAL_CAPACITY];
        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];

        int i = 0;
        for (Object it : genericList) {
            expanded[i++] = it;
        }
        expected[0] = 1;
        expected[1] = 2;
        expected[2] = 3;
        expected[3] = 4;
        expected[4] = 5;
        expected[5] = 6;

        assertArrayEquals(expected, expanded);
    }

    @Test(timeout = TIMEOUT)
    public void testAddStringsMiddle() {
        assertEquals(0, stringList.size());

        stringList.add("0a", 0); //0a
        stringList.add("1a", 1); //0a 1a
        stringList.add("2a", 2); //0a 1a 2a
        stringList.add("3a", 3); //0a 1a 2a 3a
        stringList.add("hi", 1);
        assertEquals(5, stringList.size());

        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "hi";
        expected[2] = "1a";
        expected[3] = "2a";
        expected[4] = "3a";
        assertArrayEquals(expected, stringList.getBackingArray());
    }
    @Test(timeout = TIMEOUT)
    public void testDouble() {
        assertEquals(0, stringList.size());
        stringList.add("0a", 0);
        stringList.add("1a", 0);
        stringList.add("2a", 0);
        stringList.add("3a", 0);
        stringList.add("4a", 0);
        stringList.add("5a", 0);
        stringList.add("6a", 0);
        stringList.add("7a", 0);
        stringList.add("8a", 0);
        stringList.add("9a", 0);
        stringList.add("10a",0);
        assertEquals(11, stringList.size());

        Object[] expected = new Object[20]; //this is what the size of the array should be when resizing if doubled
        expected[0] = "10a";
        expected[1] = "9a";
        expected[2] = "8a";
        expected[3] = "7a";
        expected[4] = "6a";
        expected[5] = "5a";
        expected[6] = "4a";
        expected[7] = "3a";
        expected[8] = "2a";
        expected[9] = "1a";
        expected[10] = "0a";
        assertArrayEquals(expected, stringList.getBackingArray());
    }



    @Test(timeout = TIMEOUT)
    public void testTrimToSizeMethod() {
        assertEquals(0, integerList.size());
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.trimToSize();
        assertEquals(3, integerList.getBackingArray().length);

        Object[] expected = new Object[integerList.size()];
        expected[0] = 1;
        expected[1] = 2;
        expected[2] = 3;
        assertArrayEquals(expected, integerList.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testLastIndexOfMethod() {
        assertEquals(0, integerList.size());
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(2);

        assertEquals(3, integerList.lastIndexOf(2));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveObject() {
        stringList.add("0a", 0);
        stringList.add("1a", 1);
        stringList.add("2a", 2);

        assertEquals("1a", stringList.remove(1));
        assertFalse(stringList.remove("3a"));
        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "2a";
        assertArrayEquals(expected, stringList.getBackingArray());
    }
    @Test(timeout = TIMEOUT)
    public void testRemove() {
        stringList.add("0a", 0);
        stringList.add("1a", 1);
        stringList.add("2a",2);
        stringList.add("3a",3);

        System.out.println(stringList);
       // stringList.remove(stringList.size() - 1);
        stringList.remove(1);
        stringList.remove(0);
        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        expected[0] = "2a";
        expected[1] = "3a";
        assertArrayEquals(expected, stringList.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveEmpty() {
        assertEquals(0, integerList.size());
        integerList.remove(0);
        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        assertArrayEquals(expected, integerList.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        stringList.add("0a", 0);
        stringList.add("1a", 1);
        stringList.add("2a", 2);

        stringList.clear();
        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        assertArrayEquals(expected, stringList.getBackingArray());
        assertEquals(0, stringList.size());

    }

    @Test(timeout = TIMEOUT)
    public void testShallowCopy() {
        assertEquals(0, integerList.size());
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        Object[] newArray = integerList.shallowCopy();
        newArray[1] = 5;
        assertArrayEquals(newArray, integerList.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testDeepCopy() {
        assertEquals(0, integerList.size());
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        Object[] newArray = integerList.shallowCopy();
        newArray[1] = 5;
        assertNotEquals(newArray, integerList.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void iteratorTest() {
        assertEquals(0, genericList.size());
        genericList.add(1);
        genericList.add(2);
        ArrayList subList = new ArrayList();
        subList.add(3);
        subList.add(4);
        genericList.add(subList);
        genericList.add(5);

        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        expected[0] = 1;
        expected[1] = 2;
        expected[2] = 3;
        expected[3] = 4;
        expected[4] = 5;
        Object[] newArray = new Object[ArrayListInterface.INITIAL_CAPACITY];

        Iterator itr= genericList.iterator();
        int index = 0;
        while (itr.hasNext()) {
            newArray[index++] = itr.next();
        }
        assertArrayEquals(expected, newArray);
    }

    @Test(timeout = TIMEOUT)
    public void reverseIteratorTest() {
        assertEquals(0, genericList.size());
        genericList.add(1);
        genericList.add(2);
        ArrayListInterface subList = new DeepArrayList();
        subList.add(3);
        subList.add(4);
        genericList.add(subList);
        genericList.add(5);

        Object[] expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        expected[0] = 5;
        expected[1] = 4;
        expected[2] = 3;
        expected[3] = 2;
        expected[4] = 1;
        Object[] newArray = new Object[ArrayListInterface.INITIAL_CAPACITY];

        Iterator itr= genericList.reverseIterator();
        int index = 0;
        while (itr.hasNext()) {
            newArray[index++] = itr.next();
        }
        assertArrayEquals(expected, newArray);
    }

    @After
    public void tearDown() {
        stringList = null;
        integerList = null;
        genericList = null;
    }
}
