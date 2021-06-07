import static org.junit.jupiter.api.Assertions.*;

public class TreeMapTest {
    @org.junit.jupiter.api.Test
    void get() {
        TreeMap<Integer, Integer> treeMap=new TreeMap<>();
        treeMap.put(30,1);
        treeMap.put(15,2);
        treeMap.put(24,3);
        assertEquals(2,treeMap.get(15));
        assertEquals(null,treeMap.get(5));
    }

    @org.junit.jupiter.api.Test
    void remove(){
        TreeMap<Integer, Integer> treeMap=new TreeMap<>();
        treeMap.put(30,1);
        treeMap.put(15,2);
        treeMap.put(24,3);
        assertEquals(3,treeMap.size());
        treeMap.remove(15);
        assertEquals(null,treeMap.get(15));
        assertEquals(2,treeMap.size());
    }

    @org.junit.jupiter.api.Test
    void ceilingKey() {
        TreeMap<Integer, Integer> treeMap=new TreeMap<>();
        treeMap.put(30,1);
        treeMap.put(15,2);
        treeMap.put(24,3);
        treeMap.put(13,4);
        treeMap.put(55,5);
        assertEquals(24,treeMap.ceilingKey(20));
        assertEquals(15,treeMap.ceilingKey(15));
        assertEquals(55,treeMap.ceilingKey(50));
        assertEquals(null,treeMap.ceilingKey(60));
    }
    }
