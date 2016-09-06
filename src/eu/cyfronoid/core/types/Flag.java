package eu.cyfronoid.core.types;

public class Flag {
    private int flag;

    public Flag() {
        this(0x0);
    }

    public Flag(final int mask) {
        super();
        this.flag = mask;
    }

    public void set(int mask) {
        flag = flag | mask;
    }

    public void unset(int mask) {
        flag &= ~mask;
    }

    public boolean isSet(int mask) {
        return (flag & mask) == mask;
    }

    public int flagValue() {
        return flag;
    }

}
