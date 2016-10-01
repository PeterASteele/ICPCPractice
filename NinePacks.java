import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;

public class NinePacks {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        int A = input.nextInt();
        ArrayList<Integer> possibilities = new ArrayList<Integer>();
        for (int a = 0; a < A; a++) {
            possibilities.add(input.nextInt());
        }
        int total = 0;
        for (Integer i : possibilities) {
            total += i;
        }

        int B = input.nextInt();
        for (int a = 0; a < B; a++) {
            possibilities.add(input.nextInt() * -1);
        }
        int[] arr = new int[total + 1];
        Arrays.fill(arr, Integer.MAX_VALUE);
        arr[0] = 0;
        for (Integer j : possibilities) {
            int[] newArray = (int[]) arr.clone();
            for (int a = 0; a < total + 1; a++) {
                int dogs = a;
                int turns = arr[dogs];
                if (turns != Integer.MAX_VALUE) {
                    if (dogs + j >= 0) {
                        if (newArray[dogs + j] > turns + 1 || newArray[dogs + j] == 0) {
                            newArray[dogs + j] = turns + 1;
                        }
                    }
                }
            }
            arr = newArray;
        }
        if (arr[0] == 0) {
            System.out.println("impossible");
        } else {
            System.out.println(arr[0]);
        }
    }

}
