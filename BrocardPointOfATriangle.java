import java.util.*;
import static java.lang.Math.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/brocard
public class BrocardPointOfATriangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        StringBuilder sb = new StringBuilder();
        int numCases = sc.nextInt();
        for (int asdf = 0; asdf < numCases; asdf++) {
            int asdf2 = sc.nextInt();
            System.out.print(asdf2 + " ");
            
            double ax = sc.nextDouble();
            double ay = sc.nextDouble();
            double bx = sc.nextDouble();
            double by = sc.nextDouble();
            double cx = sc.nextDouble();
            double cy = sc.nextDouble();
            
            a = new P(ax, ay);
            b = new P(bx, by);
            c = new P(cx, cy);
            
            abLine = new Line(a, b);
            bcLine = new Line(b, c);
            caLine = new Line(c, a);
            
            P abVect = b.sub(a);
            P bcVect = c.sub(b);
            P caVect = a.sub(c);
            
            P right = new P(10, 0);
            
            abAngle = abVect.angleTo(right);
            bcAngle = bcVect.angleTo(right);
            caAngle = caVect.angleTo(right);
            
            if (b.y < a.y) {
                abAngle = 2*Math.PI-abAngle;
            }
            
            if (c.y < b.y) {
                bcAngle = 2*Math.PI-bcAngle;
            }
            
            if (a.y < c.y) {
                caAngle = 2*Math.PI-caAngle;
            }
            
            double angle = ternarySearch(0, Math.PI);
            P midPoint = getMidPoint(angle);
            System.out.println(midPoint.x + " " + midPoint.y);
            
            // 
        }
    }
    
    static P a;
    static P b;
    static P c;
    
    static Line abLine;
    static Line bcLine;
    static Line caLine;
    
    static double abAngle;
    static double bcAngle;
    static double caAngle;
    
    static Polygon makePolygon(double angle) {
        double angleFromA = abAngle + angle;
        double angleFromB = bcAngle + angle;
        double angleFromC = caAngle + angle;
        angleFromA %= 2*Math.PI;
        angleFromB %= 2*Math.PI;
        angleFromC %= 2*Math.PI;
        
        P aEnd = new P(Math.cos(angleFromA), Math.sin(angleFromA)).scale(100);
        P bEnd = new P(Math.cos(angleFromB), Math.sin(angleFromB)).scale(100);
        P cEnd = new P(Math.cos(angleFromC), Math.sin(angleFromC)).scale(100);
        
        P startA = a;
        P endA = a.add(aEnd);
        Line lA = new Line(startA,endA);
        
        P startB = b;
        P endB = b.add(bEnd);
        Line lB = new Line(startB,endB);
        
        P startC = c;
        P endC = c.add(cEnd);
        Line lC = new Line(startC,endC);
        
        P intersect1 = lA.intersects(lB);
        P intersect2 = lB.intersects(lC);
        P intersect3 = lC.intersects(lA);
        P[] arr = new P[3];
        arr[0] = intersect1;
        arr[1] = intersect2;
        arr[2] = intersect3;
        Polygon p = new Polygon(arr);
        return p;
    }
    private static double toD(double bcAngle2) {
        return bcAngle2*180/Math.PI;
    }
    static P getMidPoint(double angle){
        Polygon p = makePolygon(angle);
        return p.p[0];
    }
    static double apply(double angle) {
        Polygon p = makePolygon(angle);
        return p.absoluteArea();
    }
    
    /**
     * Return x in [a, b] such that f(x) is minimal.
     * f() must have exactly one minimum in [a, b]
     */
    static double ternarySearch(double left, double right) {
        while (true) {
            if ((right - left) < 10 * Math.ulp(right))
                return (left + right)/2.0;

            double leftThird = (2*left + right)/3;
            double rightThird = (left + 2*right)/3;
            if (apply(leftThird) < apply(rightThird))
                right = rightThird; // discard right third
            else
                left = leftThird;   // discard left third
        }
    }
    
    static class P {
        final double x, y;
        P(double x, double y)    { this.x = x; this.y = y; }
        P sub(P that)            { return new P(x - that.x, y - that.y); }
        P add(P that)            { return new P(x + that.x, y + that.y); }
        double dot(P that)       { return x * that.x + y * that.y; }
        P mult(double s)         { return new P(x * s, y * s); }
        // Use hypot() only if intermediate overflow must be avoided
        double length()          { return sqrt(x*x + y*y); }    
        double length2()         { return x * x + y * y; }
        P leftNormal()           { return new P(-y, x); }   // rotateCCW(90deg)
        P rightNormal()          { return new P(y, -x); }   // rotateCW(90deg)
        P normalize()            { double n = length(); return n > 0 ? new P(x/n, y/n) : origin(); }
        P scale(double l)        { return normalize().mult(l); }
        P project(P a)           { return mult(a.dot(this) / length2()); }
        P reflect(P a)           { return project(a).mult(2.0).sub(a); }
        // use if sin, cos are known
        P rotateCCW(double sinT, double cosT) {
            return new P(x * cosT - y * sinT,
                         x * sinT + y * cosT); }
        P rotateCW(double sinT, double cosT) { return rotateCCW(-sinT, cosT); }
        P rotate(double theta)   { return rotateCCW(sin(theta), cos(theta)); }
        double theta()           { return atan2(y, x); }    // from -pi to +pi
        double angleTo(P a)      { return acos(this.dot(a) / this.length() / a.length()); }
        boolean isOrigin()       { return x == 0 && y == 0; }
        public String toString() { return String.format("(%f,%f)", this.x, this.y); }
        static P read(Scanner s) { return new P(s.nextDouble(), s.nextDouble()); }
        static P origin()        { return new P(0, 0); }
        double det(P that)       { return this.x * that.y - this.y * that.x; }
        double crossproduct(P that) { return this.det(that); }
        P half(P q)              { return normalize().add(q.normalize()); }

        double dist(P to)        { return sub(to).length(); }
        double signedParallelogramArea(P b, P c) { return (b.sub(this).crossproduct(c.sub(this))); }
        boolean isCollinearWith(P b, P c) { return abs(signedParallelogramArea(b, c)) <= EPS; }
        // is going from this to b to c a CCW turn? Do not use if points may be collinear
        boolean isCCW(P b, P c)  { return signedParallelogramArea(b, c) > 0; }
        double signedTriangleArea(P b, P c) { return signedParallelogramArea(b, c) / 2.0; }

        // memory-optimized version of this.sub(to).length2() that avoids an intermediate object
        double dist2(P to)       {
            double dx = this.x - to.x;
            double dy = this.y - to.y;
            return dx * dx + dy * dy;
        }

        /**
         * Compute x for a * x + b = 0 and ||x|| = C
         * where 'this' is a.  
         * Care must be taken to handle the case where
         * either a.x or a.y is near zero.
         */
        P [] solveDotProductConstrainedByNorm(double b, double C) {
            P a = this;
            if (a.isOrigin())
                throw new Error("degenerate case");

            boolean transpose = abs(a.x) > abs(a.y);
            a = transpose ? new P(a.y, a.x) : a;

            Double [] x = solvequadratic(a.length2(), 2.0 * b * a.x, b * b - a.y * a.y * C * C);
            P [] p = new P[x.length];
            for (int i = 0; i < x.length; i++) {
                double x1 = x[i];
                double x2 = ((-b - a.x * x1) / a.y);
                p[i] = transpose ? new P(x2, x1) : new P(x1, x2);
            }
            return p;
        }
    }

    static class HP extends P { // Hashable Point
        HP(double x, double y) { super(x, y); }
        @Override
        public int hashCode() { return Double.hashCode(x + 32768*y); }
        @Override
        public boolean equals (Object _that) {
            HP that = (HP)_that;
            return this.x == that.x && this.y == that.y;
        }
    }

    /**
     * Sort points by polar angle relative to center, using trig.
     * This is a counter-clockwise sort with zero at 3 o'clock.
     */
    static Comparator<P> makePolarAngleComparatorTrig(final P center) {
        return new Comparator<P>() {
            public int compare(P a, P b) {
                double thetaa = a.sub(center).theta();
                double thetab = b.sub(center).theta();
                if (thetaa < 0) thetaa += 2 * PI;
                if (thetab < 0) thetab += 2 * PI;
                int c = Double.compare(thetaa, thetab);
                if (c != 0)
                    return c;
                return Double.compare(b.x, a.x);    // arbitrary tie-breaker
            }
        };
    }

    /**
     * Sort points by polar angle relative to center, w/o trig.
     * This is a counter-clockwise sort with zero at 3 o'clock.
     */
    static Comparator<P> makePolarAngleComparator(final P center) {
        return new Comparator<P>() {
            public int compare(P a, P b) {
                // orientation() requires that a and b lie in the same half-plane
                if (a.y >= center.y && b.y < center.y) return -1;
                if (b.y >= center.y && a.y < center.y) return 1;
                int orientation = (int) Math.signum(center.signedParallelogramArea(b, a));
                if (orientation != 0) return orientation;
                return Double.compare(b.x, a.x);    // arbitrary tie-breaker
            }
        };
    }

    /* Solve a * x^2 + b * x + c == 0 
     * Returns 0, 1, or 2 solutions. If 2 solutions x1, x2, guarantees that x1 < x2
     */
    static Double [] solvequadratic(double a, double b, double c) {
        double D = b * b - 4 * a * c;
        if (D < -EPS)
            return new Double [] { };
        D = max(D, 0);
        if (D == 0)
            return new Double [] { -b / 2.0 / a };
        double d = sqrt(D);
        // Numerically more stable, see
        // https://en.wikipedia.org/wiki/Loss_of_significance#A_better_algorithm
        if (signum(b) == 0)
            return new Double[] { d / 2.0 / a, -d / 2.0 / a };
        double x1 = (-b - signum(b) * d) / (2 * a);
        double x2 = c / (a * x1);
        return new Double[] { Math.min(x1, x2), Math.max(x1, x2) };
    }

    /* The Line/Circle classes provide a number of methods that require dealing
     * with floating point precision issues.  Default EPS to a suitable value,
     * such as 1e-6, which should work for many problems in which the input
     * coordinates are in integers and subsequently inexact floating point
     * values are being computed.
     */
    static double EPS = 1e-6;

    /* A line denoted by two points p and q.
     * For internal computations, we use the parametric representation
     * of the line as p + t d where d = q - p.
     * For convenience, we compute and store d in the constructor. 
     * Most methods hide the parametric representation of the
     * line, but it is exposed via getPointFromParameter and 
     * intersectionParameters for those problems that need it.
     *
     * The line may be interpreted either as a line segment denoted
     * by the two end points, or as the infinite line determined
     * by these two points.  Intersection methods are provided
     * for both cases.
     */ 
    static class Line {
        P p, q, d;
        Line(P p, P q) { this.p = p; this.q = q; d = q.sub(p); }

        P getPointFromParameter(double t) { return p.add(d.mult(t)); }

        // reflect vector across vector (as if line originated at (0, 0))
        P reflect(P d2) { return d.reflect(d2); }

        // reflect point across (infinite) line 
        P reflectPoint(P r) { return reflect(r.sub(p)).add(p); }

        // project p onto this (infinite) line. Returns point on line
        P project(P a) { return p.add(d.project(a.sub(p))); }

        // return distance of point P from this (infinite) line.
        double distance(P a) { return project(a).dist(a); }

        @Override
        public String toString() { return String.format("[%s => %s]", p, q); }

        /* Point of intersection of this line segment
         * with another line segment.  Returns only points
         * that lie inside both line segments, else null.
         *
         * Result may include points "just outside" the bounds,
         * given EPS.
         */
        P intersectsInBounds(Line l) {
            double [] st = intersectionParameters(l);
            if (st == null)
                return null;

            // check that point of intersection is in direction 'd' 
            // and within segment bounds
            double s = st[0];
            double t = st[1];
            if (s >= -EPS && s <= 1+EPS && -EPS <= t && t <= 1+EPS)
                return getPointFromParameter(s);

            return null;
        }

        /* Point of intersection of this (infinite) line 
         * with other (infinite) line.  Return null if collinear.
         */
        P intersects(Line l) {
            double [] st = intersectionParameters(l);
            if (st != null)
                return getPointFromParameter(st[0]);
            return null;
        }

        /* Intersect this line with that line
         * Solves:  this.p + s * this.d == l.p + t l.d
         * Return null if lines are collinear
         * Else returns [s, t].
         */
        double [] intersectionParameters(Line l) {
            P dneg = p.sub(q);
            double D = l.d.det(dneg);
            // Use Cramer's rule; see text
            if (D == 0.0)
                return null;

            P rp = p.sub(l.p);
            return new double[] { l.d.det(rp) / D, rp.det(dneg) / D };
        }

        /* Compute points of intersection of this infinite line
         * with a circle.
         * Computes projection 'x' of c.c onto line, then computes
         * x +/- d.scale(h) where h is computed via Pythagoras.
         * Sorted by decreasing 't'
         *
         * May return two points even if line is a tangent.
         */
        P [] intersectsCircle(Circle c) {
            P x = project(c.c);
            double D = x.dist(c.c);
            // outside by more than EPS
            if (D > c.R + EPS) return new P[0];       
            double h = sqrt(max(0, c.R * c.R - D * D));
            if (h == 0) return new P[] { x };   // EPS (!?)
            return new P[] { x.add(d.scale(h)), x.add(d.scale(-h)) };
        }

        /* Compute points of intersection of this infinite line
         * with a circle.
         *
         * Solves a + t * b = c + r subject to ||r|| = R
         * Returns zero, one, or two points on the periphery, 
         * e.g. c + r[0,1], sorted by decreasing 't'.
         * Alternative version which requires solving quadratic
         * equation.
         *
         * Careful: set EPS if you need to handle round-off error
         * in discriminant.
         */
        P [] intersectsCircleAlternative(Circle c) {
            P ca = c.c.sub(p);
            P d = q.sub(p);
            Double [] t = solvequadratic(d.length2(), -2 * d.dot(ca), ca.length2() - c.R * c.R);
            P [] r = new P[t.length];
            for (int i = 0; i < t.length; i++)
                r[i] = p.add(d.mult(t[i]));
            return r;
        }

        /**
         * Is r contained within the line segment spanned by p/q,
         * including their endpoints?
         */
        boolean contains(P r) {
            double t = p.signedParallelogramArea(q, r);
            if (abs(t) > EPS)   // point not on line
                return false;

            // check that point's distance from p is less than the
            // distance between p and q, and that it lies in the same
            // direction.  We use >= 0 in case r == p. 
            return p.dist(r) < p.dist(q) + EPS && r.sub(p).dot(d) >= EPS;
        }
    }

    static class Circle {
        P c;
        double R;
        Circle(P c, double R) { this.c = c; this.R = R; }
        @Override
        public String toString() { return String.format("{%s, %.03f}", c, R); }
        boolean contains(P p) { return R > p.dist(c) + EPS; }
        /* a line segment is outside a circle if both end points are outside and
         * if any intersection points are outside the bounds of the line segment. */
        boolean isOutside(Line l) {
            if (contains(l.p) || contains(l.q))
                return false;
            P [] _is = l.intersectsCircle(this);
            if (_is.length > 1)
                for (P is : _is)
                    if (l.contains(is))
                        return false;
            return true;
        }

        /* Returns the tangent lines that the point p makes with this circle, if any. */
        Line [] tangentLines(P p)
        {
            // Let c +/- r be the tangent points.  Then there's a 'd' such that
            // p + d - r = c 
            // Since d r = 0, we multiply by r and get
            // (p - c) r - ||r|| = 0  subject to ||r|| = R
            P [] r = p.sub(c).solveDotProductConstrainedByNorm(-R*R, R);
            Line [] tangents = new Line[r.length];
            for (int i = 0; i < tangents.length; i++)
                tangents[i] = new Line(p, c.add(r[i]));
            return tangents;
        }
    }

    /**
     * Some basic operations on Polygons.
     */
    static class Polygon {
        public P [] p;         // open form, p[0] connects to p[n-1]

        // Constructors clone original array/collection
        Polygon(Collection<P> c) { this.p = c.toArray(new P[c.size()]); }
        Polygon(P []p) { this.p = (P [])p.clone(); }

        /* Absolute of signed triangle areas */
        double signedArea() {
            double area = 0.0;
            for (int i = 0; i < p.length; i++) {
                area += p[i].det(p[(i+1) % p.length]);
            }
            return area/2.0;
        }

        double absoluteArea() {
            return abs(signedArea());
        }

        /*
         *  Returns an P[] array representing an open shape that is
         *  the convex hull of the given array of points, or
         *  null if less than 2 points were given.
         */
        public Polygon convexHull()
        {
            if (p.length < 2)
                return null;

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
                    if (o != 0)
                        return - (int) Math.signum(o);

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
                    if (snd.isCCW(top, next))  // keep
                        break;

                    hull.pop(); // discard
                }
                // keep current point
                hull.push(next);
            }
            return new Polygon(hull);
        }

        /*
         * "Point-in-Polygon" PIP tests.
         * Return true if point is contained in this polygon
         *
         * All of these tests may return 0 or 1 for points
         * on the boundary.  A separate test is required to
         * determine if a point is on the boundary.
         */
        public boolean contains(P q) { return contains_WN(q); }

        /*
         * Crossing-number based algorith 
         * due to Wm. Randolph Franklin. 
         * Works only for simple polygons. */
        private boolean contains_CN(P q)
        {
            boolean c = false;
            for (int i = 0, j = p.length-1; i < p.length; j = i++) {
                if ((((p[i].y<=q.y) && (q.y<p[j].y)) || ((p[j].y<=q.y) && (q.y<p[i].y))) &&
                    (q.x < (p[j].x - p[i].x) * (q.y - p[i].y) / (p[j].y - p[i].y) + p[i].x))
                    c = !c;
            }
            return c;
        }

        /* Winding numbers based algorithm,
         * which also works for self-intersecting polygons.
         * http://geomalgorithms.com/a03-_inclusion.html describes
         * this as the always preferred algorithm.
         *
         *  Return:  wn = the winding number (=0 only when P is outside)
         */
        public boolean contains_WN(P q)
        {
            int wn = 0;    // the  winding number counter

            // loop through all edges of the polygon
            int n = p.length;
            for (int i = 0; i < n; i++) {   // edge from V[i] to  V[i+1]
                P p = this.p[i], pn = this.p[(i+1)%n];
                if (p.y <= q.y) {           // start y <= P.y
                    if (pn.y > q.y)           // an upward crossing
                         if (p.isCCW(pn, q))  // P left of  edge
                             ++wn;            // have  a valid up intersect
                } else {                      // start y > P.y (no test needed)
                    if (pn.y <= q.y)          // a downward crossing
                         if (!p.isCCW(pn, q)) // P right of  edge
                             --wn;            // have a valid down intersect
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
                P pi = this.p[i], pj = this.p[(i+1)%n];
                if (new Line(pi, pj).contains(q))
                    return true;
            }
            return false;
        }

        @Override
        public String toString() { return Arrays.toString(p); }
    }
}
