import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/contests/na16warmup1/problems/dancerecital
public class DanceRecital {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        n = sc.nextInt();
        strings = new String[n];
        for (int i = 0; i < n; i++) {
            String str = sc.next();
            strings[i] = str;
        }
        
        int[] tsp = solve();
        
        int min = Arrays.stream(tsp).min().getAsInt();
        System.out.println(min);
    }
    
    static int n;
    static String[] strings;
    
    static int[] solve() {
        // precompute/cache dist to avoid calls in inner loop 
        final int [][]D = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                D[j][i] = dist(strings[i], strings[j]);

        int[][] dp = new int[1 << n][n];
        for (int[] row : dp)
            Arrays.fill(row, Integer.MAX_VALUE / 2);

        for (int i = 0; i < n; i++)
            dp[1<<i][i] = 0;

        for (int mask = 0; mask < 1 << n; mask++)
            for (int i = 0; i < n; i++)
                if ((mask & 1 << i) > 0)
                    for (int j = 0; j < n; j++)
                        if (i != j && (mask & 1 << j) > 0)
                            dp[mask][i] = Math.min(dp[mask][i], 
                                dp[mask ^ (1<<i)][j] + D[j][i]);
        return dp[(1<<n)-1];
    }
    
    static int dist(String a, String b) {
        Set<Character> inBoth = new HashSet<>();
        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < b.length(); j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    inBoth.add(a.charAt(i));
                }
            }
        }
        return inBoth.size();
    }
}
