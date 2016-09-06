import java.util.*;
import java.math.*;
import java.io.*;
//ICPC World Finals 2013 (practice)
//https://icpc.kattis.com/problems/low
public class LowPower {

    static int n;
    static int k;
    static int[] batteries;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        n = sc.nextInt();
        k = sc.nextInt();
        batteries = new int[2 * n * k];
        for (int i = 0; i < batteries.length; i++) {
            batteries[i] = sc.nextInt();
        }
        Arrays.sort(batteries);

        int low = 0;
        int high = 1_000_000_000;
        while (low < high) {
            int mid = (low + high) / 2;
            if (canDo(mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        System.out.println(low);
    }

    static boolean canDo(int maxDiff) {
        int batteryIdx = 0;
        int emptySpace = 0;

        for (int machine = 0; machine < n; machine++) {
            int a;
            int b;
            while (true) {
                if (batteryIdx >= batteries.length) {
                    return false;
                }
                a = batteries[batteryIdx];
                batteryIdx++;

                if (batteryIdx >= batteries.length) {
                    return false;
                }
                b = batteries[batteryIdx];
                batteryIdx++;

                int diff = b - a;
                if (diff > maxDiff) {
                    batteryIdx--;
                    emptySpace--;
                    if (emptySpace < 0) {
                        return false;
                    }
                } else {
                    break;
                }
            }
            
            emptySpace += 2 * (k - 1);
        }

        return true;
    }
}