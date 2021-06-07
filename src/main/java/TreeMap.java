import interfaces.INode;
import interfaces.ITreeMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

    private final RedBlackTree<T, V> redBlackTree = new RedBlackTree<>();


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

        @Override
        public boolean equals(Object o) {
            // self check
            if (this == o)
                return true;
            // null check
            if (o == null)
                return false;
            // type check and cast
            if (getClass() != o.getClass())
                return false;

            @SuppressWarnings("unchecked")
            Pair pair = (Pair) o;
            // field comparison
            return this.getKey().equals(pair.getKey())
                    && this.getValue().equals(pair.getValue());
        }


    }

    private class EntrySetIterator implements Iterator<Map.Entry<T, V>> {
        private INode<T, V> currentNode;
        private int countItems = 0;

        EntrySetIterator() {
            currentNode = redBlackTree.getMinimum(redBlackTree.getRoot());
        }

        @Override
        public boolean hasNext() {
            return countItems < redBlackTree.getSize();
        }

        @Override
        public Map.Entry<T, V> next() {
            if (!this.hasNext()) throw new RuntimeException("No more items");

            countItems++;
            INode<T, V> old = currentNode;
            currentNode = this.getSuccessor(currentNode);
            return new Pair(old.getKey(), old.getValue());

        }

        private INode<T, V> getSuccessor(INode<T, V> currentNode) {

            if (currentNode.getRightChild() != redBlackTree.getNil())
                return redBlackTree.getMinimum(currentNode.getRightChild());

            INode<T, V> parent = currentNode.getParent();

            while (parent != null && currentNode == parent.getRightChild()) {
                currentNode = parent;
                parent = parent.getParent();
            }

            return parent;
        }
    }


    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        if (key == null) {
            return null;
        }
        for (Map.Entry<T, V> entry : entrySet()) {
            if (entry.getKey().compareTo(key) > 0 || entry.getKey().compareTo(key) == 0) {
                return entry;
            }
        }
        return null;
    }


    @Override
    public T ceilingKey(T key) {
        if (key == null) {
            return null;
        }
        Map.Entry<T, V> entry = ceilingEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getKey();
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
        return new TreeSet<>(TreeMap.this) {
            @NotNull
            @Override
            public Iterator<Map.Entry<T, V>> iterator() {
                return new EntrySetIterator();
            }

        };
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
        if (redBlackTree.contains(key)) {
            return redBlackTree.search(key);
        }
        return null;
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        if (toKey == null) throw new RuntimeException();
        return this.headMap(toKey, false);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        if (toKey == null) throw new RuntimeException();

        TreeSet<Map.Entry<T, V>> set = new TreeSet<>(this) {
            @NotNull
            @Override
            public Iterator<Map.Entry<T, V>> iterator() {
                return new EntrySetIterator();
            }
        };

        ArrayList<Map.Entry<T, V>> headMap = new ArrayList<>();

        for (Map.Entry<T, V> entry : set) {
            if (entry.getKey().compareTo(toKey) > 0) break;
            headMap.add(entry);
        }

        if (!inclusive) headMap.remove(headMap.size() - 1);
        return headMap;

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
    public synchronized void put(T key, V value) {
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
                    private final Iterator<Map.Entry<T, V>> iterator = new EntrySetIterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public V next() {
                        return iterator.next().getValue();
                    }
                };
            }
        };
    }
}

