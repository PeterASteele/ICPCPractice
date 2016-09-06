import java.util.*;
import java.math.*;
import java.io.*;
//NEERC 2015 Problem G (Practice)
//http://codeforces.com/gym/100851/attachments
public class Generators {

    public static void main(String[] args) throws IOException {
        String fileName = "generators";
        // FastScanner input = new FastScanner();
        Scanner input = new Scanner(new File(fileName + ".in"));
        // Scanner sc = new Scanner(System.in);
        // Scanner input = new Scanner(System.in);
        // FastScanner sc = new FastScanner();
        // boolean debug = true;

        PrintWriter writer = new PrintWriter(fileName + ".out");
        int n = input.nextInt();
        int k = input.nextInt();
        OBJ[] array = new OBJ[n];
        for (int loop = 0; loop < n; loop++) {
            int x0 = input.nextInt();
            int a = input.nextInt();
            int b = input.nextInt();
            int c = input.nextInt();
            array[loop] = new OBJ(x0, a, b, c);
        }
        int totalMax = 0;
        for (int loop = 0; loop < n; loop++) {
            totalMax += array[loop].max;
        }
        if (totalMax % k != 0) {
            writer.println(totalMax);
            for (int loop = 0; loop < n; loop++) {
                writer.print((array[loop].maxIndex) + " ");
            }
            writer.println();
        } else {
            Point[] swaps = new Point[totalMax + 1];
            for (int loop = 0; loop < n; loop++) {
                for (int ind = 0; ind < array[loop].c; ind++) {
                    int home = loop;
                    int index = array[loop].arr2[ind];
                    int difference = array[loop].max - ind;
                    if (array[loop].arr[ind]) swaps[difference] = new Point(index, home, difference);
                }
            }
            Point end = null;
            for (Point swap : swaps) {
                if (swap != null) {
                    int tempMax = totalMax - swap.difference;
                    if (tempMax % k != 0) {
                        end = swap;
                        break;
                    }
                }
            }
            if (end == null) {
                writer.println(-1);
            } else {
                writer.println(totalMax - end.difference);
                for (int b = 0; b < n; b++) {
                    if (end.home == b) {
                        writer.print((end.index) + " ");
                    } else {
                        writer.print((array[b].maxIndex) + " ");
                    }

                }
                writer.println();
            }
        }
        writer.close();
    }

    public static class Point implements Comparable<Point> {
        int index;
        int home;
        int difference;

        public Point(int index, int home, int difference) {
            this.index = index;
            this.home = home;
            this.difference = difference;
        }

        @Override
        public int compareTo(Point o) {
            return difference - o.difference;
        }

        public String toString() {
            return difference + "";
        }

    }

    public static class OBJ {
        int x0;
        int a;
        int b;
        int c;
        boolean[] arr;
        int[] arr2;
        int max;
        int maxIndex;

        public OBJ(int x0, int a, int b, int c) {
            this.x0 = x0;
            this.a = a;
            this.b = b;
            this.c = c;
            arr = eval();
        }

        public boolean[] eval() {
            boolean[] out = new boolean[c];
            arr2 = new int[c];
            out[x0] = true;
            arr2[x0] = 0;
            int start = x0;
            int max2 = x0;
            int maxIndex2 = 0;
            int count = 0;
            while (true) {
                count++;
                int next = (start * a + b) % c;
                if (next > max2) {
                    max2 = next;
                    maxIndex2 = count;
                }
                if (out[next]) {
                    break;
                }
                out[next] = true;
                arr2[next] = count;
                start = next;
            }
            max = max2;
            maxIndex = maxIndex2;
            return out;
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