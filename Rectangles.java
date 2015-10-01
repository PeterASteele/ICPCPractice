import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
//https://open.kattis.com/problems/rectangleland
public class Rectangles {
	public static void main(String[] args) {
		Scanner input = new Scanner();
		int numCases = input.nextInt();
		for (int cases = 0; cases < numCases; cases++) {
			////System.out.println("start");
			int numberOfRect = input.nextInt();
			Quad[] Storage = new Quad[numberOfRect * 2];
			ArrayList<Integer> yValues = new ArrayList<Integer>();
			for (int a = 0; a < numberOfRect; a++) {
				int xS = input.nextInt();
				int yS = input.nextInt();
				int xE = input.nextInt();
				int yE = input.nextInt();
				int payload = input.nextInt();
				Storage[2 * a] = new Quad(xS, yS, yE, payload);
				Storage[2 * a + 1] = new Quad(xE, yS, yE, -1 * payload);
				yValues.add(yE);
				yValues.add(yS);
			}
			Collections.sort(yValues);
			//System.out.println(yValues);
			for(int a = 0; a < 2*numberOfRect; a++){
				//transform into the new grid.
				Storage[a].yStart = Collections.binarySearch(yValues, Storage[a].yStart);
				Storage[a].yEnd = Collections.binarySearch(yValues, Storage[a].yEnd);
			}

			////System.out.println("input done");
			Arrays.sort(Storage, new Comparator<Quad>() {
				@Override
				public int compare(Quad arg0, Quad arg1) {
					return arg0.x - arg1.x;
				}
			});
			////System.out.println("sort done");
			////System.out.println("tree created");
			Snode bigTree = new Snode(0, 262143, yValues);//
			long max = -1;
			long maxArea = 0;
			for (int a = 0; a < numberOfRect * 2; a++) {
				bigTree.rangeAdd(Storage[a].yStart, Storage[a].yEnd-1,
						Storage[a].payload);
				//System.out.println(Storage[a] + "\n" + bigTree);
				
				if (max < bigTree.getMax() && (Storage[a + 1].x - Storage[a].x) != 0) {
					maxArea = ((long)(bigTree.getMaxArea(bigTree.getMax(), 0, yValues)))
							* ((long)(Storage[a + 1].x - Storage[a].x));
					max = bigTree.getMax();
					//System.out.println("max is " + max + " X area from " + (Storage[a + 1].x) + " " + (Storage[a].x) + " Y " + (bigTree.getMaxArea(bigTree.getMax(), 0, yValues)) + " "  + (bigTree.getMaxArea(bigTree.getMax(), 0, yValues))
					//		* (Storage[a + 1].x - Storage[a].x));
					
				}
				else if (max == bigTree.getMax()) {
					maxArea += ((long)(bigTree.getMaxArea(bigTree.getMax(), 0, yValues)))
							* ((long)(Storage[a + 1].x - Storage[a].x));
				}
			}
			System.out.println(max + " " + maxArea);
		}
	}

	public static class Snode {
		public Snode left;
		public Snode right;
		public long maxSum;
		public long lazyAdd;
		public int leftNum;
		public int rightNum;
		public long maxSumVolume;
		public Snode(int Num, ArrayList<Integer> yIndex) {
			maxSumVolume = (Num+1 < yIndex.size())?yIndex.get(Num+1)-yIndex.get(Num):0;
			left = null;
			right = null;
			maxSum = 0;
			lazyAdd = 0;
			leftNum = Num;
			rightNum = Num;
		}

		public Snode(int leftX, int rightX, ArrayList<Integer> yIndex) {
			maxSumVolume = (rightX+1 < yIndex.size() && leftX < yIndex.size())?yIndex.get(rightX+1)-yIndex.get(leftX):0;
			left = null;
			right = null;
			maxSum = 0;
			lazyAdd = 0;
			leftNum = leftX;
			rightNum = rightX;
			if (rightX - leftX > 1) {
				left = new Snode(leftX, leftX + (rightX - leftX) / 2, yIndex);
				right = new Snode(leftX + (rightX - leftX) / 2 + 1, rightX, yIndex);
			} else {
				left = new Snode(leftX, yIndex);
				right = new Snode(rightX, yIndex);
			}
		}

		public long getMax() {
			return maxSum;
		}

		public long getMaxArea(long l, int temp, ArrayList<Integer> yIndex) {
			return maxSumVolume;
			// ////System.out.println("Looking for sum of " + sum + " currently at "
			// + temp + " (" + leftNum + "," + rightNum + ")");
			/*
			if (maxSum + lazyAdd + temp != sum) {
				return 0;
			}
			if (temp + lazyAdd == sum) {
				if(rightNum+1 < yIndex.size()){
					return yIndex.get(rightNum+1) - yIndex.get(leftNum);
				}
				else{
					return 0;
				}
			} else {
				if (left != null) {
					return left.getMaxArea(sum, temp + lazyAdd, yIndex)
							+ right.getMaxArea(sum, temp + lazyAdd, yIndex);
				}
				return 0;
			}*/
		}

		public String toString() {
			if (left != null && right != null) {
				return "[" + "(" + leftNum + ", " + rightNum + ")" + maxSum + " " + maxSumVolume + " " 
						+ lazyAdd + "\n" + left.toString("\t|")
						+ right.toString("\t|");
			}
			if (left == null && right == null) {
				return "[" + "(" + leftNum + ", " + rightNum + ")" + maxSum+ " " + maxSumVolume + " " 
						+ lazyAdd + "\n";
			}
			if (left == null) {
				return "[" + "(" + leftNum + ", " + rightNum + ")" + maxSum+ " " + maxSumVolume + " " 
						+ lazyAdd + "\n" + right.toString("\t|");
			}
			if (right == null) {
				return "[" + "(" + leftNum + ", " + rightNum + ")" + maxSum+ " " + maxSumVolume + " " 
						+ lazyAdd + "\n" + left.toString("\t|");
			}
			return "haha";
		}

		public String toString(String tab) {
			if (left != null && right != null) {
				return tab + "[" + "(" + leftNum + ", " + rightNum + ")"
						+ maxSum + " " + maxSumVolume + " " + lazyAdd + "\n" + left.toString(tab + "\t|")
						+ right.toString(tab + "\t|");
			}
			if (left == null && right == null) {
				return tab + "[" + "(" + leftNum + ", " + rightNum + ")"
						+ maxSum + " " + maxSumVolume + " " + lazyAdd + "\n";
			}
			if (left == null) {
				return tab + "[" + "(" + leftNum + ", " + rightNum + ")"
						+ maxSum + " " + maxSumVolume + " " + lazyAdd + "\n" + right.toString(tab + "\t|");
			}
			if (right == null) {
				return tab + "[" + "(" + leftNum + ", " + rightNum + ")"
						+ maxSum + " " + maxSumVolume + " " + lazyAdd + "\n" + left.toString(tab + "\t|");
			}
			return "haha";
		}

		public String tabGen(int count) {
			String temp = "";
			for (int a = 0; a < count; a++) {
				temp += "\t";
			}
			return temp;
		}

		public void rangeAdd(int x, int y, int value) {
			if (x <= leftNum && y >= rightNum) {
				lazyAdd += value;
				return;
			}
			if ((left == null && right == null) || x > rightNum || y < leftNum) {
				return;
			}
			left.rangeAdd(x, y, value);
			right.rangeAdd(x, y, value);
			rangeAddUpdate(x, y);
		}

		public void rangeAddUpdate(int x, int y) {
			maxSum = Math.max(right.maxSum + right.lazyAdd, left.maxSum
					+ left.lazyAdd);
			maxSumVolume = (((right.maxSum+right.lazyAdd) == maxSum)?right.maxSumVolume:0) +  (((left.maxSum+left.lazyAdd) == maxSum)?left.maxSumVolume:0);
			//System.out.println("Updating node " + leftNum +  " " + rightNum + " maxSum is " + maxSum + " from " + right.maxSum + " " + right.lazyAdd + " " + left.maxSum + " " + left.lazyAdd);
		}
	}

	public static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		public Scanner(Reader in) {
			br = new BufferedReader(in);
		}

		public Scanner() {
			this(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		// Slightly different from java.util.Scanner.nextLine(),
		// which returns any remaining characters in current line,
		// if any.
		String readNextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	public static class Quad {
		public int x;
		public int yStart;
		public int yEnd;
		public int payload;

		public Quad(int x, int yS, int yE, int payload) {
			this.x = x;
			this.yStart = yS;
			this.yEnd = yE;
			this.payload = payload;
		}
		public String toString(){
			return "X is " + x + " y is " + yStart + ":" + yEnd + " payload is " + payload;
		}
	}

}
