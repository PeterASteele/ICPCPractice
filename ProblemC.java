import java.util.Arrays;
import java.util.Scanner;

//NAIPC2013
public class ProblemC {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		char[] s = input.next().toCharArray();
		SuffixArray suffixArr = new SuffixArray(s);
		int numQueries = input.nextInt();
		StringBuilder sb = new StringBuilder();	
		for (int idx = 0; idx < numQueries; idx++) {
			int i = input.nextInt();
			int j = input.nextInt();
			sb.append(suffixArr.lcp(i, j));
			if (idx < numQueries -1) {
				sb.append('\n');
			}
		}

		System.out.println(sb);
	}
	
	static class SuffixArray {
		char[] A;
		int[][] P;
		int[] rank;
		int[] sa;
		int[] lcp;
		
		SuffixArray(char[] A) {
			this.A = A;
			this.P = computeInvertedSuffixArray(A);
			this.rank = P[P.length-1];
		}
		
		int[] getSuffixArray() {
			if (sa == null) sa = invertArray(rank);
			return sa;
		}
		
		static int[] invertArray(int[] a) {
			int[] inv = new int[a.length];
			for (int i = 0; i < a.length; i++) {
				inv[a[i]] = i;
			}
			return inv;
		}
		
		int[] getLCP() {
			if ( lcp == null) {
				lcp = computeLCP(getSuffixArray(), rank, A);
			}
			return lcp;
		}
		
		int lcp(int i, int j) {
			int n = A.length;
			if (i == j) {
				return n-i;
			}
			int len = 0;
			for (int k = P.length-1; k>= 0 && i < n && j < n; k--) {
				if (P[k][i] == P[k][j]) {
					i += 1 << k;
					j += 1 << k;
					len += 1 << k;
				}
			}
			
			return len;
		}

		static class Entry implements Comparable<Entry> {
			int nr0, nr1, p;
			Entry() {
				this.p= -1;
			}
			@Override
			public int compareTo(Entry o) {
				int c = Integer.compare(this.nr0, o.nr0);
				if (c != 0) return c;
				return Integer.compare(this.nr1, o.nr1);
			}
		}
		private int[][] computeInvertedSuffixArray(char[] A) {
			int n = A.length;
			int MAXLG = 2 + 31 - Integer.numberOfLeadingZeros(n);
			Entry[] M = new Entry[n];
			for (int i = 0; i < n; i++) {
				M[i] = new Entry();
			}
			int[][] P = new int[MAXLG][n];
			for (int i = 0; i < n; i++) {
				P[0][i] = (int)A[i];
			}
			int level = 1;
			for (int skip = 1; skip < n; level++, skip *=2) {
				for (int i = 0; i < n; i++) {
					M[i].nr0 = P[level-1][i];
					M[i].nr1 = (i+skip < n) ? P[level-1][i+skip] : -1000;
					M[i].p = i;
				}
				Arrays.sort(M);
				
				for (int i = 0; i < n; i++) {
					P[level][M[i].p] = (i > 0 && M[i].nr0 == M[i-1].nr0 && M[i].nr1 == M[i-1].nr1) 
						? P[level][M[i-1].p] : i;
				}
			}
			
			return Arrays.copyOf(P, Math.max(2, level));
		}
		
		private int[] computeLCP(int[] sa, int[] rank, char[] A) {
			int n = sa.length;
			int[] lcp = new int[n];
			int h = 0;
			for (int i =0 ; i < n; i++) {
				int k = rank[i];
				if (k > 0) {
					int j = sa[k-1];
					while (Math.max(i+h, j+h) < n && A[i+h] == A[j+h]) {
						h++;
					}
					lcp[k] = h;
					if (h > 0) h--;
				} else {
					lcp[k] = 0;
				}
			}
			return lcp;
		}
	}
}
