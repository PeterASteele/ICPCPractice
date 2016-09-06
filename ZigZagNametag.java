import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import java.io.*;
//NAIPC2015 Problem J (Practice)
//https://open.kattis.com/contests/naipc16-p10/problems/zigzag

public class ZigZagNametag {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int k = sc.nextInt();
        
        String shortest = solve(k, new StringBuilder("a"), 'a');
        
        if (k > 25) {
            for (int i = 1; i < 26; i++) {
                char c = (char)('a' + i);
                String tmp = solve(k-i, new StringBuilder("a" + c), c);
                if (tmp.length() == shortest.length()) {
                    System.out.println(tmp);
                    System.exit(0);
                }
            }
        }
        
        System.out.println(shortest);
        
        
    }
    
    static String solve(int k, StringBuilder sb, char last) {
        if (k == 0) return sb.toString();
        
        if (abs(last-'z') > abs(last-'a')) {
            int diff = min(k, abs(last-'z'));
            char c = (char) (last+diff);
            sb.append(c);
            return solve(k-diff, sb, c);
        } else {
            int diff = min(k, abs(last-'a'));
            char c = (char) (last-diff);
            sb.append(c);
            return solve(k-diff, sb, c);
        }
    }
}