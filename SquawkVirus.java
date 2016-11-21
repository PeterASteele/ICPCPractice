import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/squawk
public class SquawkVirus {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int n=  sc.nextInt();
        int m = sc.nextInt();
        int src = sc.nextInt();
        int time = sc.nextInt();
        long[][] counts = new long[time + 1][n];
        counts[0][src] = 1;
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }
        
        for (int t = 0; t < time; t++) {
            for (int u = 0 ; u < n; u++) {
                for (int v  : adj[u]) {
                    counts[t+1][v] += counts[t][u];
                }
            }
        }
        
        System.out.println(Arrays.stream(counts[time]).sum());
    }
}
