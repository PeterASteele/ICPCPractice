import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
//NCPC2015 Problem E (Practice)
//https://open.kattis.com/contests/naipc16-p05/problems/entertainmentbox
public class EntertainmentBox {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int k = input.nextInt();
		TreeSet<Point2> eventQueue = new TreeSet<Point2>();
		TreeSet<Point> allEvents = new TreeSet<Point>();
		for (int a = 0; a < n; a++) {
			int start = input.nextInt();
			int end = input.nextInt();
			Point temp = new Point(start, end, a);
			allEvents.add(temp);
		}
		int eval = 0;

		for (Point p : allEvents) {
			if (eventQueue.size() == k) {
				Point2 last = eventQueue.last();
				Point2 first = eventQueue.first();
				if(p.start >= first.end){
					eval++;
					eventQueue.pollFirst();
					eventQueue.add(new Point2(p));
				}
				else{
					if(p.end <= last.end){
						eventQueue.pollLast();
						eventQueue.add(new Point2(p));
					}
						
				}
				
			}
			else{
			eventQueue.add(new Point2(p));
			}
		}
		eval += eventQueue.size();
		System.out.println(eval);

	}

	public static class Point implements Comparable<Point> {
		int start;
		int end;
		int id;

		public Point(int _x, int _y, int id) {
			start = _x;
			end = _y;
			this.id = id;
		}

		@Override
		public int compareTo(Point arg0) {
			if (start == arg0.start) {
				if (end == arg0.end) {

					return id - arg0.id;
				}
				return end - arg0.end;
			}
			return start - arg0.start;
		}

		public String toString() {
			return "[" + start + " " + end + "]:" + id;
		}
	}

	public static class Point2 implements Comparable<Point2> {
		int start;
		int end;
		int id;

		public Point2(Point k) {
			start = k.start;
			end = k.end;
			this.id = k.id;
		}

		@Override
		public int compareTo(Point2 arg0) {
			if (end == arg0.end) {
				if (start == arg0.start) {
					return id - arg0.id;
				}
				return start - arg0.start;
			}
			return end - arg0.end;
		}

		public String toString() {
			return "[" + start + " " + end + "]:" + id;
		}
	}

}