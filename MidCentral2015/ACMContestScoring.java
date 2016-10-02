import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/contests/na16warmup1/problems/acm
public class ACMContestScoring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        Map<Character, Integer> attempts = new HashMap<>();
        int numProbs = 0;
        int timeScore = 0;
        Set<Character> solved = new HashSet<>();
        while (sc.hasNext()) {
            int n = sc.nextInt();
            if (n == -1) break;
            char letter = sc.next().charAt(0);
            String str = sc.next();
            if (!solved.contains(letter)) {
                if (str.equals("right")) {
                   Integer numAttempts = attempts.get(letter);
                   if (numAttempts == null) numAttempts = 0;
                   numProbs++;
                   timeScore += numAttempts * 20 + n;
                   solved.add(letter);
                } else {
                    Integer numAttempts = attempts.get(letter);
                    if (numAttempts == null) numAttempts = 0;
                    numAttempts++;
                    attempts.put(letter, numAttempts);
                }
            }
        }
        
        System.out.printf("%d %d\n", numProbs, timeScore);
    }
}
