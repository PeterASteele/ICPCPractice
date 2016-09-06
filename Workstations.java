import java.util.*;
import java.math.*;
import java.io.*;
//NWERC 2015 Problem A(practice)
//https://open.kattis.com/contests/naipc16-p09/problems/workstations
public class Workstations {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        long n = sc.nextLong();
        long m = sc.nextLong();
        
        PriorityQueue<Long> computers = new PriorityQueue<>();
        PriorityQueue<Long> leaving = new PriorityQueue<>();
        int saved = 0;
        long time = 0;

        Tuple[] event = new Tuple[(int)n];
        for (int i = 0; i < n; i++) {
            event[i] = new Tuple(sc.nextLong(), sc.nextLong());
        }
        
        Arrays.sort(event, new Comparator<Tuple>() {
            @Override
            public int compare(Tuple o1, Tuple o2) {
                return Long.compare(o1.x, o2.x);
            }
        });
        
        for (int i = 0; i < n; i++) {
            time = event[i].x;
            long s = event[i].y;
            
            while (leaving.size() > 0 && leaving.peek() <= time) {
                computers.offer(leaving.poll() + m);
            }

            while (computers.size() > 0 && computers.peek() < time) {
                computers.poll();
            }

            if (computers.size() > 0) {
                computers.poll();
                saved += 1;
            }
            leaving.offer(time + s);
        }

        System.out.println(saved);
    }

    static class Tuple {
        long x, y;
        Tuple(long a, long b){
            x = a; y = b;
        }
    }
    
}