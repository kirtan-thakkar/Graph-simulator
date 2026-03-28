import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.*;

class GraphPanel extends JPanel {

    private static final Color BG = new Color(16, 24, 32);
    private static final Color EDGE = new Color(142, 156, 173);
    private static final Color NODE = new Color(44, 58, 73);
    private static final Color VISITED = new Color(36, 151, 111);
    private static final Color NODE_BORDER = new Color(199, 214, 229);
    private static final Color TEXT = new Color(231, 238, 246);
    private static final Color WEIGHT_BG = new Color(27, 38, 50);
    private static final int NODE_SIZE = 46;
    private static final int NODE_RADIUS = NODE_SIZE / 2;

    List<Node> nodes;
    Graph graph;

    GraphPanel(List<Node> nodes, Graph graph) {
        this.nodes = nodes;
        this.graph = graph;
        setBackground(BG);
        setOpaque(true);
        setFont(new Font("Inter", Font.PLAIN, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw all edges with optional direction markers and weight labels.
        for (int u : graph.adjList.keySet()) {
            for (int v : graph.adjList.get(u)) {
                if (!graph.isDirected && u > v) {
                    continue;
                }

                Node n1 = nodes.get(u);
                Node n2 = nodes.get(v);

                int x1 = n1.x + NODE_RADIUS;
                int y1 = n1.y + NODE_RADIUS;
                int x2 = n2.x + NODE_RADIUS;
                int y2 = n2.y + NODE_RADIUS;

                g2.setColor(EDGE);
                g2.setStroke(new BasicStroke(2.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(x1, y1, x2, y2);

                if (graph.isDirected) {
                    drawArrow(g2, x1, y1, x2, y2);
                }

                Integer weight = graph.getWeight(u, v);
                if (weight != null) {
                    int wx = (x1 + x2) / 2;
                    int wy = (y1 + y2) / 2;
                    String text = String.valueOf(weight);
                    g2.setFont(getFont().deriveFont(Font.BOLD, 14f));
                    FontMetrics fm = g2.getFontMetrics();
                    int tw = fm.stringWidth(text);
                    int th = fm.getAscent();

                    g2.setColor(WEIGHT_BG);
                    g2.fillRoundRect(wx - (tw / 2) - 6, wy - th + 4, tw + 12, th + 8, 10, 10);
                    g2.setColor(TEXT);
                    g2.drawString(text, wx - (tw / 2), wy + 4);
                }
            }
        }

        // Draw nodes after edges so nodes stay visually on top.
        for (Node node : nodes) {
            if (node.visited)
                g2.setColor(VISITED);
            else
                g2.setColor(NODE);

            g2.fillOval(node.x, node.y, NODE_SIZE, NODE_SIZE);
            g2.setColor(NODE_BORDER);
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(node.x, node.y, NODE_SIZE, NODE_SIZE);

            String label = String.valueOf(node.id);
            g2.setColor(TEXT);
            g2.setFont(getFont().deriveFont(Font.BOLD, 16f));
            FontMetrics fm = g2.getFontMetrics();
            int tx = node.x + (NODE_SIZE - fm.stringWidth(label)) / 2;
            int ty = node.y + ((NODE_SIZE - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(label, tx, ty);
        }

        g2.dispose();
    }

    private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowX = (int) (x2 - Math.cos(angle) * (NODE_RADIUS + 4));
        int arrowY = (int) (y2 - Math.sin(angle) * (NODE_RADIUS + 4));

        AffineTransform old = g2.getTransform();
        g2.translate(arrowX, arrowY);
        g2.rotate(angle);
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 0);
        arrowHead.addPoint(-10, -6);
        arrowHead.addPoint(-10, 6);
        g2.fillPolygon(arrowHead);
        g2.setTransform(old);
    }
}