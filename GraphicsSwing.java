import javax.swing.*;
import java.awt.*;

class GraphicsSwing extends JPanel {
    public static void main(String[] args) {
        GraphicsSwing m = new GraphicsSwing();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("First Swing");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        g.drawString("Hello", 40, 40);
        g.setColor(Color.BLUE);
        g.fillRect(130, 30, 100, 80);
        g.drawOval(30, 130, 50, 60);
        g.setColor(Color.RED);
        g.drawLine(0, 0, 200, 30);
        g.fillOval(130, 130, 50, 60);
        g.drawArc(30, 200, 40, 50, 90, 60);
        g.fillArc(30, 130, 40, 50, 180, 40);
        plot(g, 200, 300, 5, Color.BLACK);
        plot(g, 105, 405, 6, Color.PINK);
        plot(g, 310, 110, 7, Color.ORANGE);
        plot(g, 300, 200, 8, Color.GRAY);
        plot(g, 400, 100, 9, Color.GREEN);
        plot(g, 250, 325, 10, Color.MAGENTA);
        plot(g, 190, 320, 15, Color.CYAN);
        plot(g, 345, 123, 20, Color.BLUE);
        plot(g, 123, 456, 25, Color.YELLOW);
        plot(g, 444, 444, 30, Color.RED);
    }

    private void plot(Graphics g, int x, int y, int size, Color c) {
        g.setColor(c);
        g.drawOval(x, y, size, size);
        g.fillOval(x, y, size, size);
    }
}