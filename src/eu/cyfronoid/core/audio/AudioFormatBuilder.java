package eu.cyfronoid.core.audio;

import javax.sound.sampled.AudioFormat;

import eu.cyfronoid.core.audio.builder.AttrChannels;
import eu.cyfronoid.core.audio.builder.AttrEndian;
import eu.cyfronoid.core.audio.builder.AttrSampleRate;
import eu.cyfronoid.core.audio.builder.AttrSampleSize;
import eu.cyfronoid.core.audio.builder.AttrSigned;
import eu.cyfronoid.core.builder.BuilderInterface;

public class AudioFormatBuilder {
    public interface Order extends AttrSampleRate{}
    private interface Attributes extends AttrSampleSize, AttrChannels, AttrSigned, AttrEndian, BuilderInterface<AudioFormat>{}

    public static Order create() {
        return new Builder();
    }

    private static class Builder implements Order, Attributes {
        private int channels;
        private boolean bigEndian;
        private boolean signed;
        private int sampleSizeInBits;
        private float sampleRate;

        public AudioFormat build() {
            AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                                 channels, signed, bigEndian);
            return format;
        }

        @Override
        public AttrSampleSize withSampleRate(SampleRate sampleRate) {
            this.sampleRate = sampleRate.value;
            return this;
        }

        @Override
        public AttrChannels withSampleSize(SampleSize sampleSize) {
            sampleSizeInBits = sampleSize.value;
            return this;
        }

        @Override
        public AttrSigned oneChannel() {
            channels = 1;
            return this;
        }

        @Override
        public AttrSigned twoChannels() {
            channels = 2;
            return this;
        }

        @Override
        public AttrEndian signed() {
            signed = true;
            return this;
        }

        @Override
        public AttrEndian unsigned() {
            signed = false;
            return this;
        }

        @Override
        public BuilderInterface<AudioFormat> bigEndian() {
            bigEndian = true;
            return this;
        }

        @Override
        public BuilderInterface<AudioFormat> littleEndian() {
            bigEndian = false;
            return this;
        }

        @Override
        public AttrSampleSize rate8000() {
            this.sampleRate = SampleRate._8000.value;
            return this;
        }

        @Override
        public AttrSampleSize rate11025() {
            this.sampleRate = SampleRate._11025.value;
            return this;
        }

        @Override
        public AttrSampleSize rate16000() {
            this.sampleRate = SampleRate._16000.value;
            return this;
        }

        @Override
        public AttrSampleSize rate22050() {
            this.sampleRate = SampleRate._22050.value;
            return this;
        }

        @Override
        public AttrSampleSize rate44100() {
            this.sampleRate = SampleRate._44100.value;
            return this;
        }

        @Override
        public AttrChannels eightBits() {
            sampleSizeInBits = SampleSize._8.value;
            return this;
        }

        @Override
        public AttrChannels sixteenBits() {
            sampleSizeInBits = SampleSize._16.value;
            return this;
        }
    }
}
