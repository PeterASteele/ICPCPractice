import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/kenken
public class KenKenYouDoIt {
    static class Point implements Comparable<Point> {
        final int row, col;
        Point (int r, int c) {
            row = r;
            col = c;
        }
        @Override
        public int compareTo(Point o) {
            int cmp = Integer.compare(row, o.row);
            if (cmp != 0) return cmp;
            return Integer.compare(col, o.col);
        }
    }
    static int n;
    static int m;
    static long tLong;
    static int tInt;
    static char op;
    static int[] rows;
    static int[] cols;
    static boolean[][] usedInRow;
    static boolean[][] usedInCol;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        n = sc.nextInt();
        m = sc.nextInt();
        tLong = sc.nextLong();
        tInt = (int) tLong;
        op = sc.next().charAt(0);
        
        rows = new int[m];
        cols = new int[m];
        usedInRow = new boolean[n][n+1];
        usedInCol = new boolean[n][n+1];
        Point[] points = new Point[m];
        for (int i = 0; i < m; i++) {
            int r = sc.nextInt() - 1;
            int c = sc.nextInt() - 1;
            points[i] = new Point(r, c);
        }
        Arrays.sort(points); // Probably not necessary
        for (int i = 0; i < m; i++) {
            rows[i] = points[i].row;
            cols[i] = points[i].col;
        }
        
        count = 0;
        
        if (op == '/') {
            solveDivide();
        } else if (op == '-') {
            solveSubtract();
        } else if (op == '+') {
            solveAdd(0, 0);
        } else {
            powN = new long[m];
            powN[0] = 1;
            for (int i = 1 ; i < m; i++) {
                powN[i] = powN[i-1] * n;
            }
            solveMultiply(0, 1);
        }
        
        System.out.println(count);
    }
    
    static long count;
    
    static void solveDivide() {
        // Check divisibility or not?
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (j % i == 0 && j / i == tInt) {
                    count += 2;
                }
            }
        }
    }
    
    static void solveSubtract() {
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (j - i == tInt) {
                    count += 2;
                }
            }
        }
    }
    
    static void solveAdd(int idx, int curRes) {
        // MAYBE DP THIS
        int row = rows[idx];
        int col = cols[idx];
        if (idx == m - 1) {
            int num = tInt - curRes;
            if (!usedInRow[row][num] && !usedInCol[col][num]) {
                count++;
            }
        } else {
            for (int num = 1; num <= n; num++) {
                if (!usedInRow[row][num] && !usedInCol[col][num]) {
                    if (curRes + num + (m - (idx + 1)) > tInt) { // too high
                        break;
                    }
                    if (curRes + num + n * (m - (idx + 1)) < tInt) { // too low
                        continue;
                    }
                    
                    usedInRow[row][num] = true;
                    usedInCol[col][num] = true;
                    
                    solveAdd(idx + 1, curRes + num);
                    
                    usedInRow[row][num] = false;
                    usedInCol[col][num] = false;
                }
            }
        }
    }
    
    static long[] powN;
    
    static void solveMultiply(int idx, long curRes) {
     // MAYBE DP THIS
        int row = rows[idx];
        int col = cols[idx];
        if (idx == m - 1) {
            long num = tLong / curRes;
            if (curRes * num == tLong && num >= 1 && num <= n &&
                    !usedInRow[row][(int)num] && !usedInCol[col][(int)num]) {
                count++;
            }
        } else {
            for (int num = 1; num <= n; num++) {
                if (!usedInRow[row][num] && !usedInCol[col][num]) {
                    if (curRes * num > tLong) { // too high
                        break;
                    }
                    if (curRes * num * powN[m - (idx + 1)] < tLong) { // too low
                        continue;
                    }
                    
                    usedInRow[row][num] = true;
                    usedInCol[col][num] = true;
                    
                    solveMultiply(idx + 1, curRes * num);
                    
                    usedInRow[row][num] = false;
                    usedInCol[col][num] = false;
                }
            }
        }
    }
}
