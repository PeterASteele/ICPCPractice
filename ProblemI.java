import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.*;
//NAIPC2013
public class ProblemI {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int numPlots = input.nextInt();
		double totalArea = 0;
		List<Rectangle> rectangles = new ArrayList<>();
		List<Double> relevantY = new ArrayList<>();
		for (int i = 0; i < numPlots; i++) {
			double x1 = input.nextDouble();
			double y1 = input.nextDouble();
			double x2 = input.nextDouble();
			double y2 = input.nextDouble();
			rectangles.add(new Rectangle(x1, y1, x2, y2));
			relevantY.add(y1);
			relevantY.add(y2);
		}

		yCompressor = new Compressor<>(relevantY);
		//System.out.println(yCompressor.compressed2real);
		//System.out.println(yCompressor.real2Compressed);
		int numYCoords = yCompressor.compressed2real.size();
		int minY = 0;
		int maxY = numYCoords-1;
		double[] beginX = new double[numYCoords];
		
		MinLazySegmentTree segmentTree = new MinLazySegmentTree(minY, maxY);
		
		PriorityQueue<Event> events = new PriorityQueue<>(numPlots*2);
		for (Rectangle rect : rectangles) {
			events.add(new Event(rect.x1, Type.BEGIN, rect));
			events.add(new Event(rect.x2, Type.END, rect));
		}
		
		while (!events.isEmpty()) {
			Event event = events.poll();
			Rectangle rect = event.rect;
			int start = yCompressor.compress(rect.y1);
			int end = yCompressor.compress(rect.y2);
			//System.out.println(start + " " + end + " is added");
			if (event.type == Type.BEGIN) {
				segmentTree.RangeInsert(start, end-1, 1);
			} else { // END
				segmentTree.RangeInsert(start, end-1, -1);
			}
			//System.out.println("Segment Tree for event " + event.point);
			//System.out.println(segmentTree);
			double length = segmentTree.query();
			Event next = events.peek();
			if (next == null) {
				
			} else {
				double curX = event.point;
				double nextX = next.point;
				double area = length * (nextX-curX);
				//System.out.println(length + " " + area);
				totalArea += length * (nextX-curX);
			}
		}
		
		System.out.printf("%.2f\n", totalArea);
	}
	
	enum Type {BEGIN, END};
	
	static class Event implements Comparable<Event> {
		final double point;
		final Type type;
		final Rectangle rect;
		public Event(double pt, Type t, Rectangle r) {
			point = pt;
			type = t;
			rect = r;
		}
		@Override
		public int compareTo(Event o) {
			return Double.compare(point, o.point);
		}
	}
	
	static Compressor<Double> yCompressor;

	static class MinLazySegmentTree {
		int left;
		int right;
		int minValue;
		int lazyAdd;
		MinLazySegmentTree LeftTree;
		MinLazySegmentTree RightTree;

		public MinLazySegmentTree(int start, int end) {
			left = start;
			right = end;
			minValue = 0;
			lazyAdd = 0;
			if (start != end) {
				LeftTree = new MinLazySegmentTree(start, start + (end - start)
						/ 2);
				RightTree = new MinLazySegmentTree(start + (end - start) / 2
						+ 1, end);
			}
		}

		public void RangeInsert(int start, int end, int value) {
			if (start <= left && end >= right) {
				lazyAdd += value;
				minValue += value;
			}
			if (left > end || right < start) {
				return;
			} else {
				if (LeftTree != null && RightTree != null) {
					LeftTree.RangeInsert(start, end, value);
					RightTree.RangeInsert(start, end, value);
				}
			}
		}

		public double query() {
			if (minValue >= 1) {
				//System.out.println(left + " " + right + " " + decompressRange(left, right+1));
				return decompressRange(left, right+1);
			}
			if(LeftTree != null){
				return LeftTree.query() + RightTree.query();
			}
			return 0;
		}
		public String toString(){
			//System.out.println(minValue);
			if(LeftTree != null){
				RightTree.toStringHelper(1);
				//System.out.println("*");
				LeftTree.toStringHelper(1);
			}
			return "";
		}
		public void toStringHelper(int tabs){
			//System.out.println(tabGen(tabs) + minValue );
			if(LeftTree != null){
				RightTree.toStringHelper(tabs+1);
				//System.out.println(tabGen(tabs) + "*");
				LeftTree.toStringHelper(tabs+1);
			}
		}
		public String tabGen(int tab){
			String output = "";
			for(int a = 0; a < tab; a++){
				output += "\t";
			}
			return output;
		}
		public double decompressRange(int start, int end) {
			return yCompressor.uncompress(end) - yCompressor.uncompress(start);
		}
	}

	static class Compressor<T extends Comparable<T>> {
		Map<T, Integer> real2Compressed;
		List<T> compressed2real;

		Compressor(List<T> realC) {
			real2Compressed = new HashMap<>(realC.size()*2);
			compressed2real = new ArrayList<>(realC.size());
			Collections.sort(realC);
			int cx = 0;
			T lastreal = null;
			for (T realx : realC) {
				if (lastreal == null || !lastreal.equals(realx)) {
					real2Compressed.put(realx, cx++);
					compressed2real.add(realx);
				}
				lastreal = realx;
			}
		}

		int compress(T real) {
			return real2Compressed.get(real);
		}

		T uncompress(int comp) {
			return compressed2real.get(comp);
		}
	}

	public static class Rectangle {
		double x1;
		double y1;
		double x2;
		double y2;

		public Rectangle(double x1, double y1, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}
}
/*
3
0 100 300 200
100 0 200 300
500 500 1000 1000
*/
