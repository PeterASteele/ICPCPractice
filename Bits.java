import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/bits
public class Bits {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            long num = sc.nextLong();
            int max = 0;
            while (num > 0) {
                max = Math.max(max, Long.bitCount(num));
                num /= 10;
            }
            System.out.println(max);
        }
    }
}
