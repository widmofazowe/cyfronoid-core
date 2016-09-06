package eu.cyfronoid.core.audio.builder;

import javax.sound.sampled.AudioFormat;

import eu.cyfronoid.core.builder.BuilderInterface;

public interface AttrEndian {
    public BuilderInterface<AudioFormat> bigEndian();
    public BuilderInterface<AudioFormat> littleEndian();
}
