import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/keypad
public class MatrixKeypad {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int t = input.nextInt();
        for(int a2 = 0; a2 < t; a2++){
            int x = input.nextInt();
            int y = input.nextInt();
            int[][] arr = new int[x][y];
            HashSet<Integer> rows = new HashSet<Integer>();
            HashSet<Integer> cols = new HashSet<Integer>();
            int total = 0;
            for(int a = 0; a < x; a++){
                String in = input.next();
                for(int b = 0; b < y; b++){
                    arr[a][b] = in.charAt(b)-'0';
                    if(arr[a][b] == 1){
                        rows.add(a);
                        cols.add(b);
                        total++;
                    }
                }
            }
            if(total != rows.size()*cols.size()){
                System.out.println("impossible");
                System.out.println("----------");
            }
            else{
                char ans = 'N';
                if(Math.min(rows.size(), cols.size()) == 1){
                    ans = 'P';
                }
                else{
                    ans = 'I';
                }
                char[][] output = new char[x][y];
                for(int a = 0; a < x; a++){
                    for(int b = 0; b < y; b++){
                        if(arr[a][b] == 0){
                            output[a][b] = 'N';
                        }
                        else{
                            output[a][b] = ans;
                        }
                    }
                }
                for(int a = 0; a < x; a++){
                    System.out.println(new String(output[a]));
                }
                System.out.println("----------");
            }
        }
    }
}
