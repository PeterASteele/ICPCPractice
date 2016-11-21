import java.util.*;
import static java.lang.Math.*;
import java.math.*;
import java.io.*;
//https://open.kattis.com/problems/boxes
public class Boxes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        int n = sc.nextInt();
        int[] parents = new int[n];
        // DisjointSets uf = new DisjointSets(n);
        int numTrees = 0;
        Map<Integer, Integer> rootsToTreeIdx = new HashMap<>();
        childLinks = new List[n];
        for (int i = 0; i < n; i++) {
            childLinks[i] = new ArrayList<>();
        }
        Node[] nodes = new Node[n];
        for (int u = 0; u < n; u++) {
            int p = sc.nextInt() - 1;
            parents[u] = p;

            if (p >= 0) {
                // uf.union(p, u);
                childLinks[p].add(u);
            } else {
                Node node = new Node(u, numTrees, 0);
                rootsToTreeIdx.put(u, numTrees);
                numTrees++;
                nodes[u] = node;
            }
        }

        int[] treeSizes = new int[numTrees];
        Arrays.fill(treeSizes, 1);

        sizes = new int[n];
        roots = new int[n];
        for (int root : rootsToTreeIdx.keySet()) {
            dfs(root, root);
            roots[root] = -1;
        }

        /*
        for (int u = 0; u < n; u++) {
            System.err.printf("size(%d): %d\n", u + 1, sizes[u]);
        }*/

        for (int u = 0; u < n; u++) {
            if (parents[u] > -1) {
                int rootIdx = roots[u];
//                System.err.println("root(" + u + "): " + rootIdx);
                int treeIdx = rootsToTreeIdx.get(rootIdx);
                int idxInTree = treeSizes[treeIdx];
                treeSizes[treeIdx]++;
                Node node = new Node(u, treeIdx, idxInTree);
                nodes[u] = node;
            }
        }

        // Build LCA
        List<Integer>[][] trees = new List[numTrees][];
        for (int i = 0; i < trees.length; i++) {
            trees[i] = new List[treeSizes[i]];
            for (int j = 0; j < trees[i].length; j++) {
                trees[i][j] = new ArrayList<>();
            }
        }

        for (int u = 0; u < n; u++) {
            int p = parents[u];
            if (p >= 0) {
                int treeIdx = nodes[u].treeIdx;
                int uIdx = nodes[u].idxInTree;
                int pIdx = nodes[p].idxInTree;
                trees[treeIdx][uIdx].add(pIdx);
                trees[treeIdx][pIdx].add(uIdx);
            }
        }

        LCA[] lca = new LCA[numTrees];
        for (int i = 0; i < lca.length; i++) {
            lca[i] = new LCA(trees[i], 0);
        }
        
//        System.out.println(Arrays.toString(nodes));

        StringBuilder sb = new StringBuilder();
        int numQueries = sc.nextInt();
        for (int q = 0; q < numQueries; q++) {
            int count = 0;
            int num = sc.nextInt();
            int[] wat = new int[num];
            for (int i = 0; i < num; i++) {
                int node = sc.nextInt() - 1;
                wat[i] = node;
                for (int j = 0; j < i; j++) {
                    if (wat[j] != -1) {
                        Node iNode = nodes[wat[i]];
                        Node jNode = nodes[wat[j]];
                        if (iNode.treeIdx == jNode.treeIdx) {
                            int lowestCommonAncestor = lca[iNode.treeIdx].lca(iNode.idxInTree, jNode.idxInTree);
                            if (lowestCommonAncestor == iNode.idxInTree) {
                                wat[j] = -1;
//                                break;
                            } else if (lowestCommonAncestor == jNode.idxInTree) {
                                wat[i] = -1;
                                break;
                            }
                        }
                    }
                }
            }
//            System.out.println(Arrays.toString(wat));
            for (int node : wat) {
                if (node != -1) {
//                    if (sizes[node] > 0) {
//                        System.out.printf("Size of %d is %d\n", node + 1, sizes[node]);
//                    }
                    count += sizes[node];
                }
            }
//            System.out.println(count);
            sb.append(count).append('\n');
        }

        System.out.print(sb);
    }

    static List<Integer>[] childLinks;
    static int[] sizes;
    static int[] roots;

    static int dfs(int node, int root) {
        int size = 1;
        for (int child : childLinks[node]) {
            size += dfs(child, root);
        }
        sizes[node] = size;
        roots[node] = root;
        return size;
    }

    static class Node {
        final int originalIdx, treeIdx, idxInTree;

        Node(int a, int b, int c) {
            originalIdx = a;
            treeIdx = b;
            idxInTree = c;
        }
        
        @Override
        public String toString() {
            return String.format("(%d, %d, %d)", originalIdx, treeIdx, idxInTree); 
        }
    }

    public static class LCA {

        int[] depth; // L: [0..|V|-1] -> depth in tree; root at 0.
        int[] E; // E: [0..2*|V|-1] -> node visited on i-th leg of Euler tour
        int cnt; // Eulertour counter
        int[] R; // R: [0..|V|] -> leg. # of leg when node was first encountered
        int[] minPos; // heap-based segment tree to implement RMQ over depth[]
        int n; // 2 * |V| - 1

        /* Compute Euler Tour */
        void dfs(List<Integer>[] tree, int u, int d) {
            depth[u] = d;
            E[cnt++] = u; // leg from parent to us
            for (int v : tree[u])
                if (depth[v] == -1) { // exclude parent
                    dfs(tree, v, d + 1);
                    E[cnt++] = u; // leg from child to us
                }
        }

        /* Recursive segment tree for RMQ */
        void buildTree(int node, int left, int right) {
            if (left == right) {
                minPos[node] = E[left];
                return;
            }
            int mid = (left + right) >> 1;
            buildTree(2 * node + 1, left, mid);
            buildTree(2 * node + 2, mid + 1, right);
            minPos[node] = depth[minPos[2 * node + 1]] < depth[minPos[2 * node + 2]] ? minPos[2 * node + 1]
                    : minPos[2 * node + 2];
        }

        public LCA(List<Integer>[] tree, int root) {
            int nodes = tree.length;
            depth = new int[nodes];
            Arrays.fill(depth, -1);

            // perform Euler Tour to compute depth[], E, and R
            n = 2 * nodes - 1;
            E = new int[n];
            cnt = 0;
            dfs(tree, root, 0);

            // build segment tree for depth[]
            minPos = new int[4 * n];
            buildTree(0, 0, n - 1);

            // compute R[], point of first visit (could be done during dfs)
            R = new int[nodes];
            Arrays.fill(R, -1);
            for (int i = 0; i < E.length; i++)
                if (R[E[i]] == -1) R[E[i]] = i;
        }

        public int lca(int a, int b) {
            return minPos(Math.min(R[a], R[b]), Math.max(R[a], R[b]), 0, 0, n - 1);
        }

        /**
         * NB: the tree contains indices that can be used in depth, i.e. numbers
         * that occurred during the Euler Tour. The tree comparison operation,
         * however, is based on depth.
         */
        int minPos(int a, int b, int node, int left, int right) {
            if (a == left && right == b) return minPos[node];
            int mid = (left + right) >> 1;
            if (a <= mid && b > mid) {
                int p1 = minPos(a, Math.min(b, mid), 2 * node + 1, left, mid);
                int p2 = minPos(Math.max(a, mid + 1), b, 2 * node + 2, mid + 1, right);
                return depth[p1] < depth[p2] ? p1 : p2;
            } else if (a <= mid) {
                return minPos(a, Math.min(b, mid), 2 * node + 1, left, mid);
            } else if (b > mid) {
                return minPos(Math.max(a, mid + 1), b, 2 * node + 2, mid + 1, right);
            } else {
                throw new RuntimeException();
            }
        }
    }
}
