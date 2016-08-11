package eu.cyfronoid.core.dsp;

import static java.lang.Math.*;

public class Windowing {
    final private static double WINDOWING_HAMMING = 0.54;
    final private static double WINDOWING_BLACKMAN_A0 = 0.42659071367;
    final private static double WINDOWING_BLACKMAN_A1 = 0.49656061909;
    final private static double WINDOWING_BLACKMAN_A2 = 0.07684866724;
    final private static double WINDOWING_GAUSSIAN = 2.5;
    final private static double WINDOWING_CAUCHY = 3.0;
    final private static double WINDOWING_POISSON = 1.5;
    final private static double WINDOWING_HANNPOISSON = 0.5;
    final private static double WINDOWING_COSINE = 1;

    public static double rectangular(int n, int N) {
        return 1;
    }

    public static double hanning(int n, int N) {
        return 0.5*(1 - cos(2*PI*n/(N)));
    }

    public static double hamming(int n, int N) {
        return (WINDOWING_HAMMING + (1-WINDOWING_HAMMING)*cos(2*PI*n/(N)));
    }

    public static double blackman(int n, int N) {
        return WINDOWING_BLACKMAN_A0+WINDOWING_BLACKMAN_A1*cos(2*PI*n/(N))+WINDOWING_BLACKMAN_A2*cos(4*PI*n/(N));
    }

    public static double triangular(int n, int N) {
        return 1-abs(n-(N/2))/(N/2);
    }

    public static double cosine(int n, int N) {
        return pow(cos(PI*n/(N)), WINDOWING_COSINE);
    }

    public static double sine(int n, int N) {
        return cosine(n, N);
    }

    public static double lanczos(int n, int N) {
        double x = 2*PI*n/N-1;
        return sin(x)/x;
    }

    public static double gaussian(int n, int N) {
        double x = WINDOWING_GAUSSIAN*n/(N/2);
        return exp(-0.5*x*x);
    }

    public static double cauchy(int n, int N) {
        double x = WINDOWING_CAUCHY*abs((n-(N/2)))/(N/2);
        return 1/(1+x*x);
    }

    public static double poisson(int n, int N) {
        return exp(-WINDOWING_POISSON*abs((n-N/2))/(N/2));
    }

    public static double hann_poisson(int n, int N) {
        return 0.5*(1+cos(PI*n/(N/2)))*exp(-WINDOWING_HANNPOISSON*abs((n-N/2))/(N/2));
    }

    public static double bohman(int n, int N) {
        double x = abs((n-(N/2)))/(N/2);
        return (1-x)*cos(PI*x)+sin(PI*x)/PI;
    }

    public static double riemann(int n, int N) {
        double x = abs((n-(N/2)));
        return (n == N/2) ? 1 : sin(2*PI*x/(2*PI*x)/N);
    }

    public static double parabolic(int n, int N) {
        double x = abs((n-(N/2)))/(N/2);
        return 1-x*x;
    }
}


