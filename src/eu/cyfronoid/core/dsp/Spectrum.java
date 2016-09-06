package eu.cyfronoid.core.dsp;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.base.Preconditions;

import eu.cyfronoid.core.interceptor.annotation.MeasureTime;
import eu.cyfronoid.core.types.Complex;
import eu.cyfronoid.core.util.Maths;

public class Spectrum {
    private Complex[] spectrum;
    private int sampleSize;

    public Spectrum setSampleSize(int sampleSize) {
        Preconditions.checkArgument(Maths.isPowerOf2(sampleSize), "Argument should be a power of 2 (Argument:" + sampleSize + ").");
        this.sampleSize = sampleSize;
        spectrum = new Complex[sampleSize];
        for(int i = 0; i < spectrum.length; ++i) {
            spectrum[i] = new Complex();
        }
        return this;
    }

    public Spectrum setSamples(float[] samples) {
        double[] doubleArray = new double[samples.length];
        for (int i = 0 ; i < samples.length; i++) {
            doubleArray[i] = (float) samples[i];
        }
        return setSamples(doubleArray);
    }

    public Spectrum setSamples(double[] samples) {
        Preconditions.checkNotNull(sampleSize);
        Preconditions.checkArgument(samples.length <= sampleSize);
        for(int i = 0; i < samples.length; ++i) {
            spectrum[i] = new Complex(samples[i], 0.0d);
        }
        return this;
    }

    public Spectrum applyWindow(Window window) throws NotImplementedException {
        Preconditions.checkNotNull(window);
        windowing(window);
        return this;
    }

    private void windowing(Window window) throws NotImplementedException {
        for(int i = 0; i < spectrum.length; ++i) {
            spectrum[i] = spectrum[i].multiply(window.apply(i, sampleSize));
        }
    }

    @MeasureTime
    public void fft() {
        bitReversalPermutation();
        butterflyUpdates();
        normalize();
    }

    private void normalize() {
        for(int i = 0; i < sampleSize; ++i) {
            spectrum[i] = spectrum[i].divide(sampleSize);
        }
    }

    private void butterflyUpdates() {
        for (int L = 2; L <= sampleSize; L = L+L) {
            for (int k = 0; k < L/2; k++) {
                double kth = -2 * k * Math.PI / L;
                Complex w = new Complex(Math.cos(kth), Math.sin(kth));
                for (int j = 0; j < sampleSize/L; j++) {
                    Complex tao = w.multiply(spectrum[j*L + k + L/2]);
                    spectrum[j*L + k + L/2] = spectrum[j*L + k].substract(tao);
                    spectrum[j*L + k]       = spectrum[j*L + k].add(tao);
                }
            }
        }
    }

    private void bitReversalPermutation() {
        int shift = 1 + Integer.numberOfLeadingZeros(sampleSize);
        for (int k = 0; k < sampleSize; k++) {
            int j = Integer.reverse(k) >>> shift;
            if (j > k) {
                Complex temp = spectrum[j];
                spectrum[j] = spectrum[k];
                spectrum[k] = temp;
            }
        }
    }

    public Complex[] getSpectrum() {
        return spectrum;
    }
}


