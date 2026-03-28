
import java.util.*;

public class Graph {

    Map<Integer, List<Integer>> adjList = new HashMap<>();
    Map<String, Integer> edgeWeights = new HashMap<>();
    boolean isDirected = false;

    void addNode(int id) {
        adjList.putIfAbsent(id, new ArrayList<>());
    }

    void addEdge(int u, int v, int weight) {
        adjList.get(u).add(v);
        edgeWeights.put(edgeKey(u, v), weight);
        if (!isDirected) {
            adjList.get(v).add(u);
            edgeWeights.put(edgeKey(v, u), weight);
        }
    }

    Integer getWeight(int u, int v) {
        return edgeWeights.get(edgeKey(u, v));
    }

    private String edgeKey(int u, int v) {
        return u + ":" + v;
    }

    void clear() {
        adjList.clear();
        edgeWeights.clear();
    }

}
