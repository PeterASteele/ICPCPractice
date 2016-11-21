import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/flippingcards
public class FlippingCards {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        for (int a2 = 0; a2 < t; a2++) {
            int n = input.nextInt();
            int[][] arr = new int[2][n];
            TreeSet<Node> nodes = new TreeSet<Node>();
            Node[] rightNodes = new Node[2 * n];
            for (int a = 0; a < 2 * n; a++) {
                rightNodes[a] = new Node(a, 0);
            }
            int matchedCards = 0;
            HashSet<Integer> blackList = new HashSet<Integer>();
            for (int a = 0; a < n; a++) {
                int c1 = input.nextInt() - 1;
                int c2 = input.nextInt() - 1;
                if (c1 != c2) {
                    arr[0][a] = c1;
                    arr[1][a] = c2;
                    if (blackList.contains(c1) == false) {
                        rightNodes[c1].freq++;
                        rightNodes[c1].mapBack.add(a);
                    }
                    if (blackList.contains(c2) == false) {
                        rightNodes[c2].freq++;
                        rightNodes[c2].mapBack.add(a);
                    }
                } else {
                    if (!blackList.contains(c1)) {
                        matchedCards++;
                    }
                    blackList.add(c1);
                }
            }
            for (int a = 0; a < 2 * n; a++) {
                if (!blackList.contains(a) && rightNodes[a].freq != 0) {
                    nodes.add(rightNodes[a]);
                }
            }
            while (true) {
                if (nodes.isEmpty()) {
                    break;
                }
                matchedCards++;
                Node i = nodes.first();
//                System.out.println(nodes + ": " + matchedCards);
//                input.nextLine();
                nodes.remove(i);
                int idToRemove = i.mapBack.first();
                int otherId = getOther(idToRemove, i.id, arr);
                if (nodes.contains(rightNodes[otherId])) {
                    Node j2 = rightNodes[otherId];
                    nodes.remove(j2);
                    j2.mapBack.remove(idToRemove);
                    j2.freq--;
                    if (j2.freq != 0) {
                        nodes.add(j2);
                    }
                }
            }
            if (matchedCards == n) {
                System.out.println("possible");
            } else {
                System.out.println("impossible");
            }
        }

    }

    private static int getOther(int idToRemove, int id, int[][] arr) {
        if (arr[0][idToRemove] == id) {
            return arr[1][idToRemove];
        } else {
            return arr[0][idToRemove];
        }
    }

    public static class Node implements Comparable<Node> {
        int id;
        int freq;
        TreeSet<Integer> mapBack;

        public Node(int _id, int _freq) {
            id = _id;
            freq = _freq;
            mapBack = new TreeSet<Integer>();
        }

        public boolean equals(Object o) {
            Node i = (Node) o;
            return id == i.id;
        }

        public int compareTo(Node i) {
            if (freq == i.freq) {
                return Integer.compare(id, i.id);
            }
            return Integer.compare(freq, i.freq);
        }

        public String toString() {
            return (id + 1) + " " + freq + " " + mapBack;
        }
    }
}
