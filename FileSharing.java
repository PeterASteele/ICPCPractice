import java.util.*;
//http://codeforces.com/gym/100863/attachments, Problem F
public class FileSharing {
//@author Dustin Pho (pho)
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();

		Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
		for (int i = 0; i < N; i++) {
			int n = sc.nextInt();
			if (freq.containsKey(n)) {
				freq.put(n, freq.get(n) + 1);
			} else {
				freq.put(n, 1);
			}
		}

		PriorityQueue<Wave> pq = new PriorityQueue<Wave>();
		for (Map.Entry<Integer, Integer> m : freq.entrySet()) {
			pq.offer(new Wave(m.getValue(), m.getKey()));
		}

		int nodes = 1;
		int current = 0;
		int time = 0;
		while (true) {
			if (pq.isEmpty() && current >= K) break;

			if (pq.isEmpty()) {
				current += nodes - 1;
				time++;
			} else {
				if (current >= K) {
					Wave w = pq.poll();
					time = w.time + 1;
					current = nodes;
					nodes += w.nodes;
				} else {
					Wave w = pq.peek();
					if (w.time == time) {
						current = nodes;
						nodes += w.nodes;
						pq.poll();
					} else {
						current += nodes - 1;
					}
					time++;
				}
			}
		}

		System.out.println(time);

		sc.close();
	}

	static class Wave implements Comparable<Wave> {
		int nodes, time;

		Wave(int n, int t) {
			nodes = n;
			time = t;
		}

		@Override
		public int compareTo(Wave o) {
			return Integer.compare(time, o.time);
		}

	}

}
