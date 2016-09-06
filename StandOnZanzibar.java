import java.util.*;
import java.math.*;
import java.io.*;
//BAPC 2015 Problem L(Practice)
//https://open.kattis.com/contests/naipc16-p01/problems/zanzibar
public class StandOnZanzibar {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int total = 0;
            int last = 1;
            while (true) {
                int t = sc.nextInt();
                if (t == 0) break;
                total += Math.max(0, t-last*2);
                last = t;
            }
            System.out.println(total);
        }
        
        
        System.out.println();
    }
    
    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(Reader in) {
            br = new BufferedReader(in);
        }

        public FastScanner() { this(new InputStreamReader(System.in)); }

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

        int nextInt() { return Integer.parseInt(next()); }
        long nextLong() { return Long.parseLong(next()); }
        double nextDouble() { return Double.parseDouble(next()); }

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