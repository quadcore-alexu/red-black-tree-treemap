import com.sun.source.tree.Tree;
import interfaces.INode;
import interfaces.ITreeMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

    private class Pair implements Map.Entry<T, V> {

        private final T key;
        private V value;

        public Pair(T key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public T getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            put(this.key, value);
            this.value = value;
            return old;
        }
    }

    private final RedBlackTree<T, V> redBlackTree = new RedBlackTree<>();

    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        if (key == null) {
            return null;
        }
        Iterator<Map.Entry<T, V>> it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<T, V> entry = it.next();
            if (entry.getKey().compareTo(key) > 0 || entry.getKey().compareTo(key) == 0) {
                return entry;
            }
        }
        return null;
    }



    @Override
    public T ceilingKey(T key) {

        return key;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean containsKey(T key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        return null;
    }

    @Override
    public Map.Entry<T, V> firstEntry() {
        if (redBlackTree.isEmpty()) return null;

        INode<T, V> nil = redBlackTree.getNil();
        INode<T, V> currentNode, nextNode = redBlackTree.getRoot();

        do {
            currentNode = nextNode;
            nextNode = currentNode.getLeftChild();
        } while (!nextNode.equals(nil));

        return new Pair(currentNode.getKey(), currentNode.getValue());
    }

    @Override
    public T firstKey() {
        return firstEntry().getKey();
    }

    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        return null;
    }

    @Override
    public T floorKey(T key) {
        return null;
    }

    @Override
    public V get(T key) {
        if (redBlackTree.contains(key)){
            return redBlackTree.search(key);
        }
        return null;
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        return null;
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        return null;
    }

    @Override
    public Set<T> keySet() {
        return null;
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        if (redBlackTree.isEmpty()) return null;

        INode<T, V> nil = redBlackTree.getNil();
        INode<T, V> currentNode, nextNode = redBlackTree.getRoot();

        do {
            currentNode = nextNode;
            nextNode = currentNode.getRightChild();
        } while (!nextNode.equals(nil));

        return new Pair(currentNode.getKey(), currentNode.getValue());
    }

    @Override
    public T lastKey() {
        return lastEntry().getKey();
    }

    @Override
    public Map.Entry<T, V> pollFirstEntry() {
        Map.Entry<T, V> entry = firstEntry();
        redBlackTree.delete(entry.getKey());
        return entry;
    }

    @Override
    public Map.Entry<T, V> pollLastEntry() {
        Map.Entry<T, V> entry = lastEntry();
        redBlackTree.delete(entry.getKey());
        return entry;
    }

    @Override
    public void put(T key, V value) {
        redBlackTree.insert(key, value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        for (Map.Entry<T, V> entry : map.entrySet()) {
            redBlackTree.insert(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean remove(T key) {
        return redBlackTree.delete(key);
    }

    @Override
    public int size() {
        return redBlackTree.getSize();
    }

    @Override
    public Collection<V> values() {
        return new TreeSet<>(this) {
            @NotNull
            @Override
            public Iterator<V> iterator() {
                return new Iterator<>() {
                    private INode<T, V> current = redBlackTree.getRoot();

                    @Override
                    public boolean hasNext() {
                        return current != null && !current.equals(redBlackTree.getNil());
                    }

                    @Override
                    public V next() {
                        INode<T, V> old = current;
                        //todo: update current node according to in-order traversal
                        return old.getValue();
                    }
                };
            }
        };
    }

    private void inorderHelper(INode<T,V> root, Set<Map.Entry<T, V>> result)
    {
        if (root.isNull())
            return;
        inorderHelper(root.getLeftChild(), result);
        result.add(new Pair(root.getKey(), root.getValue()) );
        inorderHelper(root.getRightChild(), result);
    }



}
