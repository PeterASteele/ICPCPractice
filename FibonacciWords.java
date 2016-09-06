import java.util.*;
import java.math.*;
import java.io.*;
//ICPC World Finals 2012 Problem D (practice)
//https://icpc.kattis.com/problems/fibonacci
/**
 * Accepted using non-bugged KMP prefix table
 */
public class FibonacciWords {
    static KMPIsai kmp;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        kmp = new KMPIsai();
        int n = 100;
        ArrayList<StringFibo> fiboStrings = new ArrayList<StringFibo>();
        fiboStrings.add(new StringFibo("0"));
        fiboStrings.add(new StringFibo("1"));
        for (int a = 2; a <= n; a++) {
            fiboStrings.add(new StringFibo(fiboStrings.get(a - 1), fiboStrings.get(a - 2)));
        }
        int caseNum = 1;
        while (input.hasNextInt()) {
            cache = new BigInteger[n+1];
            int ncase = input.nextInt();
            String goal = input.next();
            BigInteger x = solve(ncase, goal, fiboStrings);
            System.out.printf("Case %d: %s\n", caseNum, x.toString());
            caseNum++;
        }
    }

    static int maxLength = 100000;
    static int mlm1 = maxLength-1;
    static BigInteger[] cache;
    private static BigInteger solve(int ncase, String goal, ArrayList<StringFibo> fiboStrings) {
        if (cache[ncase] != null) return cache[ncase];
        
        String mid = fiboStrings.get(ncase).mid;
        if (mid.equals(fiboStrings.get(ncase).in)) {
            String midConcat = mid;
            long num = kmp.numMatches(midConcat, goal);
            cache[ncase] = BigInteger.valueOf(num);
            return cache[ncase];
        } else {
            int length = goal.length() - 1;
            String midConcat = mid.substring(mlm1 - length, mlm1) + mid.substring(mlm1, mlm1 + length);
            long num = kmp.numMatches(midConcat, goal);
            BigInteger ret = BigInteger.valueOf(num).add(solve(ncase - 1, goal, fiboStrings)).add(solve(ncase - 2, goal, fiboStrings));
            cache[ncase] = ret;
            return ret;
        }
    }

    public static class StringFibo {
        String prefix;
        String suffix;
        String mid;
        String in;
        BigInteger length;

        public StringFibo(String in) {
            this.in = in;
            suffix = in;
            prefix = in;
            mid = in;
            length = BigInteger.valueOf(in.length());
        }

        public StringFibo(StringFibo a, StringFibo b) {
            if (a.length.compareTo(BigInteger.valueOf(15*mlm1)) < 0) {
                this.prefix = a.in;
                this.suffix = b.in;
                in = a.in + b.in;
                mid = a.in + b.in;
            } else {
                this.prefix = a.prefix;
                this.suffix = b.suffix;
                this.mid = a.suffix.substring(a.suffix.length() - mlm1, a.suffix.length())
                        + b.prefix.substring(0, mlm1);
                this.in = "33333";
            }

            this.length = a.length.add(b.length);
        }

        public String toString() {
            return mid;
        }

    }

    public static class KMPIsai {
        static long numMatches(String haystack, String needle) {
            if (haystack.length() < needle.length()) {
                return 0;
            }

            long count = 0;
            int[] S2 = prefixTable(needle.toCharArray());
            int[] S = prefixTable2(needle.toCharArray());
//            System.out.println(Arrays.toString(S));
//            System.out.println(Arrays.toString(S2));
            int t = 0, p = 0;

            while (p + t < haystack.length()) {
                if (haystack.charAt(t + p) != needle.charAt(p)) {
                    t = t + p - S[p];
                    p = -1 < S[p] ? S[p] : 0;
                } else if (++p == needle.length()) {
                    count++;
//                    System.out.println(haystack + " " + needle + " " + t + " " + p + " " + count);
                    t++;
                    p = 0;
                }
            }
            return count;
        }

//        static List<Integer> KMP_all_matches(String haystack, String needle) {
//            List<Integer> resultSet = new ArrayList<Integer>();
//            if (haystack.length() < needle.length()) {
//                return resultSet;
//            }
//
//            int[] S = prefixTable(needle.toCharArray());
//            int t = 0, p = 0;
//
//            while (p + t < haystack.length()) {
//                if (haystack.charAt(t + p) != needle.charAt(p)) {
//                    t = t + p - S[p];
//                    p = -1 < S[p] ? S[p] : 0;
//                } else if (p++ == needle.length() - 1) {
//                    resultSet.add(t);
//                    t++;
//                    p = 0;
//                }
//            }
//            return resultSet;
//        }

        private static int[] prefixTable(char[] P) {
            int[] T = new int[P.length];
            int pos = 2, m = 0;
            T[0] = -1;
            while (pos < P.length) {
                if (P[pos - 1] == P[m]) {
                    T[pos++] = ++m;
                } else if (0 < T[m]) {
                    m = T[m];
                } else {
                    pos++; // same as T[pos++] = 0;
                }
            }
            return T;
        }
        public static int[] prefixTable2(char[] P) {
            int[] T = new int[P.length+1];
            T[0] = -1;
            int k = 0;
            for (int i = 1; i < P.length; i++) {
              while (k > 0 && P[k] != P[i])
                k = T[k];
              if (P[k] == P[i])
                ++k;
              T[i+1] = k;
            }
            return T;
          }

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