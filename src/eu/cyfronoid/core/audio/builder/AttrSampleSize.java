package eu.cyfronoid.core.audio.builder;

public interface AttrSampleSize {
    public AttrChannels withSampleSize(SampleSize sampleSize);
    public AttrChannels eightBits();
    public AttrChannels sixteenBits();

    public static enum SampleSize {
        _8(8),
        _16(16)
        ;

        public final int value;

        SampleSize(int size) {
            value = size;
        }
    }
}
