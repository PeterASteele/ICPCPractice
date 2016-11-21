import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/phonelist
public class PhoneList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int numCases = sc.nextInt();

        for (int q = 0; q < numCases; q++) {
            Set<String> strs = new HashSet<>();
            int n = sc.nextInt();
            boolean bad = false;
            for (int i = 0; i < n; i++) {
                String str = sc.next();
                strs.add(str);

            }

            bad = strs.size() < n;
//            System.out.println(strs);

            if (!bad) {
                outer:
                for (String str : strs) {
                    for (int j = 1; j <= str.length() - 1; j++) {
                        String subStr = str.substring(0, j);
//                        System.out.println(subStr);
                        if (strs.contains(subStr)) {
                            bad = true;
                            break outer;
                        }
                    }
                }
            }

            if (bad) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
            }
        }
    }

    static class Node {

    }

}
