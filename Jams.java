import java.util.*;
//http://codeforces.com/gym/100863/attachments, Problem J
public class Jams {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		final int MAXV = 1000000;
		Pair[] pairs = new Pair[N];
		int[][] freq = new int[3][MAXV + 1];
		for (int idx = 0; idx < N; idx++) {
			int v = sc.nextInt();
			int c = sc.nextInt();
			pairs[idx] = new Pair(v, c);
			freq[c][v]++;
		}
		Arrays.sort(pairs);

		int[][] dp = new int[3][MAXV + 1];
		int[] drop1 = new int[MAXV + 1];
		Pair[] drop2 = new Pair[MAXV + 1];
		int curColor = 0;
		for (int idx = 0; idx < MAXV + 1; idx++) {
			if (idx != 0)
				dp[curColor][idx] = dp[curColor][idx - 1];
			dp[curColor][idx] += freq[0][idx];
		}
		curColor = 1;
		for (int idx = 0; idx < MAXV + 1; idx++) {
			if (idx == 0) {
				if(freq[curColor][idx] < dp[curColor - 1][idx]){
					drop1[idx] = idx;
				}
				else{
					drop1[idx] = 0;
				}
				dp[curColor][idx] = Math.max(freq[curColor][idx],
						dp[curColor - 1][idx]);
			} else {
				if(dp[curColor][idx - 1]
						+ freq[curColor][idx] < dp[curColor - 1][idx]){
					drop1[idx] = idx;
				}
				else{
					drop1[idx] = drop1[idx-1];
				}
				dp[curColor][idx] = Math.max(dp[curColor][idx - 1]
						+ freq[curColor][idx], dp[curColor - 1][idx]);
			}
		}
		curColor = 2;
		for (int idx = 0; idx < MAXV + 1; idx++) {
			if (idx == 0) {
				if(freq[curColor][idx] < dp[curColor - 1][idx]){
					drop2[idx] = new Pair(drop1[idx], idx);
				}
				else{
					drop2[idx] = new Pair(0,0);
				}				
				dp[curColor][idx] = Math.max(freq[curColor][idx],
						dp[curColor - 1][idx]);
			} else {
				if(dp[curColor][idx - 1]
						+ freq[curColor][idx] < dp[curColor - 1][idx]){
					drop2[idx] = new Pair(drop1[idx], idx);
				}
				else{
					drop2[idx] = new Pair(drop2[idx-1].v, drop2[idx-1].c);
				}	
				dp[curColor][idx] = Math.max(dp[curColor][idx - 1]
						+ freq[curColor][idx], dp[curColor - 1][idx]);
			}
		}
		// DEBUG TODO: DELETE
//		for (curColor = 0; curColor < 3; curColor++) {
//			System.out.println(Arrays.toString(dp[curColor]));
//		}
//		System.out.println(dp[2][MAXV]);

		System.out.printf("%d %d\n", drop2[MAXV].v, drop2[MAXV].c);

		sc.close();
	}

	static class Pair implements Comparable<Pair> {
		final int v, c;

		public Pair(int vv, int cc) {
			v = vv;
			c = cc;
		}

		@Override
		public int compareTo(Pair o) {
			int cmp = Integer.compare(v, o.v);
			if (cmp != 0)
				return cmp;
			return Integer.compare(c, o.c);
		}
	}

}
// Pair pair = pairs[idx];
// if (idx > 0) {
// dp[curColor][idx] = dp[curColor][idx-1];
// }
// if (pair.c == curColor) {
// dp[curColor][idx]++;
// }
// if (dp[curColor-1][idx] > dp[curColor][idx]) { // check row above
// dp[curColor][idx] = dp[curColor-1][idx];
// AB[curColor-1] = pair.v;
// }