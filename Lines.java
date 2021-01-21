import java.awt.*;
import javax.swing.*;

public class Lines extends JPanel {
    public static void main(String[] args) 
    {
        Lines m = new Lines();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Lines");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) 
    {
        /*naiveLine(g, 100, 300, 200, 400, 1, Color.BLACK);
        naiveLine(g, 300, 200, 500, 500, 1, Color.RED);
        naiveLine(g, 100, 100, 400, 200, 2, Color.BLACK);
        naiveLine(g, 400, 200, 100, 100, 2, Color.BLACK);
        naiveLine(g, 100, 100, 200, 400, 2, Color.BLACK);*/
        //ddaLine(g, 100, 100, 400, 200, 2, Color.BLACK);
        //ddaLine(g, 400, 200, 100, 100, 2, Color.BLUE);
        ddaLine(g, 100, 100, 200, 400, 2, Color.RED);
    }

    private void plot(Graphics g, int x, int y, int size, Color c) {
        g.setColor(c);
        g.drawOval(x, y, size, size);
        g.fillOval(x, y, size, size);
    }

    public void naiveLine(Graphics g, int x1, int y1, int x2, int y2, int size, Color c)
    {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double m = dy / dx;
        double b = y1 - m * x1;
        for (int x = x1; x <= x2; x++) {
            int y = (int) (m * x + b);
            plot(g, x, y, size, c);
        }
    }

    public void ddaLine (Graphics g, int x1, int y1, int x2, int y2, int size, Color c)
    {
        double dx = x2 - x1;
        double dy = y2 - y1;
 
        double x, y;
        double m = dy / dx;
 
        if (m <= 1 && m >= 0)
        {
            y = Math.min(y1, y2);
            for (x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
            {
                y += m;
                plot(g, (int)x, (int)y, size, c);
            }
        }
        else if (m >= -1 && m < 0)
        {
            y = Math.min(y1, y2);
            for (x = Math.max(x1, x2); x >= Math.min(x1, x2); x--)
            {
                y -= m;
                plot(g, (int)x, (int)y, size, c);
            }
        }
        else if (m > 1)
        {
            x = Math.min(x1, x2);
            for (y = Math.min(y1, y2); y <= Math.max(y1, y2); y++)
            {
                x += 1 / m;
                plot(g, (int)x, (int)y, size, c);
            }
        }
        else
        {
            x = Math.min(x1, x2);
            for (y = Math.max(y1, y2); y >= Math.min(y1, y2); y--)
            {
                x -= 1 / m;
                plot(g, (int)x, (int)y, size, c);
            }
        }
    }
}
