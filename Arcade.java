import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Arcade {
    static double answer;
    static double[] payout;
    static double unWeightedANS;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        answer = 0.0;
        unWeightedANS = 1.0;
        payout = new double[(n + 2) * (n + 2)];
        double[][] matrix = new double[(n + 2) * (n + 2)][(n + 2) * (n + 2)];

        for (int a = 1; a <= n; a++) {
            for (int b = 1; b <= a; b++) {
                int loc = a * (n + 2) + b;
                payout[loc] = input.nextInt();
            }
        }
        ArrayList<Sparse>[] sparseMatrix = new ArrayList[(n + 2) * (n + 2)];
        for (int a = 0; a < (n + 2) * (n + 2); a++) {
            sparseMatrix[a] = new ArrayList<Sparse>();
        }
        for (int a = 1; a <= n; a++) {
            for (int b = 1; b <= a; b++) {
                int loc = a * (n + 2) + b;
                sparseMatrix[loc].add(new Sparse(loc - (n + 2) - 1, input.nextDouble()));
                sparseMatrix[loc].add(new Sparse(loc - (n + 2), input.nextDouble()));
                sparseMatrix[loc].add(new Sparse(loc + (n + 2), input.nextDouble()));
                sparseMatrix[loc].add(new Sparse(loc + (n + 2) + 1, input.nextDouble()));
                sparseMatrix[loc].add(new Sparse(loc + (n + 2) * (n + 2), input.nextDouble()));
            }
        }
        double[] start = new double[(n + 2) * (n + 2)];
        start[1 + n + 2] = 1.0;
        while(true){
            if(unWeightedANS < 1E-6){
                break;
            }
            start = eval(sparseMatrix, start);
        }
        System.out.println(answer);
    }

    public static class Sparse {
        int end;
        double val;

        public Sparse(int _end, double _val) {
            end = _end;
            val = _val;
        }
    }

    public static double dot(double[] one, double[] two) {
        double ans = 0;
        for (int a = 0; a < one.length; a++) {
            ans += one[a] * two[a];
        }
        return ans;
    }

    public static double[] eval(ArrayList<Sparse>[] sparseMatrix, double[] vect) {
        double[] out = new double[vect.length];
        for (int b = 0; b < vect.length; b++) {
            for (Sparse i: sparseMatrix[b]) {
                if(i.end >= vect.length){
                    answer += payout[i.end-vect.length]*vect[b]*i.val;
                    unWeightedANS -= vect[b]*i.val;
                }
                else{
                    out[i.end] += i.val * vect[b];
                }
            }
        }
        return out;
    }
    // public static double[][] matrixExponentiate(double[][] in) {
    // int n = in.length;
    // double[][] output = new double[n][n];
    // for (int a = 0; a < n; a++) {
    // for (int b = 0; b < n; b++) {
    // for (int c = 0; c < n; c++) {
    // output[a][b] += in[c][a] + in[b][c];
    // }
    // }
    // }
    // return output;
    // }
}
