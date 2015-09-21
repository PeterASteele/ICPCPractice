
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * Bill and Ted are taking a road trip. But the odometer in their car is broken, so they don’t know how many miles they have driven. Fortunately, Bill has a working stopwatch, so they can record their speed and the total time they have driven. Unfortunately, their record keeping strategy is a little odd, so they need help computing the total distance driven. You are to write a program to do this computation.

For example, if their log shows

Speed in miles per hour
Total elapsed time in hours
20
2
30
6
10
7
this means they drove 2 hours at 20 miles per hour, then 6−2=4 hours at 30 miles per hour, then 7−6=1 hour at 10 miles per hour. The distance driven is then 2⋅20+4⋅30+1⋅10=40+120+10=170 miles. Note that the total elapsed time is always since the beginning of the trip, not since the previous entry in their log.

Input
The input consists of one or more data sets. Each set starts with a line containing an integer n, 1≤n≤10, followed by n pairs of values, one pair per line. The first value in a pair, s, is the speed in miles per hour and the second value, t, is the total elapsed time. Both s and t are integers, 1≤s≤90 and 1≤t≤12. The values for t are always in strictly increasing order. A value of −1 for n signals the end of the input.

Output
For each input set, print the distance driven, followed by a space, followed by the word “miles”.
 */
public class SpeedLimit {
	public static void main(String[] args) {
		Scanner input = new Scanner();
		int log = 0;
		int prevTime = 0;
		int total = 0;
		while((log = input.nextInt()) != -1){
			prevTime = 0;
			total = 0;
			for(int a = 0; a < log; a++){
				int speed = input.nextInt();
				int time = input.nextInt();
				int time2 = time - prevTime;
				prevTime = time;
				total += time2 * speed;
			}
			System.out.println(total + " miles");
		}
		
	}

	//////Fastscanner class.
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

}
