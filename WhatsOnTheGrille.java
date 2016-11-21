import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/grille
public class WhatsOnTheGrille {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        n = sc.nextInt();
        boolean[][] grid = new boolean[n][n];
        for (int row = 0; row < n; row++) {
            String str = sc.next();
            for (int col = 0; col < n; col++) {
                if (str.charAt(col) == '.') {
                    grid[row][col] = true;
                }
            }
        }
        String message = sc.next();

        int[][] used = new int[n][n];

        for (int i = 0; i < 4; i++) {
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (grid[row][col]) {
                        used[row][col]++;
                    }
                }
            }
            grid = rotateRight(grid);
        }
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (used[row][col] != 1) {
                    System.out.println("invalid grille");
                    return;
                }
            }
        }

        long[][] hashes = new long[n][n];
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                hashes[a][b] = Long.MAX_VALUE;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (grid[row][col]) {
                        long hash = i * 1000 + row * 10 + col;
                        if (hashes[row][col] > hash) {
                            hashes[row][col] = hash;
                        }
                    }
                }
            }

            grid = rotateRight(grid);
        }
        TreeSet<Long> ordered = new TreeSet<Long>();
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                ordered.add(hashes[a][b]);
            }
        }
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        int j = 0;
        for (Long i : ordered) {
            map.put(i, j);
            j++;
        }

        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                hashes[a][b] = map.get(hashes[a][b]);
            }
        }

        char[][] arr = new char[n][n];
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                arr[a][b] = message.charAt((int) hashes[a][b]);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                sb.append(arr[row][col]);
            }
        }
        System.out.println(sb);
        // int idx = 0;
        // char[][] board = new char[n][n];
        // for (int i = 0; i < 4; i++) {
        // for (int row = 0; row < n; row++) {
        // for (int col = 0; col < n; col++) {
        // if (grid[row][col]) {
        // board[row][col] = message.charAt(idx);
        // idx = (idx + 1) % message.length();
        // }
        // }
        // }
        // grid = rotateRight(grid);
        // }
        //
        // StringBuilder sb = new StringBuilder();
        // for (int row = 0; row < n; row++) {
        // for (int col = 0; col < n; col++) {
        // sb.append(board[row][col]);
        // }
        // }
        // System.out.println(sb);
    }

    static int n;

    static boolean[][] rotateRight(boolean[][] grid) {
        boolean[][] rotated = new boolean[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                rotated[col][n - row - 1] = grid[row][col];
            }
            // System.err.println(Arrays.toString(grid[row]));
        }
        // System.err.println("============");
        for (int row = 0; row < n; row++) {
            // System.err.println(Arrays.toString(rotated[row]));
        }

        return rotated;
    }

    static boolean[][] rotateLeft(boolean[][] grid) {
        boolean[][] rotated = new boolean[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                rotated[row][col] = grid[col][n - row - 1];
            }
            // System.err.println(Arrays.toString(grid[row]));
        }
        // System.err.println("============");
        for (int row = 0; row < n; row++) {
            // System.err.println(Arrays.toString(rotated[row]));
        }

        return rotated;
    }
}
