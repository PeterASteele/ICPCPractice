import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/city
public class CityDestruction {
    static int N;
    static long D;
    static long[] health;
    static long[] explode;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();
        for (int t2 = 0; t2 < t; t2++) {
            N = input.nextInt();
            D = input.nextInt();
            
            cache = new long [2][N+1];
            for(int a = 0; a < 2; a++){
                for(int b = 0; b < N+1; b++){
                    cache[a][b] = -1;
                }
            }
            
            health = new long[N + 1];
            for (int q = 0; q < N; q++) {
                health[q] = input.nextInt();
            }

            explode = new long[N + 1];
            for (int q = 0; q < N; q++) {
                explode[q] = input.nextInt();
            }

            System.out.println(solve(0, true, 0));

        }
    }
    
    static long[][] cache;
    
    private static long solve(int i, boolean b, long j) {
        if(i == N){
            return 0;
        }
        if(cache[b?1:0] [i]!= -1){
            return cache[b?1:0][i];
        }
        long val1 = Math.max(0, (health[i] - j + D - 1) / D);
        long val2 = Math.max(0, (health[i] - explode[i + 1] - j + D - 1) / D);
        
        long answer = Math.min(val1+solve(i+1, true, explode[i]), val2+solve(i+1, false, 0));
        cache[b?1:0][i] = answer;
        return answer;
    }
}
