import java.util.*;
import java.math.*;
import java.io.*;
//NEERC 2014 Problem J (Practice)
//http://codeforces.com/gym/100553/attachments
public class Jokewithpermutation {

    static int max;
    static char[] perm;
    static boolean[] visited;
    
    public static void main(String[] args) throws IOException {
        String fileName = "joke";
        File in = new File(fileName + ".in");
        
        Scanner sc = new Scanner(in);
        Scanner input = new Scanner(in);
        PrintWriter out = new PrintWriter(fileName + ".out");
//        
//        Scanner sc = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(System.out);
        
        String word = sc.next();
        perm = word.toCharArray();
        max = perm.length > 9 ? 9+((perm.length-9)/2) : perm.length;
        visited = new boolean[max+1];
        ArrayDeque<Integer> res = new ArrayDeque<>();
        
        solve(0, res);
        for (int i : res) {
            out.print(i + " ");
        }
        out.close();
    }
    
    static boolean solve(int i, ArrayDeque<Integer> path) {
        if (i == perm.length) return true;
        int res = 0;
        while (i < perm.length && res <= max) {
            if (res == 0 && perm[i] == '0') break;
            res = res*10 + (perm[i++] - '0');
            if (res > max) break;
            if (!visited[res]) {
                visited[res] = true;
                path.addLast(res);
                if (solve(i, path)) return true;
                visited[res] = false;
                path.removeLast();
            }
        }
        return false;
    }
}