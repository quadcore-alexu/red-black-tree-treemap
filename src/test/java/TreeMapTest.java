import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TreeMapTest {
    @Test
    void get() {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(30, 1);
        treeMap.put(15, 2);
        treeMap.put(24, 3);
        assertEquals(2, treeMap.get(15));
        assertNull(treeMap.get(5));
    }

    @Test
    void remove() {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(30, 1);
        treeMap.put(15, 2);
        treeMap.put(24, 3);
        assertEquals(3, treeMap.size());
        treeMap.remove(15);
        assertNull(treeMap.get(15));
        assertEquals(2, treeMap.size());
    }

    @Test
    void ceilingKey() {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(30, 1);
        treeMap.put(15, 2);
        treeMap.put(24, 3);
        treeMap.put(13, 4);
        treeMap.put(55, 5);
        assertEquals(24, treeMap.ceilingKey(20));
        assertEquals(15, treeMap.ceilingKey(15));
        assertEquals(55, treeMap.ceilingKey(50));
        assertNull(treeMap.ceilingKey(60));
    }

    @Test
    void putAndPutAll() {
        /*test put*/
        TreeMap<Integer, String> treeMap = new TreeMap<>();

        treeMap.put(5, "five");
        assertEquals(1, treeMap.size());
        assertEquals("five", treeMap.get(5));

        treeMap.put(5, "New five");
        assertEquals(1, treeMap.size());
        assertEquals("New five", treeMap.get(5));

        treeMap.put(6, "six");
        assertEquals(2, treeMap.size());

        treeMap.put(null, "null string");
        assertEquals(2, treeMap.size());
        assertNull(treeMap.get(null));

        /*test putAll*/
        Map<Integer, String> map = new HashMap<>();
        map.put(6, "New six");
        map.put(7, "seven");
        map.put(8, "eight");
        map.put(null, "null string");
        treeMap.putAll(map);
        assertEquals(4, treeMap.size());
        assertEquals("New six", treeMap.get(6));
        treeMap.putAll(null);
        assertEquals(4, treeMap.size());

    }

    @Test
    void values() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("a", 0);
        treeMap.put("c", 2);
        treeMap.put("b", 1);
        treeMap.put("d", 3);

        Iterator<Integer> iterator = treeMap.values().iterator();

        for (int i = 0; iterator.hasNext(); i++) {
            assertEquals(i, iterator.next());
        }

    }

    @Test
    void firstAndLastEntryAndKey() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("a", 6);
        treeMap.put("c", 3);
        treeMap.put("b", 60);
        treeMap.put("d", 5);

        /*first entry*/
        Map.Entry<String, Integer> firstEntry = treeMap.firstEntry();
        assertEquals("a", firstEntry.getKey());
        assertEquals(6, firstEntry.getValue());

        /*first key*/
        String firstKey = treeMap.firstKey();
        assertEquals("a", firstKey);

        /*last entry*/
        Map.Entry<String, Integer> lastEntry = treeMap.lastEntry();
        assertEquals("d", lastEntry.getKey());
        assertEquals(5, lastEntry.getValue());

        /*last key*/
        String lastKey = treeMap.lastKey();
        assertEquals("d", lastKey);

        assertEquals(4, treeMap.size());
    }

    @Test
    void polling() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("a", 6);
        treeMap.put("c", 3);
        treeMap.put("b", 60);
        treeMap.put("d", 5);

        assertEquals(4, treeMap.size());

        /*first entry*/
        Map.Entry<String, Integer> firstEntry = treeMap.pollFirstEntry();
        assertEquals("a", firstEntry.getKey());
        assertEquals(6, firstEntry.getValue());
        assertEquals(3, treeMap.size());
        assertNull(treeMap.get("a"));

        /*last entry*/
        Map.Entry<String, Integer> lastEntry = treeMap.pollLastEntry();
        assertEquals("d", lastEntry.getKey());
        assertEquals(5, lastEntry.getValue());
        assertEquals(2, treeMap.size());
        assertNull(treeMap.get("d"));

    }
}
