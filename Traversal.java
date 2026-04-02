
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class Traversal {

    interface OrderListener {
        void onOrderUpdate(String orderText);
    }

    static void bfs(Graph graph, List<Node> nodes, GraphPanel panel, OrderListener listener) {
        new Thread(() -> {
            try {
                boolean[] visited = new boolean[nodes.size()];
                Queue<Integer> queue = new LinkedList<>();
                List<Integer> order = new ArrayList<>();

                queue.add(0);
                visited[0] = true;

                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    order.add(current);
                    listener.onOrderUpdate(toOrderText(order));

                    nodes.get(current).visited = true;
                    panel.repaint();
                    Thread.sleep(800);

                    for (int neighbor : graph.adjList.get(current)) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            queue.add(neighbor);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    static void dfs(Graph graph, List<Node> nodes, GraphPanel panel, OrderListener listener) {
        new Thread(() -> {
            try {
                boolean[] visited = new boolean[nodes.size()];
                List<Integer> order = new ArrayList<>();
                dfsHelper(0, visited, graph, nodes, panel, listener, order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    static void dfsHelper(int node, boolean[] visited, Graph graph,
            List<Node> nodes, GraphPanel panel, OrderListener listener, List<Integer> order)
            throws InterruptedException {

        visited[node] = true;
        order.add(node);
        listener.onOrderUpdate(toOrderText(order));
        nodes.get(node).visited = true;

        panel.repaint();
        Thread.sleep(800);

        for (int neighbor : graph.adjList.get(node)) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited, graph, nodes, panel, listener, order);
            }
        }
    }

    private static String toOrderText(List<Integer> order) {
        if (order.isEmpty()) {
            return "-";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < order.size(); i++) {
            sb.append(order.get(i));
            if (i < order.size() - 1) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }

}
