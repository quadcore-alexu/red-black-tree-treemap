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

        @Override
        public boolean equals(Object o) {
            Pair p = (Pair) o;

            return this.getKey().equals(p.getKey()) && this.getValue().equals(p.getValue());
        }


    }

    private class EntrySetIterator<E> implements Iterator<E> {
        private INode<T, V> root;
        private INode<T, V> currentNode;
        private int countItems = 0;

        EntrySetIterator() {
            root = redBlackTree.getRoot();
            currentNode = this.getSmallestNode();

        }

        private INode<T, V> getSmallestNode() {
            INode<T, V> smallestNode = null;
            INode<T, V> temp = root;
            while (temp != redBlackTree.getNil()) {
                smallestNode = temp;
                temp = temp.getLeftChild();

            }
            return smallestNode;
        }

        private INode<T, V> getSuccessor(INode<T, V> currentNode) {
            INode<T, V> successor = null;
            if (currentNode.getRightChild() != redBlackTree.getNil())
                return redBlackTree.getMinimum(currentNode.getRightChild());
            INode<T, V> parent = currentNode.getParent();

            while (parent != null && currentNode == parent.getRightChild()) {
                currentNode = parent;
                parent = parent.getParent();


            }
            return parent;
        }

        @Override
        public boolean hasNext() {
            return countItems < redBlackTree.getSize();
        }

        @Override
        public E next() {
            if (this.hasNext()) {
                countItems++;
                if (countItems == 1) {
                    Pair entry = new Pair(currentNode.getKey(), currentNode.getValue());
                    return (E) entry;

                } else {
                    currentNode = this.getSuccessor(currentNode);
                    Pair entry = new Pair(currentNode.getKey(), currentNode.getValue());
                    return (E) entry;

                }
            }
            throw new RuntimeException("No more items");

        }
    }

    private final RedBlackTree<T, V> redBlackTree = new RedBlackTree<>();

    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        return null;
    }

    @Override
    public T ceilingKey(T key) {
        return null;
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
        TreeSet<Map.Entry<T, V>> entryTreeSet = new TreeSet<Map.Entry<T, V>>(this) {
            @NotNull
            @Override
            public Iterator<Map.Entry<T, V>> iterator() {

                return new EntrySetIterator<>();
            }

        };
        return entryTreeSet;
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
        return null;
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        if (toKey == null) throw new RuntimeException();
        ArrayList<Map.Entry<T, V>> headMap = new ArrayList<>();
        headMap = this.headMap(toKey, false);
        return headMap;
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        if (toKey == null) throw new RuntimeException();
        ArrayList<Map.Entry<T, V>> headMap = new ArrayList<>();
        TreeSet<Map<T, V>> set = new TreeSet<Map<T, V>>(this) {
            @NotNull
            @Override
            public Iterator<Map<T, V>> iterator() {
                return new EntrySetIterator<>();
            }
        };
        Iterator<Map<T, V>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<T, V> entry = (Map.Entry<T, V>) iterator.next();
            if (entry.getKey().compareTo(toKey) > 0) {
                break;
            }
            headMap.add(entry);
        }
        if (!inclusive)
            headMap.remove(headMap.size() - 1);
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
        return false;
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

            @Override
            public boolean add(V v) {
                return false;
            }
        };
    }
}
