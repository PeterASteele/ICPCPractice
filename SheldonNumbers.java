import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/sheldon
public class SheldonNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        long X = input.nextLong();
        long Y = input.nextLong();
        HashSet<String> possibilities = new HashSet<String>();
        for(int ASize = 1; ASize <= 63; ASize++){
            for(int BSize = 1; BSize <= 63; BSize++){
                for(int c = 1; c <= 63/(ASize+BSize) + 3; c++){
                    StringBuilder tmp = new StringBuilder();
                    for(int d = 0; d < c; d++){
                        tmp.append(getOnes(ASize) + ""+getZeros(BSize));
                    }
                    String tempString = tmp.toString();
                    if(tempString.length() <= 63){
                        possibilities.add(tempString);
                    }
                }
                for(int c = 0; c <= 63/(ASize+BSize) + 3; c++){
                    StringBuilder tmp = new StringBuilder();
                    tmp.append(getOnes(ASize));
                    for(int d = 0; d < c; d++){
                        tmp.append(getZeros(BSize) + getOnes(ASize));
                    }
                    String tempString = tmp.toString();
                    if(tempString.length() <= 63){
                        possibilities.add(tempString);
                    }
                }
            }
        }
        for(int a = 1; a <= 63; a++){
            possibilities.add(getOnes(a));
        }
//        for(String i:possibilities){
//            System.out.println(i);
//            input.nextLine();
//        }
        int count = 0;
        for(String i:possibilities){
            BigInteger i2 = new BigInteger(i, 2);
            BigInteger low = BigInteger.valueOf(X);
            BigInteger high = BigInteger.valueOf(Y);
            if(low.compareTo(i2) <= 0 && i2.compareTo(high) <= 0){
//                System.out.println(i2);
                count++;
            }
        }
        System.out.println(count);
    }
    private static String getZeros(int aSize) {
        StringBuilder output = new StringBuilder();
        for(int a = 0; a < aSize; a++){
            output.append("0");
        }
        return output.toString();
        // TODO Auto-generated method stub
    }
    private static String getOnes(int aSize) {
        StringBuilder output = new StringBuilder();
        for(int a = 0; a < aSize; a++){
            output.append("1");
        }
        return output.toString();
        // TODO Auto-generated method stub
    }
}
