
import java.util.*;

public class Graph {

    Map<Integer, List<Integer>> adjList = new HashMap<>();
    boolean isDirected = false;

    void addNode(int id) {
        adjList.putIfAbsent(id, new ArrayList<>());
    }

    void addEdge(int u, int v) {
        adjList.get(u).add(v);
        if (!isDirected) {
            adjList.get(v).add(u);
        }
    }

    void clear() {
        adjList.clear();
    }

}
