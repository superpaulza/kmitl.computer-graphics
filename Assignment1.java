import javax.swing.*;
import java.awt.*;

class Assignment1 extends JPanel {
    public static void main(String[] args) {
        Assignment1 m = new Assignment1();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Assignment1");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        /*g.drawString("Hello", 40, 40);
        g.setColor(Color.BLUE);
        g.fillRect(130, 30, 100, 80);
        g.drawOval(30, 130, 50, 60);
        g.setColor(Color.RED);
        g.drawLine(0, 0, 200, 30);
        g.fillOval(130, 130, 50, 60);
        g.drawArc(30, 200, 40, 50, 90, 60);
        g.fillArc(30, 130, 40, 50, 180, 40);*/
        int x[]={100,70,130};
        int y[]={50,100,100};
        g.setColor(Color.RED);
        g.drawPolygon(x,y,3);
        g.fillPolygon(x,y,3);
        //plot(g,100,50,1,Color.BLACK);
        //plot(g,70,100,1,Color.BLACK);
        //plot(g,130,100,1,Color.BLACK);
    }

    private void plot(Graphics g, int x, int y, int size, Color c) {
        g.setColor(c);
        g.drawRect(x, y, size, size);
        g.fillRect(x, y, size, size);
    }

    public void bezier (Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int size, Color c)
    {
        for (int i = 0; i <= 1000; i++)
        {
            double t = i / 1000.0;
            int x = (int)(Math.pow((1 - t), 3)*x1 + 3*t*Math.pow((1-t), 2)*x2 + 3*t*t*(1-t)*x3 + t*t*t*x4);
            int y = (int)(Math.pow((1 - t), 3)*y1 + 3*t*Math.pow((1-t), 2)*y2 + 3*t*t*(1-t)*y3 + t*t*t*y4);
     
            plot(g, x, y, size, c);
        }
    
}