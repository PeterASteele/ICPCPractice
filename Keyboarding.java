import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
//https://icpc.kattis.com/problems/keyboard
//World Finals 2015 (team practice)

public class Keyboarding {
	static int n;
	static int m;
	static int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		n = input.nextInt();
		m = input.nextInt();
		char[][] arr = new char[n][m];
		for (int a = 0; a < n; a++) {
			arr[a] = input.next().toCharArray();
		}
		String goal = input.next() + '*';
		int[][] keyboardGraph = new int[n * m][4];
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				int id = hash(a, b);
				Arrays.fill(keyboardGraph[id], -1);
				char current = arr[a][b];
				label: for (int c = 0; c < directions.length; c++) {
					int copyA = a;
					int copyB = b;
					while (current == arr[copyA][copyB]) {
						copyA += directions[c][0];
						copyB += directions[c][1];
						if (!inBounds(copyA, copyB)) {
							continue label;
						}
					}
					keyboardGraph[id][c] = (hash(copyA, copyB));
				}
			}
		}
		int[][][] lengths = new int[n][m][goal.length()];
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				for (int c = 0; c < goal.length(); c++) {
					lengths[a][b][c] = -1;
				}
			}
		}
		int[] bfsQueue = new int[n * m * goal.length() + 100];
		int currentInsertLoc = 0;
		int currentPullLoc = 0;
		bfsQueue[currentInsertLoc] = hash(0, 0, 0);
		currentInsertLoc++;
		lengths[0][0][0] = 0;
		while (currentInsertLoc != currentPullLoc) {
			int itemp = bfsQueue[currentPullLoc];
			currentPullLoc++;
			int xyhash = itemp % 10000;
			int x = xyhash / m;
			int y = xyhash % m;
			int layer = itemp / 10000;
			if (arr[x][y] == '*' && layer == goal.length()) {
				break;
			}
			for (int i : keyboardGraph[xyhash]) {
				if (i != -1) {
					int x2 = i / m;
					int y2 = i % m;
					if (lengths[x2][y2][layer] == -1) {
						lengths[x2][y2][layer] = lengths[x][y][layer] + 1;
						bfsQueue[currentInsertLoc] = (i + layer * 10000);
						currentInsertLoc++;
					}
				}
			}
			if (layer < goal.length() - 1 && arr[x][y] == goal.charAt(layer)) {
				if (lengths[x][y][layer + 1] == -1) {
					lengths[x][y][layer + 1] = lengths[x][y][layer] + 1;
					bfsQueue[currentInsertLoc] = xyhash + (layer + 1) * 10000;
					currentInsertLoc++;
				}
			}
		}
		// for(int c = 0; c < goal.length(); c++){
		// for(int a = 0; a < n; a++){
		// for(int b = 0; b < m; b++){
		// System.out.print(lengths[a][b][c] + " ");
		// }
		// System.out.println();
		// }
		//
		// System.out.println(goal.charAt(c));
		// input.nextLine();
		// System.out.println();
		// }
		int min = Integer.MAX_VALUE;
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				if (arr[a][b] == '*') {
					if (min > lengths[a][b][goal.length() - 1]
							&& lengths[a][b][goal.length() - 1] != -1) {
						min = lengths[a][b][goal.length() - 1];
					}
				}
			}
		}
		System.out.println((min + 1));
	}

	private static boolean inBounds(int copyA, int copyB) {
		return copyA >= 0 && copyA < n && copyB >= 0 && copyB < m;
	}

	public static int hash(int x, int y, int layer) {
		return layer * 10000 + hash(x, y);
	}

	public static int hash(int x, int y) {
		return x * m + y;
	}
}