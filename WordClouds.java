import java.util.*;
import java.math.*;
import java.io.*;

public class WordClouds {
    static int[] cache;
	//https://open.kattis.com/contests/na16warmup1/problems/wordclouds
    public static void main(String[] args) {
        cache = new int[10000];
        Arrays.fill(cache, -1);
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int maxWidth = input.nextInt();
        int[] arrX = new int[n];
        int[] arrY = new int[n];
        for (int a = 0; a < n; a++) {
            arrX[a] = input.nextInt();
            arrY[a] = input.nextInt();
        }

        System.out.println(solve(0, arrX, arrY, maxWidth));
    }

    private static int solve(int i, int[] arrX, int[] arrY, int maxWidth) {
        if (cache[i] != -1) {
            return cache[i];
        }
        if (i == arrX.length) {
            return 0;
        }
        int possibleValue = Integer.MAX_VALUE;
        int startWidth = 0;
        int startHeight = 0;
        for (int end = i; end < arrX.length; end++) {
            startWidth += arrX[end];
            startHeight = Math.max(arrY[end], startHeight);
            if (startWidth > maxWidth) {
                startHeight += Integer.MAX_VALUE / 10000;
            }
            int temp = startHeight + solve(end + 1, arrX, arrY, maxWidth);
            if (temp < possibleValue) {
                possibleValue = temp;
            }
        }
        cache[i] = possibleValue;
        return possibleValue;

        // TODO Auto-generated method stub
        // return null;
    }

}
