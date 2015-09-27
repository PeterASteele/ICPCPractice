import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;
/*
 * https://open.kattis.com/problems/2048
 * 2048 is a single-player puzzle game created by Gabriele Cirulli1. It is played on a 4×4 grid that contains integers ≥2 that are powers of 2. The player can use a keyboard arrow key (left/up/right/down) to move all the tiles simultaneously. Tiles slide as far as possible in the chosen direction until they are stopped by either another tile or the edge of the grid. If two tiles of the same number collide while moving, they will merge into a tile with the total value of the two tiles that collided. The resulting tile cannot merge with another tile again in the same move. Please observe this merging behavior carefully in all Sample Inputs and Outputs.

Input
The input is always a valid game state of a 2048 puzzle. The first four lines of input, that each contains four integers, describe the 16 integers in the 4×4 grid of 2048 puzzle. The j-th integer in the i-th line denotes the content of the cell located at the i-th row and the j-th cell. For this problem, all integers in the input will be either {0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024}. Integer 0 means an empty cell.

The fifth line of input contains an integer 0, 1, 2, or 3 that denotes a left, up, right, or down move executed by the player, respectively.

Output
Output four lines with four integers each. Two integers in a line must be separated by a single space. This describes the new state of the 4×4 grid of 2048 puzzle. Again, integer 0 means an empty cell. Note that in this problem, you can ignore the part from the 2048 puzzle where it introduces a new random tile with a value of either 2 or 4 in an empty spot of the board at the start of a new turn.

Sample Input 1	Sample Output 1
2 0 0 2
4 16 8 2
2 64 32 4
1024 1024 64 0
0
4 0 0 0
4 16 8 2
2 64 32 4
2048 64 0 0
Sample Input 2	Sample Output 2
2 0 0 2
4 16 8 2
2 64 32 4
1024 1024 64 0
1
2 16 8 4
4 64 32 4
2 1024 64 0
1024 0 0 0
Sample Input 3	Sample Output 3
2 0 0 2
4 16 8 2
2 64 32 4
1024 1024 64 0
2
0 0 0 4
4 16 8 2
2 64 32 4
0 0 2048 64
Sample Input 4	Sample Output 4
2 0 0 2
4 16 8 2
2 64 32 4
1024 1024 64 0
3
2 0 0 0
4 16 8 0
2 64 32 4
1024 1024 64 4
Sample Input 5	Sample Output 5
2 2 4 8
4 0 4 4
16 16 16 16
32 16 16 32
0
4 4 8 0
8 4 0 0
32 32 0 0
32 32 32 0
Sample Input 6	Sample Output 6
2 2 4 8
4 0 4 4
16 16 16 16
32 16 16 32
2
0 4 4 8
0 0 4 8
0 0 32 32
0 32 32 32
 */

public class G2048 {
	public static void main(String[] args) {
		Scanner input = new Scanner();
		int[][] storage = new int[4][4];
		for(int a = 0; a < 4; a++){
			for(int b = 0; b < 4; b++){
				storage[a][b] = input.nextInt();
			}
		}
		boolean[][] haveMergedOnSquare = new boolean[4][4];
		int action = input.nextInt();
		int tempAction = action;
		if(action != 0){
			storage = rotation(storage, action);
		}
		//printMatrix(storage);
		storage = move(storage, haveMergedOnSquare);
		//printMatrix(storage);
		if(tempAction != 0){
			storage = rotation(storage, 4-tempAction);
		}
		
		for(int a = 0; a < 4; a++){
			for(int b = 0; b < 4; b++){
				System.out.print(storage[a][b] + " ");
			}
			System.out.println();
		}
		
	}
	public static void printMatrix(int[][] storage){
		for(int a = 0; a < 4; a++){
			for(int b = 0; b < 4; b++){
				System.out.print(storage[a][b] + " ");
			}
			System.out.println();
		}
	}
	public static int[][] rotation(int[][] storage, int action) {
		if(action == 0){
			return storage;
		}
		int[][] newStorage = new int[4][4];
		//sorry class if you have to look at this, don't judge
			newStorage[0][0] = storage[0][3];
			newStorage[0][1] = storage[1][3];
			newStorage[0][2] = storage[2][3];
			newStorage[0][3] = storage[3][3];
			
			newStorage[1][0] = storage[0][2];
			newStorage[1][1] = storage[1][2];
			newStorage[1][2] = storage[2][2];
			newStorage[1][3] = storage[3][2];
			
			newStorage[2][0] = storage[0][1];
			newStorage[2][1] = storage[1][1];
			newStorage[2][2] = storage[2][1];
			newStorage[2][3] = storage[3][1];
			
			newStorage[3][0] = storage[0][0];
			newStorage[3][1] = storage[1][0];
			newStorage[3][2] = storage[2][0];
			newStorage[3][3] = storage[3][0];
			action--;
		return rotation(newStorage, action);
	}

	public static int[][] move(int[][] storage,
			boolean[][] haveMergedOnSquare) {
		
			for(int a = 0; a < 4; a++){
				for(int b = 0; b < 3; b++){
					if(storage[a][b] == 0){
						boolean test = true;
						for(int c = b; c < 3; c++){
							if(storage[a][c + 1] != 0){
								test = false;
							}
							storage[a][c] = storage[a][c+1];
							storage[a][c+1] = 0;
						}
						if(test == false){
							return move(storage, haveMergedOnSquare);
						}
					}
					if(!haveMergedOnSquare[a][b] && !haveMergedOnSquare[a][b+1] ){
						if(storage[a][b] == storage[a][b+1] && storage[a][b] != 0){
							storage[a][b] = storage[a][b] * 2;
							storage[a][b+1] = 0;
							haveMergedOnSquare[a][b] = true;
							haveMergedOnSquare[a][b+1] = false;
							return move(storage, haveMergedOnSquare);
						}						
					}
				}
			}
				
		return storage;
	}

	//////Fastscanner class.
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
