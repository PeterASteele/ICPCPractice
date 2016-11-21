import java.util.*;
import java.math.*;
import java.io.*;

import static java.lang.Math.*;
//https://open.kattis.com/problems/aplusb
public class ABProblem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        double tempZeros = 0;
        int n = input.nextInt();
        double[] freqDist = new double[120000];
        for(int a = 0; a < n; a++){
            freqDist[input.nextInt()+60000]++;
        }
        tempZeros = freqDist[60000];
        freqDist[60000] = 0;
        Polynomial p = new Polynomial(freqDist);
        Polynomial p2 = p.fastMultiply(p);
        for(int a = 0; a < 120000; a++){
            p2.a[2*a] -= freqDist[a];
        }
        freqDist[60000] = tempZeros;
        long count = 0;
        for(int a = 0; a < 120000; a++){
            count += Math.round(p2.a[a+60000])*Math.round(freqDist[a]);
        }    
        
        for(int a = 0; a < 120000; a++){
            if(a != 60000){
                count += Math.round(freqDist[a])*Math.round((freqDist[a]-1))*Math.round(freqDist[60000])*2;
            }
        }
        count += Math.round(freqDist[60000])*Math.round(freqDist[60000]-1)*Math.round(freqDist[60000]-2);
        
        System.out.println(count);
        
        

    }

    static interface Func {
        double apply(double v);
    }

    static class Polynomial {
        double[] a; // coefficients a[0], a[1], ...
        int n; // degree + 1

        int degree() {
            return n - 1;
        }

        Polynomial(double[] a) {
            this.a = a.clone();
            this.n = a.length;
        }

        public String toString() {
            String s = "", sep = "";
            for (int k = 0; k < n; k++) {
                if (a[k] != 0) {
                    s += String.format("%s%f x^%d", sep, a[k], k);
                    sep = " + ";
                }
            }
            return s;
        }

        /**
         * Horner's method, O(n).
         */
        double apply(double x) {
            double y = a[n - 1];
            for (int k = n - 2; k >= 0; k--)
                y = y * x + a[k];

            return y;
        }

        Polynomial add(Polynomial b) {
            int l = max(n, b.n);
            double[] s = new double[l];
            int k = 0;
            for (k = 0; k < min(n, b.n); k++)
                s[k] = a[k] + b.a[k];

            while (k < n) {
                s[k] = a[k];
                k++;
            }
            while (k < b.n) {
                s[k] = b.a[k];
                k++;
            }
            return new Polynomial(s);
        }

        Polynomial scale(double c) {
            double[] s = new double[n];
            for (int k = 0; k < n; k++)
                s[k] = a[k] * c;
            return new Polynomial(s);
        }

        Polynomial square() {
            return multiply(this);
        }

        static Polynomial identity() {
            return new Polynomial(new double[] {
                    1.0
            });
        }

        /* O(n^2) multiplication. See fastMultiply() below */
        Polynomial multiply(Polynomial b) {
            double[] s = new double[n + b.n - 1];
            for (int k = 0; k < s.length; k++) {
                double v = 0;
                for (int i = 0; i < min(n, k + 1); i++) {
                    if (k - i < b.n) v += a[i] * b.a[k - i];
                }
                s[k] = v;
            }
            return new Polynomial(s);
        }

        Polynomial integrate() {
            double[] s = new double[n + 1];
            for (int k = 0; k < n; k++)
                s[k + 1] = a[k] / (k + 1);
            return new Polynomial(s);
        }

        Polynomial diff() {
            double[] s = new double[n - 1];
            for (int k = 1; k < n; k++)
                s[k - 1] = a[k] * k;
            return new Polynomial(s);
        }

        Polynomial multiplyByX() {
            double[] s = new double[n + 1];
            for (int k = 0; k < n; k++)
                s[k + 1] = a[k];
            return new Polynomial(s);
        }

        /**
         * Reconstruct a parabola from f by solving Vandermonde by hand.
         */
        static Polynomial reconstructParabola(final Func f) {
            double k = 1e6;
            double c = f.apply(0);
            double b = (f.apply(k) - f.apply(-k)) / 2 / k;
            double a = (f.apply(k) + f.apply(-k)) / 2 / k / k - c / k / k;
            return new Polynomial(new double[] {
                    c,b,a
            });
        }

        /*
         * Assuming f represents a polynomial of degree n, try to reconstruct
         * its coefficients. This is done by using Bjorck-Pereyra on the
         * Vandermonde matrix. As such, it is not very stable for k > 10. Also,
         * to avoid bad centering, we multiply by 1e6 and shift by k/2;
         */
        static Polynomial reconstruct(final Func f, int k) {
            double s = 1e6;
            double[] x = new double[k + 1];
            double[] y = new double[k + 1];

            for (int i = 0; i < k + 1; i++) {
                x[i] = (i - k / 2) * s;
                y[i] = f.apply(x[i]);
            }

            return dvand(x, y);
        }

        /*
         * Ake Bjorck, Victor Pereyra, Solution of Vandermonde Systems of
         * Equations, Mathematics of Computation, Volume 24, Number 112, October
         * 1970, pages 893-903.
         */
        static Polynomial dvand(double alpha[], double b[]) {
            int n = alpha.length;
            double[] x = b.clone();

            for (int k = 0; k < n - 1; k++)
                for (int j = n - 1; k < j; j--)
                    x[j] = (x[j] - x[j - 1]) / (alpha[j] - alpha[j - k - 1]);

            for (int k = n - 2; k >= 0; k--)
                for (int j = k; j < n - 1; j++)
                    x[j] = x[j] - alpha[k] * x[j + 1];

            return new Polynomial(x);
        }

        /*
         * find x such that f(x) = y within low <= x <= high Works only if p is
         * monotonic within low/high and if x exists.
         */
        double binarysearch(double y, double low, double high) {
            while (high - low > 10 * ulp(high)) {
                double mid = (low + high) / 2.0;
                double midVal = apply(mid);

                if (midVal < y) low = mid;
                else high = mid;
            }
            return (low + high) / 2.0;
        }

        static double EPS = 1e-7;

        /*
         * Find root using Newton's method starting from x. Newton's method can
         * go wrong in a number of ways, including via stationary cycles, which
         * this method does not handle.
         */
        double findRoot(double x) {
            Polynomial Fd = diff();
            while (true) {
                double Fd_x = Fd.apply(x);
                if (abs(Fd_x) < EPS) { // nudge if f'(x) near 0
                    x -= 1e-5;
                    continue;
                }

                double x1 = x - apply(x) / Fd_x;
                if (abs(x1 - x) < EPS) break;
                x = x1;
            }
            return x;
        }

        /* Helper return next higher power of 2 of n */
        int nextHigherPower2() {
            int p2 = Integer.highestOneBit(n);
            if (n > p2) p2 *= 2;
            return p2;
        }

        /**
         * Multiplication via FFT. O(n log n)
         */
        Polynomial fastMultiply(Polynomial that) {
            // extend to next higher power of 2 as required by radix-2 FFT
            int need = max(this.nextHigherPower2(), that.nextHigherPower2());
            double[] re0 = Arrays.copyOf(this.a, need);
            double[] im0 = new double[need];
            double[] re1 = Arrays.copyOf(that.a, need);
            double[] im1 = new double[need];
            double[][] c = FFT.convolve(re0, im0, re1, im1);

            return new Polynomial(Arrays.copyOfRange(c[0], 0, this.degree() + that.degree() + 1));
        }
    }

    public static class FFT {
        int n, m; // 1<<m == n

        // Lookup tables. Only need to recompute when FFT size changes
        double[] cos;
        double[] sin;

        public FFT(int n) {
            this.n = n;
            this.m = (int) (Math.log(n) / Math.log(2));

            // Make sure n is a power of 2
            if (n != (1 << m)) throw new RuntimeException("FFT length must be power of 2");

            // precompute tables
            cos = new double[n / 2];
            sin = new double[n / 2];

            for (int i = 0; i < n / 2; i++) {
                cos[i] = cos(-2 * PI * i / n);
                sin[i] = sin(-2 * PI * i / n);
            }
        }

        /***********************************************************
         * fft.c Douglas L. Jones University of Illinois at Urbana-Champaign
         * January 19, 1992 http://cnx.rice.edu/content/m12016/latest/
         *
         * fft: in-place radix-2 DIT DFT of a complex input
         *
         * input: n: length of FFT: must be a power of two m: n = 2**m
         * input/output x: double array of length n with real part of data y:
         * double array of length n with imag part of data
         *
         * Permission to copy and use this program is granted as long as this
         * header is included.
         **********************************************************/
        public void fft(double[] x, double[] y) {
            int n1, n2;
            double t1, t2;

            // Bit-reverse
            int j = 0;
            n2 = n / 2;
            for (int i = 1; i < n - 1; i++) {
                n1 = n2;
                while (j >= n1) {
                    j = j - n1;
                    n1 = n1 / 2;
                }
                j = j + n1;

                if (i < j) {
                    t1 = x[i];
                    x[i] = x[j];
                    x[j] = t1;
                    t1 = y[i];
                    y[i] = y[j];
                    y[j] = t1;
                }
            }

            // FFT
            n1 = 0;
            n2 = 1;

            for (int i = 0; i < m; i++) {
                n1 = n2;
                n2 = n2 + n2;
                int a = 0;

                for (j = 0; j < n1; j++) {
                    double c = cos[a];
                    double s = sin[a];
                    a += 1 << (m - i - 1);

                    for (int k = j; k < n; k += n2) {
                        t1 = c * x[k + n1] - s * y[k + n1];
                        t2 = s * x[k + n1] + c * y[k + n1];
                        x[k + n1] = x[k] - t1;
                        y[k + n1] = y[k] - t2;
                        x[k] = x[k] + t1;
                        y[k] = y[k] + t2;
                    }
                }
            }
        }

        // inverse FFT, in-place
        public void ifft(double[] x, double[] y) {
            int N = x.length;
            double[] re = x.clone();
            double[] im = y.clone();

            // take conjugate
            for (int i = 0; i < N; i++)
                im[i] = -im[i];

            // compute forward FFT
            fft(re, im);

            // take conjugate again and divide by N
            for (int i = 0; i < N; i++) {
                x[i] = re[i] / N;
                y[i] = -im[i] / N;
            }
        }

        // compute the linear convolution of x & y, output in cre/cim
        // assume xre.length==yre.length & xre.length is a power of 2
        // @returns r[0] - real parts, r[1] - imaginary parts
        static double[][] convolve(double[] xre, double[] xim, double[] yre, double[] yim) {
            // extend to length 2n and pad with zeros
            double[] xre2 = Arrays.copyOf(xre, 2 * xre.length);
            double[] xim2 = Arrays.copyOf(xim, 2 * xim.length);
            double[] yre2 = Arrays.copyOf(yre, 2 * yre.length);
            double[] yim2 = Arrays.copyOf(yim, 2 * yim.length);

            // compute the circular convolution of x and y
            int N = xre2.length;
            FFT fft = new FFT(N);

            // compute FFT of each sequence in place
            fft.fft(xre2, xim2);
            fft.fft(yre2, yim2);

            double[][] r = new double[2][2 * xre.length];
            double[] cre = r[0]; // alias
            double[] cim = r[1]; // alias
            // point-wise multiply
            for (int i = 0; i < N; i++) {
                cre[i] = xre2[i] * yre2[i] - xim2[i] * yim2[i];
                cim[i] = xre2[i] * yim2[i] + xim2[i] * yre2[i];
            }

            // compute inverse FFT in place
            fft.ifft(cre, cim);
            return r;
        }
    }
}
