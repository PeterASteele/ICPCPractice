import java.util.*;

import java.math.*;
import java.io.*;
////NEERC 2015 Problem F (Practice)
//http://codeforces.com/gym/100851/attachments
public class FroggyFord {
    static Node[] nodeArray;

    public static void main(String[] args) throws IOException {
        String fileName = "froggy";
        // FastScanner input = new FastScanner();
         Scanner input = new Scanner(new File(fileName + ".in"));
        // Scanner sc = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
        // FastScanner sc = new FastScanner();
        PrintWriter writer = new PrintWriter(fileName + ".out");

        int w = input.nextInt();
        int n = input.nextInt();
        nodeArray = new Node[n + 2];
        for (int a = 1; a <= n; a++) {
            long x = input.nextInt();
            long y = input.nextInt();
            nodeArray[a] = new Node(a, x, y);
        }
        nodeArray[0] = new Node(0, 0, 0);
        nodeArray[n + 1] = new Node(n + 1, w, w);
        for (int a = 1; a <= n; a++) {
            for (int b = a + 1; b <= n; b++) {
                double dist =  getDistance(nodeArray[a].location, nodeArray[b].location);
                Edge temp = new Edge(a, b, dist);
                nodeArray[a].edges.add(temp);
                Edge temp2 = new Edge(b, a, dist);
                nodeArray[b].edges.add(temp2);
            }
        }
        for(int a = 1; a <= n; a++){
            Edge temp = new Edge(0, a, nodeArray[a].location.x);
            Edge temp2 = new Edge(a, 0, nodeArray[a].location.x);
            nodeArray[0].edges.add(temp);
            nodeArray[a].edges.add(temp2);
        }
        for(int a = 1; a <= n; a++){
            Edge temp = new Edge(n+1, a, w-nodeArray[a].location.x);
            Edge temp2 = new Edge(a, n+1, w-nodeArray[a].location.x);
            nodeArray[n+1].edges.add(temp);
            nodeArray[a].edges.add(temp2);
        }
        nodeArray[0].edges.add(new Edge(0, n+1, w));
        nodeArray[n+1].edges.add(new Edge(n+1, 0, w));
        
        double lowerBound = 0;
        double upperBound = w/2 + 1;
        int stoneAE = -1;
        int stoneBE = -1;
        while(upperBound - lowerBound > Math.ulp((upperBound + lowerBound)/2)*100){
            double jumpSize = (upperBound + lowerBound)/2;
            Deque<Integer> traversal = new ArrayDeque<>();
            boolean[] visited = new boolean[n+2];
            int[] stoneA = new int[n+2];
            int[] stoneB = new int[n+2];
            traversal.push(0);
            visited[0] = true;
            
            while (!traversal.isEmpty()) {
                int u = traversal.pop();
                for (Edge e : nodeArray[u].edges) {
                    int v = e.v;
                    if (!visited[v]) {
                        if (e.dist <= jumpSize) {
                            visited[v] = true;
                            traversal.push(v);
                        }
                    }
                }
            }
            HashSet<Integer> newNodes = new HashSet<Integer>();
            for(int a = 0; a <= n+1; a++){
                if(visited[a]){
                    for (Edge e : nodeArray[a].edges) {
                        int v = e.v;
                        if (!visited[v]) {
                            if (e.dist <= 2*jumpSize) {
                                newNodes.add(v);
                                stoneB[v] = v;
                                stoneA[v] = a;
                                stoneB[a] = v;
                                stoneA[a] = a;
                            }
                        }
                    }
                }
            }
            for(Integer a:newNodes){
                visited[a] = true;
                traversal.push(a);
            }
            while (!traversal.isEmpty()) {
                int u = traversal.pop();
                for (Edge e : nodeArray[u].edges) {
                    int v = e.v;
                    if (!visited[v]) {
                        if (e.dist <= jumpSize) {
                            visited[v] = true;
                            stoneB[v] = stoneB[u];
                            stoneA[v] = stoneA[u];
                            traversal.push(v);
                        }
                    }
                }
            }
//            System.out.println("A "+ Arrays.toString(stoneA));
//            System.out.println("B " + Arrays.toString(stoneB));
            if(visited[n+1]){
                upperBound = jumpSize;
                stoneAE = stoneA[n+1];
                stoneBE = stoneB[n+1];
            }
            else{
                lowerBound = jumpSize;
            }
            
        } 
        
        if (stoneAE == 0) {
            nodeArray[0].location.y = nodeArray[stoneBE].location.y;
        }
        if (stoneBE == n+1) {
            nodeArray[n+1].location.y = nodeArray[stoneAE].location.y;
        }
//        System.out.println(upperBound);
//        System.out.println(stoneAE);
//        System.out.println(stoneBE);
        Point p1 = nodeArray[stoneAE].location;
        Point p2 = nodeArray[stoneBE].location;
        double xx = (p1.x + p2.x) / 2.0;
        double xy = (p1.y + p2.y) / 2.0;
        
        writer.printf("%.20f %.20f", xx, xy);
        writer.close();

    }
    
    private static double getDistance(Point location, Point location2) {
        long xDiff = location.x - location2.x;
        long yDiff = location.y - location2.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public static class Node {
        Point location;
        int id;
        ArrayList<Edge> edges;

        public Node(int id, long x, long y) {
            this.id = id;
            location = new Point(x, y);
            edges = new ArrayList<Edge>();
        }
    }

    static class Edge implements Comparable<Edge> {
        final int u, v;
        final double dist;

        public Edge(int uu, int vv, double d) {
            u = uu;
            v = vv;
            dist = d;
        }

        @Override
        public int compareTo(Edge o) {
            return Double.compare(dist, o.dist);
        }
    }

    public static class Point {
        long x;
        long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
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