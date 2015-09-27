import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

/*https://open.kattis.com/problems/pivot
 * An O(n) Partition algorithm partitions an array A around a pivot element (pivot is a member of A) into three parts: a left sub-array that contains elements that are ≤ pivot, the pivot itself, and a right sub-array that contains elements that are > pivot.

A Partition algorithm is an integral part of a popular sorting algorithm Quicksort. Usually, the choice of pivot is randomized so that Quicksort has an expected O(nlogn) running time performance.

Now the actual job in this problem is this: Starting from an array A that has n distinct integers, we partition A using one of the member of A as pivot to produce a transformed array A’. Given this transformed array A’, your job is to count how many integers that could have been the chosen pivot.

For example, if A’ = {2,1,3,4,7,5,6,8}, then 3 integers: {3,4,8} could have been the pivot of partition, e.g. {2,1,3} to the left of integer 4 are smaller than 4 and {7,5,6,8} to the right of integer 4 are greater than 4. However, the other 5 integers cannot possibly be the pivot, e.g. integer 7 cannot possibly be the pivot as there are {5,6} to the right of integer 7.

Input
The input consists of two lines. The first line contains integer n (3≤n≤100000). The second line contains n distinct 32-bit signed integers that describes the transformed array A′.

Output
Output the required answer as a single integer in one line.

Sample Input 1	Sample Output 1
8
2 1 3 4 7 5 6 8
3
Sample Input 2	Sample Output 2
7
1 2 3 4 5 7 6
5
Download the sample data files
 */

public class Pivot {
	public static void main(String[] args) {
		Scanner input = new Scanner();
		int log = input.nextInt();
		int[] array = new int[log];
		for(int a = 0; a < log; a++){
			array[a] = input.nextInt();
		}
		boolean[] canidatesMin = new boolean[log];
		boolean[] canidatesMax = new boolean[log];
		int max = -1;
		int min = Integer.MAX_VALUE;
		for(int a = 0; a < log; a++){
			if(max <= array[a]){
				canidatesMin[a] = true;
			}
			else{
				canidatesMin[a] = false;
			}
			if(max < array[a]){
				max = array[a];
			}
		}
		for(int a = log-1; a >= 0; a--){
			if(min > array[a]){
				canidatesMax[a] = true;
			}
			else{
				canidatesMax[a] = false;
			}
			if(min > array[a]){
				min = array[a];
			}
		}
		int count = 0;
		for(int a = 0; a < log; a++){
			//System.out.println(canidatesMin[a] + " " + canidatesMax[a]);
			if(canidatesMin[a] && canidatesMax[a]){
				count++;
			}
		}
		System.out.println(count);
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
