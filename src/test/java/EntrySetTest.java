import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class EntrySetTest {
    @Test
    void Test()
    {
        TreeMap<Integer,String> treeMap= new TreeMap<>();
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
        Set<Map.Entry<Integer,String>> s=treeMap.entrySet();
        Iterator<Map.Entry<Integer,String>> iterator = s.iterator();
        int arr[]={9,10,11,12,13,15,23,24,30,55};
        int i=0;
        while(iterator.hasNext())
        {
            assertEquals(arr[i++],iterator.next().getKey());
        }

    }

}
