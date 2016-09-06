import java.util.*;
import static java.lang.Math.*;
import java.math.*;
import java.io.*;

//ICPC World Finals 2008 Problem J (practice)
//http://acm.hust.edu.cn/vjudge/contest/111395#problem/J
//Handbook geometry library removed
public class TheSkyIsTheLimit {

    public static void main(String[] args) {
        
            Scanner sc = new Scanner(System.in);
            Scanner input = new Scanner(System.in);
            int caseL = 0;
            while (true) {
                caseL++;
                int n = input.nextInt();
                if (n == 0) {
                    break;
                }
                Line2[] lines = new Line2[n];
                Line[] linesHandbook = new Line[2*n];
                double start = 999999999999L;
                double end = -999999999999L;
                for (int a = 0; a < n; a++) {
                    int center = input.nextInt();
                    int height = input.nextInt();
                    int width = input.nextInt();
                    if (center + width > end) {
                        end = center + width / 2.0;
                    }
                    if (center - width < start) {
                        start = center - width / 2.0;
                    }
                    Line2 temp = new Line2(height, center, width);
                    lines[a] = temp;
                    P start2 = new P(center-width/2.0, 0);
                    P center2 = new P(center, height);
                    P end2 = new P(center + width/2.0, 0);
                    Line first = new Line(start2, center2);
                    Line second = new Line(center2, end2);
                    linesHandbook[2*a] = first;
                    linesHandbook[2*a+1] = second;
                }
                ArrayList<Double> allIntersections = findAllIntersections(linesHandbook);
                allIntersections.add(start);
                allIntersections.add(end);
                for(int a = 0; a < n; a++){
                    allIntersections.add(lines[a].leftBound);
                    allIntersections.add(lines[a].rightBound);
                }
                Collections.sort(allIntersections);
                double total = 0;
                for (int idx = 0; idx < allIntersections.size()-1; idx++) {
                    double mid = (allIntersections.get(idx)+allIntersections.get(idx+1))/2;
                    double max = -1;
                    int index = -1;
                    for (int a = 0; a < n; a++) {
                        double temp = lines[a].evaluate(mid);
                        if (temp > max) {
                            max = temp;
                            index = a;
                        }
                    }
                    total += lines[index].ratio(mid)*((allIntersections.get(idx+1)-allIntersections.get(idx)));
                }
                System.out.printf("Case %d: %d\n", caseL, (int) (total + .5));
                System.out.println();
            }
        

    }

    private static ArrayList<Double> findAllIntersections(Line[] linesHandbook) {
        ArrayList<Double> output = new ArrayList<Double>();
        int n = linesHandbook.length;
        for(int a = 0; a < n; a++){
            for(int b = a+1; b < n; b++){
                P intersect = linesHandbook[a].intersectsInBounds(linesHandbook[b]);
                if(intersect != null){
                    output.add(intersect.x);
                }
            }
        }
        Collections.sort(output);
        return output;
    }

    public static class Line2 {
        double leftBound;
        double center;
        double rightBound;
        double height;

        public Line2(int height, int center2, int width) {

            leftBound = center2 - (width / 2);
            rightBound = center2 + (width / 2);
            center = center2;
            this.height = height;
        }

        public double evaluate(double x) {
            if (x <= leftBound || x >= rightBound) {
                return 0;
            }
            if (x == center) {
                return height;
            }
            if (x < center) {
                double diff = x - leftBound;
                double total = center - leftBound;
                return diff / total * height;
            } else {
                double diff = rightBound - x;
                double total = rightBound - center;
                return diff / total * height;
            }
        }

        public double ratio(double x) {
            if (x <= leftBound || x >= rightBound) {
                return 0;
            }
            return Math.hypot(height, (center - leftBound)) / (center - leftBound);
        }
    }



}