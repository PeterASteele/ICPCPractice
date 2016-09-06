import java.util.*;
import java.math.*;
import java.io.*;
//NWERC2015 Problem I (Practice)
//https://open.kattis.com/contests/naipc16-p09/problems/maptiles2
public class IdentifyingMapTiles {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        int[] coord = {
                0,0
        };
        System.out.print(solve(s.toCharArray(), 0, coord, (int) Math.pow(2, s.length())) + " ");
        System.out.print(coord[0] + " ");
        System.out.println(coord[1]);
    }

    static int solve(char[] s, int i, int[] coord, int stepsize) {
        if (i == s.length) return 0;

        if (s[i] == '1') {
            coord[0] += stepsize/2;
        } else if (s[i] == '2') {
            coord[1] += stepsize/2;
        } else if (s[i] == '3') {
            coord[0] += stepsize/2;
            coord[1] += stepsize/2;
        }

        return 1 + solve(s, i + 1, coord, stepsize / 2);
    }
}