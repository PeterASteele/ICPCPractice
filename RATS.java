import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/rats
public class RATS {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        for(int a = 0; a < t; a++){
            System.out.print(input.nextInt() + " ");
//            input.nextInt();
            int M = input.nextInt();
            BigInteger num = new BigInteger(input.next());
//            System.out.println(M + " " + num);
//            sc.nextLine();
            boolean alreadyBroke = false;
            int index = 1;
            HashSet<BigInteger> seq = new HashSet<>();
            while (index < M) {
                if (isCreeper(num)) {
                    System.out.println("C " + index);
                    alreadyBroke = true;
                    break;
                }
                if (seq.contains(num)) {
                    System.out.println("R " + index);
                    alreadyBroke = true;
                    break;
                }
                
                seq.add(num);
                index++;
                num = nextRATS(num);
            }
            if (!alreadyBroke) {
                if (isCreeper(num)) {
                    System.out.println("C " + index);
                } else if (seq.contains(num)) {
                    System.out.println("R " + index);
                } else {
                    System.out.println(num);
                }
            }
            
        }
        
    }

    private static BigInteger nextRATS(BigInteger num) {
        // TODO Auto-generated method stub
//        System.out.println("num: " + num);
        BigInteger reversed = new BigInteger(new StringBuilder(num.toString()).reverse().toString());
        BigInteger other = reversed.add(num);
//        System.out.println(reversed);
//        System.out.println(other);
        char[] str = other.toString().toCharArray();
        Arrays.sort(str);
//        System.out.println(new String(str));
        return new BigInteger(new String(str));
    }

    private static boolean isCreeper(BigInteger num) {
        // TODO Auto-generated method stub
        String str = num.toString();
        try {
            if (str.startsWith("1233") && str.endsWith("4444") && str.charAt(str.length() - 5) == '3') {
                return true;
            } else if (str.startsWith("5566") && str.endsWith("7777") && str.charAt(str.length() - 5) == '6') {
                return true;
            }
        } catch(Exception e) {
            
        }
//        System.err.println(num);
        return false;
                

    }
}
