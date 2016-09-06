import java.util.*;
import java.math.*;
import java.io.*;

//NEERC 2014 Problem F (Practice)
//http://codeforces.com/gym/100553/attachments
public class Filter {

    public static void main(String[] args) throws IOException {
        String fileName = "filter";
        File in = new File(fileName + ".in");

         Scanner sc = new Scanner(in);
         Scanner input = new Scanner(in);
         PrintWriter out = new PrintWriter(fileName + ".out");

////        Scanner sc = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(System.out);
        int mod = input.nextInt();
        int f = input.nextInt();
        long[] ai = new long[f];
        for (int a = 0; a < f; a++) {
            ai[a] = input.nextInt();
        }
        long[][] evaluate = new long[mod][f];
        for (int a = 0; a < mod; a++) {
            for (int b = 0; b < f; b++) {
                evaluate[a][b] = (a * ai[b]) % mod;
            }
        }
        int lines = input.nextInt();
        boolean[][] stuff = new boolean[lines][mod];
        for (int a = 0; a < lines; a++) {
            String inp = input.next();
            int index = 0;
            for (int b = 0; b < inp.length(); b++) {
                int charVar = Integer.parseInt(inp.charAt(b) + "", 16);
                if (index + 4 <= mod) {
                    for (int w = 0; w < 4; w++) {
                        stuff[a][index] = charVar % 2 == 1 ? true : false;
                        charVar /= 2;
                        index++;
                    }

                } else {
                    for (int w2 = 0; w2 < mod%4; w2++) {
                        stuff[a][index] = charVar % 2 == 1 ? true : false;
                        charVar = charVar/2;
                        index++;
                    }
                }
            }
        }
        int q = input.nextInt();
        HashSet<Integer> inFunctions = new HashSet<Integer>();
        int[] people = new int[q];
        for (int a = 0; a < q; a++) {
            people[a] = input.nextInt()%mod;
            long[] listOfBitsMust1 = evaluate[people[a]];
            boolean[] bitsNeeded = new boolean[mod];
            for (int b = 0; b < listOfBitsMust1.length; b++) {
                bitsNeeded[(int) listOfBitsMust1[b]] = true;
            }
            for (int b = 0; b < lines; b++) {
                if (!inFunctions.contains(b)) {
                    if (ifValid(bitsNeeded, stuff[b])) {
                        inFunctions.add(b);
                    }
                }
            }
        }
        ArrayList<Integer> out23 = new ArrayList<Integer>();
        for(Integer a:inFunctions){
            out23.add(a);
        }
        Collections.sort(out23);
        out.print(inFunctions.size() + " ");
        for(Integer a:out23){
            out.print(a+" ");
        }
        out.flush();
    }

    private static boolean ifValid(boolean[] bitsNeeded, boolean[] bs) {
        for (int a = 0; a < bitsNeeded.length; a++) {
            if (bitsNeeded[a] == true && bs[a] == false) {
                return false;
            }
        }
        return true;
    }
}