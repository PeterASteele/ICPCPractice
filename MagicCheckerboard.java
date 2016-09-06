import java.util.*;
import java.math.*;
import java.io.*;
//NAIPC2015 Problem C (Practice)
//https://open.kattis.com/contests/naipc16-p10/problems/checkerboard

public class MagicCheckerboard {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int m = input.nextInt();
        int[][] arr = new int[n][m];
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < m; b++) {
                arr[a][b] = input.nextInt();
            }
        }
        if (n == 1 || m == 1) {
            long possibility = evaluate(arr);
            if (possibility == Long.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(possibility);
            }
        } else {
            int oddSumParity = -1;
            int evenSumParity = -1;
            boolean OK = true;
            for (int a = 0; a < n; a++) {
                for (int b = 0; b < m; b++) {
                    if (arr[a][b] != 0) {
                        if ((a + b) % 2 == 0) {
                            if (evenSumParity == (a + arr[a][b] + 1) % 2) {
                                OK = false;
                            }
                            evenSumParity = (a + arr[a][b]) % 2;
                        } else {
                            if (oddSumParity == ((a + arr[a][b]) + 1) % 2) {
                                OK = false;
                            }
                            oddSumParity = (a + arr[a][b]) % 2;
                        }
                    }
                }
            }
            if (OK == false) {
                // System.out.println(x);
                System.out.println(-1);
            } else {
                ArrayList<Long> possibility = new ArrayList();
                int[][] copy = copyArray(arr);
                if (oddSumParity == -1 && evenSumParity == -1) {
                    copy = copyArray(arr);
                    long getSum = calculate(copy, 0, 0);
                    copy = copyArray(arr);
                    long getSum2 = calculate(copy, 0, 1);
                    copy = copyArray(arr);
                    long getSum3 = calculate(copy, 1, 0);
                    copy = copyArray(arr);
                    long getSum4 = calculate(copy, 1, 1);
                    possibility.add(getSum);
                    possibility.add(getSum2);
                    possibility.add(getSum3);
                    possibility.add(getSum4);
                } else {
                    if (oddSumParity == -1) {
                        copy = copyArray(arr);
                        long getSum = calculate(copy, 0, evenSumParity);
                        copy = copyArray(arr);
                        long getSum4 = calculate(copy, 1, evenSumParity);
                        possibility.add(getSum);
                        possibility.add(getSum4);
                    } else {
                        if (evenSumParity == -1) {
                            copy = copyArray(arr);
                            long getSum = calculate(copy, oddSumParity, 0);
                            copy = copyArray(arr);
                            long getSum4 = calculate(copy, oddSumParity, 1);
                            possibility.add(getSum);
                            possibility.add(getSum4);
                        } else {
                            copy = copyArray(arr);
                            possibility.add(calculate(copy, oddSumParity, evenSumParity));
                        }
                    }
                }
                Collections.sort(possibility);
                if (possibility.get(0).equals(Long.MAX_VALUE)) {
                    System.out.println(-1);
                } else {
                    System.out.println(possibility.get(0));
                }
            }
        }
    }

    private static long evaluate(int[][] arr) {
        if (arr[0][0] == 0) {
            arr[0][0] = 1;
        }
        if(arr.length == 1 && arr[0].length == 1){
            return sumArray(arr);
        }
        if (arr[0].length == 1) {
            for (int a = 1; a < arr.length; a++) {
                if(arr[a][0] == 0)
                arr[a][0] = arr[a-1][0]+1;
            }
        }
        if (arr.length == 1) {
            for (int b = 1; b < arr[0].length; b++) {
                if(arr[0][b] == 0)
                arr[0][b] = arr[0][b-1]+1;
            }
        }
        if(!validCheck(arr)){
            return Long.MAX_VALUE;
        }
        return sumArray(arr);
    }

    private static long calculate(int[][] arr, int oddSumParity, int evenSumParity) {
        int n = arr.length;
        int m = arr[0].length;
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < m; b++) {
                if (arr[a][b] == 0) {
                    int lowBound = -1;
                    if (a > 0 && b > 0) {
                        lowBound = Math.max(arr[a - 1][b], arr[a][b - 1]) + 1;

                    } else {
                        if (a > 0) {
                            lowBound = arr[a - 1][b] + 1;
                        } else {
                            if (b > 0) {
                                lowBound = arr[a][b - 1] + 1;
                            } else {
                                lowBound = 1;
                            }
                        }
                    }

                    if ((a + b) % 2 == 0) {
                        if (lowBound % 2 != (evenSumParity + a) % 2) {
                            lowBound++;
                        }
                    } else {
                        if (lowBound % 2 != (oddSumParity + a) % 2) {
                            lowBound++;
                        }
                    }
                    arr[a][b] = lowBound;
                }
            }
        }

        if (validCheck(arr)) {
            return sumArray(arr);
        } else {
            return Long.MAX_VALUE;
        }
    }

    private static int[][] copyArray(int[][] arr) {
        int[][] out = new int[arr.length][arr[0].length];
        for (int a = 0; a < arr.length; a++) {
            for (int b = 0; b < arr[0].length; b++) {
                out[a][b] = arr[a][b];
            }
        }
        return out;
    }

    private static boolean validCheck(int[][] arr) {
        boolean ok = true;
        for (int a = 1; a < arr.length; a++) {
            for (int b = 1; b < arr[0].length; b++) {
                if (arr[a][b] <= arr[a - 1][b] || arr[a][b] <= arr[a][b - 1]) {
                    ok = false;
                }
            }
        }
        for (int a = 1; a < arr.length; a++) {
            if (arr[a][0] <= arr[a - 1][0]) {
                ok = false;
            }
        }
        for (int b = 1; b < arr[0].length; b++) {
            if (arr[0][b] <= arr[0][b - 1]) {
                ok = false;
            }
        }
        return ok;
    }

    private static long sumArray(int[][] arr) {
        long sum = 0;
        for (int a = 0; a < arr.length; a++) {
            for (int b = 0; b < arr[0].length; b++) {
                sum += arr[a][b];
            }
        }
        return sum;
    }
}