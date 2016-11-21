import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/classrooms
public class Classrooms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int k = input.nextInt();
        ArrayList<Event> events = new ArrayList<Event>();
        for (int a = 0; a < n; a++) {
            events.add(new Event(input.nextInt(), input.nextInt()));
        }
        Collections.sort(events);
        TreeMap<Range, Integer> set = new TreeMap<Range, Integer>();
        int numThingsInTree = 0;
        int count = 0;
        for (Event e : events) {
            Range fakeRange = new Range(e.start, e.start);
            if (set.lowerKey(fakeRange) != null) {
                if (set.get(set.lowerKey(fakeRange)) == 1) {
                    set.remove(set.lowerKey(fakeRange));
                } else {
                    set.put(set.lowerKey(fakeRange), set.get(set.lowerKey(fakeRange)) - 1);
                }
                Range tmp = new Range(e.start, e.end);
                if (set.containsKey(tmp)) {
                    set.put(tmp, set.get(tmp) + 1);
                } else {
                    set.put(tmp, 1);
                }
                
                count++;
            } else {
                if (numThingsInTree < k) {
                    Range tmp = new Range(e.start, e.end);
                    if (set.containsKey(tmp)) {
                        set.put(tmp, set.get(tmp) + 1);
                    } else {
                        set.put(tmp, 1);
                    }
                    numThingsInTree++;
                    count++;
                }
            }
        }
        System.out.println(count);

    }

    public static class Range implements Comparable<Range> {
        long start;
        long end;

        public Range(long _start, long _end) {
            start = _start;
            end = _end;
        }

        @Override
        public int compareTo(Range o) {
            return Long.compare(end, o.end);
        }
    }

    public static class Event implements Comparable<Event> {
        long start;
        long end;

        public Event(long _start, long _end) {
            start = _start;
            end = _end;
        }

        @Override
        public int compareTo(Event o) {
            if (end == o.end) {
                return Long.compare(start, o.start);
            }
            return Long.compare(end, o.end);
        }
    }
}
