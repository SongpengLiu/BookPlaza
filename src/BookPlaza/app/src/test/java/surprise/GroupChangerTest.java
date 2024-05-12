package surprise;

import com.example.bookplaza.dictTree.NumbersPair;
import com.example.bookplaza.io.*;
import com.example.bookplaza.io.Reader;
import com.example.bookplaza.surprise.GroupChanger;
import com.example.bookplaza.surprise.MeanPreferencesValuesMapUpdater;
import com.example.bookplaza.trees.AttributesLoader;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.Library;
import com.example.bookplaza.trees.TreeData;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test changing books' groups
 * @author Yucheng Zhu
 */
public class GroupChangerTest {

    // Main test object
    GroupChanger groupChanger;

    // All 3 book groups' attributes
    AttributesLoader attributesLoader0;
    BooksAttributes attributes0;
    AttributesLoader attributesLoader1;
    BooksAttributes attributes1;
    AttributesLoader attributesLoader2;
    BooksAttributes attributes2;

    /**
     * Load all attributes into the optimal data structure so that they can be sorted and searched in O(1) or o(log n).
     * @author Yucheng Zhu
     */
    @Before
    public void init() throws IOException {
        // load library
        Library library = new Library(Reader.assetsFolder(Data.BOOK_CSV_FILE));

        // construct group changer
        groupChanger = new GroupChanger(library);

        // -- load all 3 book groups' attributes
        // Read the first book's attributes
        attributesLoader0 = new AttributesLoader();
        attributes0 = attributesLoader0.readBooksToAttributes(
                Reader.assetsFolder(Data.BOOK_CSV_FILE),
                IndicesReader.read(Reader.assetsFolder("DailyReading"))
        );

        // Read the second book's attributes
        attributesLoader1 = new AttributesLoader();
        attributes1 = attributesLoader1.readBooksToAttributes(
                Reader.assetsFolder(Data.BOOK_CSV_FILE),
                IndicesReader.read(Reader.assetsFolder("HotFeed"))
        );


        // Read the third book's attributes
        attributesLoader2 = new AttributesLoader();
        attributes2 = attributesLoader2.readBooksToAttributes(
                Reader.assetsFolder(Data.BOOK_CSV_FILE),
                IndicesReader.read(Reader.assetsFolder("Explore"))
        );
    }


    /**
     * Test books are correctly moved in the groups
     * @author Yucheng Zhu
     */
    @Test
    public void testMove() {
        // check the original group value
        assertEquals(966, attributes0.getUrls().size());
        assertEquals(966, attributes1.getUrls().size());
        assertEquals(968, attributes2.getUrls().size());

        // test move attributes across groups
        groupChanger.move(
                444,
                attributes0, attributes1
        );

        // test key is moved
        assertEquals(965, attributes0.getUrls().size());
        assertEquals(967, attributes1.getUrls().size());
    }

    /**
     * Test load books' indices in all 3 book groups
     * @author Yucheng Zhu
     */
    @Test
    public void testLoadAllBooksGroupsIndices() {
        HashSet<Integer>[] allGroupsIndices = groupChanger.loadAllBooksGroupsIndices();

        // test the first book group is loaded correctly
        HashSet<Integer> expectedIndices = new HashSet<>(Arrays.asList(new Integer[] {2050, 3, 2055, 8, 2057, 10, 11, 12, 2061, 2062, 2066, 19, 2070, 25, 2074, 27, 28, 2077, 2078, 34, 35, 2086, 40, 2090, 43, 2091, 44, 2094, 47, 2095, 2096, 2097, 50, 2099, 2103, 2104, 56, 2110, 62, 2111, 67, 71, 72, 74, 76, 77, 2125, 2127, 2131, 2134, 2137, 2139, 92, 94, 2142, 2146, 99, 101, 2153, 106, 2158, 114, 116, 2164, 2165, 119, 120, 121, 2169, 122, 2172, 125, 127, 2175, 134, 2183, 135, 136, 138, 2186, 2189, 142, 2191, 147, 148, 151, 2202, 2203, 2204, 2205, 2206, 160, 163, 2211, 165, 2214, 168, 171, 2223, 2225, 178, 2228, 180, 181, 2230, 182, 2233, 187, 188, 2238, 2240, 193, 194, 196, 198, 2247, 200, 2250, 202, 204, 206, 2254, 207, 2255, 2256, 209, 2258, 210, 211, 2262, 216, 2264, 217, 2265, 2266, 2268, 220, 2269, 2271, 2276, 229, 230, 2279, 2282, 235, 2284, 236, 237, 240, 2288, 243, 248, 2297, 249, 2298, 2299, 251, 2302, 254, 257, 2306, 2307, 263, 2313, 267, 2315, 268, 2317, 2318, 272, 2320, 273, 274, 276, 280, 285, 2335, 287, 2337, 2338, 291, 2340, 2342, 294, 2343, 2348, 301, 2349, 302, 2350, 303, 304, 306, 2355, 2357, 2359, 2362, 2365, 318, 319, 2367, 2369, 322, 2371, 324, 327, 2375, 328, 2378, 2379, 2381, 333, 2383, 335, 337, 339, 2387, 341, 2389, 2393, 346, 347, 348, 2396, 2397, 2400, 353, 355, 360, 362, 2410, 2411, 2413, 365, 366, 2415, 370, 2418, 2420, 2421, 2422, 375, 2423, 2424, 2428, 380, 2429, 2430, 383, 384, 2434, 2435, 2437, 390, 2440, 394, 395, 396, 397, 399, 400, 2450, 2451, 404, 405, 406, 2454, 2455, 2457, 409, 2458, 410, 413, 2461, 416, 417, 418, 2466, 421, 2469, 2474, 428, 429, 2477, 433, 435, 2483, 437, 2485, 2489, 2490, 2491, 444, 445, 448, 2498, 2499, 2500, 2501, 455, 459, 460, 2509, 463, 464, 2513, 2514, 466, 468, 2516, 2519, 474, 476, 477, 2526, 2527, 2528, 2529, 483, 2534, 487, 489, 491, 2539, 2540, 2541, 2542, 494, 2544, 2545, 2546, 498, 2547, 2550, 2552, 508, 509, 2558, 510, 512, 2561, 516, 518, 2567, 2568, 521, 523, 2571, 524, 2573, 2574, 528, 2576, 529, 2579, 531, 532, 2581, 2583, 2585, 2586, 538, 539, 541, 2590, 2591, 543, 546, 547, 548, 2597, 2598, 551, 552, 2601, 2602, 2604, 2605, 2608, 562, 2611, 563, 2615, 2617, 569, 2618, 572, 2621, 2622, 2623, 2625, 2628, 2631, 584, 588, 2636, 2638, 592, 593, 2641, 594, 2644, 596, 599, 600, 2650, 2651, 604, 605, 607, 608, 609, 2658, 2662, 615, 2664, 2665, 2667, 2670, 2672, 625, 2674, 2676, 629, 2678, 2679, 637, 638, 639, 641, 2689, 642, 2691, 2693, 654, 2702, 655, 2704, 657, 2707, 2708, 661, 2711, 2712, 2713, 667, 2715, 675, 681, 2730, 683, 2731, 2732, 684, 2733, 686, 687, 688, 2736, 691, 2742, 2746, 700, 701, 703, 2752, 706, 708, 2758, 710, 2759, 716, 2764, 717, 719, 720, 722, 2771, 2772, 725, 726, 2776, 730, 731, 733, 734, 2783, 737, 2785, 738, 2787, 739, 2789, 746, 748, 2797, 750, 2800, 753, 755, 756, 757, 2805, 2806, 758, 764, 765, 766, 2814, 767, 2815, 2817, 770, 2819, 771, 774, 2822, 2823, 2830, 2831, 786, 2835, 2836, 789, 2840, 793, 2843, 800, 2849, 803, 804, 805, 2854, 2855, 2857, 809, 813, 2864, 816, 2866, 819, 2868, 822, 823, 825, 826, 827, 829, 2878, 831, 2880, 2881, 834, 837, 2885, 2887, 2888, 840, 2889, 842, 848, 851, 854, 856, 858, 859, 860, 864, 868, 869, 871, 873, 876, 877, 884, 889, 890, 891, 892, 893, 895, 899, 905, 912, 914, 926, 930, 935, 941, 944, 945, 950, 951, 953, 954, 957, 960, 965, 971, 972, 975, 977, 978, 980, 986, 987, 994, 995, 997, 1017, 1019, 1021, 1025, 1026, 1028, 1031, 1032, 1040, 1042, 1043, 1044, 1045, 1046, 1048, 1051, 1070, 1078, 1079, 1080, 1084, 1085, 1086, 1087, 1089, 1094, 1095, 1099, 1106, 1107, 1113, 1114, 1115, 1120, 1124, 1125, 1126, 1127, 1129, 1132, 1135, 1137, 1140, 1142, 1146, 1151, 1157, 1162, 1163, 1166, 1174, 1179, 1180, 1183, 1187, 1191, 1194, 1195, 1202, 1204, 1207, 1212, 1214, 1215, 1227, 1234, 1236, 1240, 1249, 1253, 1255, 1257, 1261, 1266, 1267, 1269, 1270, 1274, 1278, 1284, 1289, 1291, 1293, 1301, 1306, 1307, 1308, 1309, 1312, 1316, 1321, 1322, 1323, 1324, 1326, 1327, 1329, 1331, 1333, 1336, 1339, 1341, 1344, 1345, 1348, 1354, 1356, 1359, 1361, 1367, 1368, 1370, 1371, 1384, 1387, 1391, 1392, 1396, 1398, 1399, 1400, 1401, 1404, 1405, 1407, 1408, 1410, 1413, 1416, 1418, 1423, 1424, 1428, 1447, 1448, 1449, 1454, 1459, 1460, 1462, 1466, 1467, 1471, 1477, 1478, 1479, 1480, 1481, 1483, 1490, 1491, 1493, 1494, 1495, 1496, 1498, 1499, 1500, 1501, 1503, 1504, 1506, 1507, 1511, 1525, 1526, 1528, 1530, 1535, 1537, 1539, 1540, 1546, 1548, 1553, 1556, 1557, 1560, 1561, 1562, 1564, 1573, 1578, 1581, 1587, 1589, 1591, 1595, 1597, 1599, 1600, 1601, 1602, 1604, 1606, 1615, 1616, 1619, 1620, 1623, 1625, 1628, 1631, 1633, 1635, 1637, 1638, 1641, 1644, 1646, 1647, 1648, 1649, 1653, 1654, 1655, 1656, 1661, 1665, 1672, 1675, 1681, 1682, 1683, 1684, 1687, 1688, 1690, 1696, 1698, 1699, 1700, 1702, 1703, 1709, 1710, 1711, 1716, 1717, 1725, 1726, 1728, 1729, 1730, 1735, 1737, 1738, 1739, 1740, 1745, 1748, 1750, 1755, 1757, 1758, 1761, 1763, 1768, 1775, 1776, 1778, 1780, 1782, 1784, 1791, 1792, 1793, 1795, 1798, 1803, 1809, 1812, 1815, 1819, 1823, 1824, 1825, 1826, 1827, 1828, 1835, 1839, 1841, 1842, 1844, 1845, 1848, 1850, 1853, 1856, 1858, 1859, 1869, 1881, 1882, 1883, 1886, 1889, 1890, 1891, 1892, 1893, 1894, 1895, 1898, 1900, 1902, 1903, 1906, 1908, 1909, 1920, 1921, 1922, 1925, 1933, 1934, 1937, 1940, 1941, 1942, 1944, 1946, 1948, 1950, 1951, 1953, 1962, 1969, 1970, 1971, 1977, 1978, 1979, 1981, 1985, 1989, 1993, 2000, 2003, 2005, 2007, 2008, 2009, 2010, 2011, 2012, 2018, 2025, 2026, 2028, 2031, 2033, 2037, 2041, 2043, 2047}));
        assertEquals(expectedIndices, allGroupsIndices[0]);

        // test loaded the correct number of book groups
        assertEquals(3, allGroupsIndices.length);
    }

    /**
     * Test after a book is clicked, record all books that changed book groups
     * @author Yucheng Zhu
     */
    @Test
    public void testRecordBooksThatChangedGroups() throws IOException {
        Data data = Data.getInstance();

        // load preference values
        MeanPreferencesValuesMapUpdater meanPreferencesValuesMapUpdater;
        try {
            meanPreferencesValuesMapUpdater = new MeanPreferencesValuesMapUpdater(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // update preference values after a book is clicked and return the old and new groups
        // at first, no book changes group
        HashSet<ArrayList<Integer>> expected = new HashSet<>();
        for (int i = 0; i <= 17; i++) {
            HashSet<ArrayList<Integer>> oldAndNewBooksGroup =
                    groupChanger.recordBooksThatChangedGroups(
                            data,
                            meanPreferencesValuesMapUpdater,
                            i
                    );
            System.out.println("i="+i);
            System.out.println("oldAndNewBooksGroup="+oldAndNewBooksGroup);
            assertEquals(expected, oldAndNewBooksGroup);
        }

        // first book that changed group
        ArrayList<Integer> newChange1 = new ArrayList<>(Arrays.asList(new Integer[] {18, 1, 2}));
        ArrayList<Integer> newChange2 = new ArrayList<>(Arrays.asList(new Integer[] {2548, 2, 1}));
        expected.add(newChange1);
        expected.add(newChange2);
        for (int i = 18; i <= 21; i++) {
            HashSet<ArrayList<Integer>> oldAndNewBooksGroup =
                    groupChanger.recordBooksThatChangedGroups(
                            data,
                            meanPreferencesValuesMapUpdater,
                            i
                    );
            System.out.println("i="+i);
            System.out.println("oldAndNewBooksGroup="+oldAndNewBooksGroup);
            assertEquals(expected, oldAndNewBooksGroup);
        }

        // second book that changed group
        ArrayList<Integer> newChange3 = new ArrayList<>(Arrays.asList(new Integer[] {1108, 1, 2}));
        ArrayList<Integer> newChange4 = new ArrayList<>(Arrays.asList(new Integer[] {2774, 2, 1}));
        expected.add(newChange3);
        expected.add(newChange4);

        HashSet<ArrayList<Integer>> oldAndNewBooksGroup = null;
        for (int i = 22; i <= 39; i++) {
            oldAndNewBooksGroup =
                    groupChanger.recordBooksThatChangedGroups(
                            data,
                            meanPreferencesValuesMapUpdater,
                            i
                    );
            System.out.println("i="+i);
            System.out.println("oldAndNewBooksGroup="+oldAndNewBooksGroup);
            assertEquals(expected, oldAndNewBooksGroup);
        }

        // test write it to file
        HashMapWriter hashMapWriter = new HashMapWriter();
        OutputStream outputStream = Files.newOutputStream(Paths.get("src/main/assets/readerTestGroupsChanges1"));
        hashMapWriter.write(outputStream, oldAndNewBooksGroup);

        // test read it from the file
        HashMapReader<HashSet<ArrayList<Integer>>> hashMapReader = new HashMapReader<>();
        InputStream inputStream = Files.newInputStream(Paths.get("src/main/assets/readerTestGroupsChanges1"));
        assertEquals(oldAndNewBooksGroup, hashMapReader.readInfoForBooksChangedGroups(inputStream));
    }

}
