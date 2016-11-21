import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/flowergarden
public class FlowerGarden {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();
        for (int a = 0; a < t; a++) {
            int n = input.nextInt();
            long steps = input.nextInt();
            double startSteps = 0;
            int startX = 0;
            int startY = 0;
            int count = 0;
            for (int b = 0; b < n; b++) {
                int x = input.nextInt();
                int y = input.nextInt();
                if (startSteps <= steps+Math.ulp(steps)*10) {
                    double dist = Math.hypot(Math.abs(x - startX), Math.abs(y - startY));
                    if (startSteps + dist <= steps+Math.ulp(steps)*10) {
                        count++;
                        startX = x;
                        startY = y;
                    }
                    startSteps += dist;
                }
            }
            while (!isPrime(count)) {
                count--;
            }
            System.out.println(count);
            
        }
    }

    private static boolean isPrime(int count) {
        if (count == 0) {
            return true;
        }
        return BigInteger.valueOf(count).isProbablePrime(30);
    }
}