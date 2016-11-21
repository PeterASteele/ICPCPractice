import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/rings2
public class Rings {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();

        char[][] board = new char[n][m];
        for (int i = 0; i < n; i++) {
            char[] array = sc.nextLine().toCharArray();
            for (int j = 0; j < m; j++) {
                board[i][j] = array[j];
            }
        }

        // for (int i = 0; i < n; i++) {
        // for (int j = 0; j < m; j++) {
        // System.out.print(board[i][j]);
        // }
        // System.out.println();
        // }

        int max = 0;
        int[][] soln = new int[n][m];
        for (int a = 0; a < 10000; a++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (board[i][j] == 'T') {
                        int lowestNeighbor = getLowestNeighbor(board, soln, i, j);
                        soln[i][j] = lowestNeighbor + 1;
                        max = Math.max(max, soln[i][j]);
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (max < 10) {
                    String num = soln[i][j] == 0 ? "" : "" + soln[i][j];
                    for (int k = 0; k < 2 - num.length(); k++) {
                        System.out.print('.');
                    }
                    if (!num.isEmpty()) {
                        System.out.print(num);
                    }
                } else {
                    String num = soln[i][j] == 0 ? "" : "" + soln[i][j];
                    for (int k = 0; k < 3 - num.length(); k++) {
                        System.out.print('.');
                    }
                    if (!num.isEmpty()) {
                        System.out.print(num);
                    }
                }

                // System.out.print(soln[i][j]);
            }
            System.out.println();
        }

    }

    public static int getLowestNeighbor(char[][] board, int[][] soln, int i, int j) {
        int min = Integer.MAX_VALUE / 1000;
        if (i - 1 >= 0) {
            if (board[i - 1][j] == '.') {
                return 0;
            }
            if (soln[i - 1][j] > 0) {
                min = Math.min(min, soln[i - 1][j]);
            }
        } else {
            return 0;
        }
        // System.err.println(min);

        if (i + 1 < board.length) {
            if (board[i + 1][j] == '.') {
                return 0;
            }
            if (soln[i + 1][j] > 0) {
                min = Math.min(min, soln[i + 1][j]);
            }
        } else {
            return 0;
        }

        if (j - 1 >= 0) {
            if (board[i][j - 1] == '.') {
                return 0;
            }
            if (soln[i][j - 1] > 0) {
                min = Math.min(min, soln[i][j - 1]);
            }
        } else {
            return 0;
        }

        if (j + 1 < board[0].length) {
            if (board[i][j + 1] == '.') {
                return 0;
            }
            if (soln[i][j + 1] > 0) {
                min = Math.min(min, soln[i][j + 1]);
            }
        } else {
            return 0;
        }

        // System.err.println(i + "," + j + "," + min);
        return min;
    }
}
