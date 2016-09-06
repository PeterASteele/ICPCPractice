import java.util.*;
import java.math.*;
import java.io.*;
//ICPC World Finals 2008 Problem F (practice)
//http://acm.hust.edu.cn/vjudge/contest/111395#problem/F

public class GlenbowMuseum {
    static BigInteger[][][] cache;

    public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int count = 0;
        while (true) {
            count++;
            int n = input.nextInt();
            if (n == 0) {
                break;
            }
            if (n % 2 == 1 || n < 4) {
                System.out.printf("Case %d: %s\n", count, "0");
            } else {
                int specialN = ((n - 4) / 2) + 5;
                int N = 5;
                System.out.printf("Case %d: %s\n", count, combo(specialN, 5).subtract(combo(specialN-2, 5)));
            }
        }

    }

    public static BigInteger combo(int n, int w) {
        if(n < w){
            return BigInteger.ZERO;
        }
        BigInteger output = BigInteger.ONE;
        for (int a = n; a >= n-w+1; a--) {
            output = output.multiply(BigInteger.valueOf(a));
        }
        for(int a = 1; a <= w; a++){
            output = output.divide(BigInteger.valueOf(a));
        }
        return output;
    }

}