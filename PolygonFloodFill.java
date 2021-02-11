import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class PolygonFloodFill extends JPanel {
    public static void main(String[] args) 
    {
        PolygonFloodFill m = new PolygonFloodFill();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Ploygon-FloorFill");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) 
    {
        BufferedImage buffer = new BufferedImage(601, 601, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        /*int xPloy[] = {150, 250, 325, 375, 400, 275, 100};
        int yPloy[] = {150, 100, 125, 225, 235, 375, 300};

        Polygon poly = new Polygon(xPloy, yPloy, xPloy.length);
        g.drawPolygon(poly);*/

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);

        g2.setColor(Color.BLACK);
        Polygon poly = new Polygon();
        poly.addPoint(150, 150);
        poly.addPoint(250, 100);
        poly.addPoint(325, 125);
        poly.addPoint(375, 225);
        poly.addPoint(400, 325);
        poly.addPoint(275, 375);
        poly.addPoint(100, 300);

        g2.drawPolygon(poly);

        /*g.setColor(Color.RED);
        poly.reset();
        poly.addPoint(100, 200);
        poly.addPoint(400, 300);
        poly.addPoint(225, 500);
        
        g.fillPolygon(poly);*/

        buffer = floodfill(buffer, 200, 150, Color.WHITE, Color.GREEN, buffer.getWidth(), buffer.getHeight());
        g.drawImage(buffer, 0, 0, null);
    }

    public BufferedImage floodfill (BufferedImage m, int x, int y, Color targetColor, Color replacementColor, int size_x, int size_y) 
    {
        Queue<Point> q = new LinkedList<Point>();
        Graphics2D g2 = m.createGraphics();

        if (new Color(m.getRGB(x, y)).equals(targetColor))
        {
            g2.setColor(replacementColor);
            plot(g2, x, y, 1);
            q.add(new Point(x, y));
        }

        while (!q.isEmpty()) 
        {
            Point p = q.poll();
            //south
            if (p.y < size_y && new Color(m.getRGB(p.x, p.y + 1)).equals(targetColor))
            {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y + 1, 1);
                q.add(new Point(p.x, p.y + 1));
            }
            //north
            if (p.y > 0 && new Color(m.getRGB(p.x, p.y - 1)).equals(targetColor))
            {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y - 1, 1);
                q.add(new Point(p.x, p.y - 1));
            }
            //east
            if (p.x < size_x && new Color(m.getRGB(p.x + 1, p.y)).equals(targetColor))
            {
                g2.setColor(replacementColor);
                plot(g2, p.x + 1, p.y, 1);
                q.add(new Point(p.x + 1, p.y));
            }
            //west
            if (p.x > 0 && new Color(m.getRGB(p.x - 1, p.y)).equals(targetColor))
            {
                g2.setColor(replacementColor);
                plot(g2, p.x - 1, p.y, 1);
                q.add(new Point(p.x - 1, p.y));
            }
        }

        return m;
    }

    private void plot(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
    }
}