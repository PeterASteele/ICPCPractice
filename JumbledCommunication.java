import java.util.*;
import java.math.*;
import java.io.*;
//NWERC2015 Problem J (Practice)
//https://open.kattis.com/contests/naipc16-p09/problems/communication
public class JumbledCommunication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int n = sc.nextInt();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            int res = i ^ ((i << 1) & 255);
            map.put(res, i);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            int x = sc.nextInt();
            sb.append(map.get(x));
        }
        System.out.println(sb);
    }
}