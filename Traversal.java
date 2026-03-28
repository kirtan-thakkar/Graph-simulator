import java.util.*;
public class Traversal {
    static void bfs(Graph graph, java.util.List<Node> nodes, GraphPanel panel) {
        new Thread(() -> {
            try {
                Set<Integer> visited = new HashSet<>();
                Queue<Integer> queue = new LinkedList<>();

                queue.add(0);
                visited.add(0);

                while (!queue.isEmpty()) {
                    int node = queue.poll();

                    nodes.get(node).visited = true;
                    panel.repaint();
                    Thread.sleep(800);

                    for (int neighbor : graph.adjList.get(node)) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }
            } catch (Exception ignored) {}
        }).start();
    }
}
