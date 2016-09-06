import java.util.ArrayList;
import java.util.Scanner;

//NCPC2015 Problem D (Practice)
//https://open.kattis.com/contests/naipc16-p05/problems/downtime
public class DisastrousDowntime {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int k = input.nextInt();
		int[] freqDist = new int[200001];
		for(int a = 0; a < n; a++){
			int d = input.nextInt();
			int d2 = d+1;
			freqDist[d]++;
			freqDist[d+1000]--;
		}
		int jobs = 0;
		int maxJobs = 0;
		for(int a = 0; a < freqDist.length; a++){
			jobs += freqDist[a];
			if(jobs > maxJobs){
				maxJobs = jobs;
			}
		}
		
		System.out.println((int)(Math.ceil(((maxJobs+0.0)/k) - 0.000000001)+.2));
	}

}