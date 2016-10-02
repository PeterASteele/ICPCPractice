import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/contests/na16warmup1/problems/kitchen
public class KitchenMeasurements {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        n = sc.nextInt();
        cupCapacity = new int[n];
        for (int i = 0; i < n; i++) {
            cupCapacity[i] = sc.nextInt();
        }
        goal = sc.nextInt();
        int[] initCups = new int[n];
        initCups[0] = cupCapacity[0];
        
        int distToGoal = dijkstra(new State(initCups, 0));
        
        if (distToGoal < INF) {
            System.out.println(distToGoal);
        } else {
            System.out.println("impossible");
        }
    }
    
    static final int INF = Integer.MAX_VALUE / 2;
    
    static int dijkstra(State initial) {
        Map<State, Integer> dist = new HashMap<>();
        Queue<Entry> pq = new PriorityQueue<>();
        dist.put(initial, 0);
        pq.offer(new Entry(initial, 0));
        
        while (!pq.isEmpty()) {
            Entry e = pq.poll();
            State cur = e.state;
            int distToCur = e.dist;
            if (distToCur <= dist.get(cur)) {
                if (cur.isGoal()) {
                    return distToCur;
                }
                
                List<State> adj = cur.adj();
                for (State next : adj) {
                    Integer nextDist = dist.get(next);
                    int updatedDist = distToCur + next.lastPoured;
                    if (nextDist == null || updatedDist < nextDist) {
                        pq.offer(new Entry(next, updatedDist));
                        dist.put(next, updatedDist);
                    }
                }
            }
        }
        
        return INF;
    }
    
    static int n;
    static int goal;
    static int[] cupCapacity;
    
    static class Entry implements Comparable<Entry> {
        final State state;
        final int dist;
        public Entry(State s, int d) {
            this.state = s;
            this.dist = d;
        }
        
        @Override
        public int compareTo(Entry e) {
            return Integer.compare(dist, e.dist);
        }
    }
    
    static class State {
        final int[] cups;
        final int lastPoured;
        
        State(int[] c, int l) {
            this.cups = c;
            this.lastPoured = l;
        }
        
        @Override
        public boolean equals(Object o) {
            State s = (State) o;
            return Arrays.equals(cups, s.cups);
        }
        
        List<State> adj() {
            List<State> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Pour i into j
                    if (i != j) {
                        int pourVal = Math.min(cups[i], cupCapacity[j] - cups[j]);
                        if (pourVal > 0) {
                            int[] newCups = cups.clone();
                            newCups[i] -= pourVal;
                            newCups[j] += pourVal;
                            State next = new State(newCups, pourVal);
                            adj.add(next);
                        }
                    }
                }
            }
            
            return adj;
        }
        
        @Override
        public int hashCode() {
            return Arrays.hashCode(cups);
        }
        
        public boolean isGoal() {
            return cups[0] == goal;
        }
    }
}
