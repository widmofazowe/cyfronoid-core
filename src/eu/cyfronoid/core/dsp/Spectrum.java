package eu.cyfronoid.core.dsp;

import eu.cyfronoid.core.util.Complex;

public class Spectrum {
    private double[] samples;
    private Complex[] spectrum;
    private int n;

    public Spectrum(double samples[], int n) {
        this.samples = samples;
        this.n = n;
        this.spectrum = new Complex[n];
    }

    private int fftSampleIndex(int n){
        int number = 0 ;
        int i = 0;
        for(i = n/2; i > 1; i /= 2) {
            number += (n&1) * i;
            n /= 2;
        }
        return number + n;
    }

    public void fft() {
        int i = 0, halfstep, k;
        int step = 0;
        Complex a = new Complex();
        Complex b= new Complex();
        int ai, bi;

        for(i = 0; i < n; i += 2) {
            spectrum[i].re = samples[fftSampleIndex(i)] + samples[fftSampleIndex(i+1)];
            spectrum[i+1].re = samples[fftSampleIndex(i)] - samples[fftSampleIndex(i+1)];
            spectrum[i].im = 0;
            spectrum[i+1].im = 0;
        }

        for(step = 4; step <= n; step *= 2) {
            halfstep = step/2;
            for(i = 0; i < n; i += step) {
                for(k = 0; k < halfstep; ++k) {
                    Complex x = new Complex();
                    ai = k + i;
                    bi = ai + halfstep;
                    a = spectrum[ai];
                    x = Complex.buildComplex(1, k*n/step);
                    b = spectrum[bi].multiply(x);
                    spectrum[ai] = a.add(b);
                    spectrum[bi] = a.substract(b);
                }
            }
        }
        halfstep = n/2;
        spectrum[0].divide(new Complex(n, 0));
        for(i = 1; i < halfstep; ++i) {
            spectrum[i].divide(new Complex(halfstep, 0));
        }
    }

    public Complex[] getSpectrum() {
        return spectrum;
    }
}


