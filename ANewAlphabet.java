import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class ANewAlphabet {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("a", "@");
        map.put("A", "@");
        map.put("b", "8");
        map.put("B", "8");
        map.put("c", "(");
        map.put("C", "(");
        map.put("d", "|)");
        map.put("D", "|)");
        map.put("e", "3");
        map.put("E", "3");
        map.put("f", "#");
        map.put("F", "#");
        map.put("g", "6");
        map.put("G", "6");
        map.put("h", "[-]");
        map.put("H", "[-]");
        map.put("i", "|");
        map.put("I", "|");
        map.put("j", "_|");
        map.put("J", "_|");
        map.put("k", "|<");
        map.put("K", "|<");
        map.put("l", "1");
        map.put("L", "1");
        map.put("m", "[]\\/[]");
        map.put("M", "[]\\/[]");
        map.put("n", "[]\\[]");
        map.put("N", "[]\\[]");
        map.put("o", "0");
        map.put("O", "0");
        map.put("p", "|D");
        map.put("P", "|D");
        map.put("q", "(,)");
        map.put("Q", "(,)");
        map.put("r", "|Z");
        map.put("R", "|Z");
        map.put("s", "$");
        map.put("S", "$");
        map.put("t", "']['");
        map.put("T", "']['");
        map.put("u", "|_|");
        map.put("U", "|_|");
        map.put("v", "\\/");
        map.put("V", "\\/");
        map.put("w", "\\/\\/");
        map.put("W", "\\/\\/");
        map.put("x", "}{");
        map.put("X", "}{");
        map.put("y", "`/");
        map.put("Y", "`/");
        map.put("z", "2");
        map.put("Z", "2");
        
        String in = input.nextLine();
        StringBuilder output =new StringBuilder();
        for(int a = 0; a < in.length(); a++){
            if(map.containsKey(in.charAt(a)+"")){
                output.append(map.get(in.charAt(a)+""));
            }
            else{
                output.append(in.charAt(a));
            }
        }
        System.out.println(output);
        
        
//      while (true) {
//          String tmp = input.nextLine();
//          if (tmp.equals("~")) {
//              break;
//          }
//          String tmp2 = input.nextLine();
//          String tmp3 = input.nextLine();
//          map.put(tmp, tmp2);
//      }
//      for(Entry<String, String> i: map.entrySet()){
//          System.out.println("map.put(\"" + i.getKey() + "\", \"" + i.getValue() + "\");");
//          System.out.println("map.put(\"" + i.getKey().toUpperCase() + "\", \"" + i.getValue() + "\");");
//      }
    }

}