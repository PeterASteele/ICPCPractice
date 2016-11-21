import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/recipes
public class ScalingRecipies {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int numCases = sc.nextInt();
        for (int q = 1; q <= numCases; q++) {
            int numLines = sc.nextInt();
            double P = sc.nextDouble();
            double D = sc.nextDouble();
            String[] names = new String[numLines];
            double[] weights = new double[numLines];
            double[] percentages = new double[numLines];
            double[] scaledWeights = new double[numLines];
            int idx = -1;
            for (int i = 0; i < numLines; i++) {
                String name = sc.next();
                double weight = sc.nextDouble();
                double percent = sc.nextDouble();
                names[i] = name;
                weights[i] = weight;
                percentages[i] = percent / 100;
                if (Math.abs(percent - 100) < 1e-2) {
                    idx = i;
                }
            }
            
            double scaleFactor = D / P;
            weights[idx] *= scaleFactor;
            
            for (int i = 0; i < numLines; i++) {
                if (i != idx) {
                    weights[i] = weights[idx] * percentages[i]; 
                }
            }
            
            System.out.println("Recipe # " + q);
            for (int i = 0; i < numLines; i++) {
                System.out.printf("%s %.1f\n", names[i], weights[i]);
            }
            
            for (int i = 0; i < 40; i++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }
}
