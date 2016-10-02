import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/contests/na16warmup1/problems/hidden
public class HiddenPassword {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        char[] passwordArr = sc.next().toCharArray();
        char[] phrase = sc.next().toCharArray();
        Queue<Character> password = new ArrayDeque<>();
        for (char c : passwordArr) {
            password.add(c);
        }
        
        int index = 0;
        for (int i = 0; i < phrase.length; i++) {
            if (password.isEmpty()) {
                break;
            }
            
            if (phrase[i] == password.peek()) {
                password.remove();
            } else if (password.contains(phrase[i])) {
                System.out.println("FAIL");
                System.exit(0);
            }
        }
        
        if (password.isEmpty()) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
        
        
    }
}
