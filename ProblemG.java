import java.util.*;
public class ProblemG {
//NAIPC2013
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int numPostings = sc.nextInt();
			int numStudents = sc.nextInt();
			if ((numPostings|numStudents) == 0) break;
			
			int[] numPositions = new int[numPostings];
			for (int posting = 0; posting < numPostings; posting++) {
				numPositions[posting] = sc.nextInt();
			}
			Student[] students = new Student[numStudents];
			for (int student = 0; student < numStudents; student++) {
				int year = sc.nextInt();
				int c1 = sc.nextInt();
				int c2 = sc.nextInt();
				int c3 = sc.nextInt();
				int c4 = sc.nextInt();
				students[student] = new Student(year, c1, c2, c3, c4);
			}
		}
	}
	
	
	
//	public static class BipartiteMatching {
//		  int[] match;
//		  int matches = 0;
//		  boolean[] done;
//		  int N;
//
//		  ArrayList<ArrayList<Integer>> adj;
//
//		  // Initialized with Adjacency List.
//		  public BipartiteMatching(ArrayList<ArrayList<Integer>> adj) {
//		    this.adj = adj;
//		    N = adj.size();
//		    match = new int[N];
//		    Arrays.fill(match, -1);
//		  }
//
//		  // Returns matching size and populates match[] with
//		  // the details. -1 indicates no match.
//		  public int findMaxMatching() {
//		    for (int i = 0; i < N; i++) {
//		      done = new boolean[N];
//		      if (augment(i)) matches++;
//		    }
//		    return matches;
//		  }
//
//		  // Attempt to find an augmenting path.
//		  public boolean augment(int at) {
//		    if (done[at]) return false;
//		    done[at] = true;
//
//		    ArrayList<Integer> edges = adj.get(at);
//		    for (int i = 0; i < edges.size(); i++) {
//		      int to = edges.get(i);
//		      if (match[to] == -1 || augment(match[to])) {
//		        match[to] = at;
//		        return true;
//		      }
//		    }
//		    return false;
//		  }
//
//		}
	
	static class Student {
		final int year, c1, c2, c3, c4;
		Student(int y, int cc1, int cc2, int cc3, int cc4) {
			year = y - 1;
			c1 = cc1;
			c2 = cc2;
			c3 = cc3;
			c4 = cc4;
//			c1 = cc1 + year * 4;
//			c2 = cc2 + year * 4;
//			c3 = cc3 + year * 4;
//			c4 = cc4 + year * 4;
		}
	}

}
