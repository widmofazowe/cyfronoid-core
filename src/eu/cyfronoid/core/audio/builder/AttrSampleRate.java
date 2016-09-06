package eu.cyfronoid.core.audio.builder;

public interface AttrSampleRate {
    public AttrSampleSize withSampleRate(SampleRate sampleRate);
    public AttrSampleSize rate8000();
    public AttrSampleSize rate11025();
    public AttrSampleSize rate16000();
    public AttrSampleSize rate22050();
    public AttrSampleSize rate44100();

    public static enum SampleRate {
        _8000(8000.0f),
        _11025(11025.0f),
        _16000(16000.0f),
        _22050(22050.0f),
        _44100(44100.0f),
        ;

        public final float value;

        SampleRate(float rate) {
            value = rate;
        }
    }
}
