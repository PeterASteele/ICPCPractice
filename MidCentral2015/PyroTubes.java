import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/contests/na16warmup1/problems/pyro
public class PyroTubes {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Scanner input = new Scanner(System.in);

		List<Integer> values = new ArrayList<>();
		Set<Integer> set = new HashSet<>();

		while (sc.hasNextInt()) {
			int val = sc.nextInt();
			if (val == -1)
				break;
			values.add(val);
			set.add(val);
		}

		StringBuilder sb = new StringBuilder();

		for (int val : values) {
			int count = 0;
			for (int i = 0; i < 18; i++) {
				int tmp = val ^ (1 << i);
				if (tmp > val && set.contains(tmp)) {
					count++;
				}
			}

			for (int i = 0; i < 18; i++) {
				for (int j = i + 1; j < 18; j++) {
					int tmp = val ^ (1 << i);
					tmp = tmp ^ (1 << j);
					if (tmp > val && set.contains(tmp)) {
						count++;
					}
				}
			}

			sb.append(val).append(':').append(count).append('\n');
		}

		System.out.print(sb);
	}
}
