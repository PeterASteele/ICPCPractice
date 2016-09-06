import java.util.*;
import java.math.*;
import java.io.*;
//ICPC World Finals 2012 Problem K (practice)
//https://icpc.kattis.com/problems/stacking

/**
 * Accepted after the contest
 */
public class StackingPlates {
    static HashMap<Integer, Integer> map;

    public static void main(String[] args) {
        map = new HashMap<Integer, Integer>();
        // Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        // FastScanner sc = new FastScanner();
        int count = 0;
        while (input.hasNext()) {
            count++;

            int n = input.nextInt();
            ArrayList<PLATE> obj = new ArrayList<PLATE>();
            for (int a = 0; a < n; a++) {
                int w = input.nextInt();
                for (int b = 0; b < w; b++) {
                    int r = input.nextInt();
                    PLATE temp = new PLATE(a, r);
                    if (obj.size() == 0 || temp.compareTo(obj.get(obj.size() - 1)) != 0) {
                        obj.add(temp);
                    }
                }
            }
            Collections.sort(obj);
            ArrayList<STACK> stacks = new ArrayList<STACK>();
            for (PLATE o : obj) {
                while (stacks.size() <= o.radius) {
                    stacks.add(new STACK(stacks.size()));
                }
                stacks.get(o.radius).id.add(o.id);
            }
            ArrayList<STACK> stacks2 = new ArrayList<STACK>();
            for (STACK a : stacks) {
                if (a.id.size() != 0) {
                    stacks2.add(a);
                }
            }
            int[][] dp = new int[stacks2.size()][n];
            for (int a = 0; a < stacks2.size(); a++) {
                for (int b = 0; b < n; b++) {
                    dp[a][b] = -1;
                }
            }

            for (int a = 0; a < stacks2.size(); a++) {
                if (a == 0) {
                    STACK tempStack = stacks2.get(a);
                    for (Integer tempID : tempStack.id) {
                        dp[a][tempID] = tempStack.id.size() - 1;
                    }
                    for (int b = 0; b < n; b++) {
                        if (dp[a][b] == -1) {
                            dp[a][b] = 99999;
                        }
                    }

                } else {
                    STACK tempStack = stacks2.get(a);
                    for (Integer tempID : tempStack.id) {
                        dp[a][tempID]=0;
                        STACK tempStackAbove = stacks2.get(a-1);
                        if(tempStackAbove.id.size() == 1){
                            if(tempStackAbove.id.get(0).equals(tempID)){
                                dp[a][tempID] = dp[a-1][tempStackAbove.id.get(0)];
                            }
                            else{
                                dp[a][tempID] = dp[a-1][tempStackAbove.id.get(0)]+1;
                            }
                        }
                        else{
                            int bestPossibility = Integer.MAX_VALUE/10;
                            for(int b = 0; b < n; b++){
                                if(tempStackAbove.id.contains(b)){
                                    int possibility = dp[a-1][b];
                                    if(b == tempID || (tempStackAbove.id.contains(tempID) == false)){
                                        possibility++;
                                    }
                                    if(bestPossibility > possibility){
                                        bestPossibility = possibility;
                                    }
                                }
                            }
                            dp[a][tempID] = bestPossibility;
                        }               
                                
                        
                    }
                    for (int b = 0; b < n; b++) {
                        if (dp[a][b] == -1) {
                            dp[a][b] = 99999;
                        }
                    }
                    for (int b = 0; b < n; b++) {
                        dp[a][b] += tempStack.id.size() - 1;
                    }
                }
            }
//            for(int a = 0; a < stacks2.size(); a++){
//                for(int b = 0; b < n;b ++){
//                    System.out.print(dp[a][b] + " ");
//                }
//                System.out.println();
//            }
            int minCuts = minValue(dp, stacks2.size() - 1, -1, n);
            int currentStacks = minCuts + 1;
//            System.out.println("Merge: " + minCuts);
//            System.out.println("Cuts: " + ((currentStacks - n)));
            System.out.println("Case " + (count) + ": " + (minCuts + (currentStacks - n)));
        }
    }

    private static int minValue(int[][] dp, int a, Integer tempID, int n) {
        int min = Integer.MAX_VALUE / 2;
        for (int b = 0; b < n; b++) {
            if (b != tempID) {
                int canidate = dp[a][b];
                if (canidate < min) {
                    min = canidate;
                }
            }
        }
        return min;
    }

    private static int minValue(int[][] dp, int a, Integer tempID, int n, STACK mustHave) {
        int min = Integer.MAX_VALUE / 2;
        for (int b = 0; b < n; b++) {
            if (b != tempID && mustHave.id.contains(b)) {
                int canidate = dp[a][b];
                if (canidate < min) {
                    min = canidate;
                }
            }
        }
        return min;
    }

    public static class STACK {
        int radius;
        ArrayList<Integer> id;

        public STACK(int radius) {
            id = new ArrayList<Integer>();
            this.radius = radius;
        }

        public String toString() {
            return radius + " " + id;
        }

    }

    public static class DP {
        int radius;
        int notTop;
        int solution;

        public DP(int radius, int notTOp, int solution) {
            this.radius = radius;
            this.notTop = notTOp;
            this.solution = solution;
        }

        @Override
        public int hashCode() {
            return radius + notTop;
        }

        @Override
        public boolean equals(Object o) {
            DP a = (DP) o;
            return a.radius == radius && a.notTop == notTop;

        }

    }

    public static class PLATE implements Comparable<PLATE> {
        int id;
        int radius;

        public PLATE(int id, int radius) {
            this.id = id;
            this.radius = radius;
        }

        @Override
        public int compareTo(PLATE o) {
            if (radius == o.radius) {
                return id - o.id;
            } else {
                return radius - o.radius;
            }
        }

    }
}