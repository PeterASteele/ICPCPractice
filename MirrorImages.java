import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/mirror
public class MirrorImages {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int numCases = sc.nextInt();
        for (int t = 0; t < numCases; t++) {
            int numRows = sc.nextInt();
            int numCols = sc.nextInt();
            char[][] grid = new char[numRows][];
            for (int i = 0; i < numRows; i++) {
                grid[i] = sc.next().toCharArray();
            }
            char[][] mirrored = new char[numRows][numCols];
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    mirrored[numRows - row - 1][numCols - col - 1] = grid[row][col];
                }
            }
            System.out.println("Test " + (t + 1));
            for (char[] row : mirrored) {
                System.out.println(row);
            }
        }
    }
}
