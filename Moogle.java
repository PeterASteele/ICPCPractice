import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/moogle
public class Moogle {
    static double[][][] dynamicProg;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        for (int q = 0; q < t; q++) {
            int n = input.nextInt();
            int m = input.nextInt();
            int[] arr = new int[n];
            dynamicProg = new double[n][n][n];
            for (int a = 0; a < n; a++) {
                for (int b = 0; b < n; b++) {
                    for (int c = 0; c < n; c++) {
                        dynamicProg[a][b][c] = -1;
                    }
                }
            }
            for (int a = 0; a < n; a++) {
                arr[a] = input.nextInt();
            }
            System.out.println(solve(arr, m - 2, 0, arr.length - 1) / n);
        }

    }

    private static double solve(int[] arr, int i, int start, int end) {
        if (dynamicProg[i][start][end] != -1) {
            return dynamicProg[i][start][end];
        }
        if (i == 0) {
            double error = 0.0;
            for (int k = start; k <= end; k++) {
                double interpolatedValue = arr[start]
                        + (k - start) * (arr[end] - arr[start] + 0.0) / (end - start + 0.0);
                error += Math.abs(arr[k] - interpolatedValue);
            }
            dynamicProg[i][start][end] = error;
            return error;
        }
        double min = solve(arr, i - 1, start, end);
        for (int a = start + 1; a <= end - i; a++) {
            double answer = 0;
            answer += solve(arr, 0, start, a);
            answer += solve(arr, i-1, a, end);
            if (answer < min) {
                min = answer;
            }
        }
        dynamicProg[i][start][end] = min;
        return min;
    }
}
