import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/driver
public class CrazyDriver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int n = sc.nextInt();
        long[] tolls = new long[n-1];
        for (int i = 0; i < n-1; i++) {
            tolls[i] = sc.nextInt();
        }
        
        int[] openTime = new int[n];
        for (int i = 0; i < n; i++) {
            openTime[i] = sc.nextInt();
        }
        
        long minSoFar = 1L << 50;
        int distToRepeat = 1 << 25;
        int curTime = 0;
        long cost = 0;
        for (int i = 1; i < n; i++) {
            cost += tolls[i-1];
            if(minSoFar > tolls[i-1]){
                minSoFar = tolls[i-1];
            }
            curTime++;

            
            if(curTime >= openTime[i]){
                continue;
            }
            else{
                int timeDiff = openTime[i]-curTime;
                cost += timeDiff*minSoFar;
                curTime += timeDiff;
                if(i%2 != curTime%2){
                    curTime++;
                    cost += minSoFar;
                }
            }
            
        }
        System.out.println(cost);
    }
}
