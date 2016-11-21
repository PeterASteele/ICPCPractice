import java.util.*;


import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/nesteddolls
public class NestedDolls_LIS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int numCases = sc.nextInt();
        for (int q = 0; q < numCases; q++) {
            int n = sc.nextInt();
            ArrayList<Point> points = new ArrayList<Point>();
            for (int i = 0; i < n; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                points.add(new Point(x, y));
            }
            Collections.sort(points);
            int[] arr = new int[points.size()];
            for(int a = 0; a < points.size(); a++){
                arr[a] = points.get(a).y;
            }
//            arr = new int[3];
            
            System.out.println(lis(arr));
        }
    }
    public static class Point implements Comparable<Point>{
        int x;
        int y;
        public Point(int _x, int _y){
            x = _x;
            y = _y;
        }
        public int compareTo(Point i){
            if(x != i.x){
                return -1 * Integer.compare(x, i.x);
            }
            return Integer.compare(y, i.y);
        }
        
    }
    static int lis(int [] X) {
        int N = X.length;
        int [] P = new int[N]; // DP parent/predecessor pointers
        // contains indices in X, M[0] is unused
        int [] M = new int[N + 1]; 

        int L = 0;                  // length of LIS

        for (int i = 0; i < N; i++) {
          // Binary search for largest positive j <= L
          // such that X[M[j]] < X[i]
          int lo = 1;
          int hi = L;
          while (lo <= hi) {
            int mid = (lo + hi + 1) / 2;
            if (X[M[mid]] <= X[i])
              lo = mid + 1;
            else
              hi = mid - 1;
          }

          P[i] = M[lo - 1];
          M[lo] = i;
          L = Math.max(L, lo);
        }

        // Reconstruct the longest increasing subsequence
        int [] S = new int [L];
        int k = M[L];
        for (int i = L - 1; i >= 0; i--) {
          S[i] = X[k];
          k = P[k];
        }
        return L;
      }
}