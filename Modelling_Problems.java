import java.util.*;
import java.util.Map.Entry;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/modelling
public class Modelling_Problems {
    static int end_line_idx = -1;
    static List<Character> qList;
    static HashMap<Character, Integer> map;
    static boolean fail = false;
    static List<Instruction> il;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int T = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < T; i++) {
            qList = new ArrayList<>();
            map = new HashMap<>();
            il = new ArrayList<>();
            end_line_idx = -1;
            fail = false;
            String s = sc.nextLine().trim();
            while (end_line_idx < s.length() - 1) {
                il.add(parseInst(s, end_line_idx + 1));
            }
            // set map state
            // for (int j = 0; j < il.size(); j++){
            Process(il, 0);
            // }
            if (fail) {
                System.out.println("ASSERTIONS INVALID");
            } else {
                System.out.println("ASSERTIONS ALWAYS HOLD");
            }
        }
    }

    public static Instruction parseInst(String str, int idx) {
        Instruction inst = new Instruction();
        switch (str.charAt(idx)) {
            case '[':
                inst.Type = Type.ASSIGN;
                inst.lvar = str.charAt(idx + 1);
                if (str.charAt(idx + 3) == '?') {
                    inst.sType = SType.Q;
                    qList.add(inst.lvar);
                    end_line_idx = idx + 4;
                    break;
                } else if (str.charAt(idx + 4) == '+') {
                    inst.sType = SType.ADD;
                    inst.rvar = str.charAt(idx + 3);
                    inst.rrvar = str.charAt(idx + 5);
                    end_line_idx = idx + 6;
                    break;
                } else {
                    inst.sType = SType.INT;
                    String s = str.substring(idx + 3);
                    Scanner sc1 = new Scanner(s).useDelimiter("]");
                    inst.imm = sc1.nextInt();
                    sc1.close();
                    int intlen = Integer.toString(inst.imm).length();
                    end_line_idx = idx + 3 + intlen;
                }

                break;
            case '(':
                inst.Type = Type.COND;
                inst.lvar = str.charAt(idx + 1);
                inst.rvar = str.charAt(idx + 3);
                idx = idx + 5;
                List<Instruction> insts = new ArrayList<>();
                while (true) {
                    insts.add(parseInst(str, idx));
                    if (str.charAt(end_line_idx + 1) == '}') {
                        break;
                    }
                    idx = end_line_idx + 1;
                }
                inst.insts = insts;
                end_line_idx = end_line_idx + 2;
                break;
            case '<':
                inst.Type = Type.ASSERT;
                inst.lvar = str.charAt(idx + 1);
                inst.rvar = str.charAt(idx + 3);
                end_line_idx = idx + 4;
                break;
            default:
                throw new NullPointerException("This should never happen");
        }
        return inst;
    }

    public static void Process(List<Instruction> s, int idx) {
        if (idx >= s.size()) {
            return;
        }
        Instruction thisOne = s.get(idx);
        switch (thisOne.Type) {
            case ASSERT:
                if (!map.getOrDefault(thisOne.lvar, 0).equals(map.getOrDefault(thisOne.rvar, 0))) {

                    fail = true;
                }
                break;
            case ASSIGN:
                switch (thisOne.sType) {
                    case ADD:
                        map.put(thisOne.lvar, map.getOrDefault(thisOne.rvar, 0) + map.getOrDefault(thisOne.rrvar, 0));
                        break;
                    case INT:
                        map.put(thisOne.lvar, thisOne.imm);
                        break;
                    case Q:
                        HashMap<Character, Integer> mc = new HashMap<>();
                        for (Entry<Character, Integer> c : map.entrySet()) {
                            char key = c.getKey();
                            int val = c.getValue();
                            mc.put(key, val);
                        }
                        map.put(thisOne.lvar, 1);
                        Process(s, idx + 1);
                        map = mc;
                        mc.put(thisOne.lvar, 0);
                        Process(s, idx + 1);
                        return;
                    default:
                        throw new NullPointerException("This should never happen");

                }
                break;
            case COND:
                if (map.getOrDefault(thisOne.lvar, 0).compareTo(map.getOrDefault(thisOne.rvar, 0)) < 0) {
                    int nidx = 0;
                    // for (nidx = 0; nidx < thisOne.insts.size(); nidx++){
                    Process(thisOne.insts, nidx);
                    // }
                }
                break;
            default:
                throw new NullPointerException("This should never happen");

        }
        Process(s, idx + 1);
    }

}

enum Type {
    ASSIGN, COND, ASSERT
}

enum SType {
    Q, INT, ADD
}

enum AssType {
    equal, lt
}

class Instruction {

    public int imm;
    public char rrvar;
    public SType sType;
    public char rvar;
    public char lvar;
    public Type Type;
    public List<Instruction> insts;

}
