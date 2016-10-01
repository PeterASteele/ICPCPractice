import java.util.Scanner;

public class QuickEstimates {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        for(int a = 0; a < n; a++){
            String in = input.next();
            System.out.println(in.length());
        }
    }
}