import java.util.Scanner;

public class IVXLCDMProblem {
//http://codeforces.com/gym/100863/attachments, Problem I
	public static void main(String[] args) {
//I can't believe I wrote this
		// DEBUG TODO: DELETE
//		debug("CDC");
//		debug("MDCLXVI");
//		debug("MMMDCLXVI");
//		debug("IXV");
//		debug("IX"); // 9
//		debug("CMXCIX"); // 999
//		debug("DCM"); // invalid

		Scanner input = new Scanner(System.in);
		String inputData = input.nextLine();
		int IFreq = 0;
		int VFreq = 0;
		int XFreq = 0;
		int LFreq = 0;
		int CFreq = 0;
		int DFreq = 0;
		int MFreq = 0;
		for (int a = 0; a < inputData.length(); a++) {
			char temp = inputData.charAt(a);
			if (isOneOfTheseChars(temp, 'i', 'I')) {
				IFreq++;
			}
			if (isOneOfTheseChars(temp, 'v', 'V')) {
				VFreq++;
			}
			if (isOneOfTheseChars(temp, 'x', 'X')) {
				XFreq++;
			}
			if (isOneOfTheseChars(temp, 'l', 'L')) {
				LFreq++;
			}
			if (isOneOfTheseChars(temp, 'c', 'C')) {
				CFreq++;
			}
			if (isOneOfTheseChars(temp, 'd', 'D')) {
				DFreq++;
			}
			if (isOneOfTheseChars(temp, 'm', 'M')) {
				MFreq++;
			}
		}
		int count = 0;
		int maxLength = -1;
		String hold = "";
		while (count < 100000) {
			count++;
			StringBuilder temp = new StringBuilder();
			int MRemain = MFreq;
			int DRemain = DFreq;
			int CRemain = CFreq;
			int LRemain = LFreq;
			int XRemain = XFreq;
			int VRemain = VFreq;
			int IRemain = IFreq;

			int MNum = (int) (Math.random() * (Math.min(3, MRemain) + 1));
			for (int a = 0; a < MNum; a++) {
				temp.append('M');
				MRemain -= 1;
			}

			if (Math.random() > .5 && MRemain > 0 && CRemain > 0) {
				temp.append("CM");
				CRemain--;
				MRemain--;
			} else {
				int CNum = (int) (Math.random() * (Math.min(3, CRemain) + 1));
				if (DRemain > 0) {
					temp.append("D");
					DRemain -= 1;
				}
				for (int b = 0; b < CNum; b++) {
					temp.append("C");
					CRemain -= 1;
				}
			}

			if (Math.random() > .5 && CRemain > 0 && XRemain > 0) {
				temp.append("XC");
				XRemain--;
				CRemain--;
			} else {
				int XNum = (int) (Math.random() * (Math.min(3, XRemain) + 1));
				if (LRemain > 0) {
					temp.append("L");
					LRemain -= 1;
				}
				for (int b = 0; b < XNum; b++) {
					temp.append("X");
					XRemain -= 1;
				}
			}

			if (Math.random() > .5 && XRemain > 0 && IRemain > 0) {
				temp.append("IX");
				IRemain--;
				XRemain--;
			} else {
				int INum = (int) (Math.random() * (Math.min(3, IRemain) + 1));
				if (VRemain > 0) {
					temp.append("V");
					VRemain -= 1;
				}
				for (int b = 0; b < INum; b++) {
					temp.append("I");
					IRemain -= 1;
				}
			}
			String finished = temp.toString();
//			if (valid(finished)) {
				if (finished.length() == maxLength) {
					if (sum(finished) > sum(hold)) {
						hold = finished;
					}
				} else if (finished.length() > maxLength) {
					hold = finished;
					maxLength = finished.length();
				}
//			}
		}
		System.out.println(hold);

	}

	static void debug(String rNumeral) {
		System.out.printf("%s: %s\n", rNumeral, (valid(rNumeral) ? "VALID"
				: "INVALID"));
		if (valid(rNumeral))
			System.out.printf("Sum = %d\n", sum(rNumeral));
	}

	static boolean valid(String rNumeral) {
		int len = rNumeral.length();

		int countM = 0;
		int countD = 0;
		int countC = 0;
		int countL = 0;
		int countX = 0;
		int countV = 0;
		int countI = 0;
		boolean canUseM = true;
		boolean canUseD = true;
		boolean canUseC = true;
		boolean canUseL = true;
		boolean canUseX = true;
		boolean canUseV = true;
		boolean canUseI = true;

		for (int idx = 0; idx < len; idx++) {
			switch (rNumeral.charAt(idx)) {
			case 'M':
				if (!canUseM)
					return false;
				countM++;
				if (countM == 4)
					canUseM = false;
				break;
			case 'D':
				if (!canUseD)
					return false;
				countD++;
				canUseM = false;
				if (countD == 1)
					canUseD = false;
				break;
			case 'C':
				if (!canUseC)
					return false;
				countC++;
				if (idx < len - 1) {
					char next = rNumeral.charAt(idx + 1);
					if (next == 'M') {
						if (!canUseD)
							return false;
						canUseC = false;
						idx++;
					}
					if (next == 'D') {
						if (!canUseD)
							return false;
						canUseC = false;
						idx++;
					}
				}
				canUseM = false;
				canUseD = false;
				if (countC == 3)
					canUseC = false;
				break;
			case 'L':
				if (!canUseL)
					return false;
				countL++;
				canUseM = false;
				canUseD = false;
				canUseC = false;
				if (countL == 1)
					canUseL = false;
				break;
			case 'X':
				if (!canUseX)
					return false;
				countX++;
				if (idx < len - 1) {
					char next = rNumeral.charAt(idx + 1);
					if (next == 'M' || next == 'D')
						return false;
					if (next == 'C') {
						if (!canUseL)
							return false;
						canUseX = false;
						idx++;
					}
					if (next == 'L') {
						if (!canUseL)
							return false;
						canUseX = false;
						idx++;
					}
				}
				canUseM = false;
				canUseD = false;
				canUseC = false;
				canUseL = false;
				if (countX == 3)
					canUseX = false;
				break;
			case 'V':
				if (!canUseV)
					return false;
				countV++;
				canUseM = false;
				canUseD = false;
				canUseC = false;
				canUseL = false;
				canUseX = false;
				if (countV == 1)
					canUseV = false;
				break;
			case 'I':
				if (!canUseI)
					return false;
				countI++;
				if (idx < len - 1) {
					char next = rNumeral.charAt(idx + 1);
					if (next == 'M' || next == 'D' || next == 'C'
							|| next == 'L')
						return false;
					if (next == 'X') {
						if (!canUseV)
							return false;
						canUseI = false;
						idx++;
					}
					if (next == 'V') {
						if (!canUseV)
							return false;
						canUseI = false;
						idx++;
					}
				}
				canUseM = false;
				canUseD = false;
				canUseC = false;
				canUseL = false;
				canUseX = false;
				canUseV = false;
				if (countI == 3)
					canUseI = false;
				break;
			}
		}
		return true;
	}

	static boolean less(String a, String b) {
		return sum(a) < sum(b);
	}

	static int sum(String a) {
		int sum = 0;
		for (int idx = 0; idx < a.length(); idx++) {
			switch (a.charAt(idx)) {
			case 'M':
				sum += 1000;
				break;
			case 'D':
				sum += 500;
				break;
			case 'C':
				if (idx < a.length() - 1) {
					if (a.charAt(idx + 1) == 'M') {
						sum += 900;
						idx++;
					} else if (a.charAt(idx + 1) == 'D') {
						sum += 400;
						idx++;
					} else {
						sum += 100;
					}
				} else {
					sum += 100;
				}
				break;
			case 'L':
				sum += 50;
				break;
			case 'X':
				if (idx < a.length() - 1) {
					if (a.charAt(idx + 1) == 'C') {
						sum += 90;
						idx++;
					} else if (a.charAt(idx + 1) == 'L') {
						sum += 40;
						idx++;
					} else {
						sum += 10;
					}
				} else {
					sum += 10;
				}
				break;
			case 'V':
				sum += 5;
				break;
			case 'I':
				if (idx < a.length() - 1) {
					if (a.charAt(idx + 1) == 'X') {
						sum += 9;
						idx++;
					} else if (a.charAt(idx + 1) == 'V') {
						sum += 4;
						idx++;
					} else {
						sum += 1;
					}
				} else {
					sum += 1;
				}
				break;
			default:
				throw new RuntimeException("Invalid character");
			}
		}
		return sum;
	}

	private static boolean isOneOfTheseChars(char temp, char c, char d) {
		return (c == temp || d == temp);
	}

}
