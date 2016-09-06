import java.util.*;
import java.math.*;
import java.io.*;
//BAPC 2015 Problem K(Practice)
//https://open.kattis.com/contests/naipc16-p01/problems/wipeyourwhiteboards
public class WipeYourWhiteboards {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        for (int a = 0; a < n; a++) {
            long R = input.nextInt();
            long S = input.nextInt();
            long Q = input.nextInt();
            ArrayList<Long> train = new ArrayList<Long>();
            train = gcdTrain(R, S * -1);
//            System.out.println(train);
            long w = train.get(train.size()-1);
            ArrayList<Long> wDio = diophantine(w, train);
            long gcd = train.get(train.size()-1);
            long lcm = (R/gcd) * S * -1;
            long multiple = Q/gcd;
            long a1 = wDio.get(0)*multiple;
//            System.out.println(a1 + " " + wDio.get(0) + " " + multiple);
            long a1Val = train.get(0);
            long b1 = wDio.get(1)*multiple;
            long b1Val = train.get(1);
//            System.out.println(Q + " = " + a1 + "*" + a1Val + " + " + b1 + "*" + b1Val);
            if(S*-1 == a1Val){
                a1Val *= -1;
                a1 *= -1;
                long tempVal = a1Val;
                long temp = a1;
                a1Val = b1Val;
                a1 = b1;
                b1Val = tempVal;
                b1 = temp;
            }
            else{
                b1Val *= -1;
                b1 *= -1;
            }
//            System.out.println(Q + " = " + a1 + "*" + a1Val + " + " + b1 + "*" + b1Val);
//            if(a1 < 1){
                long temp = a1*-1 + 1;
                long lcmMult = temp/(lcm/a1Val);
                if(temp%(lcm/a1Val) > 0){
                    lcmMult++;
                }
                a1 += lcmMult*(lcm/a1Val);
                b1 -= lcmMult*(lcm/b1Val);
//            }
//                System.out.println(Q + " = " + a1 + "*" + a1Val + " + " + b1 + "*" + b1Val);
            if(b1 <= 0){
                long temp2 = b1*-1 + 1;
                long lcmMult2 = temp2/(lcm/b1Val*-1);
                if(temp2%(lcm/b1Val*-1) > 0){
                    lcmMult2++;
                }
                a1 += lcmMult2*(lcm/a1Val);
                b1 -= lcmMult2*(lcm/b1Val);
            }
//            System.out.println(Q + " = (" + a1 + "*" + a1Val + ") + (" + b1 + "*" + b1Val + ")");
            System.out.println(a1 + " " + b1);
        }
       
    }

    private static ArrayList<Long> diophantine(long w, ArrayList<Long> train) {
        long s = w/train.get(train.size()-1);
        long sVal = train.get(train.size()-1);
        long t = 0;
        long tVal = train.get(train.size()-2);
        for(int k = train.size()-3; k >= 0; k--){
//            System.out.println(w + " = " + s + " * " + sVal + " + " + t + " * " + tVal);
            long tempVal = train.get(k);
            long temp = s;
            t -= temp * (train.get(k)/train.get(k+1));
            s = t;
            sVal = tVal;
            t = temp;
            tVal = tempVal;
        }
//        System.out.println(w + " = " + s + " * " + sVal + " + " + t + " * " + tVal);
        ArrayList<Long> out = new ArrayList<Long>();
        out.add(t);
        out.add(s);
        return out;
    }

    public static ArrayList<Long> gcdTrain(long r, long q) {
        ArrayList<Long> output = new ArrayList<Long>();
        long s1 = Math.max(r, q);
        long s2 = Math.min(r, q);
        output.add(s1);
        output.add(s2);
        while (s2 != 0) {
            // long quotient = s1/s2;
            long remainder = s1 % s2;
            s1 = s2;
            s2 = remainder;
            output.add(remainder);
        }
        output.remove(output.size()-1);
        return output;
    }

    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(Reader in) {
            br = new BufferedReader(in);
        }

        public FastScanner() {
            this(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        // Slightly different from java.util.Scanner.nextLine(),
        // which returns any remaining characters in current line,
        // if any.
        String readNextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
