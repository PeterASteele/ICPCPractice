import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/junk
public class SpaceJunk {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int numC = input.nextInt();
        for(int a = 0; a < numC; a++){
            int xLoc = input.nextInt();
            int yLoc = input.nextInt();
            int zLoc = input.nextInt();
            int rad = input.nextInt();
            int Vx = input.nextInt();
            int Vy = input.nextInt();
            int Vz = input.nextInt();
            Point3D loc1 = new Point3D(xLoc, yLoc, zLoc);
            Point3D vel1 = new Point3D(Vx, Vy, Vz);
            
            int xLoc2 = input.nextInt();
            int yLoc2 = input.nextInt();
            int zLoc2 = input.nextInt();
            int rad2 = input.nextInt();
            int Vx2 = input.nextInt();
            int Vy2 = input.nextInt();
            int Vz2 = input.nextInt();
            Point3D loc2 = new Point3D(xLoc2, yLoc2, zLoc2);
            Point3D vel2 = new Point3D(Vx2, Vy2, Vz2);
            
            double result = binSearch(loc1, vel1, loc2, vel2, 0.0, 10000000.0, rad+rad2);
            Point3D dest1 = getPosTime(loc1, vel1, result);
            Point3D dest2 = getPosTime(loc2, vel2, result);
            double distBetween1 = getDistBetween(dest1, dest2);
            
            if(result < 1E-6 || distBetween1-1E-7 > rad+rad2){
                System.out.println("No collision");
            }
            else{
                System.out.println(result);
            }
            
        }
        
    }
    private static double getDistBetween(Point3D one, Point3D two){
        double x= one.x-two.x;
        double y= one.y-two.y;
        double z= one.z-two.z;
        return Math.sqrt(x*x+y*y+z*z);
    }
    private static Point3D getPosTime(Point3D curSpot, Point3D vel, double time){
        double x= curSpot.x + vel.x*time;
        double y= curSpot.y + vel.y*time;
        double z= curSpot.z + vel.z*time;
        return new Point3D(x, y, z);
    }
    private static double binSearch(Point3D loc1, Point3D vel1, Point3D loc2, Point3D vel2, double d, double e, double radius) {
        double mid = (d + e)/2;
        if(Math.abs(e-d) < Math.ulp(d)*100){
            return mid;
        }
        double h = 1E-6;
        Point3D dest1 = getPosTime(loc1, vel1, mid);
        Point3D dest1E = getPosTime(loc1, vel1, mid+h);
        Point3D dest2 = getPosTime(loc2, vel2, mid);
        Point3D dest2E = getPosTime(loc2, vel2, mid+h);
        double distBetween1 = getDistBetween(dest1, dest2);
        double distBetween2 = getDistBetween(dest1E, dest2E);
        double margin = distBetween2-distBetween1;
        if(margin > 0 || distBetween1 < radius){
            return binSearch(loc1, vel1, loc2, vel2, d, mid, radius);
        }
        else{
            return binSearch(loc1, vel1, loc2, vel2, mid, e, radius);
        }
    }
    public static class Point3D{
        double x;
        double y;
        double z;
        public Point3D(double _x, double _y, double _z){
            x = _x;
            y = _y;
            z = _z;
        }
    }
}
