import java.util.Scanner;

public class Robotopia {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        for(int a = 0; a < n; a++){
            int leg1 = input.nextInt();
            int arm1 = input.nextInt();
            int diff1 = leg1-arm1;
            int leg2 = input.nextInt();
            int arm2 = input.nextInt();
            int diff2 = leg2-arm2;
            int legCount = input.nextInt();
            int armCount = input.nextInt();
            int diff3 = legCount-armCount;
            
            int solutionCount = 0;
            StringBuilder answer = new StringBuilder();
            for(int b = 1; b < 100000; b++){
                int legTotal = b*leg1;
                int armTotal = b*arm1;
                if(legCount >= legTotal && armCount >= armTotal){
                    int legLeft = legCount-legTotal;
                    int armLeft = armCount-armTotal;
                    if(armLeft%arm2 == 0 && legLeft%leg2 == 0){
                        if(armLeft/arm2 == legLeft/leg2 && armLeft/arm2 != 0){
                            solutionCount++;
                            answer.append(b + " " + armLeft/arm2);
                        }
                    }
                }
            }       
            if(solutionCount == 1){
                System.out.println(answer);
            }
            else{
                System.out.println("?");
            }
        }
    }

}