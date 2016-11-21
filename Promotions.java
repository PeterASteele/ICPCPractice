import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/promotions
public class Promotions {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        int left = sc.nextInt();
        int right = sc.nextInt();
        int numNodes = sc.nextInt();
        int numEdges = sc.nextInt();
        List<Integer>[] adj = new List[numNodes];
        List<Integer>[] adj2 = new List[numNodes];
        for(int i = 0; i < numNodes; i++){
            adj[i] = new ArrayList<Integer>();
            adj2[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < numEdges; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            adj[x].add(y);
            adj2[y].add(x);
        }
        int[] numGuarenteedPromo = new int[numNodes];
        int[] numNotPromo = new int[numNodes];
        for(int i = 0; i < numNodes; i++){
            numGuarenteedPromo[i] = numNodes - bfs(adj, i) + 1; 
            numNotPromo[i] = bfs(adj2, i)-1;
        }
        int sumCertainA = 0;
        int sumCertainB = 0;
        int notB = 0;
        for(int i = 0; i < numNodes; i++){
            if(numGuarenteedPromo[i] <= left){
                sumCertainA++;
            }
            if(numGuarenteedPromo[i] <= right){
                sumCertainB++;
            }
            if(numNotPromo[i] >= right){
                notB++;
            }
        }
        System.out.println(sumCertainA);
        System.out.println(sumCertainB);
        System.out.println(notB);
    }
    static int bfs(List<Integer>[] adj, int i){
        boolean[] visited = new boolean[adj.length];
        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(i);
        visited[i] = true;
        while(queue.isEmpty() == false){
            int tmp = queue.poll();
            for(Integer j:adj[tmp]){
                if(visited[j] == false){
                    queue.add(j);
                    visited[j] = true;
                }
            }
        }
        int popOff = 0;
        for(int a = 0; a < adj.length; a++){
            popOff += visited[a]?1:0;
        }
        return popOff;
    }
}
