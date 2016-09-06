import java.util.*;
import java.math.*;
import java.io.*;
//ICPC World Finals 2008 Problem B (practice)
//http://acm.hust.edu.cn/vjudge/contest/111395#problem/B

public class AlwaysAnInteger {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int count = 0;
        while (true) {
            count++;
            String _poly = sc.nextLine().trim();
            if(_poly.length() < 2){
                break;
            }
            _poly = _poly.replace("-", " -");
            _poly = _poly.replace("+", " ");

            int idx = _poly.indexOf('/');
            String[] terms = _poly.substring(1, idx - 1).split(" ");
            long[] poly = new long[101];
            for (String term : terms) {
                Term t = new Term(term.trim());
                if (t.power != -1) poly[t.power] = t.coefficient;
            }
            int mod = Integer.parseInt(_poly.substring(idx + 1));
            Polynomial p = new Polynomial(poly);
            boolean All0 = true;
            for(int a = 0; a < 10000; a++){
               long ans = evaluateMod(p, (int)(Math.random() * mod), mod);
               if(ans != 0){
                   All0 = false;
                   break;
               }
            }
            if(All0){
                System.out.printf("Case %d: %s\n", count,  "Always an integer");
            }
            else{
                System.out.printf("Case %d: %s\n", count,  "Not always an integer");
            }
        }

    }

    public static long evaluateMod(Polynomial p, long x, long mod) {
        long value = 0;
        int n = p.coefficients.length;
        for (int a = n - 1; a >= 0; a--) {
            value *= x;
            value = value + p.coefficients[a];
            value = value % mod;
        }
        return (value + mod) % mod;
    }

    public static class Polynomial {
        long[] coefficients;

        public Polynomial(long[] coefficients2) {
            coefficients = new long[coefficients2.length];
            for (int a = 0; a < coefficients2.length; a++) {
                coefficients[a] = coefficients2[a];
            }
        }

    }

    static class Term {
        int coefficient;
        int power;

        Term(String s) {
            parse(s);
        }

        int parseNum(String s, int idx) {
            int res = 0;
            while (idx != s.length() && s.charAt(idx) >= '0' && s.charAt(idx) <= '9') {
                res = res * 10 + (s.charAt(idx) - '0');
                idx += 1;
            }
            coefficient = res == 0 ? 1 : res;

            return idx;
        }

        void parse(String term) {
            if (term.isEmpty()) {
                power = -1;
                return;
            }
            boolean neg = false;
            int idx = 0;
            if (term.charAt(idx) == '-') {
                neg = true;
                idx++;
            }

            idx = parseNum(term, idx);
            if (neg) coefficient = -coefficient;
            if (idx == term.length()) {
                power = 0;
            } else if (idx + 1 == term.length()) {
                power = 1;
            } else {
                int tmp = coefficient;
                parseNum(term, idx + 2);
                power = coefficient;
                coefficient = tmp;
            }
        }
    }

}