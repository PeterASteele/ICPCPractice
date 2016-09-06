import java.util.*;
import java.math.*;
import java.io.*;

//NCPC2015 Problem A (Practice)
//https://open.kattis.com/contests/naipc16-p05/problems/adjoin

public class AdjoinTheNetworks {

    static List<Integer>[] adj;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int c = sc.nextInt();
        int l = sc.nextInt();
        adj = new List[c];
        for (int i = 0; i < c; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        
        for (int i = 0; i < l; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        visited = new boolean[c];
        List<Integer> longestpaths = new ArrayList<>();
        for (int i = 0; i < c; i++) {
            if (!visited[i]) {
                longestpaths.add(longest(i));
            }
        }

        Collections.sort(longestpaths, Collections.reverseOrder());
        
        int result = longestpaths.get(0);
        int longestside = result % 2 == 0 ? result / 2 : result / 2 + 1;
        for (int i = 1; i < longestpaths.size(); i++) {
            int tmp = longestpaths.get(i) % 2 == 0 ? longestpaths.get(i) / 2 : longestpaths.get(i) / 2 + 1;
            if (tmp+1+longestside > result) {
                result = tmp+1+longestside;
                longestside = result % 2 == 0 ? result / 2 : result / 2 + 1;
            } else 
                break;
        }

        System.out.println(result);
        
    }

    static int furthest = -1;
    static int furthestdist = -1;

    static int longest(int c) {
        furthestdist = -1;
        dfs(c, -1, 0);
        furthestdist = -1;
        dfs(furthest, -1, 0);
        return furthestdist;
    }

    static void dfs(int c, int p, int d) {
        visited[c] = true;
        if (d > furthestdist) {
            furthestdist = d;
            furthest = c;
        }

        for (int n : adj[c]) {
            if (n != p) {
                dfs(n, c, d + 1);
            }
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