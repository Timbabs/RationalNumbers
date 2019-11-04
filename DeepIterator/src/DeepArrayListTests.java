import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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


    @After
    public void tearDown() {
        stringList = null;
        integerList = null;
        genericList = null;
    }
}
