import java.util.*;
import java.math.*;
import java.io.*;
//BAPC 2015 Problem A(Practice)
//https://open.kattis.com/contests/naipc16-p01/problems/freighttrain
public class FreightTrain {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int tests = input.nextInt();
        for (int b = 0; b < tests; b++) {
            long n = input.nextInt();
            int w = input.nextInt();
            long loc = input.nextInt();
            long[] locations = new long[w];
            for (int a = 0; a < w; a++) {
                locations[a] = input.nextInt();
            }
            long lowest = 1;
            long highest = n;
            long answer = BINARYSEARCH(lowest, highest, locations, loc, n);

            System.out.println(answer);
        }
    }

    private static long BINARYSEARCH(long low, long high, long[] locations, long loc, long n) {
//        System.out.println("Checking " + low + " " + high);
        if (low == high) {
            return high;
        }
        long middle = (long) (Math.random() * (high - low)) + low;
        if (isPossible(middle, locations, loc, n)) {
            return BINARYSEARCH(low, middle, locations, loc, n);
        } else {
            return BINARYSEARCH(middle + 1, high, locations, loc, n);
        }

    }

    private static boolean isPossible(long middle, long[] locations, long maxAllowed, long n) {
//        System.out.println("Checking isPossible with " + middle + " with " + maxAllowed);
        long currentValue = 1;
        int index = 0;
        int loco = 0;
        while (currentValue <= n) {
//            System.out.println(currentValue);
            if (index >= locations.length || locations[index] - currentValue > middle) {
                loco++;
//                index++;
                if (index < locations.length) currentValue = locations[index];
                else currentValue = n+1;
//                System.out.println("CurrentValue is " + currentValue);
            } else {
                currentValue += middle;

                while (index < locations.length && locations[index] < currentValue) {
                    index++;
                }
                loco++;
            }
        }
        if (loco > maxAllowed) {
            return false;
        } else {
            return true;
        }
    }

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
}
