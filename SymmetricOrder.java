
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * In your job at Albatross Circus Management (yes, it’s run by a bunch of clowns), you have just finished writing a program whose output is a list of names in nondescending order by length (so that each name is at least as long as the one preceding it). However, your boss does not like the way the output looks, and instead wants the output to appear more symmetric, with the shorter strings at the top and bottom and the longer strings in the middle. His rule is that each pair of names belongs on opposite ends of the list, and the first name in the pair is always in the top part of the list. In the first example set below, Bo and Pat are the first pair, Jean and Kevin the second pair, etc.

Input
The input consists of one or more sets of strings, followed by a final line containing only the value 0. Each set starts with a line containing an integer, n, which is the number of strings in the set, followed by n strings, one per line, sorted in nondescending order by length. None of the strings contain spaces. There is at least one and no more than 15 strings per set. Each string is at most 25 characters long.

Output
For each input set print “SET n” on a line, where n starts at 1, followed by the output set as shown in the sample output.
 */
public class SymmetricOrder {
	public static void main(String[] args) {
		Scanner input = new Scanner();
		int log = 0;
		int count = 1;
		while((log = input.nextInt()) != 0){
			String[] hold = new String[log];
			System.out.println("SET " + count);
			count++;
			for(int a = 0; a < log/2; a++){
				hold[a] = input.readNextLine();
				hold[log-a-1] = input.readNextLine();
			}
			if(log%2 != 0){
				hold[log/2] = input.readNextLine();
			}
			for(int a = 0; a < log; a++){
				System.out.println(hold[a]);
			}
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
