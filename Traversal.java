
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class Traversal {

    interface OrderListener {
        void onOrderUpdate(String orderText);
    }

    static void bfs(Graph graph, List<Node> nodes, GraphPanel panel, OrderListener listener) {
        new Thread(() -> {
            try {
                boolean[] visited = new boolean[nodes.size()];
                Queue<Integer> queue = new LinkedList<>();
                String order = "";

                queue.add(0);
                visited[0] = true;

                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    if (order.equals("")) {
                        order = "" + current;
                    } else {
                        order = order + " -> " + current;
                    }
                    listener.onOrderUpdate(order);

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
                String[] order = { "" };
                dfsHelper(0, visited, graph, nodes, panel, listener, order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    static void dfsHelper(int node, boolean[] visited, Graph graph,
            List<Node> nodes, GraphPanel panel, OrderListener listener, String[] order)
            throws InterruptedException {

        visited[node] = true;
        if (order[0].equals("")) {
            order[0] = "" + node;
        } else {
            order[0] = order[0] + " -> " + node;
        }
        listener.onOrderUpdate(order[0]);
        nodes.get(node).visited = true;

        panel.repaint();
        Thread.sleep(800);

        for (int neighbor : graph.adjList.get(node)) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited, graph, nodes, panel, listener, order);
            }
        }
    }

}
