import java.util.*;
import java.math.*;
import java.io.*;

public class CuckooHashing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int numCases = sc.nextInt();
        for (int q = 0; q < numCases; q++) {
            int numWords = sc.nextInt();
            int n = sc.nextInt();
            HopcroftKarp bm = new HopcroftKarp(numWords, n);
            for (int i = 0; i < numWords; i++) {
                int a = sc.nextInt() % n;
                int b = sc.nextInt() % n;
                bm.addEdge(i, a);
                bm.addEdge(i, b);
            }
            
            int matching = bm.findMaxMatching();
            if (matching == numWords) {
                System.out.println("successful hashing");
            } else {
                System.out.println("rehash necessary");
            }
        }
        
    }
    
    public static class HopcroftKarp {
        int n1, n2;
        int edges = 0;
        int[] last;
        List<Integer> prev;
        List<Integer> head;
        int[] matching;
        int[] dist;
        int[] Q;
        boolean[] used;
        boolean[] vis;

        HopcroftKarp(int n1, int n2) {
            this.n1 = n1;
            this.n2 = n2;

            last = new int[n1];
            prev = new ArrayList<>();
            head = new ArrayList<>();
            matching = new int[n2];
            dist = new int[n1];
            Q = new int[n1];
            used = new boolean[n1];
            vis = new boolean[n1];

            Arrays.fill(last, -1);
        }

        void addEdge(int u, int v) {
            head.add(v);
            prev.add(last[u]);
            last[u] = edges++;
        }

        void bfs() {
            Arrays.fill(dist, -1);
            int sizeQ = 0;
            for (int u = 0; u < n1; u++) {
                if (!used[u]) {
                    Q[sizeQ++] = u;
                    dist[u] = 0;
                }
            }
            for (int i = 0; i < sizeQ; i++) {
                int u1 = Q[i];
                for (int e = last[u1]; e >= 0; e = prev.get(e)) {
                    int u2 = matching[head.get(e)];
                    if (u2 >= 0 && dist[u2] < 0) {
                        dist[u2] = dist[u1] + 1;
                        Q[sizeQ++] = u2;
                    }
                }
            }
        }

        boolean dfs(int u1) {
            vis[u1] = true;
            for (int e = last[u1]; e >= 0; e = prev.get(e)) {
                int v = head.get(e);
                int u2 = matching[v];
                if (u2 < 0 || !vis[u2] && dist[u2] == dist[u1] + 1 && dfs(u2)) {
                    matching[v] = u1;
                    used[u1] = true;
                    return true;
                }
            }
            return false;
        }

        int findMaxMatching() {
            Arrays.fill(matching, -1);
            int numMatches = 0;
            int f;
            do {
                bfs();
                Arrays.fill(vis, false);
                f = 0;
                for (int u = 0; u < n1; u++) {
                    if (!used[u] && dfs(u)) {
                        f++;
                    }
                }
                numMatches += f;
            } while (f > 0);
            return numMatches;
        }
    }

}
