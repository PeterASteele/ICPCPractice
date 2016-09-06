import java.util.*;
import java.math.*;
import java.io.*;
//NAIPC2015 Problem E (Practice)
//https://open.kattis.com/contests/naipc16-p10/problems/primal

public class PrimalPartitions {
    static ArrayList<Integer>[] primeArr;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        boolean[] arr = sieve(1000010);
        ArrayList<Integer> primeList = new ArrayList<Integer>();
        for (int a = 0; a < 1000010; a++) {
            if (arr[a]) {
                primeList.add(a);
            }
        }
        int n = input.nextInt();
        int m = input.nextInt();
        int[] inputs = new int[n];
        for (int a = 0; a < n; a++) {
            inputs[a] = input.nextInt();
        }
        primeArr = new ArrayList[n];
        for (int a = 0; a < n; a++) {
            primeArr[a] = getPrimes(primeList, inputs[a]);
        }

        int low = 0;
        int high = primeList.size() - 1;
        boolean workedOnce = false;
        int backup = 0;
        while (low < high) {
            backup++;
            if(backup > 50){
                break;
            }
            int mid = (low + high+1) / 2;
            if (works(primeList.get(mid), m, inputs)) {
                workedOnce = true;
                if(low == mid){
                    break;
                }
                low = mid;
            } else {
                high = mid-1;
            }
        }
        if (workedOnce) {
            System.out.println(primeList.get(low));
        } else {
            if (works(primeList.get(0), m, inputs)) {
                System.out.println(primeList.get(0));
            } else {
                System.out.println(0);
            }
        }
    }
    
    static boolean works(int factor, int m, int[] inputs) {
        int numRegions = 1;
        HashSet<Integer> current = new HashSet<Integer>();
        current.add(-1);
        int backup = 0;
        for (int i = 0; i < inputs.length; i++) {
            backup++;
            if(backup > 50000){
                return true;
            }
            HashSet<Integer> temp = new HashSet<Integer>();
            temp.addAll(primeArr[i]);
            current = gcd(current, temp);
            int score = getLargest(current);
            if (score < factor) {
                numRegions++;
                i--;
                current = new HashSet<Integer>();
                current.addAll(primeArr[i+1]);
            }
            if (numRegions > m) {
                return false;
            }
        }
        
        return true;
    }

    private static ArrayList<Integer> getPrimes(ArrayList<Integer> primeList, int i) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int start = 0;
        while (i != 1 && start*start <= i) {
            while (i % primeList.get(start) == 0 && i != 1) {
                i /= primeList.get(start);
                if (result.size() == 0 || result.get(result.size() - 1) != primeList.get(start)) {
                    result.add(primeList.get(start));
                }
            }
            start++;
        }
        if(i != 1){
            result.add(i);
        }
        return result;
    }

    static boolean[] sieve(int N) {
        boolean[] a = new boolean[N + 1];
        Arrays.fill(a, true);
        a[0] = a[1] = false;
        for (int p = 2; p * p <= N; p++) {
            if (a[p]) {
                for (int m = p * p; m <= N; m += p) {
                    a[m] = false;
                }
            }
        }
        return a;
    }

    public static HashSet<Integer> gcd(HashSet<Integer> value2, HashSet<Integer> value3) {
        if (value2.contains(-1) && value3.contains(-1)) {
            return value2;
        }
        if (value2.contains(-1)) {
            return value3;
        }
        if (value3.contains(-1)) {
            return value2;
        }
        HashSet<Integer> output = new HashSet<Integer>();
        for (Integer a : value2) {
            if (value3.contains(a)) {
                output.add(a);
            }
        }
        return output;
    }

    public static int getLargest(HashSet<Integer> set) {
        int max = -1;
        for (Integer a : set) {
            if (max < a) {
                max = a;
            }
        }
        return max;
    }
}