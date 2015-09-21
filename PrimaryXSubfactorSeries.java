//Gets the correct answer, but this solution times out.
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * Let n be any positive integer. A factor of n is any number that divides evenly into n, without leaving a remainder. For example, 13 is a factor of 52, since 52/13 = 4. A subsequence of n is a number without a leading zero that can be obtained from n by discarding one or more of its digits. For example, 2, 13, 801, 882, and 1324 are subsequences of 8013824, but 214 is not (you can’t rearrange digits), 8334 is not (you can’t have more occurrences of a digit than appear in the original number), 8013824 is not (you must discard at least one digit), and 01 is not (you can’t have a leading zero). A subfactor of n is an integer greater than 1 that is both a factor and a subsequence of n. 8013824 has subfactors 8, 13, and 14. Some numbers do not have a subfactor; for example, 6341 is not divisible by 6, 3, 4, 63, 64, 61, 34, 31, 41, 634, 631, 641, or 341.

An x-subfactor series of n is a decreasing series of integers n1,…,nk, in which (1) n=n1, (2) k≥1, (3) for all 1≤i<k, ni+1 is obtained from ni by first discarding the digits of a subfactor of ni, and then discarding leading zeros, if any, and (4) nk has no subfactor. The term “x-subfactor” is meant to suggest that a subfactor gets x’ed, or discarded, as you go from one number to the next. For example, 2004 has two distinct x-subfactor series, the second of which can be obtained in two distinct ways. The highlighted digits show the subfactor that was removed to produce the next number in the series.

2004 4
2004 200 0
2004 200 0

The primary x-subfactor series has maximal length (the largest k possible, using the notation above). If there are two or more maximal-length series, then the one with the smallest second number is primary; if all maximal-length series have the same first and second numbers, then the one with the smallest third number is primary; and so on. Every positive integer has a unique primary x-subfactor series, although it may be possible to obtain it in more than one way, as is the case with 2004.

Input
The input consists of one or more positive integers, each less than one billion, without leading zeroes, and on a line by itself. Following is a line containing only “0” that signals the end of the input.

Output
For each positive integer, output its primary x-subfactor series using the exact format shown in the examples below.
 */
public class PrimaryXSubfactorSeries {
	static ArrayList<Integer> recordResult;
	public static void main(String[] args) {
		Scanner input = new Scanner();
		/*ArrayList<StringBuffer> a = subtract2("", 0, 0,  12341234, 1234);
		for(StringBuffer b: a){
			System.out.println(b);
		}*/
		
		int log = 0;
		while((log = input.nextInt()) != 0){
		//while((log = ((int) (Math.random() * 100000000))) < 999000000){
			recordResult = new ArrayList<Integer>();
			ArrayList<Integer> answers = solve(log, new ArrayList<Integer>());
			for(int a = 0; a < recordResult.size(); a++){
				if(a != answers.size()-1){
					System.out.print(recordResult.get(a) + " ");
				}
				else{
					System.out.print(recordResult.get(a));
				}
			}
			System.out.println();
		}	
		
	}
	public static void updateRecordResult(ArrayList<Integer> recordResultNew){
		//System.out.println(recordResultNew);
		if(losesTo(recordResult, recordResultNew)){
			//System.out.println(recordResult + " has been replaced by " + recordResultNew);
			recordResult = clone(recordResultNew);
		}
	}
	public static ArrayList<Integer> solve(int log, ArrayList<Integer> prevSequence){
		//System.out.println("test");
		if(prevSequence.size() + (log+"").length() < recordResult.size()){
			return prevSequence;
		}
		if(log == -1){
			//System.out.println(prevSequence);
			ArrayList<Integer> temp3 = clone(prevSequence);
			if(losesTo(recordResult, temp3)){
				updateRecordResult(temp3);	
			}
			return prevSequence;
		}
		
		
		ArrayList<Integer> logFactors = factor(log);
		ArrayList<Integer> output = new ArrayList<Integer>();
		for(int a = 0; a < logFactors.size(); a++){
			//System.out.println("Checking " + logFactors.get(a));
			if(isSubsequence(logFactors.get(a), log)){
				//System.out.println("It is good");
				output.add(logFactors.get(a));
			}
		}
		if(output.size() > 0){
		for(Integer a: output){
			ArrayList<StringBuffer> temp = subtract2("", 0, 0, log, a);
			for(StringBuffer current: temp){
				prevSequence.add(log);
				int tempInt = current.toString().equals("")?-1:Integer.parseInt(current.toString());
				ArrayList<Integer> temp2 = solve(tempInt, prevSequence);
				if(losesTo(recordResult, temp2)){
					updateRecordResult(temp2);	
				}
				prevSequence.remove(prevSequence.size()-1);
			}
		}
		}
		else{
			
			ArrayList<Integer> temp3 = clone(prevSequence);
			temp3.add(log);
			if(losesTo(recordResult, temp3)){
				updateRecordResult(temp3);	
			}
		}
		//System.out.println(recordResult);
		return recordResult;
	}
	public static ArrayList<Integer> clone(ArrayList<Integer> a){
		ArrayList<Integer> output = new ArrayList<Integer>();
		for(Integer b: a){
			output.add(b);
		}
		return output;
	}
	public static int subtract(int big, int small){
		ArrayList<StringBuffer> results = new ArrayList<StringBuffer>();
		StringBuffer output = new StringBuffer();
		String big1 = "" + big;
		String small1 = "" + small;
		int index = 0;
		for(int a = 0; a < big1.length(); a++){
			if(index < small1.length()){
			if(big1.charAt(a) == small1.charAt(index)){
				index++;
			}
			else{
				output.append(big1.charAt(a));
			}
			}
			else{
				output.append(big1.charAt(a));
			}
		}
		if(output.length() == 0){
			return -1;
		}
		return Integer.parseInt(output.toString());
	}
	public static ArrayList<StringBuffer> subtract2(String input, int index1, int index2, int big, int small){
		ArrayList<StringBuffer> results = new ArrayList<StringBuffer>();
		String big2 = "" + big;
		String small2 = "" + small;
		if(big2.length() - index1 < small2.length() - index2){
			return new ArrayList<StringBuffer>();
		}
		if(index1 >= big2.length() && index2 >= small2.length()){
			results.add(new StringBuffer());
			results.get(0).append(input);
			return results;
		}
		if(index1 >= big2.length()){
			if(index2 < small2.length()){
				return new ArrayList<StringBuffer>();
			}
		}
		if(index2 >= small2.length()){			
			if(index1 < big2.length()){
				//System.out.println("done but increaseing");
				results.addAll(subtract2(input+ big2.charAt(index1), index1+1, index2, big, small));
				return results;
			}
			else{
				return results;
			}
		}
		
		if(big2.charAt(index1) == small2.charAt(index2)){
			results.addAll(subtract2(input, index1 + 1, index2 + 1, big, small));
			results.addAll(subtract2(input+ big2.charAt(index1), index1+1, index2, big, small));
		}
		else{
			results.addAll(subtract2(input+ big2.charAt(index1), index1+1, index2, big, small));
		}
		
		
		return results;
	}
	
	
	public static boolean losesTo(ArrayList<Integer> record, ArrayList<Integer> challenger){
		
		
		if(record.size() > challenger.size()){
			return false;
		}
		if(record.size() < challenger.size()){
			return true;
		}
		for(int a = 0; a < record.size(); a++){
			if(!record.get(a).equals(challenger.get(a))){
				if(record.get(a) < challenger.get(a)){
					return false;
				}
				else{
					//System.out.println("Beat because " + record.get(a) + " is bigger than " + challenger.get(a));
					return true;
				}
			}
		}
		return false;
	}
	public static ArrayList<Integer> factor(int n){
		ArrayList<Integer> results = new ArrayList<Integer>();
		results.add(n);
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) {
				results.add(i);
				results.add(n/i);
			}
		}
		
		return results;
	}
	public static boolean isSubsequence(int a3, int b){
		
		String a2 = "" + a3;
		String b2 = "" + b;
		if(a2.length() == 0 || b2.length() == 0){
			return false;
		}
		int index = 0;
		for(int a = 0; a < b2.length(); a++){
			if(index >= a2.length()){
				return true;
			}
			if(b2.charAt(a) == a2.charAt(index)){
				index++;
			}
		}
		if(index >= a2.length()){
			return true;
		}
		return false;
	}
	//////FastScanner class.
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
