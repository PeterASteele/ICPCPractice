import java.util.*;
import java.math.*;
import java.io.*;
//NWERC2015 Problem E (Practice)
//https://open.kattis.com/contests/naipc16-p09/problems/elementarymath
public class ElementaryMath {
    static int n;
    static long[] a;
    static long[] b;
    static long[][] results;
    static Set<Long> used;
    static int[] op;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        op = new int[n];
        a = new long[n];
        b = new long[n];
        results = new long[n][4];
        Map<Long, Integer> compressed = new HashMap<>();
        List<Long> uncompressed = new ArrayList<>();
        int cur = n;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> equationNode = adj.get(i);
            long x = sc.nextLong();
            long y = sc.nextLong();
            long add = x + y;
            long sub = x - y;
            long mul = x * y;
            
            Integer addIdx = compressed.get(add);
            if (addIdx == null) {
                adj.add(new ArrayList<>());
                addIdx = cur;
                compressed.put(add, cur++);
                uncompressed.add(add);
            }
            Integer subIdx = compressed.get(sub);
            if (subIdx == null) {
                adj.add(new ArrayList<>());
                subIdx = cur;
                compressed.put(sub, cur++);
                uncompressed.add(sub);
            }
            Integer mulIdx = compressed.get(mul);
            if (mulIdx == null) {
                adj.add(new ArrayList<>());
                mulIdx = cur;
                compressed.put(mul, cur++);
                uncompressed.add(mul);
            }
            
            equationNode.add(compressed.get(add));
            adj.get(addIdx).add(i);
            if (!equationNode.contains(subIdx)) {
                equationNode.add(compressed.get(sub));
                adj.get(subIdx).add(i);
            }
            if (!equationNode.contains(mulIdx)) {
                equationNode.add(compressed.get(mul));
                adj.get(mulIdx).add(i);
            }
            
            results[i][ADD] = add;
            results[i][SUBTRACT] = sub;
            results[i][MULTIPLY] = mul;
            a[i] = x;
            b[i] = y;
        }
//        System.out.println(adj);
        BipartiteMatching bm = new BipartiteMatching(adj);
        int flow = bm.findMaxMatching();
        int[] match = bm.match;
//        System.out.println(Arrays.toString(match));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (match[i] == -1) {
                System.out.println("impossible");
                return;
            } else {
                long res = uncompressed.get(match[i]-n);
                sb.append(a[i]);
                if (res == a[i] + b[i]) {
                    sb.append(" + ");
                } else if (res == a[i] - b[i]) {
                    sb.append(" - ");
                } else if (res == a[i] * b[i]) {
                    sb.append(" * ");
                }
                sb.append(b[i]).append(" = ").append(res).append('\n');
            }
        }
        System.out.println(sb);
        
//        for (int i = 0; i < results.length; i++) {
//            System.out.println(Arrays.toString(results[i]));
//        }
        
    }
    
    static final int ADD = 1;
    static final int SUBTRACT = 2;
    static final int MULTIPLY = 3;
    
    public static class BipartiteMatching {
        int[] match;
        int matches = 0;
        boolean[] done;
        int N;

        ArrayList<ArrayList<Integer>> adj;

        // Initialized with Adjacency List.
        public BipartiteMatching(ArrayList<ArrayList<Integer>> adj) {
          this.adj = adj;
          N = adj.size();
          match = new int[N];
          Arrays.fill(match, -1);
        }

        // Returns matching size and populates match[] with
        // the details. -1 indicates no match.
        public int findMaxMatching() {
          for (int i = 0; i < N; i++) {
            done = new boolean[N];
            if (augment(i)) matches++;
          }
          return matches;
        }

        // Attempt to find an augmenting path.
        public boolean augment(int at) {
          if (done[at]) return false;
          done[at] = true;

          ArrayList<Integer> edges = adj.get(at);
          for (int i = 0; i < edges.size(); i++) {
            int to = edges.get(i);
            if (match[to] == -1 || augment(match[to])) {
              match[to] = at;
              return true;
            }
          }
          return false;
        }

      }
}