import java.util.*;
import java.math.*;
import java.io.*;
//NEERC 2014 Problem B (Practice)
//http://codeforces.com/gym/100553/attachments
public class BurritoKing {

    public static void main(String[] args) throws IOException {
        String fileName = "burrito";
        File in = new File(fileName + ".in");

         Scanner sc = new Scanner(in);
         Scanner input = new Scanner(in);
         PrintWriter out = new PrintWriter(fileName + ".out");

//        Scanner sc = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(System.out);
        int n = input.nextInt();
        long a = input.nextInt();
        long b = input.nextInt();
        ArrayList<Burrito> burrito = new ArrayList<Burrito>();
        for (int q = 0; q < n; q++) {
            int gi = input.nextInt();
            int ai = input.nextInt();
            int bi = input.nextInt();
            Burrito k = new Burrito(ai, bi, gi, q);
            burrito.add(k);
        }
        Collections.sort(burrito);
        double joy = 0;
        double unhappy = 0;
        String[] output2 =  new String[n];
        for (Burrito q : burrito) {
            if (unhappy + q.gi * q.bi <= b) {
                joy += q.gi * q.ai;
                unhappy += q.gi * q.bi;
                output2[q.id] = q.gi + "";
            } else {
                double left = b - unhappy;
                joy += left/q.bi * q.ai;
                unhappy = b;
                output2[q.id] = (left/q.bi) + "";
            }
            
        }
        if(joy >= a){
            StringBuilder output = new StringBuilder();
            output.append(joy + " " + unhappy + "\n");
            for(int q = 0; q < n-1; q++){
                output.append(output2[q]+" ");
            }
            output.append(output2[n-1]);
            out.println(output);
        }
        else{
            out.println(-1 + " "  + -1);
        }
        out.flush();
    }

    public static class Burrito implements Comparable<Burrito> {
        double ai;
        double bi;
        double gi;
        double ratio;
        int id;
        public Burrito(int _ai, int _bi, int _gi, int _id) {
            ai = _ai;
            bi = _bi;
            if (bi == 0) {
                ratio = Double.MAX_VALUE / 2;
            }
            ratio = ai / bi;
            gi = _gi;
            id = _id;
        }

        @Override
        public int compareTo(Burrito o) {
            return -1 * Double.compare(ratio, o.ratio);
        }
        public String toString(){
            return ai + " " + bi + " " + gi + " " + ratio + " " + id;
        }

    }
}