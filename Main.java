
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    private static final Color APP_BG = new Color(10, 18, 25);
    private static final Color CARD_BG = new Color(21, 31, 42);
    private static final Color BTN_BG = new Color(44, 58, 73);
    private static final Color BTN_TEXT = new Color(232, 239, 247);
    private static final Font UI_FONT = new Font("Inter", Font.PLAIN, 14);

    Graph graph = new Graph();
    java.util.List<Node> nodes = new ArrayList<>();
    GraphPanel panel;

    public Main() {
        setTitle("Graph Traversal Simulator - Classic Dijkstra Graph");
        setSize(980, 700);
        getContentPane().setBackground(APP_BG);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 12));

        createGraph();

        panel = new GraphPanel(nodes, graph);
        panel.setBorder(new EmptyBorder(20, 20, 8, 20));
        add(panel, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        controls.setBackground(CARD_BG);
        controls.setBorder(new EmptyBorder(0, 0, 12, 0));

        JButton bfsBtn = new JButton("BFS");
        JButton dfsBtn = new JButton("DFS");
        JButton resetBtn = new JButton("Reset");
        JToggleButton toggleBtn = new JToggleButton("Undirected Graph", false);

        java.util.List<AbstractButton> buttons = Arrays.asList(bfsBtn, dfsBtn, resetBtn, toggleBtn);
        for (AbstractButton btn : buttons) {
            btn.setFont(UI_FONT.deriveFont(Font.BOLD, 14f));
            btn.setBackground(BTN_BG);
            btn.setForeground(BTN_TEXT);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        controls.add(bfsBtn);
        controls.add(dfsBtn);
        controls.add(resetBtn);
        controls.add(toggleBtn);

        add(controls, BorderLayout.SOUTH);

        bfsBtn.addActionListener(e -> Traversal.bfs(graph, nodes, panel));
        dfsBtn.addActionListener(e -> Traversal.dfs(graph, nodes, panel));

        resetBtn.addActionListener(e -> {
            for (Node n : nodes) {
                n.visited = false;
            }
            panel.repaint();
        });

        toggleBtn.addActionListener(e -> {
            graph.isDirected = toggleBtn.isSelected();
            toggleBtn.setText(graph.isDirected ? "Directed Graph" : "Undirected Graph");
            createGraph();
            panel.repaint();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createGraph() {
        graph.clear();
        nodes.clear();

        // Classic Dijkstra teaching graph (same topology and weights as shared image).
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
