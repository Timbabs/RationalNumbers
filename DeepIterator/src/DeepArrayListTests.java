import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Sample JUnit tests for our DeepArrayList.
 * @version 1
 */
@SuppressWarnings("unchecked")
public class DeepArrayListTests {
    private static final int TIMEOUT = 200;

    private ArrayListInterface<String> stringList;
    private ArrayListInterface<Integer> integerList;
    private ArrayListInterface genericList;

    @Before
    public void setUp() {
        stringList = new DeepArrayList<>();
        integerList = new DeepArrayList<>();
        genericList = new DeepArrayList();
    }

    @Test(timeout = TIMEOUT)
    public void testAddStrings() {
        assertEquals(0, stringList.size());

        stringList.add("0a"); //0a
        stringList.add("1a"); //0a 1a
        stringList.add("2a"); //0a 1a 2a
        stringList.add("3a"); //0a 1a 2a 3a

        assertEquals(4, stringList.size());
        System.out.println("inputList: " + stringList);

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
        System.out.println("inputList: " + genericList);

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


    @After
    public void tearDown() {
        stringList = null;
        integerList = null;
        genericList = null;
    }
}
