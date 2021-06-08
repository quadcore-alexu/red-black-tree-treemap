package RedBlackTreeAssignment;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public abstract class TreeSet<E> implements Set<E> {

    TreeMap<?, ?> treeMap;

    public TreeSet(TreeMap<?, ?> treeMap) {
        this.treeMap = treeMap;
    }

    @Override
    public int size() {
        return treeMap.size();
    }

    @Override
    public boolean isEmpty() {
        return treeMap.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }


    @NotNull
    @Override
    abstract public Iterator<E> iterator();

    @NotNull
    @Override
    public Object[] toArray() {
        Iterator<E> iterator = iterator();
        Object[] arr = new Object[treeMap.size()];
        for (int i = 0; iterator.hasNext(); i++) {
            arr[i] = iterator.next();
        }
        return arr;
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
