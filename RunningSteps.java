import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/runningsteps
public class RunningSteps {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        long[][] grid = new long[100][100];
        for(int a = 0; a < 100; a++){
            grid[0][a] = 1;
            grid[a][0] = 1;
        }
        for(int a = 1; a < 100; a++){
            for(int b = 1; b < 100; b++){
                grid[a][b] = grid[a][b-1]+grid[a-1][b];
            }
        }
//        for(int a = 1; a < 100; a++){
//            for(int b = 1; b < 100; b++){
//                System.out.print(grid[a][b] + " ");
//            }
//            System.out.println();
//        }
        
        for(int a = 0; a < t; a++){
            System.out.print(input.nextInt() + " ");
            int n = input.nextInt()/2;
            
            long sum = 0;
            for(int k = 0; k <= n/2; k++){
                int temp = n-2*k;
                if(k >= temp){
                    sum += grid[k][temp]*grid[k][temp];
                }
            }
            System.out.println(sum);
        }
        
    }
}
