import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * https://open.kattis.com/problems/tutorial
 * ACM-ICPC returns to Singapore in 2015 after a long absence. There may be new contestants from this region who are joining ACM-ICPC for the very first time1. This problem serves as a tutorial for such contestants.

First, let establish the fact that the problems posed in ICPC are not research problems where nobody on earth knows how to solve them efficiently. Some people (at least the problem authors) have solved these problems before. There can be more than one possible solution to these problems. As the contest has limited time (5 hours) and each problem has an associated time penalty, it is always beneficial to pick the easiest problem to solve first2.

Some problems may look complicated but happen to have a small input size constraint n that allows even a naïve brute force solution to pass. Some other problems may look simple but standard textbook algorithm cannot be used to pass the time limit as the input size constraint n is too big and one has to figure out the special properties that simplify the problem.

In the “Competitive Programming” book3 that has been written specifically for preparing for programming contests such as ICPC, we have the following compilation of typical algorithm complexities found in programming contests:

t
algorithm complexity for input size n
1
O(n!)
2
O(2n)
3
O(n4)
4
O(n3)
5
O(n2)
6
O(nlog2n)
7
O(n)
For this problem, we ignore the constant factor and the lower terms hidden in the Big O notation, i.e. an O(g(n)) algorithm is assumed to perform exactly g(n) operations.

Let m be the number of operations that the computer used in the contest4 can run in one second. Suppose m=100000000 and the team is trying to solve a problem with a time limit of one second. If the team can devise an algorithm of type t=3, i.e., a rather slow O(n4) algorithm, but the largest n mentioned in the problem description is just 50, then this algorithm is actually fast enough and will get “Accepted” since 504=6250000 is less than (or equal to) m.

However, if for another problem also with one second time limit, the team can only devise an algorithm of type t=5, i.e. an O(n2) algorithm, but the largest n mentioned in the problem description is 10001, then this algorithm is likely not fast enough to pass the time limit and will get “Time Limit Exceeded”, since 100012=100020001 which is greater than m.


Formally, given three integer parameters m (1≤m≤109), n (1≤n≤109), and t∈[1..7], decide if an algorithm of type t with time complexity as described in the table above can pass the time limit of one second, that is, performs less than (or equal to) m operations. Output “AC” (that stands for “Accepted”) if that is the case, or “TLE” (that stands for “Time Limit Exceeded”) otherwise.

Input
The input consists of three integers in one line: m, n, and t as elaborated above.

Output
Output a single string “AC” or “TLE” in one line as elaborated above.

Sample Input 1	Sample Output 1
100000000 500 3
TLE
Sample Input 2	Sample Output 2
100000000 50 3
AC
Sample Input 3	Sample Output 3
100000000 10001 5
TLE
Sample Input 4	Sample Output 4
100000000 10000 5
AC
Sample Input 5	Sample Output 5
19931568 1000000 6
TLE
Sample Input 6	Sample Output 6
19931569 1000000 6
AC
Footnotes

Note that if this is the very first time that your University sends a team to compete in an ACM-ICPC regional contest, you should ask your coach to inform the Regional Contest Director to be eligible for the “Best Newcomer Team” award.
ICPC SG Regional Contest 15 has “First Team to Solve Problem [A/B/C,...]” awards. Note that the presence of such awards may lead some teams to strategically solve a “medium difficulty” problem first at the early stage of the contest, hoping that they end up solving that problem first thus getting the award.
Teams who advance from this ICPC SG Preliminary Contest 15 will receive a sponsored copy of the Competitive Programming 3 book at the onsite registration of ICPC SG Regional Contest 15.
Usually, experienced teams will perform tests during practice contest to measure m.
 */
public class Tutorial {




	public static void main(String[] args) {
		Scanner input = new Scanner();
		int numberOfOperations = input.nextInt();
		int n = input.nextInt();
		int type = input.nextInt();
		boolean limitExceeded = false;
		if(type == 1){
			if(n > 13){
				limitExceeded = true;
			}
			else{
				if(factorial(n) <= numberOfOperations){
					limitExceeded = false;
				}
				else{
					limitExceeded = true;
				}
			}
		}	
		if(type == 2){
			if(n > 30){
				limitExceeded = true;
			}
			else{
				if(Math.pow(2, n) <= numberOfOperations){
					limitExceeded = false;
				}
				else{
					limitExceeded = true;
				}
			}
		}
		if(type == 3){
			if(n > 211){
				limitExceeded = true;
			}
			else{
				if(Math.pow(n, 4) <= numberOfOperations){
					limitExceeded = false;
				}
				else{
					limitExceeded = true;
				}
			}
		}
		if(type == 4){
			if(n > 1259){
				limitExceeded = true;
			}
			else{
				if(Math.pow(n, 3) <= numberOfOperations){
					limitExceeded = false;
				}
				else{
					limitExceeded = true;
				}
			}
		}
		if(type == 5){
			if(n > 44721){
				limitExceeded = true;
			}
			else{
				if(Math.pow(n, 2) <= numberOfOperations){
					limitExceeded = false;
				}
				else{
					limitExceeded = true;
				}
			}
		}
		if(type == 6){
			if(n > 70000000){
				limitExceeded = true;
			}
			else{
				if(Math.log(n)/Math.log(2) * (n + 0.0) <= numberOfOperations){
					limitExceeded = false;
				}
				else{
					limitExceeded = true;
				}
			}
		}
		if(type == 7){
			if(n > 1500000000){
				limitExceeded = true;
			}
			else{
				if(n <= numberOfOperations){
					limitExceeded = false;
				}
				else{
					limitExceeded = true;
				}
			}
		}
		if(limitExceeded){
			System.out.println("TLE");
		}
		else{
			System.out.println("AC");
		}
	}
	public static int factorial(int n){
		int total = 1;
		for(int a = 1; a <= n; a++){
			total = total * a;
		}
		return total;
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
