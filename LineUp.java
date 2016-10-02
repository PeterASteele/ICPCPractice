import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/contests/na16warmup1/problems/lineup
public class LineUp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int n = sc.nextInt();
        
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(sc.next());
        }
        
        List<String> copy1 = new ArrayList<>(list);
        List<String> copy2 = new ArrayList<>(list);
        
        Collections.sort(copy1);
        Collections.sort(copy2, Collections.reverseOrder());
        
        if (list.equals(copy1)) {
            System.out.println("INCREASING");
        } else if (list.equals(copy2)) {
            System.out.println("DECREASING");
        } else {
            System.out.println("NEITHER");
        }
        
        
    }
}
