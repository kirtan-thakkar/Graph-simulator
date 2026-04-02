import java.util.ArrayList;
import java.util.List;

public class Graph {

    List<List<Integer>> adjList = new ArrayList<>();
    int[][] edgeWeights = new int[0][0];
    boolean isDirected = false;

    void addNode(int id) {
        while (adjList.size() <= id) {
            adjList.add(new ArrayList<>());
        }
        ensureWeightMatrixSize(adjList.size());
    }

    void addEdge(int u, int v, int weight) {
        adjList.get(u).add(v);
        edgeWeights[u][v] = weight;
        if (!isDirected) {
            adjList.get(v).add(u);
            edgeWeights[v][u] = weight;
        }
    }

    Integer getWeight(int u, int v) {
        if (u < 0 || v < 0 || u >= edgeWeights.length || v >= edgeWeights.length) {
            return null;
        }
        return edgeWeights[u][v] == 0 ? null : edgeWeights[u][v];
    }

    void clear() {
        adjList.clear();
        edgeWeights = new int[0][0];
    }

    private void ensureWeightMatrixSize(int size) {
        if (edgeWeights.length >= size) {
            return;
        }

        int[][] newWeights = new int[size][size];
        for (int i = 0; i < edgeWeights.length; i++) {
            for (int j = 0; j < edgeWeights[i].length; j++) {
                newWeights[i][j] = edgeWeights[i][j];
            }
        }
        edgeWeights = newWeights;
    }

}
