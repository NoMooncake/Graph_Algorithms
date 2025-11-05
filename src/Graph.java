/**
 * -----------------------------------------------------------------------------
 * File Name: Graph.java
 * Project: Graph Algorithms
 * Description:
 * Stores a directed graph of named nodes. Supports creating nodes/edges,
 * printing adjacency, reversing edges (G^R),
 * and running DFS to produce a decreasing finish-time order used by SCC.
 * <p>
 * Author: Yue Wu
 * Date: 2025/11/4
 * Version: 1.0
 * -----------------------------------------------------------------------------
 */


/**
 * Stores nodes and directed edges for a graph. Provides:
 * - addNode / addEdge to create and store vertices and edges
 * - toString to print the adjacency list
 * - dfsFinishOrder: runs DFS on this graph and returns vertices in decreasing finish time
 * - getReverse: builds and returns the edge-reversed graph
 *
 */
import java.util.*;

public class Graph {
    private final Map<String, Node> nodes = new LinkedHashMap<>();

    /** Ensures a node exists and returns it. */
    public Node addNode(String name) {
        return nodes.computeIfAbsent(name, Node::new);
    }

    /** Adds a directed edge u -> v, creating nodes if needed. */
    public void addEdge(String from, String to) {
        Node u = addNode(from);
        Node v = addNode(to);
        u.addNeighbor(v);
    }

    /** @return all nodes */
    public Collection<Node> getNodes() {
        return nodes.values();
    }

    /**
     * Builds the reverse graph G^R by reversing every edge direction.
     */
    public Graph getReverse() {
        Graph r = new Graph();
        // create all vertices first
        for (Node n : nodes.values()) {
            r.addNode(n.getName());
        }
        // reverse each edge
        for (Node u : nodes.values()) {
            for (Node v : u.getNeighbors()) {
                r.addEdge(v.getName(), u.getName());
            }
        }
        return r;
    }

    /**
     * Runs DFS on this graph and returns a list of vertex names in decreasing finish time.
     * Append a vertex to 'post' when its DFS finishes, then reverse the list.
     */
    public List<String> dfsFinishOrder() {
        Set<String> visited = new HashSet<>();
        List<String> post = new ArrayList<>();
        for (Node n : getNodes()) {
            if (!visited.contains(n.getName())) {
                dfsPost(n, visited, post);
            }
        }
        Collections.reverse(post); // now in decreasing finish time
        return post;
    }

    /** Helper DFS that records postorder. */
    private void dfsPost(Node u, Set<String> visited, List<String> post) {
        visited.add(u.getName());
        for (Node v : u.getNeighbors()) {
            if (!visited.contains(v.getName())) {
                dfsPost(v, visited, post);
            }
        }
        post.add(u.getName()); // record on exit
    }

    /** Prints the adjacency list as: A -> [B, C] */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node u : getNodes()) {
            sb.append(u.getName()).append(" -> [");
            for (int i = 0; i < u.getNeighbors().size(); i++) {
                sb.append(u.getNeighbors().get(i).getName());
                if (i + 1 < u.getNeighbors().size()) sb.append(", ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}
