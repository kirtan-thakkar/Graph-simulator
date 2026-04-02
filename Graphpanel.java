import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.*;

class GraphPanel extends JPanel {

    private static final int NODE_SIZE = 40;
    private static final int R = NODE_SIZE / 2;

    List<Node> nodes;
    Graph graph;

    GraphPanel(List<Node> nodes, Graph graph) {
        this.nodes = nodes;
        this.graph = graph;
        setBackground(Color.WHITE);
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int u = 0; u < graph.adjList.size(); u++) {
            for (int v : graph.adjList.get(u)) {
                if (!graph.isDirected && u > v) {
                    continue;
                }

                Node n1 = nodes.get(u);
                Node n2 = nodes.get(v);

                int x1 = n1.x + R;
                int y1 = n1.y + R;
                int x2 = n2.x + R;
                int y2 = n2.y + R;

                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(x1, y1, x2, y2);

                if (graph.isDirected) {
                    drawArrow(g2, x1, y1, x2, y2);
                }

                Integer weight = graph.getWeight(u, v);
                if (weight != null) {
                    int wx = (x1 + x2) / 2;
                    int wy = (y1 + y2) / 2;
                    String text = String.valueOf(weight);
                    g2.setFont(new Font("Arial", Font.PLAIN, 13));
                    FontMetrics fm = g2.getFontMetrics();
                    int tw = fm.stringWidth(text);
                    int th = fm.getAscent();

                    g2.setColor(Color.WHITE);
                    g2.fillRect(wx - (tw / 2) - 4, wy - th + 4, tw + 8, th + 6);
                    g2.setColor(Color.BLACK);
                    g2.drawString(text, wx - (tw / 2), wy + 4);
                }
            }
        }

        for (Node node : nodes) {
            if (node.visited) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.LIGHT_GRAY);
            }

            g2.fillOval(node.x, node.y, NODE_SIZE, NODE_SIZE);
            g2.setColor(Color.BLACK);
            g2.drawOval(node.x, node.y, NODE_SIZE, NODE_SIZE);

            String label = String.valueOf(node.id);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            int tx = node.x + (NODE_SIZE - fm.stringWidth(label)) / 2;
            int ty = node.y + ((NODE_SIZE - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(label, tx, ty);
        }
    }

    private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowX = (int) (x2 - Math.cos(angle) * (R + 2));
        int arrowY = (int) (y2 - Math.sin(angle) * (R + 2));

        AffineTransform old = g2.getTransform();
        g2.translate(arrowX, arrowY);
        g2.rotate(angle);
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 0);
        arrowHead.addPoint(-8, -5);
        arrowHead.addPoint(-8, 5);
        g2.fillPolygon(arrowHead);
        g2.setTransform(old);
    }
}