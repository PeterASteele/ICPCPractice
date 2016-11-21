import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/magical3
public class TheMagicalThree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        l:
        while (true) {
            int n = input.nextInt();
            if (n == 0) {
                break;
            }
            if (n < 3) {
                System.out.println("No such base");
                continue l;
            }
            if (n == 3) {
                System.out.println("4");
                continue l;
            }
            int tmp = n - 3;
            for (int base = 4; base <= Math.sqrt(n) + 1; base++) {
                if (tmp % base == 0) {
                    System.out.println(base);
                    continue l;
                }
            }
            if (tmp % 3 == 0) {
                if (tmp / 3 > 3) {
                    System.out.println(tmp / 3);
                    continue l;
                }
            }
            if (tmp % 2 == 0) {
                if (tmp / 2 > 3) {
                    System.out.println(tmp / 2);
                    continue l;
                }
            }
            if (tmp > 3) {
                System.out.println(tmp);
                continue l;
            }

            System.out.println("No such base");
            // for(int base = 1; base < )
        }

    }
}
