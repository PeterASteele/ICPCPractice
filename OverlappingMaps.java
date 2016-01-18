import java.util.Scanner;

//NAIPC2013
public class OverlappingMaps {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		while(true){
			int w = input.nextInt(); //width of larger map
			int h = input.nextInt(); //height of larger map
			int x = input.nextInt(); //start point x value of small map
			int y = input.nextInt(); //start point y value of small map
			int s = input.nextInt(); //scale factor, 0 to 100
			int r = input.nextInt(); //angle, in degrees
			if(w == 0 && h == 0 && x == 0 && y == 0 && s == 0 && r == 0){
				break;
			}
			double startX = 0;
			double startY = 0;
			Point start = new Point(startX, startY);
			double epsilon = Math.ulp(0.0) * 10;
			double error = 1;
			int count = 0;//1000 1000 1 0 99 0
			while(error > epsilon && count < 5000){
				count++;
				Point temp = transform(start, x, y, s, r);
				error = getError(start, temp);
				start = temp;
			}
			//System.out.println(count);
			System.out.printf("%.2f", start.x);
			System.out.printf(" %.2f\n", start.y);
		}

	}
	public static class Point{
		double x;
		double y;
		public Point(double x2, double y2){
			x = x2;
			y = y2;
		}
	}
	public static double getError(Point a, Point b){
		double yValue = Math.abs(a.y-b.y);
		double xValue = Math.abs(a.x-b.x);
		return Math.hypot(xValue, yValue);
	}
	public static Point transform(Point a, int x, int y, int s, double r){
		double xResult = x + (a.x*Math.cos(Math.toRadians(r))-a.y*Math.sin(Math.toRadians(r)))*s/100.0;
		double yResult = y + (a.x*Math.sin(Math.toRadians(r))+a.y*Math.cos(Math.toRadians(r)))*s/100.0;
		return new Point(xResult, yResult);
	}
}
