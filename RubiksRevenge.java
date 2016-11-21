import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/rubiksrevenge
public class RubiksRevenge {
    static int[] rgby;
    static char[][] answer = {{'R', 'R', 'R', 'R'}, {'G', 'G', 'G', 'G'}, {'B', 'B', 'B', 'B'}, {'Y', 'Y', 'Y', 'Y'}};
    static char[] rgbyRev = {'R', 'G', 'B', 'Y'};
    static int ans;
    static HashMap<Integer, Integer> megaMap;
    static HashMap<Integer, Integer> megaMap2;
    public static void main(String[] args) {
        rgby = new int[256];
        rgby['R'] = 0;
        rgby['G'] = 1;
        rgby['B'] = 2;
        rgby['Y'] = 3;
        ans = parseIn(answer);
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        megaMap = new HashMap<Integer, Integer>();
        megaMap2 = new HashMap<Integer, Integer>();
        String[] in = new String[4];
        for(int a = 0; a < 4; a++){
            in[a] = input.next();
        }
        char[][] start = new char[4][4];
        for(int a = 0; a < 4; a++){
            start[a] = in[a].toCharArray();
        }
        bfs(answer);
        
        System.out.println(bfs2(start));
        
    }
    private static int bfs2(char[][] start) {
//        System.out.println("SecondBFS");
        // TODO Auto-generated method stub
        Queue<Integer> bfsQueue = new ArrayDeque<Integer>();
        bfsQueue.add(parseIn(start));
        megaMap2.put(parseIn(start), 0);
        while(bfsQueue.isEmpty() == false){
//                System.out.println(megaMap.size());
            int first = bfsQueue.poll();
//            print(parseOut(first));
            int dist = megaMap2.get(first);
//            System.out.println(dist);
            if(megaMap.containsKey(first)){
                return dist+megaMap.get(first);
            }
            char[][] tmp = parseOut(first);
            for(int a = 0; a < 4; a++){
                char[][] tmp1 = shiftRight(tmp, a, 1);
                char[][] tmp2 = shiftRight(tmp, a, -1);
                char[][] tmp3 = shiftUp(tmp, a, 1);
                char[][] tmp4 = shiftUp(tmp, a, -1);
                int tmp1Int = parseIn(tmp1);
                int tmp2Int = parseIn(tmp2);
                int tmp3Int = parseIn(tmp3);
                int tmp4Int = parseIn(tmp4);
                if(!megaMap2.containsKey(tmp1Int)){
                    megaMap2.put(tmp1Int, dist+1);
                    bfsQueue.add(tmp1Int);
                }
                if(!megaMap2.containsKey(tmp2Int)){
                    megaMap2.put(tmp2Int, dist+1);
                    bfsQueue.add(tmp2Int);
                }
                if(!megaMap2.containsKey(tmp3Int)){
                    megaMap2.put(tmp3Int, dist+1);
                    bfsQueue.add(tmp3Int);
                }
                if(!megaMap2.containsKey(tmp4Int)){
                    megaMap2.put(tmp4Int, dist+1);
                    bfsQueue.add(tmp4Int);
                }
            }
        }
        
        
        return -1;
//        return null;
    }
    private static int bfs(char[][] start) {
        // TODO Auto-generated method stub
        Queue<Integer> bfsQueue = new ArrayDeque<Integer>();
        bfsQueue.add(parseIn(start));
        megaMap.put(parseIn(start), 0);
        while(bfsQueue.isEmpty() == false){
//                System.out.println(megaMap.size());
            int first = bfsQueue.poll();
//            print(parseOut(first));
            int dist = megaMap.get(first);
            if(dist > 5){
                break;
            }
//            System.out.println(dist);
            char[][] tmp = parseOut(first);
            for(int a = 0; a < 4; a++){
                char[][] tmp1 = shiftRight(tmp, a, 1);
                char[][] tmp2 = shiftRight(tmp, a, -1);
                char[][] tmp3 = shiftUp(tmp, a, 1);
                char[][] tmp4 = shiftUp(tmp, a, -1);
                int tmp1Int = parseIn(tmp1);
                int tmp2Int = parseIn(tmp2);
                int tmp3Int = parseIn(tmp3);
                int tmp4Int = parseIn(tmp4);
                if(!megaMap.containsKey(tmp1Int)){
                    megaMap.put(tmp1Int, dist+1);
                    bfsQueue.add(tmp1Int);
                }
                if(!megaMap.containsKey(tmp2Int)){
                    megaMap.put(tmp2Int, dist+1);
                    bfsQueue.add(tmp2Int);
                }
                if(!megaMap.containsKey(tmp3Int)){
                    megaMap.put(tmp3Int, dist+1);
                    bfsQueue.add(tmp3Int);
                }
                if(!megaMap.containsKey(tmp4Int)){
                    megaMap.put(tmp4Int, dist+1);
                    bfsQueue.add(tmp4Int);
                }
            }
        }
        
        return -1;
//        return null;
    }
    private static void print(char[][] parseOut) {
        System.out.println("");
        for(int a = 0; a < 4; a++){
            System.out.println(Arrays.toString(parseOut[a]));
        }
        System.out.println("");
    }

    public static char[][] shiftRight(char[][] in, int idx, int direction){
        char[][] out = new char[4][4];
        for(int a = 0; a < 4; a++){
            for(int b = 0; b < 4; b++){
                int tmp = idx==a?direction+4:0;
                out[a][b] = in[a][(b+tmp)%4];
            }
        }
        return out;
    }
    public static char[][] shiftUp(char[][] in, int idx, int direction){
        char[][] out = new char[4][4];
        for(int a = 0; a < 4; a++){
            for(int b = 0; b < 4; b++){
                int tmp = idx==a?direction+4:0;
                out[b][a] = in[(b+tmp)%4][a];
            }
        }
        return out;
    }
    public static int parseIn(char[][] i){
        int out = 0;
        for(int a = 0; a < 4; a++){
            for(int b = 0; b < 4; b++){
                out = out << 2;
                out = out | rgby[i[a][b]];
            }
        }
        return out;
    }
    
    public static char[][] parseOut(int i){
        char[][] out = new char[4][4];
        for(int a = 3; a >= 0; a--){
            for(int b = 3; b >= 0; b--){
                int tmp = i&3;
                out[a][b] = rgbyRev[tmp];
                i = i >>> 2;
            }
        }
        return out;
    }
}
