import interfaces.ITreeMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntrySetTest {
    private static final ITreeMap<Integer, String> treeMap = new TreeMap<>();


    @BeforeAll
    public static void setUpClass() {
        treeMap.put(30, null);
        treeMap.put(15, null);
        treeMap.put(24, null);
        treeMap.put(13, null);
        treeMap.put(55, null);
        treeMap.put(12, null);
        treeMap.put(23, null);
        treeMap.put(9, null);
        treeMap.put(10, null);
        treeMap.put(11, null);

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
        int[] arr2 = {9, 10, 11, 12, 13, 15, 23, 24, 30, 55};
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

}
