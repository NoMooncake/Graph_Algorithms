/**
 * -----------------------------------------------------------------------------
 * File Name: Node.java
 * Project: Graph Algorithms
 * Description:
 * Minimal vertex type for a directed graph,
 * holding a unique name and an adjacency list of outgoing neighbors.
 * <p>
 * Author: Yue Wu
 * Date: 2025/11/4
 * Version: 1.0
 * -----------------------------------------------------------------------------
 */


/**
 * Represents a directed graph vertex with a name and an adjacency list of outgoing neighbors.
 *
 * Minimal fields and methods are provided to satisfy the rubric's Node class requirement.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private final String name;
    private final List<Node> neighbors = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    /** Adds a directed edge from this node to the given neighbor. */
    public void addNeighbor(Node v) {
        neighbors.add(v);
    }

    /** @return node's unique name */
    public String getName() {
        return name;
    }

    /** @return list of outgoing neighbors */
    public List<Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        return name;
    }

    /** Equality and hashCode based on name to allow map/set usage. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
