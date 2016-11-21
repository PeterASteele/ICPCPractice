import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/rationalsequence2
public class ARationalSequence2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        for(int a = 0; a < t; a++){
            System.out.print(input.nextInt() + " ");
            String[] frac = input.next().split("/");
            long finalNum = Long.parseLong(frac[0]);
            long finalDenom = Long.parseLong(frac[1]);
            
            List<Integer> turns = new ArrayList<>();
            long num = finalNum;
            long denom = finalDenom;
            long level = 0;
            while (num != 1 || denom != 1) {
                if (num > denom) {
                    num = num - denom;
                    turns.add(1);
                } else {
                    denom = denom - num;
                    turns.add(0);
                }
                
                level++;
            }
            Collections.reverse(turns);
            StringBuilder out = new StringBuilder();
            if(turns.size() == 0){
                out.append(0);
            }
            for(Integer i:turns){
                out.append(i);
            }
            BigInteger peter = new BigInteger(out.toString(), 2);
            System.out.println(peter.add(BigInteger.valueOf((1l<<level))));
            
            
 
            
            
            
        }
        
    }
}
