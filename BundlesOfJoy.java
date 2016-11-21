import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/bundles
public class BundlesOfJoy {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int numCases = sc.nextInt();
        for (int q = 0; q < numCases; q++) {
            int n = sc.nextInt();
            int numBundles = sc.nextInt();
            Bundle[] bundles = new Bundle[numBundles];
            for (int i = 0; i < numBundles; i++) {
                int cost = sc.nextInt();
                int numNums = sc.nextInt();
                Bundle bundle = new Bundle(cost);
                for (int j = 0; j < numNums; j++) {
                    int num = sc.nextInt();
                    bundle.add(num);
                }
                bundles[i] = bundle;
            }

            // Kill duplicates
            // outer:
            // for (int i = 0; i < numBundles; i++) {
            // if (bundles[i] != null) {
            // for (int j = i + 1; j < numBundles; j++) {
            // if (bundles[j] != null) {
            // if (bundles[i].bundle.equals(bundles[j].bundle)) {
            // if (bundles[i].cost < bundles[j].cost) {
            // bundles[j] = null;
            // } else {
            // bundles[i] = null;
            // continue outer;
            // }
            // }
            // }
            // }
            // }
            // }

            List<Bundle> bundleList = new ArrayList<>();
            for (int i = 0; i < numBundles; i++) {
                if (bundles[i] != null) {
                    bundleList.add(bundles[i]);
                }
            }

            Bundle fakeRoot = new Bundle(INF);
            for (int i = 1; i <= n; i++) {
                fakeRoot.add(i);
            }

            Collections.sort(bundleList);
            for (int i = bundleList.size() - 1; i >= 0; i--) {
                fakeRoot.insert(bundleList.get(i));
            }
            fakeRoot.complete();
            System.out.println(fakeRoot.getCost());

            // for (Bundle bundle : bundleList) {
            // System.out.println(bundle);
            // }
        }
    }

    static long INF = Long.MAX_VALUE / 4;

    static class Bundle implements Comparable<Bundle> {
        Set<Integer> bundle;
        boolean complete;
        List<Bundle> children;
        final long cost;

        Bundle(long c) {
            this.cost = c;
            bundle = new HashSet<>();
            children = new ArrayList<>();
        }

        public long getCost() {
            long possibility = cost;
            long childrenCost = 0;
            if (complete) {
                for (Bundle i : children) {
                    childrenCost += i.getCost();
                }
            }
            if (complete) {
                possibility = Math.min(possibility, childrenCost);
            }
            return possibility;
        }

        void add(int num) {
            bundle.add(num);
        }

        void complete() {
            HashSet<Integer> tmp = new HashSet<Integer>();
            for (Bundle i : children) {
                i.complete();
                for (Integer j : i.bundle) {
                    tmp.add(j);
                }
            }
            if (tmp.size() == bundle.size()) {
                complete = true;
            }
        }

        void insert(Bundle child) {
            boolean OK = true;
            for (Bundle i : children) {
                if (i.contains(child)) {
                    OK = false;
                    i.insert(child);
                    break;
                }
            }
            if (OK) {
                children.add(child);
            }
        }

        boolean contains(Bundle child) {
            if (child.bundle.size() > bundle.size()) {
                return false;
            }

            for (int item : child.bundle) {
                if (!bundle.contains(item)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public int compareTo(Bundle o) {
            return Integer.compare(bundle.size(), o.bundle.size());
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Bundle = " + bundle + " (" + cost + "), children = ");
            for (Bundle child : children) {
                sb.append(child.bundle).append(',');
            }
            return sb.toString();
        }
    }
}
