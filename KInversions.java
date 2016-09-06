import java.util.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;
//NAIPC 2016 Problem E
//FFT implementation deliberately removed, so this code will not compile.
//https://naipc16.kattis.com/problems/kinversions
public class KInversions {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String inStr = input.nextLine();
        double[] arr = new double[inStr.length()];
        double[] arr2 = new double[inStr.length()];
        for(int a = 0; a < inStr.length(); a++){
            if(inStr.charAt(a) == 'A'){
                arr[a] = 1;
            }
            else{
                arr2[inStr.length()-a-1] = 1;
            }
        }
        Polynomial testPoly = new Polynomial(arr);
        Polynomial testPoly2 = new Polynomial(arr2);
        Polynomial ans = testPoly.fastMultiply(testPoly2); //n * log(n) polynomial multipication implementation.
        StringBuilder out = new StringBuilder();
        for(int a = 0; a < inStr.length()-1; a++){
            out.append(((int)(ans.a[inStr.length()+a]+.5))+"\n");
        }
        System.out.print(out);
    }
    
    
}