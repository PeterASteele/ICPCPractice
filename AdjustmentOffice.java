import java.util.*;
import java.math.*;
import java.io.*;

//NEERC 2015 Problem A (Practice)
//http://codeforces.com/gym/100851/attachments
public class AdjustmentOffice {

    public static void main(String[] args) throws IOException {
        String fileName = "adjustment";
        Scanner sc = new Scanner(new File(fileName + ".in"));
//        Scanner input = new Scanner(new File(fileName + ".in"));
//        Scanner sc = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
//        FastScanner sc = new FastScanner();
        
        StringBuilder sb = new StringBuilder();
        long size = sc.nextLong();
        int numQueries = sc.nextInt();
        long rowSum = size*(size+1)/2;
        long colSum = size*(size+1)/2;
        boolean[] rowIsZero = new boolean[(int)size+1];
        boolean[] colIsZero = new boolean[(int)size+1];
        int numRowsGone = 0;
        int numColsGone = 0;
        for (int i = 0; i < numQueries; i++) {
            String type = sc.next();
            if (type.equals("R")) {
                long row = sc.nextInt();
                if (rowIsZero[(int)row]) {
                    sb.append(0).append('\n');
                } else {
                    long sum = rowSum + row*(size-numColsGone);
                    colSum -= row;
                    sb.append(sum).append('\n');
                    
                    rowIsZero[(int)row] = true;
                    numRowsGone++;
                }
            } else {
                long col = sc.nextInt();
                if (colIsZero[(int)col]) {
                    sb.append(0).append('\n');
                } else {
                    long sum = colSum + col*(size-numRowsGone);
                    rowSum -= col;
                    
                    sb.append(sum).append('\n');
                    colIsZero[(int)col] = true;
                    numColsGone++;
                }
            }
        }
        
//        System.out.print(sb);
        
        PrintWriter writer = new PrintWriter(fileName + ".out");
        writer.print(sb);
        writer.close();
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