import java.util.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;
//https://open.kattis.com/problems/transportation
public class TransportationDelegation {
    static HashMap<String, Node> map;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        MaxFlowSolver i = new Dinic();
        map = new HashMap<String, Node>();
        int states = input.nextInt();
        int materials = input.nextInt();
        int factories = input.nextInt();
        int transportation = input.nextInt();
        
        Node source = i.addNode();
        Node sink = i.addNode();
        
        for(int a = 0; a < materials; a++){
            String in = input.next();
            Node materialSite = i.addNode();
            i.link(source, materialSite, 1);
            map.put(in, materialSite);
        }
        
        for(int a = 0; a < factories; a++){
            String in = input.next();
            Node factory = i.addNode();
            i.link(factory, sink, 1);
            map.put(in, factory);
        }
        
        for(int a = 0; a < transportation; a++){
            Node transportHub = i.addNode();
            Node transportHubEnd = i.addNode();
            i.link(transportHub, transportHubEnd, 1);
            int numStates = input.nextInt();
            for(int b = 0; b < numStates; b++){
                String stateName = input.next();
                if(!map.containsKey(stateName)){
                    map.put(stateName, i.addNode());
                }
                i.link(map.get(stateName), transportHub, 1);
                i.link(transportHubEnd, map.get(stateName), 1);
            }
        }
        System.out.println(i.getMaxFlow(source, sink));
        
    }
    
    
    public static class Node {
        // thou shall not create nodes except through addNode()
        private Node() { }

        List<Edge> edges = new ArrayList<Edge>();
        int index;      // index in nodes array
      }

      public static class Edge {
        boolean forward; // true: edge is in original graph
                         // false: edge is a backward edge
        Node from, to;   // nodes connected
        int flow;        // current flow
        final int capacity;
        Edge dual;      // reference to this edge's dual
        long cost;      // only used for MinCost.

        // thou shall not create edges except through link()
        protected Edge(Node s, Node d, int c, boolean f) {
          forward = f;
          from = s;
          to = d;
          capacity = c;
        }

        int remaining() { return capacity - flow; }

        void addFlow(int amount) {
          flow += amount;
          dual.flow -= amount;
        }
      }

      public static abstract class MaxFlowSolver {
        /* List of nodes, indexed. */
        List<Node> nodes = new ArrayList<Node>();

        public void link(Node n1, Node n2, int capacity) {
          Edge e12 = new Edge(n1, n2, capacity, true);
          Edge e21 = new Edge(n2, n1, 0, false);
          e12.dual = e21;
          e21.dual = e12;
          n1.edges.add(e12);
          n2.edges.add(e21);
        }

        public void link(Node n1, Node n2, int capacity, long cost) {
          Edge e12 = new Edge(n1, n2, capacity, true);
          Edge e21 = new Edge(n2, n1, 0, false);
          e12.dual = e21;
          e21.dual = e12;
          n1.edges.add(e12);
          n2.edges.add(e21);
          e12.cost = cost;
          e21.cost = -cost;
        }

        void link(int n1, int n2, int capacity) {
          link(nodes.get(n1), nodes.get(n2), capacity);
        }

        protected MaxFlowSolver(int n) {
          for (int i = 0; i < n; i++)
            addNode();
        }

        protected MaxFlowSolver() { this(0); }

        public abstract int getMaxFlow(Node src, Node snk);

        public Node addNode() {
          Node n = new Node();
          n.index = nodes.size();
          nodes.add(n);
          return n;
        }

        /* OPTIONAL: Returns the edges associated with the Min Cut.
         * Must be run immediately after a getMaxFlow() call.  */
        List<Edge> getMinCut(Node src) {
          Queue<Node> bfs = new ArrayDeque<Node>();
          Set<Node> visited = new HashSet<Node>();
          bfs.offer(src);
          visited.add(src);
          while (!bfs.isEmpty()) {
            Node u = bfs.poll();
            for (Edge e : u.edges) {
              if (e.remaining() > 0 && !visited.contains(e.to)) {
                visited.add(e.to);
                bfs.offer(e.to);
              }
            }
          }
          List<Edge> minCut = new ArrayList<Edge>();
          for (Node s : visited) {
            for (Edge e : s.edges)
              if (e.forward && !visited.contains(e.to))
                minCut.add(e);
          }
          return minCut;
        }

        /* OPTIONAL: for min-cost, Klein's cycle canceling algorithm.
         * Must be run after getMaxFlow() call.
         * Given any max flow (with edge costs), cancel negative cycles
         * until the flow is a min-cost max-flow.
         * According to CS226 slides, this is O(E^2 C U) where C and U
         * are max cost and capacity, respectively.
         * @return total cost of flow
         */
        long minimizeFlow(Node src) {
          final long SUITABLE_INF = Long.MAX_VALUE / 2 - 1;

          // While there are negative cycles
          while (true) {

            // Find a negative cycle (sparse Bellman-Ford)
            // Initialize the graph
            int V = nodes.size();
            long[] dist = new long[V];
            Node[] pred = new Node[V];
            Arrays.fill(dist, SUITABLE_INF);
            dist[src.index] = 0;

            // Run up to V times
            boolean change = true;
            for (int i = 0; change && i < V; i++) {
              change = false;
              // For each (u, v) edge, try to relax
              for (Node from : nodes) {
                for (Edge e : from.edges) {
                  if (e.remaining() == 0) {
                    continue;
                  }
                  Node to = e.to;
                  if (dist[from.index] + e.cost < dist[to.index]) {
                    change = true;
                    dist[to.index] = dist[from.index] + e.cost;
                    pred[to.index] = from;
                  }
                }
              }
            }

            // Find a negative-weight cycle
            ArrayList<Node> cycle = null;
            for (Node startingNode : nodes) {
              boolean[] seen = new boolean[V];
              Node currNode = startingNode;
              while (pred[currNode.index] != null 
                  && !seen[currNode.index]) {
                seen[currNode.index] = true;
                currNode = pred[currNode.index];
              }
              if (pred[currNode.index] == null) {
                continue;
              }

              Node endNode = currNode;
              cycle = new ArrayList<Node>();
              while (true) {
                cycle.add(currNode);
                currNode = pred[currNode.index];
                if (currNode == endNode) {
                  break;
                }
              }
            }

            // If there's no negative weight cycle, we're done
            if (cycle == null) {
              break;
            }

            // Get a list of edges in the cycle
            ArrayList<Edge> edgeCycle = new ArrayList<Edge>();
            for (int i = 0; i < cycle.size(); i++) {
              Node to = cycle.get(i);
              Node from = cycle.get((i + 1) % cycle.size());
              for (Edge e : from.edges) {
                if (e.to == to) {
                  edgeCycle.add(e);
                  break;
                }
              }
            }

            // Find the minimum residual capacity in the cycle
            int minResidual = Integer.MAX_VALUE;
            for (Edge e : edgeCycle) {
              minResidual = Math.min(minResidual, e.remaining());
            }

            if (minResidual == 0) {
              break;
            }

            // Add this flow to every edge on the cycle
            for (Edge e : edgeCycle) {
              e.addFlow(minResidual);
            }
          }

          return calcFlowCost();
        }

        long calcFlowCost() {
          // Calculate the cost of the flow
          long cost = 0;
          for (Node n : nodes) {
            for (Edge e : n.edges) {
              if (e.flow > 0) {
                cost += e.cost * e.flow;
              }
            }
          }
          return cost;
        }
      }

      public static class Dinic extends MaxFlowSolver {
        int [] dist;          // distance from src for level graph
        boolean [] blocked;

        int BlockingFlow(Node src, Node snk) {
          int N = nodes.size();
          dist = new int[N];
          Arrays.fill(dist, -1);
          int [] Q = new int[N];
          int s = src.index;
          int t = snk.index;

          /* Step 1.  BFS from source to compute levels */
          dist[s] = 0;
          int head = 0, tail = 0;
          Q[tail++] = s;
          while (head < tail) {
            int x = Q[head++];
            List<Edge> succ = nodes.get(x).edges;
            for (Edge e : succ) {
              if (dist[e.to.index] == -1 && e.remaining() > 0) {
                dist[e.to.index] = dist[e.from.index] + 1;
                Q[tail++] = e.to.index;
              }
            }
          }

          if (dist[t] == -1)     // no flow if sink is not reachable
            return 0;

          /* Step 2. Push flow down until we have a blocking flow */
          int flow, totflow = 0;
          blocked = new boolean[N];
          do {
            flow = dfs(src, snk, Integer.MAX_VALUE);
            totflow += flow;
          } while (flow > 0);
          return totflow;
        }

        // Run DFS on the BFS level graph.
        int dfs(Node v, Node snk, int mincap) {
          if (v == snk) // reached sink
            return mincap;

          for (Edge e : v.edges) {
            if (!blocked[e.to.index]
                && dist[e.from.index] + 1 == dist[e.to.index]
                && e.remaining() > 0) {
              int flow = dfs(e.to, snk, min(mincap, e.remaining()));
              if (flow > 0) {
                e.addFlow(flow);
                return flow;
              }
            }
          }
          // if we couldn't add any flow then there is no point in 
          // ever going past this node again.  Mark it blocked.
          blocked[v.index] = true;
          return 0;
        }

        @Override
        public int getMaxFlow(Node src, Node snk) {
          int flow, totflow = 0;
          while ((flow = BlockingFlow(src, snk)) != 0)
            totflow += flow;
          return totflow;
        }

        public Dinic () { this(0); }
        public Dinic (int n) { super(n); }
      }

    
}
