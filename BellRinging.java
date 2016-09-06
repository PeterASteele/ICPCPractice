import java.util.*;
import java.math.*;
import java.io.*;

//NCPC2015 Problem B (Practice)
//https://open.kattis.com/contests/naipc16-p05/problems/bells
/**
 * Accepted post-contest 
 */
public class BellRinging {

    public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        // Scanner input = new Scanner(System.in);
        FastScanner sc = new FastScanner();

        int n = sc.nextInt();
        List<List<Integer>> prevLists = new ArrayList<>();
        List<Integer> justOne = new ArrayList<>();
        justOne.add(1);
        prevLists.add(justOne);
        List<List<Integer>> curLists = null;
        for (int i = 2; i <= n; i++) {
            curLists = new ArrayList<>();
            boolean forward = false;
            for (List<Integer> perm : prevLists) {
                if (forward) {
                    for (int j = 0; j <= i - 1; j++) {
                        List<Integer> newList = new ArrayList<>();
                        for (int k = 0; k < j; k++) {
                            newList.add(perm.get(k));
                        }
                        newList.add(i);
                        for (int k = j; k < i - 1; k++) {
                            newList.add(perm.get(k));
                        }
                        curLists.add(newList);
                    }
                } else {
                    for (int j = i-1; j >= 0; j--) {
                        List<Integer> newList = new ArrayList<>();
                        for (int k = 0; k < j; k++) {
                            newList.add(perm.get(k));
                        }
                        newList.add(i);
                        for (int k = j; k < i - 1; k++) {
                            newList.add(perm.get(k));
                        }
                        curLists.add(newList);
                    }
                }
                forward = !forward;
            }
            prevLists = curLists;

        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prevLists.size(); i++) {
            List<Integer> perm = prevLists.get(i);
            for (int j = 0; j < perm.size(); j++) {
                if (j > 0) sb.append(' ');
                sb.append(perm.get(j));
            }
            sb.append('\n');
        }
        System.out.print(sb);
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