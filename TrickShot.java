import java.util.*;
import java.math.*;
import java.io.*;

import static java.lang.Math.*;

public class TrickShot {
    static int width;
    static int length;
    static int radius;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        width = input.nextInt();
        length = input.nextInt();
        radius = input.nextInt();
        P ball1 = new P(input.nextInt(), input.nextInt());
        
        
        P ball2 = new P(input.nextInt(), input.nextInt());
        P leftGoal = new P(0, length);
        P vectorBall2ToLeftGoal = leftGoal.sub(ball2);

        System.err.println("SHORTVECT QUEUE TARGET: " + vectorBall2ToLeftGoal);
        double multFactor = (vectorBall2ToLeftGoal.length()+2*radius)/vectorBall2ToLeftGoal.length();
        System.err.println("mult FACTOR: " + multFactor);
        P longVect = (vectorBall2ToLeftGoal.mult(multFactor)).mult(-1);
        System.err.println("LONGVECT QUEUE TARGET: " + longVect);
        P queueTarget = leftGoal.add(longVect);
        checkBounds(queueTarget);
        System.err.println("QUEUE TARGET: " + queueTarget);

        P ball3 = new P(input.nextInt(), input.nextInt());
        P rightGoal = new P(width, length);
        P vectorBall3ToRightGoal = rightGoal.sub(ball3);
        double multFactor2 = (vectorBall3ToRightGoal.length()+2*radius)/vectorBall3ToRightGoal.length();
        P longVect3 = vectorBall3ToRightGoal.mult(multFactor2).mult(-1);
        P ball1Target = rightGoal.add(longVect3);
        checkBounds(ball1Target);
        System.err.println("Ball1 Target: " + ball1Target);
        
        P ball1Direction = ball1Target.sub(ball1);
        double multFactor3 = (ball1Direction.length()+2*radius)/ball1Direction.length();
        P ball1TargetToQueueMidTarget = ball1Direction.mult(multFactor3).mult(-1);
        P queueMidTarget = ball1Target.add(ball1TargetToQueueMidTarget);
        checkBounds(queueMidTarget);
        System.err.println("QUEUE MID TARGET: " + queueMidTarget);
        
        
        
       
        
        
        P queueAfter1Hit = queueMidTarget.sub(queueTarget);
        System.err.println("BEFORE REFLECTION: " + queueAfter1Hit);
        P reflectedQueue = ball1TargetToQueueMidTarget.reflect(queueAfter1Hit);
        System.err.println("AFTER REFLECTION: " + reflectedQueue);
        P pointToStart = reflectedQueue.mult(-1);
        
        P secondPointOnLine = queueMidTarget.add(pointToStart);
        
        System.err.println("ANGLE THAT MUST BE > 90: " + ball1TargetToQueueMidTarget.angleTo(queueAfter1Hit)*180/Math.PI);
        if(ball1TargetToQueueMidTarget.angleTo(queueAfter1Hit) < Math.PI/2){
            impossible();
        }
        //ball1TargetToQueueMidTarget
        //queueAfter1Hit
        
        
     
        
        Line hit = new Line(queueMidTarget, secondPointOnLine);

        System.err.println("LINE SHOT: " + hit.p + " " + hit.q);
        
        int h = input.nextInt();
        Line startLine = new Line(new P(0, h), new P(width, h));
        
        P st = hit.intersects(startLine);
        checkBounds(st);
        
        if(st == null){
            impossible();
        }
        if(st.x < radius || st.x > width-radius){
            impossible();
        }
        
        
   
        
        //make sure things are not too close together to hit properly
        
        //P ball2
        //P ball3
        
        //P ball1
        //P st
        checkPrematureBallCollision(st, ball1, ball2);
        checkPrematureBallCollision(st, ball1, ball3);
        
        
        
        double fromLeft = st.x;
        P toFirstHit = pointToStart.mult(-1);
        double theta = (new P(width, h).sub(st)).angleTo(toFirstHit);
        System.out.printf("%.02f %.02f\n", fromLeft, 180.0/Math.PI*theta);
        //sc

    }
    private static void checkPrematureBallCollision(P st, P ball1, P ball2) {
        // TODO Auto-generated method stub
        P startToBall2 = ball2.sub(st);
        P startToBall1 = ball1.sub(st);
        P proj = startToBall1.project(startToBall2);
        if(proj.length() >= startToBall1.length()){
            return;
        }
        if(startToBall2.sub(proj).length() >= 2*radius){
            return;
        }
        impossible();
    }
    private static void checkBounds(P in){
        if(in.x < 0 || in.x > width || in.y < 0 || in.y > length){
            impossible();
        }
    }
    private static void impossible() {
        // TODO Auto-generated method stub
        System.out.println("impossible");
        System.exit(0);
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

        // is going from this to b to c a CCW turn?
        // Do not use if points may be collinear
        boolean isCCW(P b, P c) {
            return signedParallelogramArea(b, c) > 0;
        }

        double signedTriangleArea(P b, P c) {
            return signedParallelogramArea(b, c) / 2.0;
        }

        // memory-optimized version of this.sub(to).length2()
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
    static double EPS = 1e-6;

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

        // reflect vector across vector as if line originated at (0, 0)
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
      
    }





}
