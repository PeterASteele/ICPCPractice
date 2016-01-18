import java.util.Scanner;
import java.util.TreeMap;
import java.util.*;
//http://codeforces.com/gym/100863/attachments, Problem A
public class AgeOfRetirement {
	//@Author Chris Wu (chriswu)
	static int[] daysInMonth = new int[] {
		0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
	};

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
//		System.out.println(new Date(29, 2, 2015));
//		System.out.println(new Date(29, 2, 2016));
//		System.out.println(new Date(29, 2, 2016).accelerate(12, 4));
		
//		System.out.println(new Date(1, 2, 2016).minus(new Date(2, 1, 2016)));
//		System.out.println(new Date(2, 2, 2016).minus(new Date(2, 1, 2016)));
//		System.out.println(new Date(3, 2, 2016).minus(new Date(2, 1, 2016)));
		
		int M = input.nextInt();
		int N = input.nextInt();
		int K = input.nextInt();
		String[] date = input.next().split("\\.");
		int birthDay = Integer.parseInt(date[0]);
		int birthMonth = Integer.parseInt(date[1]);
		int birthYear = Integer.parseInt(date[2]);
		Date birthDate = new Date(birthDay, birthMonth, birthYear);
		
		int retirementAgeMonths = 60*12;
		
		TreeMap<Date, Integer> retirementAgeAt = new TreeMap<>();
		retirementAgeAt.put(new Date(0, 0, 0), retirementAgeMonths);
		Date cur = new Date(1, 1, 2012);
		for (int idx = 1; idx <= K; idx++) {
			retirementAgeMonths += M;
			retirementAgeAt.put(cur, retirementAgeMonths);
			cur = cur.accelerate(N, 0);
		}
		
//		System.out.println(retirementAgeAt);
		
		int retireDay = birthDay;
		int retireMonth = birthMonth;
		int retireYear = birthYear + 60;
		Date curRetirementAge = new Date(retireDay, retireMonth, retireYear);
		while (true) {
			int curAgeMonths = curRetirementAge.minus(birthDate);
			int retirementAgeNeeded = retirementAgeAt.floorEntry(curRetirementAge).getValue();
			if (curAgeMonths >= retirementAgeNeeded) break;
			curRetirementAge = birthDate.accelerate(retirementAgeNeeded, 0);
		}
		
		System.out.println(curRetirementAge);
	}
	
	static class Date implements Comparable<Date> {
		int day, month, year;
		public Date(int d, int m, int y) {
			day = d;
			month = m;
			year = y;
			
			// Normalize
			year += (month-1) / 12;
			month = (month-1) % 12 + 1;
			
			
			if (isLeapYear(year)) {
				daysInMonth[2] = 29;
			}
			if (day > daysInMonth[month]) {
				day = 1;
				month++;
				if (month > 12) {
					month -= 12;
					year++;
				}
			}
			
			daysInMonth[2] = 28;
		}
		
		// In months
		public int minus(Date o) {
			int diff = (year - o.year) * 12;
			diff += (month - o.month);
			if (o.day > day) diff--;
			return diff;
		}
		
		public Date accelerate(int months, int years) {
			return new Date(day, month+months, year+years);
		}
		
		@Override
		public int compareTo(Date o) {
			int cmp = Integer.compare(year, o.year);
			if (cmp != 0) return cmp;
			cmp = Integer.compare(month, o.month);
			if (cmp != 0) return cmp;
			return Integer.compare(day, o.day);
		}
		
		public String toString() {
			return String.format("%02d.%02d.%04d\n", day, month, year);
		}
	}
	
	static boolean isLeapYear(int year) {
		return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
	}

}
