import java.util.Scanner;
//http://codeforces.com/gym/100863/attachments, Problem C
public class Crisis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		int a = input.nextInt();
		int b = input.nextInt();
		if (a == 0 && b == 0) {
			System.out.println(0 + " " + 0);
		} else {
			int count = 2;
			while (b >= 0) {
				int temp = a - b;
				a = b;
				b = temp;
				count++;
			}
			System.out.println(count + " " + b);
		}
	}

}
