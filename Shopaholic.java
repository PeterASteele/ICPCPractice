import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/shopaholic
public class Shopaholic {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        long[] arr = new long[N];
        for (int a = 0; a < N; a++) {
            arr[a] = input.nextInt();
        }
        Arrays.sort(arr);
        long total = 0;
        for (int count = 0, a = N - 1; a >= 0; count++, a--) {
            if (count % 3 == 2) {
                total += arr[a];
            }
        }
        System.out.println(total);

    }
}
