package eu.cyfronoid.core.util;

import static java.lang.Math.*;

public class Complex {
    public double re;
    public double im;

    public Complex() {
        re = 0;
        im = 0;
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex cpx) {
        Complex tmp = new Complex();
        tmp.re = re + cpx.re;
        tmp.im = im + cpx.im;
        return tmp;
    }

    public Complex substract(Complex cpx) {
        Complex tmp = new Complex();
        tmp.re = re - cpx.re;
        tmp.im = im - cpx.im;
        return tmp;
    }

    public Complex multiply(Complex cpx) {
        Complex tmp = new Complex();
        tmp.re = re * cpx.re - im * cpx.im;
        tmp.im = re * cpx.im + cpx.re * im;
        return tmp;
    }

    public Complex divide(Complex cpx) {
        Complex tmp = new Complex();
        double tm = cpx.re * cpx.re + cpx.im * cpx.im;
        tmp.re = (re * cpx.re + im * cpx.im)/tm;
        tmp.im = (im * cpx.re - re * cpx.im)/tm;
        return tmp;
    }

    public double absolute() {
        return sqrt(re*re + im*im);
    }

    public double angle() {
        double tmp;
        if(im == 0) {
            return PI/2;
        }
        tmp = atan(im/re);
        if(re < 0) {
            tmp = (im > 0) ? tmp + PI : tmp - PI;
        }
        return tmp;
    }

    public Complex conjugate() {
        Complex tmp = new Complex();
        tmp.re = re;
        tmp.im = -im;
        return tmp;
    }

    public static double rad2deg(double x) {
        return x*180/PI;
    }

    public static double deg2rad(double x) {
        return x*PI/180;
    }

    public static Complex buildComplex(double mag, double angle) {
        Complex tmp = new Complex();
        tmp.re = mag*cos(angle);
        tmp.im = mag*sin(angle);
        return tmp;
    }

    public boolean equals(Complex cpx) {
        return (re == cpx.re && im == cpx.im);
    }
}




