import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/woodcutting
public class WoodCutting {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int t = input.nextInt();
        for(int q = 0; q < t; q++){
            
            ArrayList<Double> customerTime = new ArrayList<Double>();
            int n = input.nextInt();
            for(int a = 0; a < n; a++){
                double xyz = 0.0;
                
                int w = input.nextInt();
                for(int b = 0; b < w; b++){
                    xyz += input.nextInt();
                }
                customerTime.add(xyz);
            }
            Collections.sort(customerTime);

            ArrayList<Double> waitTime = new ArrayList<Double>();
            double time = 0;
            for(Double i:customerTime){
                time += i;
                waitTime.add(time);
            }
            
            double sum = 0;
            for(Double i:waitTime){
                sum += i;
            }
            double average = sum / waitTime.size();
            System.out.println(average);
            
            
        }
    }
}
