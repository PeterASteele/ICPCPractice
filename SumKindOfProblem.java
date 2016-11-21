import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/sumkindofproblem
public class SumKindOfProblem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        for(int a = 0; a < t; a++){
            System.out.print(input.nextInt() + " ");
            long x = input.nextLong();
            System.out.print((x*(x+1)/2) + " ");
            System.out.print(x*x + " ");
            System.out.print(x*(x+1));
            System.out.println();
            
            
            
        }
        
    }
}
