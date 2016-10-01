import java.util.ArrayList;
import java.util.*;
import static java.lang.Math.*;

public class UnusualDarts {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        for (int a = 0; a < n; a++) {
            int[] arr = { 0, 1, 2, 3, 4, 5, 6 };
            int[] current = new int[7];
            boolean[] used = new boolean[7];
            ArrayList<int[]> perms = getPerm(arr, current, used, 0);

            P[] points = new P[7];
            for (int b = 0; b < 7; b++) {
                points[b] = new P(Math.round(input.nextDouble() * 1000), Math.round(input.nextDouble() * 1000));
            }
            double bobWin = input.nextDouble();
            double bobWinLowRange = bobWin - 1E-5;
            double bobWinHighRange = bobWin + 1E-5;
            bobWinLowRange = Math.pow(bobWinLowRange, 1 / 3.0);
            bobWinHighRange = Math.pow(bobWinHighRange, 1 / 3.0);
            double areaGoalLow = bobWinLowRange * 4000000;
            double areaGoalHigh = bobWinHighRange * 4000000;
            // System.out.println(areaGoal);
            for (int[] i : perms) {

                P[] temp = new P[7];
                for (int q = 0; q < 7; q++) {
                    temp[q] = points[i[q]];
                }
                Polygon p = new Polygon(temp);
                try {
                    double polyArea = p.absoluteArea();
                    if (areaGoalLow < polyArea && polyArea < areaGoalHigh) {
                        if (!selfIntersection(p)) {
                            for (int q2 = 0; q2 < 7; q2++) {
                                System.out.print((i[q2] + 1) + " ");
                            }
                            System.out.println();
                            break;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private static boolean selfIntersection(Polygon p) {
        for (int a = 0; a < 7; a++) {
            for (int b = a + 2; b < 7; b++) {
                if (a == 0 && b == 6) {

                } else {
                    Line i = new Line(p.p[a], p.p[(a + 1) % 7]);
                    Line i2 = new Line(p.p[b], p.p[(b + 1) % 7]);
                    if (i.intersectsInBounds(i2) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<int[]> getPerm(int[] input, int[] current, boolean[] used, int placed) {

        ArrayList<int[]> ans = new ArrayList<int[]>();

        // Terminating condition - array is filled
        if (placed == input.length) {

            // Be sure to clone - else you may end up editing your results later
            int[] toAdd = current.clone();

            ans.add(toAdd);
            return ans;
        }

        // Attempt to place & recurse
        for (int i = 0; i < input.length; i++) {
            if (!used[i]) {

                // Set up for next step
                current[placed] = input[i];
                used[i] = true;

                // Recurse
                ans.addAll(getPerm(input, current, used, placed + 1));

                // Return state to initial
                used[i] = false;

            }
        }
        return ans;
    }

    // GEOMETRY2016
    // P is point
    // Line
    // Circle
    static class P {
        final double x, y;

        P(double x, double y) {
            this.x = x;
            this.y = y;
        }

        P sub(P that) {
            return new P(x - that.x, y - that.y);
        }

        P add(P that) {
            return new P(x + that.x, y + that.y);
        }

        double dot(P that) {
            return x * that.x + y * that.y;
        }

        P mult(double s) {
            return new P(x * s, y * s);
        }

        // Use hypot() only if intermediate overflow must be avoided
        double length() {
            return sqrt(x * x + y * y);
        }

        double det(P that) {
            return this.x * that.y - this.y * that.x;
        }

        double crossproduct(P that) {
            return this.det(that);
        }

        double dist(P to) {
            return sub(to).length();
        }

        double signedParallelogramArea(P b, P c) {
            return (b.sub(this).crossproduct(c.sub(this)));
        }

        double signedTriangleArea(P b, P c) {
            return signedParallelogramArea(b, c) / 2.0;
        }
    }

    /*
     * The Line/Circle classes provide a number of methods that require dealing
     * with floating point precision issues. Adjust EPS to a suitable value,
     * such as 1e-4, which should work for many problems in which the input
     * coordinates are in integers.
     */
    static double EPS = 0.0;

    /*
     * A line denoted by two points p and q. For internal computations, we use
     * the parametric representation of the line as p + t d where d = q - p. For
     * convenience, we compute and store d in the constructor. Most methods hide
     * the parametric representation of the line, but it is exposed via
     * getPointFromParameter and intersectionParameters for those problems that
     * need it.
     *
     * The line may be interpreted either as a line segment denoted by the two
     * end points, or as the infinite line determined by these two points.
     * Intersection methods are provided for both cases.
     */
    static class Line {
        private P p, q, d;

        Line(P p, P q) {
            this.p = p;
            this.q = q;
            d = q.sub(p);
        }

        P getPointFromParameter(double t) {
            return p.add(d.mult(t));
        }

        /*
         * Point of intersection of this line segment with another line segment.
         * Returns only points that lie inside both line segments, else null.
         */
        P intersectsInBounds(Line l) {
            double[] st = intersectionParameters(l);
            if (st == null)
                return null;

            // check that point of intersection is in direction 'd'
            // and within segment bounds
            double s = st[0];
            double t = st[1];
            if (s >= 0 && s <= 1 && 0 <= t && t <= 1)
                return getPointFromParameter(s);

            return null;
        }

        /*
         * Intersect this line with that line Solves: this.p + s * this.d == l.p
         * + t l.d Return null if lines are collinear Else returns [s, t].
         */
        double[] intersectionParameters(Line l) {
            P dneg = p.sub(q);
            double D = l.d.det(dneg);
            // Use Cramer's rule; see text
            if (D == 0.0)
                return null;

            P rp = p.sub(l.p);
            return new double[] { l.d.det(rp) / D, rp.det(dneg) / D };
        }
    }

  
    /**
     * Some basic operations on Polygons.
     */
    static class Polygon {
        P[] p; // open form, p[0] connects to p[n-1]

        // Constructors clone original array/collection
        Polygon(P[] p) {
            this.p = (P[]) p.clone();
        }

        /* Absolute of signed triangle areas */
        double signedArea() {
            double area = 0.0;
            for (int i = 0; i < p.length; i++) {
                area += p[i].det(p[(i + 1) % p.length]);
            }
            return area / 2.0;
        }

        double absoluteArea() {
            return abs(signedArea());
        }
    }
}