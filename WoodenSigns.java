import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/woodensigns
public class WoodenSigns {
    static long MOD = (1l << 31) - 1;
    static int[] right;
    static long[][] DYNAMIC;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int N = input.nextInt();
        DYNAMIC = new long[N + 2][N + 2];
        for (int a = 0; a < N + 2; a++) {
            for (int b = 0; b < N + 2; b++) {
                DYNAMIC[a][b] = -1;
            }
        }
        right = new int[N];
        int startLeft = input.nextInt();
        for (int a = 0; a < N; a++) {
            right[a] = input.nextInt();
        }
        System.out.println(solve(startLeft, right[0], 0));

    }

    public static class Point implements Comparable<Point> {
        int x;
        int y;

        public Point(int _x, int _y) {
            x = _x;
            y = _y;
        }

        @Override
        public boolean equals(Object o) {
            Point p = (Point) o;
            return p.x == x && p.y == y;
        }

        @Override
        public int hashCode() {
            return (int) ((x * 100000) + y);
        }

        @Override
        public int compareTo(Point o) {
            if (x == o.x) {
                return Integer.compare(y, o.y);
            } else {
                return Integer.compare(x, o.x);
            }
        }
    }

    private static long solve(int startLeft, int i, int j) {
        // TODO Auto-generated method stub
        if (j == right.length - 1) {
            return 1;
        }
        if (DYNAMIC[startLeft][j] != -1) {
            return DYNAMIC[startLeft][j];
        }
        long answer = 0;
        int nextRight = right[j + 1];
        if (startLeft == nextRight) {
            for (int left = 0; left <= right.length; left++) {
                if (isValid(startLeft, i, left + 1, nextRight)) {
                    answer += solve(left + 1, nextRight, j + 1);
                }
            }
        }
        else{
            int startRight = i;
            int poss1 = startLeft;
            int poss2 = startRight;
            if(isValid(startLeft, startRight, poss1, nextRight)){
                answer += solve(poss1, nextRight, j+1);
            }
            if(isValid(startLeft, startRight, poss2, nextRight)){
                answer += solve(poss2, nextRight, j+1);
            }
        }
        answer = answer % MOD;
        DYNAMIC[startLeft][j] = answer;
        return answer;
    }

    private static boolean isValid(int prevBack, int prevFront, int nextBack, int nextFront) {
        if (prevBack == prevFront) {
            return false;
        }
        if (nextBack == nextFront) {
            return false;
        }
        if (nextFront == prevFront) {
            return false;
        }
        if (nextFront == prevBack || nextBack == prevFront) {
            if (prevBack > prevFront && nextBack < nextFront) return true;
            if (prevBack < prevFront && nextBack > nextFront) return true;
            return false;
        }
        if (nextBack == prevBack) {
            if (prevBack > prevFront && nextBack > nextFront) return true;
            if (prevBack < prevFront && nextBack < nextFront) return true;
            return false;
        }
        return false;
    }
    // private static boolean isValid(int startLeft, int startRight, int
    // nextLeft, int nextRight) {
    //
    // return false;
    // }
}
