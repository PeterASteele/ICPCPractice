
	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * Spelling Be
Time Limit: 1 seconds

It’s a simple requirement your company has, really—every document should be spell-checked before it’s sent out to a customer. Unfortunately, while word processing documents are easily spell-checked, your employees have not been checking email every time they send out a message. So you’ve come up with a little improvement. You are going to write a program that will check email on its way out. You will spell-check each message, and if you find any spelling errors, it will be returned to the sender for correction.

When you announced this plan, one of your coworkers fell off their chair laughing, saying that you couldn’t possibly anticipate every name, technical acronym, and other terms that might appear in an email. Undaunted, however, you are going to test-run your code with an online dictionary and some sample emails you have collected.

Input
The input consists of two sections, the dictionary and the emails. The first line of input specifies the number of words in the dictionary, followed by that many lines, with one word per line. There is no whitespace before, after, or in any words, although there may be apostrophes or hyphens in the words, which are considered part of the word (i.e. “bobs” is different than “bob’s”. There will be no duplicate words. All words will be in lower case.

Following that are the emails. The first line of this section has the number of emails in the input. Following that line begins the first email. It has been preprocessed, so it consists of one word per line, with no punctuation (other than apostrophes and hyphens) or whitespace, and all words are in lower case. The last word in the email is followed by a line with only “-1”. Each email will have at least one word.

Output
For each email, you must either print:

Email X is spelled correctly.
where X begins with 1 and counts up. Or, if a word is found that is not in the dictionary, print out:

Email X is not spelled correctly.
followed by a list of unknown words in the order that you find them, one per line. If an unknown word is found multiple times, it should be printed multiple times.

There are no spaces between datasets. Following the output for the final dataset, print a line stating “End of Output”.
 */

	public class SpellingBe {
		public static void main(String[] args){
			Scanner input = new Scanner();
			ArrayList<String> dictionary = new ArrayList<String>();
		    
			int number = input.nextInt();
			
			for(int a = 0; a < number; a++){
				dictionary.add(input.readNextLine());
			}
			Collections.sort(dictionary);
			int emails = input.nextInt();
			for(int a = 0; a < emails; a++){
				String output = "";
				boolean OK = true;
				while(true){
					String temp2 = input.readNextLine();
					if(temp2.equals("-1")){
						break;
					}
					if(Collections.binarySearch(dictionary, temp2) < 0){
						OK = false;
						output += temp2 + "\r\n";
					}
				}
				if(OK){
					System.out.println("Email " + (a+1)+ " is spelled correctly.");
				}
				if(!OK){
					System.out.println("Email " + (a+1) + " is not spelled correctly.");
				}
				System.out.print(output);
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


