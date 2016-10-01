import java.util.Scanner;

public class Brackets {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        int[] reverseStart = new int[in.length() + 1];
        int start = 0;
        for (int a = in.length() - 1; a >= 0; a--) {
            if (in.charAt(a) == '(') {
                start -= 1;
            } else {
                start += 1;
            }
            if (start < 0) {
                start = Integer.MAX_VALUE / 2;
            }
            reverseStart[a] = start;
        }

        for (int startFlip = 0; startFlip < in.length(); startFlip++) {
            int st = 0;
            for (int a = 0; a < startFlip; a++) {
                st += in.charAt(a) == '(' ? 1 : -1;
                if (st < 0) {
                    st = Integer.MIN_VALUE / 2;
                }
            }
            for (int b = startFlip; b < in.length(); b++) {
                if(st == reverseStart[b]){
                    System.out.println("possible");
                    System.exit(0);
                }
                st += in.charAt(b) == ')' ? 1 : -1;
                if(st < 0){
                    st = Integer.MIN_VALUE / 2;
                }
                if(st == reverseStart[b+1]){
                    System.out.println("possible");
                    System.exit(0);
                }
            }
        }
        System.out.println("impossible");
    }

}
