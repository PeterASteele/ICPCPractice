import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/vote
public class PopularVote {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int n = sc.nextInt();
            
            int[] votes = new int[n];
            int sum = 0;
            for (int j = 0; j < n; j++) {
                votes[j] = sc.nextInt();
                sum += votes[j];
            }
            
            int max = Integer.MIN_VALUE;
            int index = 0;
            boolean tie = false;
            for (int j = 0; j < n; j++) {
                if (votes[j] > max) {
                    tie = false;
                    index = j;
                    max = votes[j];
                } else if (votes[j] == max) {
                    tie = true;
                }
            }
            
            if (tie) {
                System.out.println("no winner");
            } else {
                if (votes[index] > sum / 2) {
                    System.out.println("majority winner " + (index + 1));
                } else {
                    System.out.println("minority winner " + (index + 1));
                }
            }
            
            
        }
    }
}
