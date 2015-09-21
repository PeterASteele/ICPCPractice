
	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
 * Cantoring Along
Time Limit: 1 seconds

The Cantor set was discovered by Georg Cantor. It is one of the simpler fractals. It is the result of an infinite process, so for this program, printing an approximation of the whole set is enough.

The following steps describe one way of obtaining the desired output for a given order Cantor set:

Start with a string of dashes, with length 3order

Replace the middle third of the line of dashes with spaces. You are left with two lines of dashes at each end of the original string.

Replace the middle third of each line of dashes with spaces. Repeat until the lines consist of a single dash.

For example, if the order of approximation is 3, start with a string of 27 dashes:

---------------------------
Remove the middle third of the string:

--------- ---------
and remove the middle third of each piece:

--- --- --- ---
and again:

- - - - - - - -
The process stops here, when the groups of dashes are all of length 1. You should not print the intermediate steps in your program. Only the final result, given by the last line above, should be displayed.

Input
Each line of input will be a single number between 0 and 12, inclusive, indicating the order of the approximation. The input stops when end-of-file is reached.

Output
You must output the approximation of the Cantor set, followed by a newline. There is no whitespace before or after your Cantor set approximation. The only characters that should appear on your line are ’-’ and ’ ’. Each set is followed by a newline, but there should be no extra newlines in your output.
 */
	public class CantoringAlong {
		public static void main(String[] args){
			Scanner input = new Scanner();
			int temp = 0;
			try{
				while((temp = input.nextInt()) != -1){
					System.out.println(getString(temp, "-"));
				}
			}
			catch(Exception e){
				
			}
		}
		public static String getString(int a, String b){
			if(a == 0){
				return b;
			}
			else{
				String temp = getString(a-1, b);
				StringBuffer ret = new StringBuffer();
				ret.append(temp);
				for(int c = 0; c < temp.length(); c++){
					ret.append(' ');
				}
				ret.append(temp);
				return ret.toString();
			}
		}
		
		public static class Scanner {
	        BufferedReader br;
	        StringTokenizer st;

	        public Scanner(Reader in) {
	            br = new BufferedReader(in);
	        }

	        public Scanner() { this(new InputStreamReader(System.in)); }

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

	        int nextInt() { return Integer.parseInt(next()); }
	        long nextLong() { return Long.parseLong(next()); }
	        double nextDouble() { return Double.parseDouble(next()); }

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


