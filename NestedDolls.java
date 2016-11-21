import java.util.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/nesteddolls
public class NestedDolls {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int numCases = sc.nextInt();
        for (int q = 0; q < numCases; q++) {
            int n = sc.nextInt();
            List<Double> relevantX = new ArrayList<>();
            List<Double> relevantY = new ArrayList<>();
            HashMap<Point, Integer> freqDist = new HashMap<Point, Integer>(n * 2);
            MinSegmentTree tree = new MinSegmentTree(0, n);
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                double x2 = x - 1E-10 * y;
                double y2 = y - 1E-10 * x;
                Point p = new Point(x2, y2);
                if (!freqDist.containsKey(p)) {
                    freqDist.put(p, 0);
                }
                freqDist.put(p, freqDist.get(p) + 1);
                relevantX.add(x2);
                relevantY.add(y2);
            }
            // System.out.println(relevantX);
            // System.out.println(relevantY);
            Compressor<Double> xCompressor = new Compressor(relevantX);
            Compressor<Double> yCompressor = new Compressor(relevantY);
            for (Point p : freqDist.keySet()) {
                tree.insert(xCompressor.compress(p.x), yCompressor.compress(p.y));
            }
//            System.out.println(tree);
            int count = 0;
            while (freqDist.size() != 0) {
                count++;
                if(count >= n){
                    break;
                }
                int prev = 0;
                while (true) {
                    int[] query = tree.query(prev, n);
                    if(query[0] == Integer.MAX_VALUE){
                        break;
                    }
                    Point temp = new Point(xCompressor.uncompress(query[1]), yCompressor.uncompress(query[0]));
                    freqDist.put(temp, freqDist.get(temp) - 1);
                    if (freqDist.get(temp) == 0) {
                        freqDist.remove(temp);
                        tree.remove(query[1], query[0]);
                    }
                    prev = query[1]+1;
                }
            }
            System.out.println(count);
        }
    }

    static class MinSegmentTree {
        int left;
        int right;
        MinSegmentTree leftTree;
        MinSegmentTree rightTree;
        int minVal;
        int minIdx;

        public MinSegmentTree(int _left, int _right) {
            left = _left;
            right = _right;
            if (left != right) {
                leftTree = new MinSegmentTree(left, (left + right) / 2);
                rightTree = new MinSegmentTree((left + right) / 2 + 1, right);
            }
            minVal = Integer.MAX_VALUE;
            minIdx = -1;
        }

        public void insert(int xCor, int yCor) {
            if (xCor >= left && xCor <= right) {
                if (left != right) {
                    leftTree.insert(xCor, yCor);
                    rightTree.insert(xCor, yCor);
                    if (leftTree.minVal < rightTree.minVal) {
                        minVal = leftTree.minVal;
                        minIdx = leftTree.minIdx;
                    } else {
                        minVal = rightTree.minVal;
                        minIdx = rightTree.minIdx;
                    }
                } else {
                    minVal = yCor;
                    minIdx = xCor;
                }
            }
        }

        public void remove(int xCor, int yCor) {
            if (xCor >= left && xCor <= right) {
                if (left != right) {
                    leftTree.remove(xCor, yCor);
                    rightTree.remove(xCor, yCor);
                    if (leftTree.minVal < rightTree.minVal) {
                        minVal = leftTree.minVal;
                        minIdx = leftTree.minIdx;
                    } else {
                        minVal = rightTree.minVal;
                        minIdx = rightTree.minIdx;
                    }
                } else {
                    minVal = Integer.MAX_VALUE;
                    minIdx = -1;
                }
            }
        }

        public int[] query(int _left, int _right) {
            if (_left <= right && _right >= left) {
                if (_left <= left && right <= _right) {
                    int[] result = new int[2];
                    result[0] = minVal;
                    result[1] = minIdx;
                    return result;
                } else {
                    int[] arr1 = leftTree.query(_left, _right);
                    int[] arr2 = rightTree.query(_left, _right);
                    if (arr1[0] < arr2[0]) {
                        return arr1;
                    } else {
                        return arr2;
                    }
                }
            }
            int[] result = new int[2];
            result[0] = Integer.MAX_VALUE;
            result[1] = -1;
            return result;
        }
        public String toString(){
            if(left == right){
                return left + " " + right + ": " + minIdx + " " + minVal;
            }
            else{
                return left + " " + right + ": " + minIdx + " " + minVal + "\n" + leftTree + "\n" + rightTree;
            }
        }
    }

    static class Point {
        final double x, y;

        Point(double xx, double yy) {
            x = xx;
            y = yy;
        }

        @Override
        public boolean equals(Object o) {
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(x * 1e9 + y);
        }
    }

    static class Compressor<T extends Comparable<T>> {
        Map<T, Integer> real2Compressed = new HashMap<>();
        List<T> compressed2Real = new ArrayList<>();

        Compressor(List<T> realC) {
            Collections.sort(realC);
            int cx = 0;
            T lastreal = null;
            for (T realx : realC) {
                if (!realx.equals(lastreal)) {
                    real2Compressed.put(realx, cx++);
                    compressed2Real.add(realx);
                }
                lastreal = realx;
            }
        }

        int compress(T real) {
            return real2Compressed.get(real);
        }

        T uncompress(int comp) {
            return compressed2Real.get(comp);
        }

        int size() {
            return real2Compressed.size();
        }
    }
}