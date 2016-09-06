import java.util.*;
import java.math.*;
import java.io.*;

//NAIPC 2016 Problem F
//https://naipc16.kattis.com/problems/scenes
public class MountainScenes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int length = input.nextInt();
        int w = input.nextInt();
        int h = input.nextInt();
        long[][] dp = new long[w+1][length + 1];
        dp[0][length] = 1;
        for (int w2 = 1; w2 <= w; w2++) {
            for (int len = 0; len <= length; len++) {
                for (int h2 = 0; h2 <= h; h2++) {
                    try {
                        dp[w2][len - h2] += dp[w2 - 1][len];
                        dp[w2][len - h2] = dp[w2][len - h2] % 1000000007;
                    } catch (Exception e) {

                    }
                }
            }
        }
        long sum = 0;
        for(int b =0; b <= length; b++){
            sum += dp[w][b];
        }
        sum = (sum - Math.min(w*h, length)/w - 1)%1000000007;
        System.out.println(sum);
    }
}