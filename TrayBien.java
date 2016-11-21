import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/tray
public class TrayBien {
    static HashMap<State, Long> DYNAMIC;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        DYNAMIC = new HashMap<State, Long>();
        int m = input.nextInt();
        int n = input.nextInt();
        boolean[][] arr = new boolean[4][m + 1];
        for (int a = 0; a < n; a++) {
            int Y = (int) Math.round(input.nextDouble() * 100) / 100;
            int X = (int) Math.round(input.nextDouble() * 100) / 100;
            arr[X][Y] = true;
        }
        for (int a = 0; a < m; a++) {
            arr[3][a] = true;
        }
        for (int b = 0; b < 4; b++) {
            arr[b][m] = true;
        }
        System.out.println(solve(new State(arr)));

    }

    public static class State {
        boolean[][] arr;

        public State(boolean[][] _arr) {
            arr = _arr;
        }

        @Override
        public boolean equals(Object i2) {
            State i = (State) i2;
            return Arrays.deepEquals(arr, i.arr);
        }

        @Override
        public int hashCode() {
            return (Arrays.deepHashCode(arr));
        }
    }

    public static long solve(State arr) {
        if (DYNAMIC.containsKey(arr)) {
            return DYNAMIC.get(arr);
        }
        int m = arr.arr[0].length - 1;

        for (int b = 0; b < arr.arr[0].length - 1; b++) {
            for (int a = 0; a < arr.arr.length - 1; a++) {
                if (!arr.arr[a][b]) {
                    long answer = 0;
                    if (!arr.arr[a + 1][b]) {
                        boolean[][] stuff = new boolean[4][m + 1];
                        for (int q = 0; q < 4; q++) {
                            for (int q2 = 0; q2 < m + 1; q2++) {
                                stuff[q][q2] = arr.arr[q][q2];
                            }
                        }
                        stuff[a + 1][b] = true;
                        stuff[a][b] = true;
                        State temp = new State(stuff);
                        answer += solve(temp);
                    }
                    if (!arr.arr[a][b + 1]) {
                        boolean[][] stuff = new boolean[4][m + 1];
                        for (int q = 0; q < 4; q++) {
                            for (int q2 = 0; q2 < m + 1; q2++) {
                                stuff[q][q2] = arr.arr[q][q2];
                            }
                        }
                        stuff[a][b + 1] = true;
                        stuff[a][b] = true;
                        State temp = new State(stuff);
                        answer += solve(temp);
                    }
                    if (true) {
                        boolean[][] stuff = new boolean[4][m + 1];
                        for (int q = 0; q < 4; q++) {
                            for (int q2 = 0; q2 < m + 1; q2++) {
                                stuff[q][q2] = arr.arr[q][q2];
                            }
                        }
                        stuff[a][b] = true;
                        State temp = new State(stuff);
                        answer += solve(temp);
                    }
                    DYNAMIC.put(arr, answer);
                    return answer;
                }
            }
        }
        return 1;
    }

}
