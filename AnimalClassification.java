import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Map.Entry;

/*
 * https://open.kattis.com/problems/animal
 * Animal Classification
Problem ID: animalTime limit: 4 secondsMemory limit: 1024 MB
DIFFICULTY
6.5
Alice and Bob are biologists. They like to group animals using classification trees. A classification tree is a binary tree where the leaves are labeled by the animals.

For example, Figure 1 below shows the classification of 5 animals (labeled as 1,2,3,4,5) by Alice and Bob. Alice’s classification has nine groups: {1}, {2}, {3}, {4}, {5}, {2,5}, {1,2,5}, {1,2,3,5} and {1,2,3,4,5}. Bob’s classification also has nine groups: {1}, {2}, {3}, {4}, {5}, {1,5}, {2,3}, {1,2,3,5} and {1,2,3,4,5}.

\includegraphics[width=0.5\textwidth ]{example.png}
Figure 1: Illustration of Sample Input 1
As you can observe, Alice and Bob classify the animals differently. Carol is interested to know the number of common groups between the classifications of Alice and Bob. For the above example, there are 7 common groups. They are: {1}, {2}, {3}, {4}, {5}, {1,2,3,5} and {1,2,3,4,5}.

Carol usually counts the number of common goups by hand. On one day, an outer space alien came to our earth. He asked Alice and Bob to classify aliens in outer space. The number N of aliens can be as big as 100000. It is impossible to count the number of common goups by hand now. Can you make a program to help Carol?

Input
The input consists of three lines:

one line with one integer N (1≤N≤100000)
the classification tree of Alice
the classification tree of Bob
Note that both classification trees are leaf-labeled by 1 to N. The tree is represented by the parathesis encoding. (Note that the encoding only have numbers and 3 symbols, that is, ‘(’, ‘)’, ‘,’.) For the example in Figure 1, see Sample Input 1.

Output
Output one line with a single integer that represents the number of common groups between Alice and Bob’s classification trees.

Sample Input 1	Sample Output 1
5
((3,(1,(5,2))),4)
(((5,1),(2,3)),4)
7
Sample Input 2	Sample Output 2
10
(1,(2,(3,(4,(5,(6,(7,(8,(9,10)))))))))
(((((((((1,2),3),4),5),6),7),8),9),10)
11
Sample Input 3	Sample Output 3
10
(10,(9,(8,(7,(6,(5,(4,(3,(2,1)))))))))
(((((((((1,2),3),4),5),6),7),8),9),10)
19
 */
public class AnimalClassification {

	static ArrayList<Integer> numbers;
	static HashMap<Point, Long> storageAlice;
	static HashMap<Point, Long> storageBob;

	public static void main(String[] args) {
		numbers = new ArrayList<Integer>();
		numbers.add(2);
		numbers.add(3);
		numbers.add(5);
		numbers.add(7);
		numbers.add(11);
		numbers.add(13);
		numbers.add(17);
		numbers.add(19);
		
		Scanner2 input = new Scanner2();
		int N = input.nextInt();
		String AliceString = input.readNextLine();
		String BobString = input.readNextLine();
		char[] Alice = AliceString.toCharArray();
		char[] Alice2 = AliceString.toCharArray();
		char[] Bob = BobString.toCharArray();
		char[] Bob2 = BobString.toCharArray();
		ArrayList<Point> Alices = new ArrayList<Point>();
		ArrayList<Point> Bobs = new ArrayList<Point>();
		int[] AlicesNumbers = new int[N];
		int[] BobsNumbers = new int[N];
		int countAlice = 0;
		ArrayList<Integer> fakeStack = new ArrayList<Integer>();
		int number = 0;
		
		//First, we split up and index all of the possible groups.
		for (int a = 0; a < Alice.length; a++) {
			if (Alice[a] == '(') {
				fakeStack.add(number);
			}
			if (Alice[a] == ')') {
				Alices.add(new Point(fakeStack.get(fakeStack.size() - 1),
						number));
				fakeStack.remove(fakeStack.size() - 1); 
				//Warning: This seems like O(n) squared. Fortunately, we are just removing the last element so it's ok.
				countAlice++;
			}
			if (Alice[a] == ',') {
				number++;
			}
		}
		number = 0;
		//First, we split up and index all of the possible groups except for single groups.
		for (int a = 0; a < Bob.length; a++) {
			if (Bob[a] == '(') {
				fakeStack.add(number);
			}
			if (Bob[a] == ')') {
				Bobs.add(new Point(fakeStack.get(fakeStack.size() - 1), number));
				fakeStack.remove(fakeStack.size() - 1);
			}
			if (Bob[a] == ',') {
				number++;
			}
		}
		//Now, we remove all non-space delimiters so we can use scanner on it
		for (int a = 0; a < Alice2.length; a++) {
			if (Alice2[a] == '(' || Alice2[a] == ')' || Alice2[a] == ',') {
				Alice2[a] = ' ';
			}
		}
		for (int a = 0; a < Bob2.length; a++) {
			if (Bob2[a] == '(' || Bob2[a] == ')' || Bob2[a] == ',') {
				Bob2[a] = ' ';
			}
		}
		Scanner AliceScan = new Scanner(new String(Alice2));
		Scanner BobScan = new Scanner(new String(Bob2));
		HashMap<Long, Integer> AlicesEvals = new HashMap<Long, Integer>();
		HashMap<Long, Integer> BobsEvals = new HashMap<Long, Integer>();
		number = 0;
		//Use scanners to split up and generate numbers.
		storageAlice = new HashMap<Point, Long>();
		storageBob = new HashMap<Point, Long>();
		while (AliceScan.hasNextInt()) {
			int temp = AliceScan.nextInt();
			Alices.add(new Point(number, number));
			// System.out.println(temp + " is the temp");
			AlicesNumbers[number] = temp;
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			tempList.add(temp);
			Long temp2 = hashItIhopethisworks((Long) (long) temp);
			storageAlice.put(new Point(number, number), temp2);
			AlicesEvals.put(temp2, 0);
			number++;
		}
		number = 0;
		while (BobScan.hasNextInt()) {
			int temp = BobScan.nextInt();
			Bobs.add(new Point(number, number));
			BobsNumbers[number] = temp;
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			tempList.add(temp);
			Long temp2 = hashItIhopethisworks((Long) (long) temp);
			storageBob.put(new Point(number, number), temp2);
			BobsEvals.put(temp2, 0);
			number++;
		}
		//N log N sort of all 2*N groups.
		Collections.sort(Bobs, new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				if (Double.compare(o1.getX(), o2.getX()) == 0) {
					return -1 * Double.compare(o1.getY(), o2.getY());
				}
				return Double.compare(o1.getX(), o2.getX());
			}
		});
		Collections.sort(Alices, new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				if (Double.compare(o1.getX(), o2.getX()) == 0) {
					return -1 * Double.compare(o1.getY(), o2.getY());
				}
				return Double.compare(o1.getX(), o2.getX());
			}
		});
		
		//Now we add them all to a hashmap
		addEvaluationsAlice(AlicesEvals, Alices, AlicesNumbers);
		addEvaluationsBob(BobsEvals, Bobs, BobsNumbers);
		//O(N) elements in the first hashmap, times O(1) lookup in the other hashmap.
		int count2 = 0;
		for (Entry<Long, Integer> entryV : AlicesEvals.entrySet()) {
			Long v = entryV.getKey();
			if (BobsEvals.containsKey(v)) {
				count2++;
			}
		}
		
		System.out.println(count2);
	}
//see addEvaluationsAlice
	private static void addEvaluationsBob(
			HashMap<Long, Integer> bobsEvals, ArrayList<Point> bobs,
			int[] bobsNumbers) {
		
		for(int a = bobs.size()-1; a >= 0; a--){
			if(storageBob.containsKey(bobs.get(a))){
				bobsEvals.put(storageBob.get(bobs.get(a)), 0); //if we already have it computed, stick it in
			}
			else{
				//if not, simply look up the answers of the sub items.
				//Because of the way that our sort goes, the sub-items are always computed before the items.
				int b = Collections.binarySearch(bobs, new Point((int) bobs.get(a+1).getY(), -1), new Comparator<Point>() {
					public int compare(Point o1, Point o2) {
						if(Double.compare(o1.getX(), o2.getX()) == 0){
							return -1 * Double.compare(o1.getY(), o2.getY());
						}
						return Double.compare(o1.getX(), o2.getX());}}
					);	
				try{			//this was there for debugging earlier; we got rid of it since.
					storageBob.put(bobs.get(a), storageBob.get(bobs.get(a+1)) + storageBob.get(bobs.get(-1 * b -1)));
					bobsEvals.put(storageBob.get(bobs.get(a+1)) + storageBob.get(bobs.get(-1 * b -1)), 0);
				}
				catch(Exception e){
					}
			}
		}
	}
	 //same as bob but for a different static variable. 
	private static void addEvaluationsAlice(
			HashMap<Long, Integer> alicesEvals, ArrayList<Point> alices,
			int[] alicesNumbers) {
		for(int a = alices.size()-1; a >= 0; a--){
			if(storageAlice.containsKey(alices.get(a))){
				//System.out.println(alices.get(a));
				alicesEvals.put(storageAlice.get(alices.get(a)), 0);
			}
			else{
				int b = Collections.binarySearch(alices, new Point((int) alices.get(a+1).getY(), -1), new Comparator<Point>() {
					public int compare(Point o1, Point o2) {
						if(Double.compare(o1.getX(), o2.getX()) == 0){
							return -1 * Double.compare(o1.getY(), o2.getY());
						}
						return Double.compare(o1.getX(), o2.getX());}}
					);	
				//this binary search gets us the spot we need in the search. Essentially, it's in order by X's, but in backwards order by ties.
				//This is very nice because it means that 
				//Lets say 1:6 = 1:4 + 5:6
				//The order of our list would be (1:6 (1:4 (1:2 2:2) (3:4 4:4)) (5:6 (5:5 6:6))) (parentesis do not exist just there for mental grouping.
				
				//If we look at 1:6, we need to sum 1:4 and 5:6 to make our solution fast and O(N). 
				//1:4 will always be the next one, but we have to binary search for where 5:6 is. because of the way we sorted, it'll always be the first one that starts with 5.
						
				try{			
					
				storageAlice.put(alices.get(a), storageAlice.get(alices.get(a+1)) + storageAlice.get(alices.get(-1 * b -1)));
				alicesEvals.put(storageAlice.get(alices.get(a+1)) + storageAlice.get(alices.get(-1 * b -1)), 0);
				}
				catch(Exception e){
				}
			}
		}
			
	}

	//The idea behind this is that hash(1) + (hash(2) + hash(3)) = hash(2) + (hash(3) + hash(1)).
	//Even if we have (1, (2, 3)) and (2, (3, 1)), the groups with all 3 are the same. Therefore, we need an associative hash.
	
	//the thing we don't want is for hash(x) + hash(y) = hash(z), or hash(x) + hash(y) + hash(z) = hash(a) + hash(b) etc.
	//those would cause us to get the wrong answer. Hopefully this spreads things out enough that it does the job.
	//Pretty much any hash algorithm could work for this, as long as it gives results that were not likely to have collisions.
	//Also, it has to be pretty fast; We have to finish in 4 seconds giving us around ~2 billion calculations.
	//For a large test case of 100,000, we have several O(NlogN) sorts, tons of O(N) copies, plenty of O(N) traversals through the hashmaps
	//So this must be quick enough, as it is O(N*M), where M is how long it takes to hash it and N is the number of elements.
	//It turns out that this is just barely fast enough.
	
	//If this was codeforces or topcoder, this may be hackable if you could find a case where hash(x) + hash(y) + hash(z) = hash(a) + hash(b)

	private static Long hashItIhopethisworks(Long temp) {
		long output = 0;
		for (Integer a : numbers) {
			output = output + (long) Math.pow(temp.longValue(), a);
		}
		// System.out.println(output);
		return output;
	}
	//I don't actually use this, but it illustrates the concept a bit.
	private static Long hashItIhopethisworks(Long temp, Long temp2) {
		return hashItIhopethisworks(temp) + hashItIhopethisworks(temp2);
	}

	//speedy scanner class, thanks objective-H (I borrowed it from your codeforces solution)
	public static class Scanner2 {
		BufferedReader br;
		StringTokenizer st;

		public Scanner2(Reader in) {
			br = new BufferedReader(in);
		}

		public Scanner2() {
			this(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String readNextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
