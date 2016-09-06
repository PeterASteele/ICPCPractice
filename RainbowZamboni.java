import java.util.*;
import java.math.*;
import java.io.*;

//NAIPC2015 Problem I (Practice)
//https://open.kattis.com/contests/naipc16-p10/problems/zamboni
public class RainbowZamboni {
    static char[][] grid;
    static int[] dx = new int[] {
            0,1,0,-1
    };
    static int[] dy = new int[] {
            -1,0,1,0
    };
    static final int NORTH = 0;
    static final int EAST = 1;
    static final int SOUTH = 2;
    static final int WEST = 3;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numRows = sc.nextInt();
        int numCols = sc.nextInt();
        int rowStart = sc.nextInt() - 1;
        int colStart = sc.nextInt() - 1;
        long numSteps = sc.nextLong();
        grid = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = '.';
            }
        }

        char curChar = 'A';
        if (numSteps < 12100) {
            int curRow = (int) ((rowStart) % numRows);
            int curCol = (int) ((colStart) % numCols);
            int dir = NORTH;
            for (long i = 1; i <= numSteps; i++) {

                long temp = (i);
                if (dir == NORTH || dir == SOUTH) {
                    temp = (i);
                }
                for (long steps = 0; steps < temp; steps++) {

                    grid[curRow][curCol] = curChar;
                    curRow = (curRow + dy[dir] + numRows) % numRows;
                    curCol = (curCol + dx[dir] + numCols) % numCols;
                }
                dir = (dir + 1) % 4;
                if (curChar == 'Z') {
                    curChar = 'A';
                } else {
                    curChar++;
                }
            }
            grid[curRow][curCol] = '@';

        } else {
            long startIterations = ((numSteps - 12000) / 4) * 4 + 2;
            long x = (startIterations / 4) * 2 + 2;
            long y = (startIterations / 4) * 2 + 1;
 
            curChar = (char) (((startIterations)%26)+'A');
            int curRow = (int) ((rowStart - (y%numRows) + numRows) % numRows);
            int curCol = (int) ((colStart + x) % numCols);
            int dir = SOUTH;
            for (long i = startIterations+1; i <= numSteps; i++) {
                long temp = (i % numCols) + numCols;
                if (dir == NORTH || dir == SOUTH) {
                    temp = (i % numRows) + numRows;
                }
                for (long steps = 0; steps < temp; steps++) {

                    grid[curRow][curCol] = curChar;
                    curRow = (curRow + dy[dir] + numRows) % numRows;
                    curCol = (curCol + dx[dir] + numCols) % numCols;
                }
                dir = (dir + 1) % 4;
                if (curChar == 'Z') {
                    curChar = 'A';
                } else {
                    curChar++;
                }
            }
            grid[curRow][curCol] = '@';
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            sb.append(grid[i]).append('\n');
        }
        System.out.print(sb);

    }
}
