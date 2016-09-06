import java.util.*;
import java.math.*;
import java.io.*;

//ICPC World Finals 2011 Problem K (practice)
//http://acm.hust.edu.cn/vjudge/contest/114197#problem/K

public class TrashRemoval {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int cases = 0;
        while(true){
            cases++;
            int n = input.nextInt();
            if(n == 0){
                break;
            }
            Point[] points = new Point[n];
            for(int a = 0; a < n; a++){
                double x = input.nextInt();
                double y = input.nextInt();
                points[a] = new Point(x, y);
            }
            
            double min = Double.MAX_VALUE;
            double inc = 2*Math.PI/1E4;
            double past1 = 0;
            double past2 = 0;
            ArrayList<Double> promising =  new ArrayList<Double>();
            for(double theta = 0; theta < 2*Math.PI; theta += inc){
                double sz = size(points, theta);
                if(past1 < past2 && past1 < sz){
                    promising.add(theta-inc);
                }
                past2 = past1;
                past1 = sz;
            }
            promising.add(0.0);
            for(double theta2:promising){
                for(double d = theta2-inc*3; d <= theta2+inc*3; d+= inc/100){
                    double sz = size(points, d);
                    if(sz < min){
                        min = sz;
                    }
                }
            }
            System.out.println(String.format("Case %d: %.2f", cases, min+.00499));
        }
        
    }
    public static double size(Point[] a, double theta){
        double left = Double.MAX_VALUE;
        double right = Double.MIN_VALUE;
        for(int k = 0; k < a.length; k++){
            double tmp = a[k].project(theta);
            if(tmp < left){
                left = tmp;
            }
            if(tmp > right){
                right = tmp;
            }
        }
        double sz = right-left;
        return sz;
    }
    public static class Point{
        double x;
        double y;
        public Point(double x2, double y2){
            x = x2;
            y = y2;
        }
        public double project(double a, double b){
            return x*a + y*b;
        }
        public double project(double theta){
            return x*Math.sin(theta) + y*Math.cos(theta);
        }
    }
}