import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/soylent
public class Soylent {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++){
            int val = sc.nextInt();
            val = val + 399;
            System.out.println(val/400);
        }
        
    }
}
