import java.util.*;
import java.math.*;
import java.io.*;

//NEERC 2014 Problem K (Practice)
//http://codeforces.com/gym/100553/attachments
public class KnockoutRacing {

    public static void main(String[] args) throws IOException {
        String fileName = "knockout";
        File in = new File(fileName + ".in");
        
        Scanner sc = new Scanner(in);
        Scanner input = new Scanner(in);
        PrintWriter out = new PrintWriter(fileName + ".out");
        
//        Scanner sc = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(System.out);
        
        n = sc.nextInt();
        int m = sc.nextInt();
        a = new long[n];
        b = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
            b[i] = sc.nextLong();
        }
        for (int i = 0; i < m; i++) {
            long left = sc.nextLong();
            long right = sc.nextLong();
            long time = sc.nextLong();
            int count = count(left, right, time);
            out.println(count);
        }
        
        out.close();
    }
    
    static int count(long left, long right, long time) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            long pos = posAtTime(i, time);
            if (pos >= left && pos <= right) count++;
        }
        return count;
    }
    
    static long posAtTime(int idx, long time) {
        long diff = b[idx] - a[idx];
        long distFromEndpt = time % diff;
        boolean forward = (time / (diff)) % 2 == 0;
        long pos;
        if (forward) {
            pos = a[idx] + distFromEndpt;
        } else {
            pos = b[idx] - distFromEndpt;
        }
//        System.out.printf("Car %d is at position %d at time %d.\n", idx, pos, time);
        return pos;
    }
    
    static int n;
    static long[] a;
    static long[] b;
}