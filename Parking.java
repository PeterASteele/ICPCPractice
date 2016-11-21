import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/parking2
public class Parking {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int T = input.nextInt();
        for (int q = 0; q < T; q++) {
            int N = input.nextInt();
            long[] arr = new long[N];
            for (int a = 0; a < N; a++) {
                arr[a] = input.nextInt();
            }
            Arrays.sort(arr);
            System.out.println(2 * (arr[arr.length - 1] - arr[0]));
        }

    }
}
