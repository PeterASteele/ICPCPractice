import java.util.PriorityQueue;
import java.util.*;

public class BigTruck {
    static ArrayList<Edge>[] arr;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        arr = new ArrayList[n];
        int[] guys = new int[n];
        for (int a = 0; a < n; a++) {
            guys[a] = input.nextInt();
            arr[a] = new ArrayList<Edge>();

        }
        int m = input.nextInt();
        for (int a = 0; a < m; a++) {
            int st = input.nextInt() - 1;
            int end = input.nextInt() - 1;
            long dist = input.nextInt();
            arr[st].add(new Edge(end, dist * 1000000 - guys[end]));
            arr[end].add(new Edge(st, dist * 1000000 - guys[st]));
        }
        State i = dijkstras(0, n - 1, guys[0]);
        if (i == null) {
            System.out.println("impossible");
        } else {
            System.out.println(((i.dist.dist - 1) / 1000000 + 1) + " " + (1000000 - i.dist.dist % 1000000));
        }
    }

    public static class Edge {
        int end;
        long dist;

        public Edge(int _end, long _dist) {
            end = _end;
            dist = _dist;
        }
    }

    /**
     * How to implement Dijkstra's algorithm when the nodes/edges of a graph
     * either are not enumerable in advance, or change dynamically. Courtesy
     * Scott Pruett.
     *
     * Key is a "State" class that represents a shortest path from the source
     * along with any information that may be relevant to the problem.
     */
    static class State implements Comparable<State> {
        final DistanceType dist; // cost of path + tiebreakers
        final int id;
        // any other information about it
        // e.g. location, other information

        public State(DistanceType dist, int _id) {
            this.dist = dist;
            this.id = _id;
            // ...
        }

        @Override
        public int compareTo(State s) {
            // delegate to distance
            return dist.compareTo(s.dist);
        }

        @Override
        public int hashCode() {
            // use as much information as possible
            // do not include dist. must obey contract
            // that a == b implies a.hashCode() = b.hashCode()
            // ...
            return id;
        }

        @Override
        public boolean equals(Object o) {
            State s = (State) o;
            // compare additional information.
            // do not include distance: two states are
            // the same regardless of dist.
            // ...
            return id == id;
        }

        /**
         * Produce this state's successors.
         */
        List<State> adj() {
            List<State> adj = new ArrayList<State>();
            // compute successor states based on this state's
            // information. The successor states may depend
            // on internal information that compress information
            // about the route taken from the start vertex.
            // each successor must be supplied with an
            // appropriate dist.
            /***************/
            for(Edge e: arr[id]){
                State tmp = new State(new DistanceType(e.dist+dist.dist), e.end);
                adj.add(tmp);
            }
            return adj;
        }
    }

    /*
     * A type to represent the distance from start. In this simplest case, an
     * int. If the problem asks for a given way of breaking ties, use a class
     * and implement an approriate tie breaker
     */
    static class DistanceType implements Comparable<DistanceType> {
        final long dist;

        DistanceType(long dist) {
            this.dist = dist;
        }

        public int compareTo(DistanceType a) {
            // first order comparator is distance
            return Long.compare(dist, a.dist);
        }
    }

    static State dijkstras(int st, int e, int initialHelp) {
        // uses compare() for sorting by distance
        PriorityQueue<State> pq = new PriorityQueue<State>();

        // uses hashCode() + equals() to find if equivalent state was already
        // found
        HashMap<State, DistanceType> dist = new HashMap<State, DistanceType>();

        State start = new State(new DistanceType(initialHelp * -1), st);
        pq.offer(start);
        dist.put(start, new DistanceType(initialHelp * -1));
        while (!pq.isEmpty()) {
            State cur = pq.poll();

            if (cur.id == e) { // check if target is reached
                return cur;
            }

            for (State adj : cur.adj()) {
                DistanceType bestSoFar = dist.get(adj);
                if (bestSoFar == null || adj.dist.compareTo(bestSoFar) < 0) {
                    pq.offer(adj);
                    dist.put(adj, adj.dist);
                }
            }
        }
        // no path to target
        return null;
    }
}