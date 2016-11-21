import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/twostones
public class TakeTwoStones {
    static int[] arr;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        if(n % 2 == 0){
            Bob();
        }
        else{
            Alice();
        }
    }
    private static void Bob() {
        // TODO Auto-generated method stub
        System.out.println("Bob");
        System.exit(0);;
    } 
    private static void Alice() {
        // TODO Auto-generated method stub
        System.out.println("Alice");
        System.exit(0);;
    }
}
