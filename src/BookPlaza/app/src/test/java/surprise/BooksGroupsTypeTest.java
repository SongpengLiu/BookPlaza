package surprise;

import com.example.bookplaza.surprise.BooksGroupsType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test BooksGroupsType
 * @author Yucheng Zhu
 */
public class BooksGroupsTypeTest {
    /**
     * Test BooksGroupsType
     * @author Yucheng Zhu
     */
    @Test
    public void testBooksGroupsType() {
        assertEquals(
                BooksGroupsType.DailyReading,
                BooksGroupsType.fromValue(0)
        );
        assertEquals(
                BooksGroupsType.HotFeed,
                BooksGroupsType.fromValue(1)
        );
        assertEquals(
                BooksGroupsType.Explore,
                BooksGroupsType.fromValue(2)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> BooksGroupsType.fromValue(3)
        );
    }

    /**
     * Test BooksGroupsType
     * @author Yucheng Zhu
     */
    @Test
    public void testValue() {
        BooksGroupsType bookGroupType = BooksGroupsType.DailyReading;
        assertEquals(
                0,
                bookGroupType.getGroupCount()
        );
    }

    /**
     * Test fromValue
     * @author Yucheng Zhu
     */
    @Test
    public void testFromValue() {
        // test all 3 types
        BooksGroupsType bookGroupType = BooksGroupsType.fromValue("DailyReading");
        assertEquals(
                BooksGroupsType.DailyReading,
                bookGroupType
        );

        BooksGroupsType bookGroupType2 = BooksGroupsType.fromValue("HotFeed");
        assertEquals(
                BooksGroupsType.HotFeed,
                bookGroupType2
        );

        BooksGroupsType bookGroupType3 = BooksGroupsType.fromValue("Explore");
        assertEquals(
                BooksGroupsType.Explore,
                bookGroupType3
        );

        // illegal book group
        assertThrows(
                IllegalArgumentException.class,
                () -> BooksGroupsType.fromValue("WTF")
        );
    }
}
