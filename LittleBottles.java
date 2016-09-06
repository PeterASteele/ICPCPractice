import java.util.*;
import java.math.*;
import java.io.*;

//ICPC World Finals 2012 Problem B (practice)
//https://icpc.kattis.com/problems/bottles


public class LittleBottles {

    public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        // FastScanner sc = new FastScanner();
        int CASE = 0;
        while (input.hasNextInt()) {
            CASE++;
            int n = input.nextInt();
            double[] coefficients = new double[n + 1];
            for (int a = 0; a < n + 1; a++) {
                coefficients[a] = input.nextDouble();
            }
            double[] integral = integratePoly(squarePolyMultiplyPi(coefficients));
            double xLow = input.nextDouble();
            double xHigh = input.nextDouble();
            double volumeInc = input.nextDouble();
            // System.out.println(xLow + " " + xHigh + " " + volumeInc);
            double position = xLow;
            double volume = 0.0;
            ArrayList<Double> markedVolumes = new ArrayList<Double>();
            double delta = (xHigh - xLow) / 100000.0;
            while (position <= xHigh) {
                double lowerBound = position;
                double upperBound = 1000;
                while (upperBound - lowerBound > Math.ulp((upperBound + lowerBound) / 2) * 100) {
                    double test = (lowerBound + upperBound) / 2;
                    double bottom = evaluatePoly(integral, position);
                    double top = evaluatePoly(integral, test);
                    double vol = top - bottom;
                    if (vol > volumeInc) {
                        upperBound = test;
                    } else {
                        lowerBound = test;
                    }
                }
                volume += volumeInc;
                position = (upperBound + lowerBound) / 2;
                if (position <= xHigh) markedVolumes.add(position);
            }
            double totalVolume = evaluatePoly(integral, xHigh) - evaluatePoly(integral, xLow);
            System.out.println("Case " + CASE + ": " + String.format("%.2f", totalVolume));
            if (markedVolumes.size() == 0) {
                System.out.println("insufficient volume");
            } else {
                for (int a = 0; a < Math.min(8, markedVolumes.size()); a++) {
                    System.out.print(String.format("%.2f", markedVolumes.get(a)-xLow));
                    if (a != Math.min(8, markedVolumes.size()) - 1) {
                        System.out.print(" ");
                    }

                }
                System.out.println();
            }
        }
       
    }

    public static double[] squarePolyMultiplyPi(double[] coefficients) {
        double[] out = new double[coefficients.length * 2 - 1];
        for (int a = 0; a < coefficients.length; a++) {
            for (int b = 0; b < coefficients.length; b++) {
                out[a + b] += coefficients[a] * coefficients[b] * Math.PI;
            }
        }
        return out;

    }

    public static double[] integratePoly(double[] coefficients) {
        double[] newPoly = new double[coefficients.length + 1];
        for (int a = 1; a <= coefficients.length; a++) {
            newPoly[a] = coefficients[a - 1] / a;
        }
        return newPoly;
    }

    public static double evaluatePoly(double[] coefficients, double x) {
        double out = 0;
        for (int a = 0; a < coefficients.length; a++) {
            out += coefficients[a] * Math.pow(x, a);
        }
        return out;
    }

    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(Reader in) {
            br = new BufferedReader(in);
        }

        public FastScanner() {
            this(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        // Slightly different from java.util.Scanner.nextLine(),
        // which returns any remaining characters in current line,
        // if any.
        String readNextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}