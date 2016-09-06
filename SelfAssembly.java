import java.util.*;
//ICPC World Finals 2013 Problem A (practice)
////ICPC World Finals 2013 Problem J (practice)
//Dustin's solution
public class SelfAssembly {

	static Map<String, Set<String>> adj = new HashMap<>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			String molecule = sc.next();
			String[] node = new String[] { 
					molecule.substring(0, 2), 
					molecule.substring(2, 4), 
					molecule.substring(4, 6),
					molecule.substring(6, 8)};
			
			for (int u = 0; u < 4; u++) {
				if (node[u].equals("00")) continue;
				for (int v = 0; v < 4; v++) {
					if (node[v].equals("00")) continue;
					if (u == v) continue;
					if (!adj.containsKey(node[u]))
						adj.put(node[u], new HashSet<>());
					adj.get(node[u]).add(opposite(node[v]));
				}
			}
		}

		boolean cycle = false;
		for (String u : adj.keySet())
			if (cycle = backtrackcycle(u))
				break;
		System.out.println(cycle ? "unbounded" : "bounded");
		
		sc.close();
	}

	static Set<String> visited = new HashSet<>();
	static boolean backtrackcycle(String u) {
		if (!adj.containsKey(u)) return false;
		if (visited.contains(u)) return true;
		visited.add(u);
		
		for (String v : adj.get(u))
			if (backtrackcycle(v)) 
				return true;
		
		visited.remove(u);
		return false;
	}
	
	static String opposite(String e) {
		return e.charAt(0) + (e.charAt(1) == '-' ? "+" : "-");
	}

}