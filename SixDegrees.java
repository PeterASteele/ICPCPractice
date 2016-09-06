import java.util.*;
import java.math.*;
import java.io.*;
//BAPC 2015 Problem I(Practice)
//https://open.kattis.com/contests/naipc16-p01/problems/sixdegrees
public class SixDegrees {

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        
        int T = sc.nextInt();
        Map<String, Integer> strToIdx = new HashMap<>();
        Map<String, List<String>> graph = new HashMap<>();
        List<String> idxToString = new ArrayList<>();
        
        for (int c = 0; c < T; c++) {
            strToIdx.clear();
            graph.clear();
            idxToString.clear();
            set.clear();
            int numNodes = 0;
            int numEdges = sc.nextInt();
            for (int i = 0; i < numEdges; i++) {
                String u = sc.next();
                String v = sc.next();
                if (!strToIdx.containsKey(u)) {
                    strToIdx.put(u, numNodes);
                    numNodes++;
                    graph.put(u, new ArrayList<>());
                    idxToString.add(u);
                    
                }
                if (!strToIdx.containsKey(v)) {
                    strToIdx.put(v, numNodes);
                    numNodes++;
                    graph.put(v, new ArrayList<>());
                    idxToString.add(v);
                }
                graph.get(u).add(v);
//                graph.get(v).add(u);// Dont need
            }
            
            List<Integer>[] adj = new List[numNodes];
            for (int u = 0; u < numNodes; u++) {
                adj[u] = new ArrayList<>();
            }
            for (int u = 0; u < numNodes; u++) {
                String uu = idxToString.get(u);
                for (String vv : graph.get(uu)) {
                    int v = strToIdx.get(vv);
                    adj[u].add(v);
                    adj[v].add(u);
                }
            }
            
            // BFS
            int[] dist = new int[numNodes];
            Queue<Integer> bfs = new ArrayDeque<>();
            for (int u = 0; u < numNodes; u++) {
                for (int node = 0; node < numNodes; node++) {
                    dist[node] = -1;
                }
                dist[u] = 0;
                bfs.offer(u);
                while (!bfs.isEmpty()) {
                    int cur = bfs.poll();
                    int curDist = dist[cur];
                    
                    for (int v : adj[cur]) {
                        if (dist[v] == -1) {
                            dist[v] = curDist+1;
                            if (dist[v] < 6) {
                                bfs.offer(v);
                            }
                        }
                    }
                }
                for (int v = 0; v < numNodes; v++) {
                    if (dist[v] == -1) {
                        set.add(v);
                    }
                }
            }
            
//            System.out.println(set.size());
            int setSize = set.size();
            int threshhold = numNodes / 20;
            if (numNodes % 20 != 0) {
                threshhold++;
            }
            System.err.println(threshhold);
            if (setSize > threshhold) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
            }
        }
        
    }
    
    static Set<Integer> set = new HashSet<>(); 
    
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
