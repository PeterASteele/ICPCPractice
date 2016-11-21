import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/compositions
public class Compositions {
    static int n;
    static int m;
    static int k;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        for (int a = 0; a < t; a++) {
            System.out.print(input.nextInt() + " ");
            DYNAMIC = new long[100][100];
            for(int b = 0; b < 100; b++){
                for(int c = 0; c < 100; c++){
                    DYNAMIC[b][c] = -1;
                }
            }
            n = input.nextInt();
            m = input.nextInt();
            k = input.nextInt();
            System.out.println(solve(0, 0));

        }
    }

    private static boolean isBanned(int test) {
        return (test - m) % k == 0;
    }
    static long[][] DYNAMIC;
    private static long solve(int idx, int i) {
//        System.out.println(idx + " " + i);
        if(DYNAMIC[idx][i] != -1){
            return DYNAMIC[idx][i];
        }
        if (idx > n) {
            return 0;
        }
        if (idx == n) {
            return 1;
//            int ok = 0;
//            if (!isBanned(i + 1)) {
//                ok++;
//            }
//            if (!isBanned(1)) {
//                ok++;
//            }
//            return ok;
        }
        long ans = 0;
        if (idx != 0) {
            int tmp = 1;
            while (isBanned(i + tmp)) {
                tmp++;
                if (tmp > 100) {
                    break;
                }
            }
            ans += solve(idx + tmp, i + tmp);
        }
        int tmp2 = 1;
        while (isBanned(tmp2)) {
            tmp2++;
            if (tmp2 > 100) {
                break;
            }
        }
        ans += solve(idx + tmp2, tmp2);
        DYNAMIC[idx][i] = ans;
        return ans;
    }
}
