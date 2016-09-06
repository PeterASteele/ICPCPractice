import java.util.*;
import java.math.*;
import java.io.*;

//ICPC World Finals 2011 Problem E (practice)
//http://acm.hust.edu.cn/vjudge/contest/114197#problem/E
public class CoffeeCentral {

    static int testcase = 1;
    static int dx, dy, n, q;
    static boolean[][] grid;
    static int[][] prefixSums;

    static int MAX = 2100;
    static int height = MAX;
    static int width = MAX;

    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(Reader in) {
            br = new BufferedReader(in);
        }

        public FastScanner() {
            this(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        // Slightly different from java.util.Scanner.nextLine(),
        // which returns any remaining characters in current line,
        // if any.
        String readNextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();

        while (true) {

            dx = sc.nextInt();
            dy = sc.nextInt();
            n = sc.nextInt();
            q = sc.nextInt();

            grid = new boolean[height][width];
            prefixSums = new int[height][width];

            if (dx == dy && dy == n && n == q && q == 0) break;

            System.out.println(String.format("Case %d:", testcase++));
            for (int i = 0; i < n; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                P p = new P(x, y);
                P np = p.transformTo();
                grid[np.y][np.x] = true;
            }

            buildPrefixSums();

            // for (int row = 1005; row >= 999; row--) {
            // for (int col = 0; col < 30; col++) {
            // System.out.print((grid[row][col] ? 1 : 0) + "\t");
            // }
            // System.out.println();
            // // System.out.println(Arrays.toString(grid[row]));
            // }
            // for (int row = 1005; row >= 999; row--) {
            // System.out.println(Arrays.toString(prefixSums[row]));
            // }
            for (int i = 0; i < q; i++) {
                int m = sc.nextInt();
                int max = -1;
                int maxA = -1;
                int maxB = -1;
                for (int a = 1; a <= 1000; a++) {
                    for (int b = 1; b <= 1000; b++) {
                        P p1 = new P(b, a).transformTo();
                        P p0 = new P(p1.x - m, p1.y - m);
                        p1.y += m;
                        p1.x += m;
                        int tmp = rangeSum(p0.y, p0.x, p1.y, p1.x);
                        if (tmp > max) {
                            max = tmp;
                            maxA = a;
                            maxB = b;
                        }
                    }
                }

                System.out.println(max + " " + new P(maxB, maxA));
            }

        }
    }

    static void buildPrefixSums() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int sum = grid[row][col] ? 1 : 0;

                if (row - 1 >= 0) {
                    int above = prefixSums[row - 1][col];
                    sum += above;
                }

                if (col - 1 >= 0) {
                    int left = prefixSums[row][col - 1];
                    sum += left;
                }

                if (row - 1 >= 0 && col - 1 >= 0) {
                    int aboveLeft = prefixSums[row - 1][col - 1];
                    sum -= aboveLeft;
                }

                prefixSums[row][col] = sum;
            }
        }
    }

    static int rangeSum(int r1, int c1, int r2, int c2) {
        r1 = Math.max(0, r1) - 1;
        c1 = Math.max(0, c1) - 1;
        r2 = Math.min(height - 1, r2);
        c2 = Math.min(width - 1, c2);
         

        int sum1 = prefixSums[r2][c2];

        int aboveSum = 0;
        if (r1 >= 0) {
            aboveSum = prefixSums[r1][c2];
        }

        int leftSum = 0;
        if (c1 >= 0) {
            leftSum = prefixSums[r2][c1];
        }

        int sum2 = 0;
        if (r1 >= 0 && c1 >= 0) {
            sum2 = prefixSums[r1][c1];
        }

        return sum1 - aboveSum - leftSum + sum2;
    }

    static class P {
        int x, y;
        boolean coffee = false;

        P(int a, int b) {
            x = a;
            y = b;
        }

        P transformTo() {
            return new P(x + y, x - y + 1000);
        }

        P transformBack() {
            return new P((-x - (y - 1000)) / -2, (-x + (y - 1000)) / -2);
        }

        public String toString() {
            return String.format("(%d,%d)", x, y);
        }
    }
}