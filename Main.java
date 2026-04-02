import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

    Graph graph = new Graph();
    List<Node> nodes = new ArrayList<>();
    GraphPanel panel;
    JTextArea orderArea;

    String undirectedBfsOrder = "-";
    String undirectedDfsOrder = "-";
    String directedBfsOrder = "-";
    String directedDfsOrder = "-";

    public Main() {
        setTitle("Graph Traversal Visualizer");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createGraph();

        panel = new GraphPanel(nodes, graph);
        add(panel, BorderLayout.CENTER);

        orderArea = new JTextArea(5, 40);
        orderArea.setEditable(false);
        orderArea.setFocusable(false);
        updateOrderText();
        add(orderArea, BorderLayout.NORTH);

        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());

        JButton bfsBtn = new JButton("BFS");
        JButton dfsBtn = new JButton("DFS");
        JButton resetBtn = new JButton("Reset");
        JToggleButton toggleBtn = new JToggleButton("Directed: OFF", false);

        controls.add(bfsBtn);
        controls.add(dfsBtn);
        controls.add(resetBtn);
        controls.add(toggleBtn);

        add(controls, BorderLayout.SOUTH);

        bfsBtn.addActionListener(e -> {
            clearVisitedNodes();
            Traversal.bfs(graph, nodes, panel, order -> {
                if (graph.isDirected) {
                    directedBfsOrder = order;
                } else {
                    undirectedBfsOrder = order;
                }
                SwingUtilities.invokeLater(this::updateOrderText);
            });
        });

        dfsBtn.addActionListener(e -> {
            clearVisitedNodes();
            Traversal.dfs(graph, nodes, panel, order -> {
                if (graph.isDirected) {
                    directedDfsOrder = order;
                } else {
                    undirectedDfsOrder = order;
                }
                SwingUtilities.invokeLater(this::updateOrderText);
            });
        });

        resetBtn.addActionListener(e -> {
            clearVisitedNodes();
            panel.repaint();
        });

        toggleBtn.addActionListener(e -> {
            graph.isDirected = toggleBtn.isSelected();
            toggleBtn.setText(graph.isDirected ? "Directed: ON" : "Directed: OFF");
            createGraph();
            updateOrderText();
            panel.repaint();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createGraph() {
        graph.clear();
        nodes.clear();

        // Fixed graph with 5 nodes.
        nodes.add(new Node(0, 120, 250));
        nodes.add(new Node(1, 300, 120));
        nodes.add(new Node(2, 300, 380));
        nodes.add(new Node(3, 560, 380));
        nodes.add(new Node(4, 560, 120));

        for (int i = 0; i < nodes.size(); i++) {
            graph.addNode(i);
        }

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 8);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 4, 6);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 4, 10);

        for (Node n : nodes) {
            n.visited = false;
        }
    }

    private void clearVisitedNodes() {
        for (Node n : nodes) {
            n.visited = false;
        }
    }

    private void updateOrderText() {
        String currentMode = graph.isDirected ? "Directed" : "Undirected";
        orderArea.setText(
                "Current mode: " + currentMode + "\n" +
                        "Undirected BFS order: " + undirectedBfsOrder + "\n" +
                        "Undirected DFS order: " + undirectedDfsOrder + "\n" +
                        "Directed BFS order: " + directedBfsOrder + "\n" +
                        "Directed DFS order: " + directedDfsOrder);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
