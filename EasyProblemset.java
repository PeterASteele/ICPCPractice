import java.util.*;
import java.math.*;
import java.io.*;
////NEERC 2015 Problem E (Practice)
//http://codeforces.com/gym/100851/attachments
public class EasyProblemset {

    public static void main(String[] args) throws IOException {
        String fileName = "easy";
//        FastScanner input = new FastScanner();
        Scanner sc = new Scanner(new File(fileName + ".in"));
//        Scanner sc = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
//        FastScanner sc = new FastScanner();
        PrintWriter writer = new PrintWriter(fileName + ".out");
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] ptr = new int[n];
        List<Integer>[] judges = new List[n];
        for (int i = 0; i < n; i++) {
            judges[i] = new ArrayList<Integer>();
            int p = sc.nextInt();
            for (int j = 0; j < p; j++) {
                judges[i].add(sc.nextInt());
            }
        }
        
        int hardness = 0;
        int judge = 0;
        int done = 0;
        while (k > 0) {
            if (done == n) {
                hardness += 50;
                k--;
                continue;
            }
            
//            System.out.println(done);
            
            if (ptr[judge] == judges[judge].size()) {
                done++;
                if (50 >= hardness) {
//                    System.out.println("judge " + judge + " selected " + 50);
                    hardness += 50;
                    k--;
                }
//                System.out.println("judge " + judge + " did not select " + 50);
            } else {
                if (judges[judge].get(ptr[judge]) >= hardness) {
//                    System.out.println("judge " + judge + " selected " + judges[judge].get(ptr[judge]));
                    hardness += judges[judge].get(ptr[judge]);
                    k--;
                } 
//                    System.out.println("judge " + judge + " did not select " + judges[judge].get(ptr[judge]));
                ptr[judge]++;
            }
            judge = (judge+1)%n;
        }
        
        writer.println(hardness);
        
        
        writer.println();
        writer.close();
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