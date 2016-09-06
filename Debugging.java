import java.util.*;
import java.math.*;
import java.io.*;
//NWERC2015 Problem D (Practice)
//https://open.kattis.com/contests/naipc16-p09/problems/debugging
public class Debugging {
    
    static long n, r, p;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        n = sc.nextLong();
        r = sc.nextLong();
        p = sc.nextLong();
        
        memo = new long[(int)n+1];
        Arrays.fill(memo, -1L);
        
        System.out.println(solve(n));
    }
    
    static long[] memo;
    static long solve(long k) {
        if (memo[(int)k] != -1L) return memo[(int)k];
        if (k == 1) return 0L;
        
        long res = (k-1)*p + r;
        for (int i = 2; i < k; i++) {
            res = Math.min(res, solve((long)Math.ceil(k*1.0/i)) + (i-1)*p + r);
        }
        return (memo[(int)k] = res);
    }
    
    static long original(long k) {
        if (k == 1) return 0L;
        return Math.min((k-1)*p + r, original((long)Math.ceil(k/2.)) + p + r);
    }
}