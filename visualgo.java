import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
/*
 * 
 * https://open.kattis.com/problems/visualgo
 * VisuAlgo (http://visualgo.net) is a website developed by a team of staff and students of School of Computing, National University of Singapore, the host of the 2015 ACM-ICPC Asia Singapore Regional. VisuAlgo visualizes a number of popular data structures and algorithms in the Computer Science curriculum. Currently, it receives approximately 2000 hits/day from CS students and instructors worldwide.

One new feature of VisuAlgo is the online quiz. As an example, the above figure shows a question about the classic Single-Source (Single-Destination) Shortest Paths problem in graph theory. The beauty of this online quiz feature is that the question parameters are randomized. The drawn graph G is taken from a collection of hundreds of directed weighted graphs (with their 2-D layouts) in VisuAlgo’s internal database. The graph G has V vertices numbered from [0..V−1]. The source vertex s and the destination vertex t are selected at random from [0..V−1].

However, such randomization of the question parameters may produce either a trivial question (e.g. “No Answer” when s and t are disconnected, 0 when s=t, simple tracing of a path if there is only a single unique path from s to t as shown in the above figure) or insanely difficult question to be computed manually if there are too many possible shortest paths from s to t.

The developers of VisuAlgo want to calibrate such Shortest Paths question with randomized parameters so that it is possible for a normal Computer Science student to answer the randomly generated question manually within a reasonable amount of time. Please help them.

Input
The first line of input contains two non-negative integers 1≤V≤10000 and 0≤E≤200000, giving the number of vertices and edges of the drawn graph G.

Thereafter follow E lines, each describing the directed weighted edges in G by three integers 0≤u,v≤V−1 and 1≤w≤99 (VisuAlgo limits the edge weight to be at most 2 characters for visual aesthetic purpose), where u and v are the vertex numbers and w is the weight of the directed edge u→v. It is guaranteed that G is a simple graph without self-loops or multiple directed edges with the same direction between the same pair of vertices.

Finally, there are two integers in the last line of input 0≤s,t≤V−1.

Output
Print a line with the number of different shortest paths between s to t in G. Two shortest paths p1 and p2 are considered different if there exists at least one edge in p1 that is not found in p2. It is guaranteed that the answer fits in a 32-bit signed integer data type.

Sample Input 1	Sample Output 1
6 10
0 1 26
1 3 29
1 5 9
2 3 25
2 4 43
4 2 3
5 0 13
5 2 33
5 3 18
5 4 58
5 1
1
Sample Input 2	Sample Output 2
7 9
0 1 1
0 2 2
1 2 1
2 3 1
2 4 3
3 4 1
4 5 1
4 6 2
5 6 1
0 6
4
Sample Input 3	Sample Output 3
5 6
0 1 1
1 2 2
2 4 3
0 3 3
3 4 4
0 4 6
0 4
2
 */
public class visualgo {
	static ArrayList<HashMap<Integer, Integer>> storage;
	static long[] dist;
	static long[] numDist;
	public static void main(String[] args) {
		storage = new ArrayList<HashMap<Integer, Integer>>();
		
		Scanner input = new Scanner();
		int nodes = input.nextInt();
		int edges = input.nextInt();
		dist = new long[nodes];
		numDist = new long[nodes];
		for(int a = 0; a < nodes; a++){
			storage.add(new HashMap<Integer, Integer>());
		}
		for(int a = 0; a < edges; a++){
			int edge1 = input.nextInt();
			int edge2 = input.nextInt();
			storage.get(edge1).put(edge2, input.nextInt());
		}
		int start = input.nextInt();
		int goal = input.nextInt();
		//store those edges
		dijkstra(nodes, start, goal);
		System.out.println(numDist[goal]);
		
	}

	static final long INF = Long.MAX_VALUE;


	static class Entry2 implements Comparable {
		public int index;
		public long distance;

		public Entry2(long d, int i) {
			distance = d;
			index = i;
		}

		public int compareTo(Object o) {
			Entry2 e = (Entry2) o;
			if (distance != e.distance) {
				return (int) (distance - e.distance);
			}
			return index - e.index;
		}
	}

	static void dijkstra(int N, int start, int goal) {
		Arrays.fill(dist, INF);
		Arrays.fill(numDist, 0);
		dist[start] = 0;
		numDist[start] = 1;
		int[] prev = new int[N];
		Queue<Entry2> frontier = new PriorityQueue<Entry2>(dist.length);
		frontier.offer(new Entry2(0, start));

		while (!frontier.isEmpty()) {
			Entry2 e = frontier.poll();
			int u = e.index;
			if (u == goal)
				break;

				if (dist[u] < e.distance)
				continue;

			for (Entry<Integer, Integer> entryV : neighborsOf(u).entrySet()) {
				int v = entryV.getKey();
				long uv = distance2(u, v);
				if (dist[u] + uv <= dist[v]) {
					numDist[v] = numDist[u] + numDist[v];
				}
				if (dist[u] + uv < dist[v]) {
					prev[v] = u;
					dist[v] = dist[u] + uv;
					numDist[v] = numDist[u];
					frontier.offer(new Entry2(dist[v], v));
				}
				
			}
		}
		}

	public static HashMap<Integer, Integer> neighborsOf(int u) {
		return storage.get(u);
	}

	public static long distance2(int u, int v) {
		return storage.get(u).get(v);
	}

	public static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		public Scanner(Reader in) {
			br = new BufferedReader(in);
		}

		public Scanner() {
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
