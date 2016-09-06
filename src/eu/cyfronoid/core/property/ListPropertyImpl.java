package eu.cyfronoid.core.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class ListPropertyImpl<T> implements ListProperty<T> {
    private final List<T> items;

    public ListPropertyImpl(Collection<T> items) {
        Preconditions.checkNotNull(items);
        this.items = Lists.newArrayList(items);
    }

    public ListPropertyImpl() {
        this.items = Lists.newArrayList();
    }

    @Override
    public boolean add(T item) {
        return items.add(item);
    }

    @Override
    public boolean add(int index, T item) {
        if (!items.contains(item)) {
            items.add(index, item);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(T item) {
        return items.remove(item);
    }

    @Override
    public T get(int index) {
        return items.get(index);
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public int count() {
        return items.size();
    }

    @Override
    public boolean contains(T item) {
        return items.contains(item);
    }

    @Override
    public List<T> toList() {
        return new ArrayList<T>(items);
    }

    @Override
    public Set<T> toSet() {
        return new HashSet<T>(items);
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}