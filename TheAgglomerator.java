import java.util.*;
import java.math.*;
import java.io.*;

import static java.lang.Math.*;
//https://open.kattis.com/contests/na16warmup1/problems/agglomerator
public class TheAgglomerator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        ArrayList<P> arr = new ArrayList<P>();
        ArrayList<P> velocity = new ArrayList<P>();
        ArrayList<Double> radius = new ArrayList<Double>();
        for (int a = 0; a < N; a++) {
            arr.add(new P(input.nextInt(), input.nextInt()));
            velocity.add(new P(input.nextInt(), input.nextInt()));
            radius.add(0.0 + input.nextInt());
        }
        double time = 0;
        double prevTime = 0;
        while (true) {
            prevTime = time;
            int prev = arr.size();
            time = merge(arr, velocity, radius, time);
            if(arr.size() == prev){
                break;
            }
        }
        System.out.print(arr.size() + " ");
        System.out.println(prevTime);
    }

    private static double merge(ArrayList<P> arr, ArrayList<P> velocity, ArrayList<Double> radius, double time) {
        double bestTime = Double.MAX_VALUE;
        int idx1 = -1;
        int idx2 = -1;
        for (int a = 0; a < arr.size(); a++) {
            for (int b = a + 1; b < arr.size(); b++) {
                P p1 = arr.get(a);
                P p2 = arr.get(b);
                P velocity1 = velocity.get(a);
                P velocity2 = velocity.get(b);
                double timeMinDist = ternarySearch(p1, velocity1, p2, velocity2, time, Integer.MAX_VALUE, time);
                //System.out.println(a + " " + b + " " + timeMinDist);
                if (timeMinDist < 1100000000) {
                    double hit = binarySearch(p1, velocity1, p2, velocity2, time, timeMinDist,
                            radius.get(a) + radius.get(b));
                    if (Math.abs(hit - timeMinDist) > Math.ulp(hit) * 10000) {
                        if (hit < bestTime) {
                            bestTime = hit;
                            idx1 = a;
                            idx2 = b;
                        }
                    }
                }
            }
        }
        if (idx2 != -1) {

            double mass1 = radius.get(idx1) * radius.get(idx1);
            double mass2 = radius.get(idx2) * radius.get(idx2);
            double xVal = (arr.get(idx1).x * mass1 + arr.get(idx2).x * mass2) / (mass1 + mass2);
            double yVal = (arr.get(idx1).y * mass1 + arr.get(idx2).y * mass2) / (mass1 + mass2);
            double xVelocity = (velocity.get(idx1).x * mass1 + velocity.get(idx2).x * mass2) / (mass1 + mass2);
            double yVelocity = (velocity.get(idx1).y * mass1 + velocity.get(idx2).y * mass2) / (mass1 + mass2);
            double rad = Math.sqrt(mass1 + mass2);
            arr.remove(idx2);
            velocity.remove(idx2);
            radius.remove(idx2);
            arr.remove(idx1);
            velocity.remove(idx1);
            radius.remove(idx1);
            arr.add(new P(xVal, yVal));
            velocity.add(new P(xVelocity, yVelocity));
            radius.add(rad);

        }
        return bestTime;
    }

    public static double binarySearch(P start, P velocity, P start2, P velocity2, double timeS, double timeE,
            double distTarget) {
        if (Math.abs(timeE - timeS) < Math.ulp(timeS) * 1000) {
            return timeS;
        }
        double mid = (timeS + timeE) / 2.0;
        if (eval(start, velocity, start2, velocity2, mid) < distTarget) {
            return binarySearch(start, velocity, start2, velocity2, timeS, mid, distTarget);
        } else {
            return binarySearch(start, velocity, start2, velocity2, mid, timeE, distTarget);
        }
    }

    public static double ternarySearch(P start, P velocity, P start2, P velocity2, double timeS, double timeE,
            double badTime) {
        //System.out.println(timeS + " " + timeE);
        if (Math.abs(timeE - timeS) < Math.ulp(timeS) * 1000) {
            if (Math.abs(badTime - timeS) < 1E-5) {
                return Integer.MAX_VALUE;
            } else {
                return timeS;
            }
        }
        double mid1 = (timeE - timeS) * 1 / 3.0 + timeS;
        double mid2 = (timeE - timeS) * 2 / 3.0 + timeS;
        double val1 = eval(start, velocity, start2, velocity2, mid1);
        double val2 = eval(start, velocity, start2, velocity2, mid2);
        //System.out.println(mid1 + ": DIST : " + val1);
        //System.out.println(mid2 + ": DIST : " + val2);
        if (val1 < val2) {
            return ternarySearch(start, velocity, start2, velocity2, timeS, mid2, badTime);
        } else {
            return ternarySearch(start, velocity, start2, velocity2, mid1, timeE, badTime);
        }

    }

    private static double eval(P start, P velocity, P start2, P velocity2, double mid2) {
        P point1 = new P(start.x + velocity.x * mid2, start.y + velocity.y * mid2);
        P point2 = new P(start2.x + velocity2.x * mid2, start2.y + velocity2.y * mid2);
        //System.out.println(point1 + " " + point2 + " " + mid2);
        return point1.dist(point2);
    }

    static class P {
        final double x, y;

        P(double x, double y) {
            this.x = x;
            this.y = y;
        }

        P sub(P that) {
            return new P(x - that.x, y - that.y);
        }

        // Use hypot() only if intermediate overflow must be avoided
        double length() {
            return sqrt(x * x + y * y);
        }

        public String toString() {
            return String.format("(%f,%f)", this.x, this.y);
        }

        double dist(P to) {
            return sub(to).length();
        }
    }
}
