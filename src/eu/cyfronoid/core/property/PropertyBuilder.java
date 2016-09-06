package eu.cyfronoid.core.property;

public class PropertyBuilder<T> {
    private boolean isAllowSetNotNull = true;
    private boolean isAllowGetNull = true;
    private String valueName;

    public static <T> Property<T> create() {
        return new PropertyBuilder<T>().build();
    }

    public static <T> Property<T> create(String name) {
        return new PropertyBuilder<T>().setName(name).build();
    }

    public static <T> Property<T> createSafe() {
        return new PropertyBuilder<T>().doNotAllowGetNull().doNotAllowSetNotNull().build();
    }

    public static <T> Property<T> createSafe(String name) {
        return new PropertyBuilder<T>().doNotAllowGetNull().doNotAllowSetNotNull().setName(name).build();
    }

    public PropertyBuilder<T> setName(String name) {
        valueName = name;
        return this;
    }

    public PropertyBuilder<T> doNotAllowSetNotNull() {
        isAllowSetNotNull = false;
        return this;
    }

    public PropertyBuilder<T> doNotAllowGetNull() {
        isAllowGetNull = false;
        return this;
    }

    public Property<T> build() {
        return new Property<T>() {
            private boolean allowSetNotNull = isAllowSetNotNull;
            private boolean allowGetNull = isAllowGetNull;
            private String name = valueName;
            private T value;

            public void set(T value) {
                if(this.value != null && !allowSetNotNull) {
                    throw new IllegalArgumentException("It is not allowed to set the value if it is already set. Value name: " + getName());
                }
                this.value = value;
            }

            public T get() {
                if(value == null && !allowGetNull) {
                    throw new IllegalArgumentException("It is not allowed to get the value if it is not set yet. Use isNotNull() first. Value name: " + getName());
                }
                return value;
            }

            public boolean isNotNull() {
                return value != null;
            }

            public boolean isNull() {
                return value == null;
            }

            @Override
            public String toString() {
                return String.valueOf(get());
            }

            private String getName() {
                return name != null ? name : "<not set>";
            }

        };
    }
}
