import java.awt.*;
import java.util.List;
import javax.swing.*;

class GraphPanel extends JPanel {

    List<Node> nodes;
    Graph graph;

    GraphPanel(List<Node> nodes, Graph graph) {
        this.nodes = nodes;
        this.graph = graph;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //edge
        for (int u : graph.adjList.keySet()) {
            for (int v : graph.adjList.get(u)) {
                Node n1 = nodes.get(u);
                Node n2 = nodes.get(v);
                g.setColor(Color.BLACK);
                g.drawLine(n1.x, n1.y, n2.x, n2.y);
                if (graph.isDirected) {
                    g.fillOval(n2.x + 15, n2.y + 15, 6, 6); 
                }
            }
        }

        //nodes
        for (Node node : nodes) {
            if (node.visited)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.GRAY);
            g.fillOval(node.x, node.y, 40, 40);
            g.setColor(Color.BLACK);
            g.drawString("" + node.id, node.x + 15, node.y + 25);
        }
    }
}