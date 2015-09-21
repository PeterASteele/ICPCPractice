
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * A flow layout manager takes rectangular objects and places them in a rectangular window from left to right. If there isn’t enough room in one row for an object, it is placed completely below all the objects in the first row at the left edge, where the order continues from left to right again. Given a set of rectangular dimensions and a maximum window width, you are to write a program that computes the dimensions of the final window after all the rectangles have been placed in it.

For example, given a window that can be at most 35 units wide, and three rectangles with dimensions 10×5, 20×12, and 8×13, the flow layout manager would create a window that looked like the figures below after each rectangle was added.

\includegraphics[width=.3\textwidth ]{fig1a.png}   \includegraphics[width=.3\textwidth ]{fig1b.png}   \includegraphics[width=.3\textwidth ]{fig1c.png}
The final dimensions of the resulting window are 30×25, since the width of the first row is 10+20=30 and the combined height of the first and second rows is 12+13=25.

Input
The input consists of one or more sets of data, followed by a final line containing only the value 0. Each data set starts with a line containing an integer, m, 1≤m≤80, which is the maximum width of the resulting window. This is followed by at least one and at most 15 lines, each containing the dimensions of one rectangle, width first, then height. The end of the list of rectangles is signaled by the pair -1 -1, which is not counted as the dimensions of an actual rectangle. Each rectangle is between 1 and 80 units wide (inclusive) and between 1 and 100 units high (inclusive).

Output
For each input set print the width of the resulting window, followed by a space, then the lowercase letter “x”, followed by a space, then the height of the resulting window.
 */
public class FlowLayout {
	public static void main(String[] args) {
		Scanner input = new Scanner();
		int log = 0;
		while((log = input.nextInt()) != 0){
			ArrayList<Integer> pairX = new ArrayList<Integer>();
			ArrayList<Integer> pairY = new ArrayList<Integer>();
			int temp1 = 0;
			int temp2 = 0;
			while(true){
				temp1 = input.nextInt(); 
				temp2 = input.nextInt();
				if(temp1 == -1 && temp2 == -1){
					break;
				}
				pairX.add(temp1);
				pairY.add(temp2);
			}	
			//System.out.println(log);
			int maxXPossible = log;
			int maxX = 0;
			int maxY = 0;
			int currentX = 0;
			int currentY = 0;
			for(int a = 0; a < pairX.size(); a++){
				
				if(currentX + pairX.get(a) > maxXPossible){ //go to next line
					maxY += currentY;
					if(currentX > maxX){
						maxX = currentX;
					}
					currentX = 0;
					currentY = 0;
					a--;
					
				}
				else{
					currentX += pairX.get(a);
					if(currentY < pairY.get(a)){
						currentY = pairY.get(a);
					}
				}	
				//System.out.println(currentX + " " + currentY);
			}
			maxY += currentY;
			if(currentX > maxX){
				maxX = currentX;
			}
			System.out.println(maxX + " x " + maxY);
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
