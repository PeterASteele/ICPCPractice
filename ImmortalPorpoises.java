import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/porpoises
public class ImmortalPorpoises {
    static long[][] matrix = {{1, 1},{1, 0}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        for(int a2 = 0; a2 < t; a2++){
            System.out.print(input.nextInt() + " ");
            long in = input.nextLong();
            long[][] mat = exponentiate(matrix, in);
            System.out.println(mat[0][1]);
        }
    }
    
    public static long[][] exponentiate(long[][] matrix, long power){
        if(power == 0){
            long[][] arr = {{1, 0}, {0, 1}};
            return arr;
        }
        if(power == 1){
            return matrix;
        }
        long[][] temp = exponentiate(matrix, power/2);
        return matrixMult(matrixMult(temp, temp), exponentiate(matrix, power%2));
        
    }
    static long MOD = 1000000000;
    public static long[][] matrixMult(long[][] a, long[][] b){
        long[][] out = new long[a.length][b.length];
        for(int a2 = 0; a2 < 2; a2++){
            for(int b2= 0; b2 < 2; b2++){
                for(int c2 = 0; c2 < 2; c2++){
                    out[a2][b2] += a[a2][c2]*b[c2][b2];
                }
            }
        }
        for(int a2 = 0; a2 < 2; a2++){
            for(int b2 = 0; b2 < 2; b2++){
                out[a2][b2] %= MOD;
            }
        }
        
        return out;
        
        
    }
    
}
