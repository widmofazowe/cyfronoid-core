package eu.cyfronoid.core.dsp;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import org.apache.commons.lang3.NotImplementedException;

public enum Window {

    RECTANGULAR {
        @Override
        public double apply(int sampleNumber, int N) {
            return 1;
        }
    },
    HANNING {
        @Override
        public double apply(int sampleNumber, int N) {
            return 0.5*(1 - cos(2*PI*sampleNumber/(N)));
        }
    },
    HAMMING {
        @Override
        public double apply(int sampleNumber, int N) {
            return (WINDOWING_HAMMING + (1-WINDOWING_HAMMING)*cos(2*PI*sampleNumber/(N)));
        }
    },
    BLACKMAN {
        @Override
        public double apply(int sampleNumber, int N) {
            return WINDOWING_BLACKMAN_A0+WINDOWING_BLACKMAN_A1*cos(2*PI*sampleNumber/(N))+WINDOWING_BLACKMAN_A2*cos(4*PI*sampleNumber/(N));
        }
    },
    TRIANGULAR {
        @Override
        public double apply(int sampleNumber, int N) {
            return 1-abs(sampleNumber-(N/2))/(N/2);
        }
    },
    COSINE {
        @Override
        public double apply(int sampleNumber, int N) {
            return pow(cos(PI*sampleNumber/(N)), WINDOWING_COSINE);
        }
    },
    SINE {
        @Override
        public double apply(int sampleNumber, int N) {
            return pow(cos(PI*sampleNumber/(N)), WINDOWING_COSINE);
        }
    },
    LANCZOS {
        @Override
        public double apply(int sampleNumber, int N) {
            double x = 2*PI*sampleNumber/N-1;
            return sin(x)/x;
        }
    },
    GAUSSIAN {
        @Override
        public double apply(int sampleNumber, int N) {
            double x = WINDOWING_GAUSSIAN*sampleNumber/(N/2);
            return exp(-0.5*x*x);
        }
    },
    CAUCHY {
        @Override
        public double apply(int sampleNumber, int N) {
            double x = WINDOWING_CAUCHY*abs((sampleNumber-(N/2)))/(N/2);
            return 1/(1+x*x);
        }
    },
    POISSON {
        @Override
        public double apply(int sampleNumber, int N) {
            return exp(-WINDOWING_POISSON*abs((sampleNumber-N/2))/(N/2));
        }
    },
    HANN_POISSON {
        @Override
        public double apply(int sampleNumber, int N) {
            return 0.5*(1+cos(PI*sampleNumber/(N/2)))*exp(-WINDOWING_HANNPOISSON*abs((sampleNumber-N/2))/(N/2));
        }
    },
    BOHMAN {
        @Override
        public double apply(int sampleNumber, int N) {
            double x = abs((sampleNumber-(N/2)))/(N/2);
            return (1-x)*cos(PI*x)+sin(PI*x)/PI;
        }
    },
    RIEMANN {
        @Override
        public double apply(int sampleNumber, int N) {
            double x = abs((sampleNumber-(N/2)));
            return (sampleNumber == N/2) ? 1 : sin(2*PI*x/(2*PI*x)/N);
        }
    },
    PARABOLIC {
        @Override
        public double apply(int sampleNumber, int N) {
            double x = abs((sampleNumber-(N/2)))/(N/2);
            return 1-x*x;
        }
    }
    ;

    final private static double WINDOWING_HAMMING = 0.54;
    final private static double WINDOWING_BLACKMAN_A0 = 0.42659071367;
    final private static double WINDOWING_BLACKMAN_A1 = 0.49656061909;
    final private static double WINDOWING_BLACKMAN_A2 = 0.07684866724;
    final private static double WINDOWING_GAUSSIAN = 2.5;
    final private static double WINDOWING_CAUCHY = 3.0;
    final private static double WINDOWING_POISSON = 1.5;
    final private static double WINDOWING_HANNPOISSON = 0.5;
    final private static double WINDOWING_COSINE = 1;

    public double apply(int sampleNumber, int N) throws NotImplementedException {
        throw new NotImplementedException("Method apply in enum " + Window.class.getSimpleName() + " must be implemented for each window.");
    }

    public float[] apply(float[] samples, int sampleSize) throws NotImplementedException {
        float[] windowed = samples.clone();
        for(int i = 0; i < windowed.length; ++i) {
            windowed[i] *= apply(i, sampleSize);
        }
        return windowed;
    }

    public double[] apply(double[] samples, int sampleSize) throws NotImplementedException {
        double[] windowed = samples.clone();
        for(int i = 0; i < windowed.length; ++i) {
            windowed[i] *= apply(i, sampleSize);
        }
        return windowed;
    }

}

