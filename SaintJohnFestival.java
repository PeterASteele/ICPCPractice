import java.util.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;
//https://open.kattis.com/problems/saintjohn
public class SaintJohnFestival {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int L = input.nextInt();
        P[] arr = new P[L];
        for (int a = 0; a < L; a++) {
            arr[a] = new P(input.nextInt(), input.nextInt());
        }
        Polygon poly = new Polygon(arr);
        poly = poly.convexHull();

        P arbitrary = poly.p[0];

        TreeMap<Double, Integer> polyTree = new TreeMap<Double, Integer>();
        for (int a = 1; a < poly.p.length; a++) {
            P other = poly.p[a];
            P vectory = other.sub(arbitrary);
            double yDiff = vectory.y;
            double xDiff = vectory.x;
            double angle = Math.atan2(yDiff, xDiff)*180/Math.PI;
            polyTree.put(angle, a);
        }
//        System.out.println(poly);
//        System.out.println(polyTree);
        int small = input.nextInt();
        int count = 0;
        for (int a = 0; a < small; a++) {
            P point = new P(input.nextInt(), input.nextInt());
            P vectory = point.sub(arbitrary);
            double yDiff = vectory.y;
            double xDiff = vectory.x;
            double angle = Math.atan2(yDiff, xDiff)*180/Math.PI;
            ArrayList<Integer> possibilities = new ArrayList();
            if(polyTree.lowerEntry(angle) != null){
                possibilities.add(polyTree.lowerEntry(angle).getValue());
            }
            if(polyTree.higherEntry(angle) != null){
                possibilities.add(polyTree.higherEntry(angle).getValue());
            }
            if(polyTree.firstEntry() != null){
                possibilities.add(polyTree.firstEntry().getValue());
            }
            if(polyTree.lastEntry() != null){
                possibilities.add(polyTree.lastEntry().getValue());
            }
            ArrayList<Line> possibleIntersectionLines = new ArrayList<Line>();
            for(Integer i:possibilities){
                int above = (i+1)%poly.p.length;
                int below = (i-1+poly.p.length)%poly.p.length;
                if(i != 0 && below != 0){
                    possibleIntersectionLines.add(new Line(poly.p[below], poly.p[i]));
                }
                if(i != 0 && above != 0){
                    possibleIntersectionLines.add(new Line(poly.p[i], poly.p[above]));
                }
            }
            boolean OK = false;
            for(Line i:possibleIntersectionLines){
                Line longLine = new Line(arbitrary, arbitrary.add(vectory.scale(10000000000.0)));
//                System.out.println(longLine);
                if(i.intersectsInBounds(longLine) != null){
                    Line shortLine = new Line(arbitrary.add(vectory.mult(.0000000001)), arbitrary.add(vectory.mult(0.9999999999)));
                    if(i.intersectsInBounds(shortLine) == null){
                        OK = true;
                    }
                    else{
//                        System.out.println(i + " " + shortLine);
                    }
//                    System.out.println("INTERSECTION");
                }
                else{
//                    System.out.println(i + " " + longLine);
                }
            }
            if(OK){
                count++;
            }
        }
        System.out.println(count);
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

        double length2() {
            return x * x + y * y;
        }

        P leftNormal() {
            return new P(-y, x);
        } // rotateCCW(90deg)

        P rightNormal() {
            return new P(y, -x);
        } // rotateCW(90deg)

        P normalize() {
            double n = length();
            return n > 0 ? new P(x / n, y / n) : origin();
        }

        // Scale will scale the vector to the given magnitude. To scale by a
        // factor, use the mult method.
        P scale(double l) {
            return normalize().mult(l);
        }

        P project(P a) {
            return mult(a.dot(this) / length2());
        }

        P reflect(P a) {
            return project(a).mult(2.0).sub(a);
        }

        // use if sin, cos are known
        P rotateCCW(double sinT, double cosT) {
            return new P(x * cosT - y * sinT, x * sinT + y * cosT);
        }

        P rotateCW(double sinT, double cosT) {
            return rotateCCW(-sinT, cosT);
        }

        P rotate(double theta) {
            return rotateCCW(sin(theta), cos(theta));
        }

        double theta() {
            return atan2(y, x);
        } // from -pi to +pi

        double angleTo(P a) {
            return acos(this.dot(a) / this.length() / a.length());
        }

        boolean isOrigin() {
            return x == 0 && y == 0;
        }

        public String toString() {
            return String.format("(%f,%f)", this.x, this.y);
        }

        static P read(Scanner s) {
            return new P(s.nextDouble(), s.nextDouble());
        }

        static P origin() {
            return new P(0, 0);
        }

        double det(P that) {
            return this.x * that.y - this.y * that.x;
        }

        double crossproduct(P that) {
            return this.det(that);
        }

        P half(P q) {
            return normalize().add(q.normalize());
        }

        double dist(P to) {
            return sub(to).length();
        }

        double signedParallelogramArea(P b, P c) {
            return (b.sub(this).crossproduct(c.sub(this)));
        }

        boolean isCollinearWith(P b, P c) {
            return abs(signedParallelogramArea(b, c)) <= EPS;
        }

        // is going from this to b to c a CCW turn? Do not use if points may be
        // collinear
        boolean isCCW(P b, P c) {
            return signedParallelogramArea(b, c) > 0;
        }

        double signedTriangleArea(P b, P c) {
            return signedParallelogramArea(b, c) / 2.0;
        }

        // memory-optimized version of this.sub(to).length2() that avoids an
        // intermediate object
        double dist2(P to) {
            double dx = this.x - to.x;
            double dy = this.y - to.y;
            return dx * dx + dy * dy;
        }

        /**
         * Compute x for a * x + b = 0 and ||x|| = C where 'this' is a. Care
         * must be taken to handle the case where either a.x or a.y is near
         * zero.
         */
        P[] solveDotProductConstrainedByNorm(double b, double C) {
            P a = this;
            if (a.isOrigin()) throw new Error("degenerate case");

            boolean transpose = abs(a.x) > abs(a.y);
            a = transpose ? new P(a.y, a.x) : a;

            Double[] x = solvequadratic(a.length2(), 2.0 * b * a.x, b * b - a.y * a.y * C * C);
            P[] p = new P[x.length];
            for (int i = 0; i < x.length; i++) {
                double x1 = x[i];
                double x2 = ((-b - a.x * x1) / a.y);
                p[i] = transpose ? new P(x2, x1) : new P(x1, x2);
            }
            return p;
        }
    }

    static class HP extends P { // Hashable Point
        HP(double x, double y) {
            super(x, y);
        }

        @Override
        public int hashCode() {
            return Double.hashCode(x + 32768 * y);
        }

        @Override
        public boolean equals(Object _that) {
            HP that = (HP) _that;
            return this.x == that.x && this.y == that.y;
        }
    }

    /**
     * Sort points by polar angle relative to center, using trig. This is a
     * counter-clockwise sort with zero at 3 o'clock.
     */
    static Comparator<P> makePolarAngleComparatorTrig(final P center) {
        return new Comparator<P>() {
            public int compare(P a, P b) {
                double thetaa = a.sub(center).theta();
                double thetab = b.sub(center).theta();
                if (thetaa < 0) thetaa += 2 * PI;
                if (thetab < 0) thetab += 2 * PI;
                int c = Double.compare(thetaa, thetab);
                if (c != 0) return c;
                return Double.compare(b.x, a.x); // arbitrary tie-breaker
            }
        };
    }

    /**
     * Sort points by polar angle relative to center, w/o trig. This is a
     * counter-clockwise sort with zero at 3 o'clock.
     */
    static Comparator<P> makePolarAngleComparator(final P center) {
        return new Comparator<P>() {
            public int compare(P a, P b) {
                // orientation() requires that a and b lie in the same
                // half-plane
                if (a.y >= center.y && b.y < center.y) return -1;
                if (b.y >= center.y && a.y < center.y) return 1;
                int orientation = (int) Math.signum(center.signedParallelogramArea(b, a));
                if (orientation != 0) return orientation;
                return Double.compare(b.x, a.x); // arbitrary tie-breaker
            }
        };
    }

    /*
     * Solve a * x^2 + b * x + c == 0 Returns 0, 1, or 2 solutions. If 2
     * solutions x1, x2, guarantees that x1 < x2
     */
    static Double[] solvequadratic(double a, double b, double c) {
        double D = b * b - 4 * a * c;
        if (D < -EPS) return new Double[] {};
        D = max(D, 0);
        if (D == 0) return new Double[] {
                -b / 2.0 / a
        };
        double d = sqrt(D);
        // Numerically more stable, see
        // https://en.wikipedia.org/wiki/Loss_of_significance#A_better_algorithm
        if (signum(b) == 0) return new Double[] {
                d / 2.0 / a,-d / 2.0 / a
        };
        double x1 = (-b - signum(b) * d) / (2 * a);
        double x2 = c / (a * x1);
        return new Double[] {
                Math.min(x1, x2),Math.max(x1, x2)
        };
    }

    /*
     * The Line/Circle classes provide a number of methods that require dealing
     * with floating point precision issues. Default EPS to a suitable value,
     * such as 1e-6, which should work for many problems in which the input
     * coordinates are in integers and subsequently inexact floating point
     * values are being computed.
     */
    static double EPS = 1e-13;

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
        P p, q, d;

        Line(P p, P q) {
            this.p = p;
            this.q = q;
            d = q.sub(p);
        }

        P getPointFromParameter(double t) {
            return p.add(d.mult(t));
        }

        // reflect vector across vector (as if line originated at (0, 0))
        P reflect(P d2) {
            return d.reflect(d2);
        }

        // reflect point across (infinite) line
        P reflectPoint(P r) {
            return reflect(r.sub(p)).add(p);
        }

        // project p onto this (infinite) line. Returns point on line
        P project(P a) {
            return p.add(d.project(a.sub(p)));
        }

        // return distance of point P from this (infinite) line.
        double distance(P a) {
            return project(a).dist(a);
        }

        @Override
        public String toString() {
            return String.format("[%s => %s]", p, q);
        }

        /*
         * Point of intersection of this line segment with another line segment.
         * Returns only points that lie inside both line segments, else null.
         *
         * Result may include points "just outside" the bounds, given EPS.
         */
        P intersectsInBounds(Line l) {
            double[] st = intersectionParameters(l);
            if (st == null) return null;

            // check that point of intersection is in direction 'd'
            // and within segment bounds
            double s = st[0];
            double t = st[1];
            if (s >= -EPS && s <= 1 + EPS && -EPS <= t && t <= 1 + EPS) return getPointFromParameter(s);

            return null;
        }

        /*
         * Point of intersection of this (infinite) line with other (infinite)
         * line. Return null if collinear.
         */
        P intersects(Line l) {
            double[] st = intersectionParameters(l);
            if (st != null) return getPointFromParameter(st[0]);
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
            if (D == 0.0) return null;

            P rp = p.sub(l.p);
            return new double[] {
                    l.d.det(rp) / D,rp.det(dneg) / D
            };
        }

        /*
         * Compute points of intersection of this infinite line with a circle.
         * Computes projection 'x' of c.c onto line, then computes x +/-
         * d.scale(h) where h is computed via Pythagoras. Sorted by decreasing
         * 't'
         *
         * May return two points even if line is a tangent.
         */
        P[] intersectsCircle(Circle c) {
            P x = project(c.c);
            double D = x.dist(c.c);
            // outside by more than EPS
            if (D > c.R + EPS) return new P[0];
            double h = sqrt(max(0, c.R * c.R - D * D));
            if (h == 0) return new P[] {
                    x
            }; // EPS (!?)
            return new P[] {
                    x.add(d.scale(h)),x.add(d.scale(-h))
            };
        }

        /*
         * Compute points of intersection of this infinite line with a circle.
         *
         * Solves a + t * b = c + r subject to ||r|| = R Returns zero, one, or
         * two points on the periphery, e.g. c + r[0,1], sorted by decreasing
         * 't'. Alternative version which requires solving quadratic equation.
         *
         * Careful: set EPS if you need to handle round-off error in
         * discriminant.
         */
        P[] intersectsCircleAlternative(Circle c) {
            P ca = c.c.sub(p);
            P d = q.sub(p);
            Double[] t = solvequadratic(d.length2(), -2 * d.dot(ca), ca.length2() - c.R * c.R);
            P[] r = new P[t.length];
            for (int i = 0; i < t.length; i++)
                r[i] = p.add(d.mult(t[i]));
            return r;
        }

        /**
         * Is r contained within the line segment spanned by p/q, including
         * their endpoints?
         */
        boolean contains(P r) {
            double t = p.signedParallelogramArea(q, r);
            if (abs(t) > EPS) // point not on line
                return false;

            // check that point's distance from p is less than the
            // distance between p and q, and that it lies in the same
            // direction. We use >= 0 in case r == p.
            return p.dist(r) < p.dist(q) + EPS && r.sub(p).dot(d) >= EPS;
        }
    }

    static class Circle {
        P c;
        double R;

        Circle(P c, double R) {
            this.c = c;
            this.R = R;
        }

        @Override
        public String toString() {
            return String.format("{%s, %.03f}", c, R);
        }

        boolean contains(P p) {
            return R > p.dist(c) + EPS;
        }

        /*
         * a line segment is outside a circle if both end points are outside and
         * if any intersection points are outside the bounds of the line
         * segment.
         */
        boolean isOutside(Line l) {
            if (contains(l.p) || contains(l.q)) return false;
            P[] _is = l.intersectsCircle(this);
            if (_is.length > 1) for (P is : _is)
                if (l.contains(is)) return false;
            return true;
        }

        /*
         * Returns the tangent lines that the point p makes with this circle, if
         * any.
         */
        Line[] tangentLines(P p) {
            // Let c +/- r be the tangent points. Then there's a 'd' such that
            // p + d - r = c
            // Since d r = 0, we multiply by r and get
            // (p - c) r - ||r|| = 0 subject to ||r|| = R
            P[] r = p.sub(c).solveDotProductConstrainedByNorm(-R * R, R);
            Line[] tangents = new Line[r.length];
            for (int i = 0; i < tangents.length; i++)
                tangents[i] = new Line(p, c.add(r[i]));
            return tangents;
        }

        /*
         * Compute points of intersection of this circle (c1, r1) with that
         * circle (c2, r2). Model as triangle equation m = c2 - c1 = r1 - r2 m =
         * r1 - r2 -> m^2 = r1^2 + r2^2 - 2 r1 r2 (squaring) -> r1 r2 = (r1^2 +
         * r2^2 - m^2)/2 and by multiplying by r1 we obtain m = r1 - r2 -> m r1
         * = r1^2 - r1 r2 -> m r1 = r1^2 - (r1^2 + r2^2 - m^2)/2 -> m r1 + (r2^2
         * - r1^2 - m^2)/2 = 0 and ready for solveDotProductConstrainedByNorm
         *
         * Note that if the circles are (apprx) touching, this function may
         * return 0, 1, or 2 intersection points, depending on which side of 0
         * the discriminant falls. You will not get NaN.
         */
        P[] intersectsCircle(Circle that) {
            double r1 = this.R;
            double r2 = that.R;
            P m = that.c.sub(this.c);
            P[] r1sol = m.solveDotProductConstrainedByNorm((r2 * r2 - r1 * r1 - m.length2()) / 2, r1);
            // compute [c +/- r1] to obtain intersection points
            P[] is = new P[r1sol.length];
            for (int i = 0; i < r1sol.length; i++)
                is[i] = this.c.add(r1sol[i]);
            return is;
        }

        // returns true if this circle is outside that circle
        boolean isOutside(Circle that) {
            return this.c.dist(that.c) > (this.R + that.R);
        }

        // returns true if this circle is entirely contained inside that circle
        boolean isContainedIn(Circle that) {
            // extend line from that.c to this.c by radius R
            P m = this.c.sub(that.c);
            return that.contains(this.c.add(m.scale(this.R)));
        }
    }

    /**
     * Some basic operations on Polygons.
     */
    static class Polygon {
        P[] p; // open form, p[0] connects to p[n-1]

        // Constructors clone original array/collection
        Polygon(Collection<P> c) {
            this.p = c.toArray(new P[c.size()]);
        }

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

        /*
         * Returns an P[] array representing an open shape that is the convex
         * hull of the given array of points, or null if less than 2 points were
         * given.
         */
        public Polygon convexHull() {
            if (p.length < 2) return null;

            // (0) find the lowest point, breaking ties to the right
            final P min = Collections.min(Arrays.asList(p), new Comparator<P>() {
                public int compare(P p1, P p2) {
                    int y = Double.valueOf(p1.y).compareTo(p2.y);
                    return y != 0 ? y : Double.valueOf(p1.x).compareTo(p2.x);
                }
            });

            // (1) sort points by angle from pivot
            Arrays.sort(p, new Comparator<P>() {
                public int compare(P p1, P p2) {
                    double o = min.signedParallelogramArea(p1, p2); // crossproduct
                    if (o != 0) return -(int) Math.signum(o);

                    // if collinear, use distance to break tie
                    return Double.valueOf(min.dist(p1)).compareTo(min.dist(p2));
                }
            });

            // (3) create stack
            Stack<P> hull = new Stack<P>();
            assert p[0] == min;
            hull.push(p[0]);
            hull.push(p[1]);

            // (4) select points that maintain left turns
            for (int i = 2; i < p.length; i++) {
                P next = p[i];
                while (hull.size() >= 2) {
                    P snd = hull.get(hull.size() - 2);
                    P top = hull.peek();
                    if (snd.isCCW(top, next)) // keep
                        break;

                    hull.pop(); // discard
                }
                // keep current point
                hull.push(next);
            }
            return new Polygon(hull);
        }

        /*
         * "Point-in-Polygon" PIP tests. Return true if point is contained in
         * this polygon
         *
         * All of these tests may return 0 or 1 for points on the boundary. A
         * separate test is required to determine if a point is on the boundary.
         */
        public boolean contains(P q) {
            return contains_WN(q);
        }

        /*
         * Crossing-number based algorith due to Wm. Randolph Franklin. Works
         * only for simple polygons.
         */
        private boolean contains_CN(P q) {
            boolean c = false;
            for (int i = 0, j = p.length - 1; i < p.length; j = i++) {
                if ((((p[i].y <= q.y) && (q.y < p[j].y)) || ((p[j].y <= q.y) && (q.y < p[i].y)))
                        && (q.x < (p[j].x - p[i].x) * (q.y - p[i].y) / (p[j].y - p[i].y) + p[i].x))
                    c = !c;
            }
            return c;
        }

        /*
         * Winding numbers based algorithm, which also works for
         * self-intersecting polygons.
         * http://geomalgorithms.com/a03-_inclusion.html describes this as the
         * always preferred algorithm.
         *
         * Return: wn = the winding number (=0 only when P is outside)
         */
        public boolean contains_WN(P q) {
            int wn = 0; // the winding number counter

            // loop through all edges of the polygon
            int n = p.length;
            for (int i = 0; i < n; i++) { // edge from V[i] to V[i+1]
                P p = this.p[i], pn = this.p[(i + 1) % n];
                if (p.y <= q.y) { // start y <= P.y
                    if (pn.y > q.y) // an upward crossing
                        if (p.isCCW(pn, q)) // P left of edge
                            ++wn; // have a valid up intersect
                } else { // start y > P.y (no test needed)
                    if (pn.y <= q.y) // a downward crossing
                        if (!p.isCCW(pn, q)) // P right of edge
                            --wn; // have a valid down intersect
                }
            }
            return wn != 0;
        }

        /**
         * Is q on the boundary of this polygon?
         */
        public boolean onBoundary(P q) {
            int n = p.length;
            for (int i = 0; i < n; i++) {
                P pi = this.p[i], pj = this.p[(i + 1) % n];
                if (new Line(pi, pj).contains(q)) return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return Arrays.toString(p);
        }
    }

    static class KDTree {
        final static int DIM = 2; // 2D

        static class Node {
            P p;
            final int level;
            Node below;
            Node above;

            Node(P p, int level) {
                this.p = p;
                this.level = level;
            }

            // difference on axis corresponding to level/dim
            double diff(P pt) {
                switch (level) {
                    case 0:
                        return pt.x - p.x;
                    case 1:
                        return pt.y - p.y;
                }
                throw new Error("invalid level: " + level);
            }

            /* count # of points within range of test point */
            int rangeCount(P pt, double maxRadius) {
                int count = (p.dist2(pt) <= maxRadius * maxRadius) ? 1 : 0;
                double d = diff(pt);
                if (-d <= maxRadius && above != null) count += above.rangeCount(pt, maxRadius);
                if (d <= maxRadius && below != null) count += below.rangeCount(pt, maxRadius);
                return count;
            }

            /* collect points within range of test point */
            void collectPointsInRange(P pt, double maxRadius, List<P> bag) {
                if (p.dist2(pt) <= maxRadius * maxRadius) bag.add(p);

                double d = diff(pt);
                if (-d <= maxRadius && above != null) above.collectPointsInRange(pt, maxRadius, bag);
                if (d <= maxRadius && below != null) below.collectPointsInRange(pt, maxRadius, bag);
            }

            void getKNearestNeighborsHelper(P pt, double maxRadius, int k, TreeSet<P> knn) {
                if (p.dist2(pt) < maxRadius * maxRadius) knn.add(p);

                double d = diff(pt);
                Node myside = d <= 0 ? below : above;
                Node otherside = d <= 0 ? above : below;

                if (myside != null) myside.getKNearestNeighborsHelper(pt, maxRadius, k, knn);

                // Prune if all points on 'maybeside' are outside maxRadius
                if (otherside != null && abs(d) <= maxRadius) {
                    double w = knn.size() == k ? pt.sub(knn.last()).length2() : Double.POSITIVE_INFINITY;

                    // ... or if we already have k points at distance w (or
                    // closer).
                    if (d * d <= w) otherside.getKNearestNeighborsHelper(pt, maxRadius, k, knn);
                }
            }

            @Override
            public String toString() {
                return String.format("KD[%s - %c,%s,%s]", p, "XY".charAt(level), below, above);
            }

            int size() {
                int sz = 1;
                if (below != null) sz += below.size();
                if (above != null) sz += above.size();
                return sz;
            }
        }

        public int size() {
            return root == null ? 0 : root.size();
        }

        private Node insert(Node t, P pt, int parentLevel) {
            if (t == null) { // leaf
                t = new Node(pt, (parentLevel + 1) % DIM);
            } else {
                if (t.diff(pt) < 0.0) // crucially, below tree has coord < pt,
                                      // above >= pt
                                      // delete depends on this in a subtle way
                    t.below = insert(t.below, pt, t.level);
                else t.above = insert(t.above, pt, t.level);
            }
            return t;
        }

        Node root;

        public void insert(P pt) {
            root = insert(root, pt, -1);
        }

        List<P> collectPointsInRange(P pt, double maxRadius) {
            List<P> bag = new ArrayList<>();
            if (root != null) root.collectPointsInRange(pt, maxRadius, bag);
            return bag;
        }

        // find minimum along dimension (x: dim = 0, y: dim = 1, ...)
        public P findmin(final int dim) {
            return findmin(root, dim);
        }

        private P findmin(Node t, final int dim) {
            if (t == null) return null;

            if (dim == t.level) {
                if (t.below == null) return t.p;
                else return findmin(t.below, dim);
            } else {
                // find min(pa, pt, pb) along dim
                P pb = findmin(t.below, dim);
                P pa = findmin(t.above, dim);
                P pt = t.p;
                // pt = min(pa, pt)
                if (pa != null) {
                    if (dim == 0 && pa.x < pt.x) pt = pa;
                    if (dim == 1 && pa.y < pt.y) pt = pa;
                }

                // pt = min(pb, pt)
                if (pb != null) {
                    if (dim == 0 && pb.x < pt.x) pt = pb;
                    if (dim == 1 && pb.y < pt.y) pt = pb;
                }
                return pt;
            }
        }

        public void delete(P x) {
            root = delete(x, root);
        }

        // delete a point x that is .equals to a point in the tree
        private Node delete(P x, Node t) {
            if (t == null) throw new Error(String.format("Point %s not found", x));

            if (t.p.equals(x)) {
                if (t.above != null) {
                    t.p = findmin(t.above, t.level); // replace with smallest
                                                     // node in dim
                    t.above = delete(t.p, t.above); // delete that node from
                                                    // subtree
                } else if (t.below != null) {
                    t.p = findmin(t.below, t.level); // replace with smallest
                                                     // node from below
                    t.above = delete(t.p, t.below); // delete that node from
                                                    // below and attach above
                    t.below = null;
                } else {
                    t = null;
                }
            } else {
                double d = t.diff(x);
                if (d < 0) t.below = delete(x, t.below);
                else t.above = delete(x, t.above);
            }
            return t;
        }

        /*
         * For testing only. Not recommended in contest.
         */
        public void checkTreeInvariant() {
            checkTreeInvariant(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
                    Double.POSITIVE_INFINITY, 0);
        }

        private void checkTreeInvariant(Node t, double xlow, double xhigh, double ylow, double yhigh, int level) {
            if (t == null) return;
            if (t.level != level) throw new Error();
            switch (t.level) {
                case 0:
                    if (t.p.x < xlow) throw new Error();
                    if (t.p.x >= xhigh) throw new Error();
                    checkTreeInvariant(t.above, t.p.x, xhigh, ylow, yhigh, (level + 1) % 2);
                    checkTreeInvariant(t.below, xlow, t.p.x, ylow, yhigh, (level + 1) % 2);
                    break;
                case 1:
                    if (t.p.y < ylow) throw new Error();
                    if (t.p.y >= yhigh) throw new Error();
                    checkTreeInvariant(t.above, xlow, xhigh, t.p.y, yhigh, (level + 1) % 2);
                    checkTreeInvariant(t.below, xlow, xhigh, ylow, t.p.y, (level + 1) % 2);
                    break;
            }
        }

    }
}
