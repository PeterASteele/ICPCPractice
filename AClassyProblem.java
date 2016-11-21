import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/classy
public class AClassyProblem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int n = sc.nextInt();
            
            String[] names = new String[n];
            String[] classes = new String[n];
            int maxLen = 0;
            for (int j = 0; j < n; j++) {
                names[j] = sc.next().trim();
                names[j] = names[j].substring(0, names[j].length() - 1);
                
                String temp = sc.next();
                temp = temp.replace("upper", "0");
                temp = temp.replace("middle", "1");
                temp = temp.replace("lower", "2");
                String[] arr = temp.split("-");
                String simpleClass = "";
                for (int k = arr.length - 1; k >= 0; k--) {
                    simpleClass += arr[k];
                }
                
                if (simpleClass.length() > maxLen) {
                    maxLen = simpleClass.length();
                }
                
                classes[j] = simpleClass;
                
                sc.next();
            }
            
            for (int j = 0; j < n; j++) {
                int len = classes[j].length();
                for (int k = 0; k < (maxLen - len); k++) {
                    classes[j] += "1";
                }
                classes[j] += ((char)('z'+1)) + " " + names[j];
//                System.err.println(classes[j]);

            }
            
            Arrays.sort(classes);
            
            for (int k = 0; k < n; k++) {
                String[] temp = classes[k].split(" ");
                System.out.println(temp[1]);
            } 
            
            for (int j = 0; j < 30; j++) {
                System.out.print("=");
            }
            System.out.println();
        }
    }
}
