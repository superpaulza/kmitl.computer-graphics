import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;


import java.util.*; 
import java.util.Timer;

public class Animation extends JPanel {
     public static void main(String args[]) {

          Animation m = new Animation();
          JFrame f = new JFrame();
          f.add(m);
          f.setTitle("First Swing");
          f.setSize(480, 480);
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.setVisible(true);
     }
     public void paintComponent(Graphics g)  {
          int height = 0, width = 0;
          int[][] PixelArray = {}, PixelArray2 = {};

          try {
               BufferedImage bImage = ImageIO.read(new File("frames/frame1.jpg"));
               BufferedImage bImage2 = ImageIO.read(new File("frames/frame2.jpg"));
               height = bImage.getHeight();
               width = bImage.getWidth();
               PixelArray = new int[width][height];
               PixelArray2 = new int[width][height];

               for(int i=0;i<width;i++){
                    for(int j=0;j<height;j++){
                         PixelArray[i][j] = bImage.getRGB(i, j);
                         PixelArray2[i][j] = bImage2.getRGB(i, j);
                    }
               }
          } catch (Exception ee) {
               ee.printStackTrace();
          }
          //while (true) {
               //drawing(g, width, height, PixelArray);
               /*try {
               Thread.sleep(1000);
               } catch (InterruptedException e) {
                    break;
               }*/
               drawing(g, width, height, PixelArray2);
          //}

     }

     public void drawing(Graphics g, int width, int height, int[][] PixelArray) {
          //BufferedImage b = new BufferedImage(width, height, 3);

          for(int x = 0; x < width; x++) {
               for(int y = 0; y < height; y++) {
                    int rgb = (int)PixelArray[x][y] << 16 | (int)PixelArray[x][y] << 8 | (int)PixelArray[x][y];
                    int c1 = (rgb>>16)&0xFF;
                    int c2 = (rgb>>8)&0xFF;
                    int c3 = (rgb>>0)&0xFF;
                    g.setColor(new Color(c1, c2, c3));
                    g.fillRect(x, y, 1, 1);
               }
          }
     }

}
