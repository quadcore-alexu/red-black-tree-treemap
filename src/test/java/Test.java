import interfaces.ITreeMap;

import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Test {
    @org.junit.jupiter.api.Test
    void Test() {
        TreeMap<Integer, String> treemap = new TreeMap<>();
        TreeMap<Integer, String> t = new TreeMap<>();
        Random r = new Random();

        for (int i = 0; i < 1000; i++) {
            int key = r.nextInt(10000);
            t.put(key, "soso" + key);
            treemap.put(key, "soso" + key);
        }
        Iterator<Map.Entry<Integer, String>> itr1 = treemap.entrySet().iterator();
        Iterator<Map.Entry<Integer, String>> itr2 = t.entrySet().iterator();

        while (itr1.hasNext() && itr2.hasNext()) {
            Map.Entry<Integer, String> entry1 = itr1.next();

            Map.Entry<Integer, String> entry2 = itr2.next();
            assertEquals(entry1, entry2);

        }

    }
}

