package eu.cyfronoid.core.property;

import java.util.List;

import com.google.common.collect.Lists;

public class DynamicPropertyImpl<T> extends PropertyImpl<T> implements DynamicProperty<T> {
    private final List<PropertyListener<T>> listeners = Lists.newArrayList();

    public DynamicPropertyImpl(T value) {
        super(value);
    }

    @Override
    public void addPropertyListener(PropertyListener<T> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removePropertyListener(PropertyListener<T> listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void set(T value) {
        T oldVal = get();
        super.set(value);
        firePropertyChanged(this, oldVal, value);
    }

    private void firePropertyChanged(Property<T> property, T oldVal, T newVal) {
        if(this.listeners != null) {
            if(oldVal == null && newVal == null) {
                return;
            } else if(oldVal != null && newVal != null) {
                if(oldVal.equals(newVal)) {
                    return;
                }
            }
            this.listeners.forEach(l -> l.valueChanged(property, oldVal, newVal));
        }
    }
}
