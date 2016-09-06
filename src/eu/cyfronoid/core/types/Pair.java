package eu.cyfronoid.core.types;

import java.io.Serializable;

import com.google.common.base.Objects;

public class Pair<First, Second> implements Serializable {
    private static final long serialVersionUID = -7034892008727030334L;
    public final First first;
    public final Second second;

    public static <First, Second> Pair<First, Second> of(First first, Second second) {
        return new Pair<>(first, second);
    }

    protected Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(first, second);
    }

    @Override
    public boolean equals(Object p_obj) {
        if (this == p_obj) {
            return true;
        } else if (p_obj == null) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Pair<First, Second> other = (Pair<First, Second>) p_obj;
        return Objects.equal(first, other.first) && Objects.equal(second, other.second);
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(first) + ", " + String.valueOf(second) + ")";
    }

}
