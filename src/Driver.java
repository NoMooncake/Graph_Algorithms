/**
 * -----------------------------------------------------------------------------
 * File Name: Driver.java
 * Project: Graph Algorithms
 * Description:
 * [Add brief description here]
 * <p>
 * Author: Yue Wu
 * Date: 2025/11/1
 * Version: 1.0
 * -----------------------------------------------------------------------------
 */


/**
 * Driver creates a graph (>= 6 vertices), displays required information,
 * and runs the Strongly Connected Components algorithm (Kosaraju)
 * by calling Graph.getReverse() and Graph.dfsFinishOrder().
 */
import java.util.*;

public class Driver {

    public static void main(String[] args) {
        Graph g = buildSampleGraph(); // >= 6 vertices, multiple SCCs

        // Display adjacency lists for the original and reversed graph
        System.out.println("=== Original Graph (Adjacency) ===");
        System.out.println(g);

        Graph r = g.getReverse();
        System.out.println("=== Reversed Graph (Adjacency) ===");
        System.out.println(r);

        // First pass: DFS on reversed graph to get decreasing finish-time order
        List<String> orderOnReversed = r.dfsFinishOrder();
        System.out.println("=== Finish Order on Reversed Graph (desc) ===");
        System.out.println(orderOnReversed);

        // Second pass: run SCC using the order from the first pass
        List<List<String>> sccs = stronglyConnectedComponents(g, orderOnReversed);

        // Display SCCs to the user
        System.out.println("=== Strongly Connected Components (Kosaraju) ===");
        for (int i = 0; i < sccs.size(); i++) {
            System.out.println("SCC #" + (i + 1) + ": " + sccs.get(i));
        }
    }

    /**
     * Builds a simple directed graph with >= 6 vertices and multiple SCCs.
     * You can tweak edges but keep it ≥6 to satisfy the rubric.
     */
    private static Graph buildSampleGraph() {
        Graph g = new Graph();

        // SCC1: A -> B -> C -> A (3-cycle)
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "A");

        // SCC2: D <-> E (2-cycle)
        g.addEdge("D", "E");
        g.addEdge("E", "D");

        // Connections across SCCs (do not merge SCCs)
        g.addEdge("C", "D");
        g.addEdge("E", "F");
        g.addEdge("F", "G");
        g.addEdge("G", "F"); // SCC3: F <-> G
        g.addEdge("G", "H"); // H is a singleton SCC

        return g;
    }

    /**
     * Implements Kosaraju's SCC using:
     * Finish order from DFS on the reversed graph
     * DFS on the original graph in that order to collect components
     */
    private static List<List<String>> stronglyConnectedComponents(Graph g, List<String> orderFromReversed) {
        List<List<String>> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        // Build a quick name->node map for original graph
        Map<String, Node> name2node = new HashMap<>();
        for (Node n : g.getNodes()) name2node.put(n.getName(), n);

        // Process vertices in the finish-time order (from reversed graph)
        for (String name : orderFromReversed) {
            if (!visited.contains(name)) {
                List<String> component = new ArrayList<>();
                dfsCollect(name2node.get(name), visited, component);
                result.add(component);
            }
        }
        return result;
    }

    /** DFS that collects all reachable vertices from 'u' into 'component'. */
    private static void dfsCollect(Node u, Set<String> visited, List<String> component) {
        if (u == null) return;
        visited.add(u.getName());
        component.add(u.getName());
        for (Node v : u.getNeighbors()) {
            if (!visited.contains(v.getName())) {
                dfsCollect(v, visited, component);
            }
        }
    }
}
