import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class InverseFactorial {
    long MOD = 1000000007;
    long MOD2 = 899809363;
    long MOD3 = 982451653;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        if (in.length() < 100) {
            BigInteger i = new BigInteger(in);
            for (int a = 1; a < 100; a++) {
                i = i.divide(BigInteger.valueOf(a));
                if (i.equals(BigInteger.ONE)) {
                    System.out.println(a);
                    break;
                }
            }
        } else {
            int numZeros = 0;
            for (int a = in.length() - 1; a >= 0; a--) {
                if (in.charAt(a) == '0') {
                    numZeros++;
                } else {
                    break;
                }
            }
            int fiveCount = 0;
            for (int a = 0; a < 100000; a++) {
                int tmp = a;
                int total = 0;
                while (tmp != 0) {
                    total += tmp;
                    tmp = tmp / 5;
                }
                if (total == numZeros) {
                    fiveCount = a;
                    break;
                }
            }
            long l33thax1 = 1;
            String match = in.substring(in.length() - numZeros - 6, in.length() - numZeros);
            for (int a = 1; a < fiveCount * 5 + 10; a++) {
                l33thax1 *= a;
                while (l33thax1 % 10 == 0) {
                    l33thax1 = l33thax1 / 10;
                }
                l33thax1 = l33thax1 % 1000000000000l;
                if ((l33thax1%1000000) == (Long.parseLong(match))) {
                    if (a >= fiveCount * 5 && a < fiveCount * 5 + 5) {
                        System.out.println(a);
                        break;
                    }
                }
            }
        }
    }

}