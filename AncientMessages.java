import java.util.*;
import java.math.*;
import java.io.*;
//ICPC World Finals 2011 Problem C (practice)
//http://acm.hust.edu.cn/vjudge/contest/114197#problem/C
public class AncientMessages {
    
    static int id = 2;
    static int height;
    static int width;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int caseNum = 1;
        while (sc.hasNext()) {
            id = 2;
            height = sc.nextInt() + 2;
            int numHex = sc.nextInt();
            if (height-2 == 0 && numHex == 0) {
                break;
            }
            width = numHex * 4 + 2;
            
            int[][] grid = new int[height][width];
            for (int row = 1; row < height-1; row++) {
                String line = sc.next();
                int col = 1;
                for (int i = 0; i < numHex; i++) {
                    int num = 0;
                    char chr = line.charAt(i);
                    if (Character.isDigit(chr)) {
                        num = chr - '0';
                    } else {
                        num = chr - 'a' + 10;
                    }
                    for (int j = 3; j >= 0; j--) {
                        if ((num & (1 << j)) != 0) {
                            grid[row][col] = 1;
                        } else {
                            grid[row][col] = 0;
                        }
                        col++;
                    }
                }
            }
            
//            printGrid(grid);
            
            List<Boolean> idIsBlack = new ArrayList<>();
            idIsBlack.add(false);
            idIsBlack.add(true);
            
            // exterior whitespace should have id = 2
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (grid[row][col] == 0) {
                        floodfill(row, col, 0, grid);
                        idIsBlack.add(false);
                        id++;
                    }
                    if (grid[row][col] == 1) {
                        floodfill(row, col, 1, grid);
                        idIsBlack.add(true);
                        id++;
                    }
                }
            }
            
//            printGrid(grid);
//            System.out.println(idIsBlack);
            
            Set<Integer>[] adj = new Set[id];
            for (int i = 0; i < adj.length; i++) {
                adj[i] = new HashSet<>();
            }
            
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    int curId = grid[row][col];
                    for (int k = 0; k < 4; k++) {
                        int drow = y[k];
                        int dcol = x[k];
                        int nextRow = row + drow;
                        int nextCol = col + dcol;
                        if (valid(nextRow, nextCol)) {
                            int nextId = grid[nextRow][nextCol];
                            if (nextId != curId) {
                                adj[curId].add(nextId);
                                adj[nextId].add(curId);
                            }
                        }
                    }
                }
            }
            
//            for (int i = 0; i < adj.length; i++) {
//                System.out.printf("Component %d: %s\n", i, adj[i]);
//            }
            
            char[] letters = "WAKJSD".toCharArray();
            List<Character> answer = new ArrayList<>();
            for (int i = 2; i < adj.length; i++) {
                if (idIsBlack.get(i)) { // The shape
                    int degree = adj[i].size() - 1;
                    answer.add(letters[degree]);
                }
            }
            Collections.sort(answer);
            StringBuilder sb = new StringBuilder();
            for (char c : answer) {
                sb.append(c);
            }
            System.out.printf("Case %d: %s\n", caseNum, sb.toString());
            caseNum++;
        }
    }
    
    static void printGrid(int[][] grid) {
        for (int row = 0; row < height; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < width; col++) {
                sb.append(grid[row][col]);
            }
            System.out.println(sb);
        }
        System.out.println();
    }
    
    
    
    static int[] x = {1, -1, 0, 0,};
    static int[] y = {0, 0, 1, -1};
    
    static boolean valid(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
    
    static void floodfill(int row, int col, int c, int[][] board) {
        board[row][col] = id;
        for (int k = 0; k < 4; k++) {
            int drow = y[k];
            int dcol = x[k];
            int nextRow = row + drow;
            int nextCol = col + dcol;
            if (valid(nextRow, nextCol)) {
                if (board[nextRow][nextCol] == c) {
                    floodfill(nextRow, nextCol, c, board);
                }
            }
        }
    }
}