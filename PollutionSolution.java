import java.util.*;


import java.math.*;
import java.io.*;
import static java.lang.Math.*;

//ICPC World Finals 2013 Problem J (practice)
//https://icpc.kattis.com/problems/pollution
//Team handbook geometry library code removed
public class PollutionSolution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int n = sc.nextInt();
        int r = sc.nextInt();

        Circle pollutionfield = new Circle(new P(0, 0), r);

        P[] pts = new P[n];
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            pts[i] = new P(x, y);
        }
        double total = 0;

        for (int i = 0; i < n; i++) {
            P a = pts[i];
            P b = pts[(i + 1) % n];
            P[] arr = new P[3];
            arr[0] = new P(0, 0);
            arr[1] = a;
            arr[2] = b;
            total += figure(arr, r, pollutionfield);
        }
        System.out.println(Math.abs(total));
    }

    static double area(List<P> poly) {
        int n = poly.size();
        double area = 0;
        for (int i = 0; i <= n - 1; i++) {
            P j = poly.get(i);
            P k = poly.get((i + 1)%n);
            area += (j.x * k.y - k.x * j.y);
        }

        return area / 2.;
    }
    static double figure(P[] pts, double r, Circle pollutionfield){
        int n = pts.length;
        List<P> slivers = new ArrayList<>();
        List<P> inside = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            P a = pts[i];
            P b = pts[(i + 1) % n];
            boolean a_in = pollutionfield.contains(a);
            boolean b_in = pollutionfield.contains(b);
            if (a_in && b_in) {
                inside.add(a);
                inside.add(b);
            } 
            else {
                Line ab = new Line(a, b);
                P[] intersections = ab.intersectsCircle(pollutionfield);
                
                List<P> actual = new ArrayList<>();
                for (int j = 0; j < intersections.length; j++) {
                    if (ab.contains(intersections[j])) actual.add(intersections[j]);
                }
                if(actual.size()== 0){
                    continue;
                }
                if (a_in && !b_in) {
                    inside.add(a);
                }
                
                slivers.addAll(actual);
                // add intersections
                if (actual.size() == 2) {
                    if (actual.get(0).dist(a) < actual.get(1).dist(a)) {
                        inside.add(actual.get(0));
                        inside.add(actual.get(1));
                    } else {
                        inside.add(actual.get(1));
                        inside.add(actual.get(0));
                    }
                } else {
                    inside.add(actual.get(0));
                }
                if (!a_in && b_in) {
                    inside.add(b);
                }
                
            }
        }

        double out = area(inside);
        boolean sign = (out) < 0;
        out = Math.abs(out);
        if(slivers.size()%2 == 1){
            throw (new RuntimeException());
        }
        Collections.sort(slivers, new Comparator<P>() {

            @Override
            public int compare(P o1, P o2) {
                return Double.compare(o1.x, o2.x);
            }
            
        });
        for (int a = 0; a < slivers.size(); a+=2) {
            P start = slivers.get(a);
            P end = slivers.get(a+1);
            double theta = Math.atan2(end.y, end.x)-Math.atan2(start.y, start.x);
            double bigArea = Math.PI*r*r * theta/(2*Math.PI);
            ArrayList<P> triangle = new ArrayList<P>();
            triangle.add(start);
            triangle.add(end);
            triangle.add(new P(0, 0));
            bigArea -= area(triangle);
            out += Math.abs(bigArea);
        }
        return sign?-1*out:out;
    }


}