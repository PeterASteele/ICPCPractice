import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/everywhere
public class IveBeenEverywhereMan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        for(int a = 0; a < t; a++){
            int n = input.nextInt();
            HashSet<String> set = new HashSet<String>();
            for(int b = 0; b < n; b++){
                set.add(input.next());
            }
            System.out.println(set.size());
        }
        
    }
}
