import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/daceydice
public class DaceyTheDice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int numCases = sc.nextInt();
        outer:
        for (int t = 0; t < numCases; t++) {
            n = sc.nextInt();
            grid = new char[n][];
            int startRow = -1;
            int startCol = -1;
            for (int i = 0; i < n; i++) {
                grid[i] = sc.next().toCharArray();
                for (int col = 0; col < n; col++) {
                    if (grid[i][col] == 'S') {
                        startRow = i;
                        startCol = col;
                    }
                }
            }
            
            State start = new State(startRow, startCol, 6, 4, 5);
            Set<State> visited = new HashSet<>();
            visited.add(start);
            Deque<State> dfs = new ArrayDeque<>();
            dfs.push(start);
            while (!dfs.isEmpty()) {
                State cur = dfs.pop();
                if (cur.isGoal()) {
                    System.out.println("Yes");
                    continue outer;
                }
                
                for (State neighbor : cur.adj()) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        dfs.push(neighbor);
                    }
                }
            }
            System.out.println("No");
        }
    }
    
    
    static final int[][] neighbors = {
            {},
            {},
            {},
            {},
            {},
            {}
    };
    
    static int n;
    static char[][] grid;
    
    static boolean valid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n
                && grid[row][col] != '*';
    }
    
    static class State {
        final int row, col;
        final int down, forward;
        final int left;
        
        State(int r, int c, int d, int ro, int l) {
            row = r;
            col = c;
            down = d;
            forward = ro;
            left = l;
        }
        
        @Override
        public int hashCode() {
            int hash = row;
            hash = 29 * hash + col;
            hash = 29 * hash + down;
            hash = 29 * hash + forward;
            hash = 29 * hash + left;
            return hash;
        }
        
        @Override
        public boolean equals(Object o) {
            State s = (State) o;
            return row == s.row && col == s.col && down == s.down && forward == s.forward
                    && left == s.left;
        }
        
        public boolean isGoal() {
            return grid[row][col] == 'H' && down == 5;
        }
        
        List<State> adj() {
            List<State> adj = new ArrayList<>();
            
            // North
            if (true) {
                int nextRow = row - 1;
                int nextCol = col;
                
                int x = down;
                int y = forward;
                int z = left;
                
                if (valid(nextRow, nextCol)) {
                    State state = new State(nextRow, nextCol, y, 7 - x, z);
                    adj.add(state);
                }
            }
            
            // East
            if (true) {
                int nextRow = row;
                int nextCol = col + 1;
                
                int x = down;
                int y = forward;
                int z = left;
                
                if (valid(nextRow, nextCol)) {
                    State state = new State(nextRow, nextCol, 7 - z, y, x);
                    adj.add(state);
                }
            }
            
            // South
            if (true) {
                int nextRow = row + 1;
                int nextCol = col;
                
                int x = down;
                int y = forward;
                int z = left;
                
                if (valid(nextRow, nextCol)) {
                    State state = new State(nextRow, nextCol, 7 - y, x, z);
                    adj.add(state);
                }
            }
            
            // West
            if (true) {
                int nextRow = row;
                int nextCol = col - 1;
                
                int x = down;
                int y = forward;
                int z = left;
                
                if (valid(nextRow, nextCol)) {
                    State state = new State(nextRow, nextCol, z, y, 7 - x);
                    adj.add(state);
                }
            }
            
            return adj;
        }
    }
    
    static final int[] dRow = {-1, 0, 1, 0};
    static final int[] dCol = {0, 1, 0, -1};
}
