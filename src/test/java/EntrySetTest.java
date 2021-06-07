import interfaces.ITreeMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class EntrySetTest {
    private static final ITreeMap<Integer, String> treeMap = new TreeMap<>();
    int[] arr2 = {9, 10, 11, 12, 13, 15, 23, 24, 30, 55};


    @BeforeAll
    public static void setUpClass() {
        treeMap.put(30, "a");
        treeMap.put(15, "b");
        treeMap.put(24, "c");
        treeMap.put(13, "asmaa");
        treeMap.put(55, "d");
        treeMap.put(12, "e");
        treeMap.put(23, "f");
        treeMap.put(9, "g");
        treeMap.put(10, "h");
        treeMap.put(11, "i");


    }

    @Test
    void testEntrySet() {
        Set<Map.Entry<Integer, String>> s = treeMap.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = s.iterator();
        int[] arr = {9, 10, 11, 12, 13, 15, 23, 24, 30, 55};
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(arr[i++], iterator.next().getKey());
        }
    }

    @Test
    void testHeadMap() {
        int[] arr1 = {9, 10, 11, 12, 13, 15};

        int i = 0;
        ArrayList<Map.Entry<Integer, String>> result = treeMap.headMap(23);
        ArrayList<Map.Entry<Integer, String>> result2 = treeMap.headMap(55, true);
        for (Map.Entry<Integer, String> entry : result) {
            assertEquals(arr1[i++], entry.getKey());
        }
        i = 0;
        for (Map.Entry<Integer, String> entry : result2) {
            assertEquals(arr2[i++], entry.getKey());
        }

    }
    @Test
    void testKeySet()
    {
        Set<Map.Entry<Integer, String>> s = treeMap.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = s.iterator();
        int i=0;
        while(iterator.hasNext())
        {
            assertEquals(arr2[i++],iterator.next().getKey());
        }
    }
    @Test
    void testContainsValue(){
        assertTrue(treeMap.containsValue("asmaa"));
        assertFalse(treeMap.containsValue("asma"));
    }


}
