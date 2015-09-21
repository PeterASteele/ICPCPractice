
	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * Your chemistry lab instructor is a very enthusiastic graduate student who clearly has forgotten what their undergraduate Chemistry 101 lab experience was like. Your instructor has come up with the brilliant idea that you will monitor the temperature of your mixture every minute for the entire lab. You will then plot the rate of change for the entire duration of the lab.

Being a promising computer scientist, you know you can automate part of this procedure, so you are writing a program you can run on your laptop during chemistry labs. (Laptops are only occasionally dissolved by the chemicals used in such labs.) You will write a program that will let you enter in each temperature as you observe it. The program will then calculate the difference between this temperature and the previous one, and print out the difference. Then you can feed this input into a simple graphing program and finish your plot before you leave the chemistry lab.

Input
The input is a series of temperatures, one per line, ranging from -10 to 200. The temperatures may be specified up to two decimal places. After the final observation, the number 999 will indicate the end of the input data stream. All data sets will have at least two temperature observations.

Output
Your program should output a series of differences between each temperature and the previous temperature. There is one fewer difference observed than the number of temperature observations (output nothing for the first temperature). Differences are always output to two decimal points, with no leading zeroes (except for the ones place for a number less than 1, such as 0.01) or spaces.

After the final output, print a line with “End of Output.”
 */

	public class ASimpleQuestionOfChemistry {
		public static void main(String[] args){
			Scanner input = new Scanner();
			double temp = -100;
			while(true){
				double temp2 = input.nextDouble();
				if(temp2 == 999){
					break;
				}
				if(temp != -100){
					System.out.println(String.format("%.2f", temp2-temp));
				}
				temp = temp2;
			}
			System.out.println("End of Output");			
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


