// 62050199 Paul Hochaicharoen
// 62050245 Apichai Samuttong 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Holiday {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.setTitle("Holyday Time");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		BufferedImage image = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
		makeImage(image);
		
		panel.add(new JLabel(new ImageIcon(image)));
		frame.add(panel);
		frame.pack();
		
		//// Save file process.
		boolean save = false;
		if(save) {
			try {
				File fileLocation = new File("Holiday.png");
				ImageIO.write(image, "png", fileLocation);
			} catch (IOException e) {
				System.out.println("Can't save image file.");
			}
		}
	}

	private static void makeImage(BufferedImage image) {
		Graphics2D g = image.createGraphics();
		
		//Set default Color;
		Color tone1,tone2 ;
		tone1 = new Color(144,105,78);
		tone2 = new Color(80,60,40);
		
		//Every Glass. (left building, centre buliding)
		//Light on
		Color glass1 = new Color(238, 176, 29);
		Color glass2 = new Color(228, 189, 84);
		
		//Only right building windows.
		//Light off
		Color glass3 = new Color(238, 176, 29);
		Color glass4 = new Color(228, 189, 84);
		
		//Decorations.
		Color tinsel1 = Color.GRAY;
		Color tinsel2 = Color.PINK;
		Color tinsel3 = Color.ORANGE;
		
		//Set background.
		g.setColor(Color.black);
		filltri(g, 0, 0, 600, 0, 600, 600);
		filltri(g, 0, 0, 0, 600, 600, 600);
		
		
		//Sky shade.
		int r = 0;
		for (int c = 0; c < 300;c++) {
			if (c-45 <= 255 && c-45 > 0) g.setColor(new Color(r,15,c-45));
			else g.setColor(new Color(5,15,0));
			if(c > 150) r++;
			g.drawLine(0, 0+c, 600, 0+c);
		}
		
		//Ground
		g.setColor(new Color(230,215,135));
		filltri(g, 0, 300, 0, 600, 600, 300);
		filltri(g, 0, 600, 600, 300, 600, 600);
		
		int floor[] = {365,369,378,394,419,455,504,568};
		
		g.setColor(Color.black);
		/* De line
		for (int i = 0; i < floor.length; i++) 
			g.drawLine(0,floor[i],600,floor[i]);
		*/
		
		
		ArrayList<Point> floor_around = new ArrayList<>();
		floor_around.add(new Point(0,450));
		floor_around.add(new Point(0,480));
		floor_around.add(new Point(0,525));
		for(int i = 0; i <= 600; i+= 100) 
			floor_around.add(new Point(i,600));
		floor_around.add(new Point(600,525));
		floor_around.add(new Point(600,480));
		floor_around.add(new Point(600,450));
		
		/* De line
		for (int i = 0; i < floor_around.size(); i++) {
			g.drawLine(300,300,floor_around.get(i).x,floor_around.get(i).y);
		}
		*/
		
		//Floor paint.
		g.setColor(new Color(185,122,88,145)); 
		for (int i = 1; i < floor.length; i+=2) {
			filltri(g,0,floor[i-1],600,floor[i-1],0,floor[i]);
			filltri(g,600,floor[i-1],0,floor[i],600,floor[i]);
		}
		
		for (int i = 1; i < floor_around.size(); i+= 2) {
			filltri(g,300,300,floor_around.get(i-1).x,floor_around.get(i-1).y,floor_around.get(i).x,floor_around.get(i).y);
		}
		
		//Meteor shower + stars
		g.setColor(Color.WHITE);
		g.drawLine(400, 0, 200, 150);
		//curve_bezier()
		g.drawLine(450, 0, 250, 100);
		g.drawLine(500, 0, 190, 200);
		plot(g, 215, 80);		plot(g, 100, 40);		plot(g, 20, 20);
		plot(g, 450, 70);		plot(g, 338, 84);		plot(g, 80, 200);
		plot(g, 275, 25);		plot(g, 500, 125);		plot(g, 575, 40);
		
		//Mountain
		/* Old
		g.setColor(new Color(0,20,10));
		filltri(g,255,225,175,300,335,300); //left tall
		filltri(g,540,175,420,300,660,300); //right tall
		filltri(g,66,195,-34,300,166,300); //left tall
		///BIG Mountain
		g.setColor(new Color(0,30,20));
		filltri(g,180,180,80,300,280,300); //left taller
		filltri(g,400,150,240,300,560,300); //Tallest
		*/
		
		//Set new mountain.
		ArrayList<Point> moun = new ArrayList<>();
		//left tall
		moun.add(new Point(175,300));	moun.add(new Point(335,300));	moun.add(new Point(255,225));		
		moun.add(new Point(252,222));
		mounPaint(g,moun);
		moun.clear();
		
		//right tall
		moun.add(new Point(420,300));	moun.add(new Point(660,300));	moun.add(new Point(540,175));		
		moun.add(new Point(550,220));	moun.add(new Point(515,235));	moun.add(new Point(560,260));
		mounPaint(g,moun);
		moun.clear();
		
		//left tall
		moun.add(new Point(-34,300));	moun.add(new Point(166,300));	moun.add(new Point(66,195));		
		moun.add(new Point(70,240));
		mounPaint(g,moun);
		moun.clear();
		
		//left taller
		moun.add(new Point(240,300)); 	moun.add(new Point(560,300));	moun.add(new Point(400,150));
		moun.add(new Point(380,200));	moun.add(new Point(400,240)); 	moun.add(new Point(390,250));
		mounPaint(g,moun);
		moun.clear();
		
		//Tallest
		moun.add(new Point(80,300));	moun.add(new Point(280,300));	moun.add(new Point(180,180));		
		moun.add(new Point(200,222));	moun.add(new Point(190,240));	moun.add(new Point(150,270));
		mounPaint(g,moun);
		moun.clear();
		
		
		//Moon
		for(int i = 100; i > 0 ; i--) { //60
			int r_up = 100 - (65*i/100);
			int r_down = 100 + (65*i/100);
			int font = 150-i;
			int c = 0;
			g.setColor(new Color(205+i/2,155+i,0));
			curve_bezier(g,font,100,font,r_up,150 - c,r_up,150 - c/5,100 - c);
			curve_bezier(g,font,100,font,r_down,150 - c,r_down,150 - c/5,100 + c);
		}
		
		//Font mountain
		//Brick
		Point p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12;
		p1 = new Point(175,265);
		p2 = new Point(425,265);
		
		p3 = new Point(175,255);
		p4 = new Point(425,255);
		
		g.setColor(Color.black);
		for (int i = 175; i <= 425; i+=25) {
			filltri(g,i,245,i-2,280,i+2,280);
		}
		g.setColor(tone1);
		for (int i = 0; i < 5; i++) {
			g.setColor(new Color(tone1.getRGB()-i*0x101010));
			g.drawLine(p1.x,p1.y+i,p2.x,p2.y+i);
			g.drawLine(p3.x,p3.y+i,p4.x,p4.y+i);
			
		}
		
		//Tower I
		p1 = new Point(420,280);
		p2 = new Point(435,280);
		p3 = new Point(450,280);
		
		p4 = new Point(425,120);
		p5 = new Point(445,120);
		
		p6 = new Point(415,115);
		p7 = new Point(455,115);
		
		p8 = new Point(415,100);
		p9 = new Point(455,100);
		
		p10 = new Point(435,80);
		
		g.setColor(tone1);
		filltri(g,p1.x,p1.y,p4.x,p4.y,p2.x,p2.y);
		g.setColor(new Color(tone1.getRGB() - 0x101010));
		filltri(g,p4.x,p4.y,p2.x,p2.y,p5.x,p5.y);
		g.setColor(new Color(tone1.getRGB() - 0x202020));
		filltri(g,p2.x,p2.y,p5.x,p5.y,p3.x,p3.y);
		
		g.setColor(new Color(tone1.getRGB() - 0x303030));
		filltri(g,p4.x,p4.y,p6.x,p6.y,p5.x,p5.y);
		g.setColor(new Color(tone1.getRGB() - 0x404040));
		filltri(g,p6.x,p6.y,p5.x,p5.y,p7.x,p7.y);
		
		g.setColor(glass2);
		filltri(g,p6.x,p6.y,p7.x,p7.y,p8.x,p8.y);
		g.setColor(glass1);
		filltri(g,p7.x,p7.y,p8.x,p8.y,p9.x,p9.y);
		
		g.setColor(new Color(0xC01000));
		filltri(g,p8.x,p8.y,p9.x,p9.y,p10.x,p10.y);
		
		//Tower II
		p1 = new Point(150,280);		p2 = new Point(175,280);
		
		p3 = new Point(150,220);		p4 = new Point(175,220);
		
		p5 = new Point(145,215);		p6 = new Point(180,215);
		
		p7 = new Point(163,200);
		
		g.setColor(tone1);
		filltri(g,p1.x,p1.y,p3.x,p3.y,p2.x,p2.y);
		g.setColor(new Color(tone1.getRGB() - 0x101010));
		filltri(g,p3.x,p3.y,p2.x,p2.y,p4.x,p4.y);
		g.setColor(new Color(tone1.getRGB() - 0x202020));
		filltri(g,p3.x,p3.y,p4.x,p4.y,p5.x,p5.y);
		g.setColor(new Color(tone1.getRGB() - 0x303030));
		filltri(g,p4.x,p4.y,p5.x,p5.y,p6.x,p6.y);
		g.setColor(new Color(0xC01000));
		filltri(g,p5.x,p5.y,p6.x,p6.y,p7.x,p7.y);
		
		//Center background
		g.setColor(new Color(144,105,78)); 
		filltri(g,460,305,140,305,140,365);
		filltri(g,460,305,140,365,460,365);
		
		//paint background building
		Color roof1 = new Color(0x780F0F); 
		Color roof2 = new Color(0xAA1616); 
		Color roof3 = new Color(0xDC1E1E);
		for (int i = 140; i <= 460; i++) {
			g.setColor((i % 2 == 0 ? roof2 : i % 3 == 1 ? roof1:roof3));
			g.drawLine(i,279,i,304);
		}
		
		
		g.setColor(glass1);
		filltri(g,149,316,149,355,169,355);
		filltri(g,451,316,451,355,411,316);
		for (int i = 149; i <= 435; i++) {
			g.setColor((i % 30 > 10 ? glass1 : glass2));
			g.drawLine(i,316,i+20,355);
		}
		
		//Saint & moss.
		//r_nose
		g.setColor(Color.red);
		filltri(g,186, 369 ,  197, 366 ,  197, 374 );
		
		//r_head
		g.setColor(new Color(0xA05020));
		 filltri(g,186, 369 ,  142, 346 ,  142, 386 );
		 
		 //r_eye
		 g.setColor(Color.white);
		 g.drawLine(156, 360 ,  156, 364);

		 //r_horn
		 g.setColor(Color.BLACK);
		 filltri(g, 151, 354, 168, 328, 171, 332);
		 g.drawLine(157, 342, 151, 335);
		 g.drawLine(161, 337, 156, 329);
		 g.drawLine(166, 331, 159, 324);
		 
		//arm_san
		 g.setColor(Color.red);
		 filltri(g,406, 375 ,  448, 375 ,  470, 399 );
		 
		//beard_san
		 g.setColor(Color.white);
		 filltri(g,439, 364 ,  466, 347 ,  467, 377 );
		 
		 
		 
		//head
		 g.setColor(new Color(0xFFEE80));
		 moun.add(new Point(461, 350));
		 moun.add(new Point(443, 334));
		 moun.add(new Point(440, 364));
		 moun.add(new Point(420, 352));
		 
		 mutitri(g,moun,3);
		 moun.clear();
		 
		 //eye
		 g.setColor(Color.black);
		 g.drawLine(432,352,436,356);
		 g.drawLine(440,345,444,348);
		 
		 //hat
		 g.setColor(Color.white);
		 moun.add(new Point(420, 355));
		 moun.add(new Point(415, 349));
		 moun.add(new Point(445, 334));
		 moun.add(new Point(440, 328));
		 mutitri(g,moun,2);
		 moun.clear();
		 
		 //hat more
		 g.setColor(Color.red);
		 filltri(g,415, 349, 440, 328, 408, 310);
		 
		 // white inner
		 g.setColor(Color.white);
		 moun.add(new Point(409, 307));
		 moun.add(new Point(406, 307));
		 moun.add(new Point(409, 312));
		 moun.add(new Point(406, 312));
		 mutitri(g,moun,2);
		 moun.clear();
		 
		  // hat Curcle
		 g.setColor(Color.lightGray);
		 g.drawLine(406, 307,409, 307);
		 g.drawLine(406, 312,409, 312);
		 g.drawLine(406, 307,406, 307);
		 g.drawLine(409, 312,409, 312);
		 g.drawLine(406, 308, 406, 311);
		 g.drawLine(409, 308, 409, 311);
		 
		 
		 
		//// Left Building Paint //// Clone to right.
		//Start paint build Color Left (Up to Down to Border) 
		
		ArrayList<Point> build = new ArrayList<>();
			
		
		int freq = 3;
		//roof
		p1 = new Point(149,266);
		p2 = new Point(-1,233);
		p3 = new Point(149,291);
		p4 = new Point(-1,283);
		
		// darker tone
		p5 = new Point(144,290);
		p6 = new Point(-1,283);
		p7 = new Point(144,318);
		p8 = new Point(-1,333);
		
		//Upper room set.
		p9  = new Point(149,317);
		p10 = new Point(-1,333);
		p11 = new Point(149,372);
		p12 = new Point(-1,444);
		
		for (int flip = 0; flip <= 1; flip++) {
		
			//roof
			build.add(p1);
			build.add(p2);
			build.add(p3);
			build.add(p4);
		
			g.setColor(tone1); 		
			mutitri(g,build,freq);
			build.clear();

			// darker tone
			build.add(p5);
			build.add(p6);
			build.add(p7);
			build.add(p8);
			
			g.setColor(tone2); 		
			mutitri(g,build,freq);
			build.clear();
		
			//Upper room set.
			build.add(p9);
			build.add(p10);
			build.add(p11);
			build.add(p12);
			
			g.setColor(tone1); 		
			mutitri(g,build,freq);
			build.clear();
			
			if(flip == 0 ) {
				p1.x = 600-p1.x;	p2.x = 600-p2.x;	p3.x = 600-p3.x; 
				p4.x = 600-p4.x;	p5.x = 600-p5.x;  	p6.x = 600-p6.x;  
				p7.x = 600-p7.x;  	p8.x = 600-p8.x;  	p9.x = 600-p9.x;  
				p10.x = 600-p10.x;  p11.x = 600-p11.x; 	p12.x = 600-p12.x;  
			}
		
		}
		
		// Room avenues pole.
		p1 = new Point(149,372);
		p2 = new Point(149,478);
		p3 = new Point(145,482);
		p4 = new Point(136,378);
		p5 = new Point(136,482);
		
		//Font room
		p6 = new Point(136,378);
		p7 = new Point(70,409);
		p8 = new Point(136,482);
		p9 = new Point(70,553);

		for (int flip = 0; flip <= 1; flip++) {
			
			// Room avenues pole.
			g.setColor(tone2);
			filltri(g,p1.x,p1.y,p2.x,p2.y,p3.x,p3.y);
			g.setColor(new Color(tone2.getRGB()-0x101010));
			filltri(g,p1.x,p1.y,p4.x,p4.y,p3.x,p3.y);
			g.setColor(new Color(tone2.getRGB()-0x202020));
			filltri(g,p5.x,p5.y,p4.x,p4.y,p3.x,p3.y);
		
		
			//Font room
			build.add(p6);
			build.add(p7);
			build.add(p8);
			build.add(p9);
		
			g.setColor(new Color(tone1.getRGB()-0x202020));
			mutitri(g,build,freq);
			build.clear();
			
			if(flip == 0 ) {
				p1.x = 600-p1.x;	p2.x = 600-p2.x;	p3.x = 600-p3.x; 
				p4.x = 600-p4.x;	p5.x = 600-p5.x;  	p6.x = 600-p6.x;  
				p7.x = 600-p7.x;  	p8.x = 600-p8.x;  	p9.x = 600-p9.x;  
			}
		
		}
		
		//Room border pole.
		p1 = new Point(70,409);
		p2 = new Point(72,568);
		p3 = new Point(57,586);
		p4 = new Point(43,422);
		p5 = new Point(43,586);
		
		//Room Border
		p6 = new Point(43,422);
		p7 = new Point(-1,444);
		p8 = new Point(43,586);
		p9 = new Point(-1,633);
		
		for (int flip = 0; flip <= 1; flip++) {
			//Room border pole.
			g.setColor(tone2);
			filltri(g,p1.x,p1.y,p2.x,p2.y,p3.x,p3.y);
			g.setColor(new Color(tone2.getRGB()-0x101010));
			filltri(g,p1.x,p1.y,p4.x,p4.y,p3.x,p3.y);
			g.setColor(new Color(tone2.getRGB()-0x202020));
			filltri(g,p5.x,p5.y,p4.x,p4.y,p3.x,p3.y);		
		
			//Room Border
			build.add(p6);
			build.add(p7);
			build.add(p8);
			build.add(p9);
		
			g.setColor(new Color(tone1.getRGB()-0x202020));
			mutitri(g,build,freq-1);
			build.clear();
			
			if(flip == 0 ) {
				p1.x = 600-p1.x;	p2.x = 600-p2.x;	p3.x = 600-p3.x; 
				p4.x = 600-p4.x;	p5.x = 600-p5.x;  	p6.x = 600-p6.x;  
				p7.x = 600-p7.x;  	p8.x = 600-p8.x;  	p9.x = 600-p9.x;  
			}
		
		}
		
		//Glass  font
		p1 = new Point(105,400);
		p2 = new Point(130,394);
		p3 = new Point(75,425);
		p4 = new Point(75,460);
		p5 = new Point(75,525);
		p6 = new Point(130,470);
		
		//Glass border
		p7 = new Point(130,410);
		p8 = new Point(0,455);
		p9 = new Point(25,452);
		p10 = new Point(0,500);
		p11 = new Point(25,575);
		p12 = new Point(0,600);
		
		
		for (int flip = 0; flip <= 1; flip++) {
			//Glass  font
			g.setColor(flip == 0 ? glass1 : glass3);
			filltri(g,p1.x,p1.y,p2.x,p2.y,p3.x,p3.y); //L
			filltri(g,p2.x,p2.y,p3.x,p3.y,p4.x,p4.y);
			filltri(g,p2.x,p2.y,p5.x,p5.y,p6.x,p6.y);
		
			filltri(g,p8.x,p8.y,p9.x,p9.y,p10.x,p10.y);// L2
		
			g.setColor(flip == 0 ?glass2 : glass4);
			filltri(g,p7.x,p7.y,p2.x,p2.y,p4.x,p4.y); //L
			filltri(g,p7.x,p7.y,p5.x,p5.y,p4.x,p4.y);
		
			filltri(g,p9.x,p9.y,p10.x,p10.y,p11.x,p11.y);// L2
			filltri(g,p12.x,p12.y,p10.x,p10.y,p11.x,p11.y);// L2
			
			if(flip == 0 ) {
				p1.x = 600-p1.x;	p2.x = 600-p2.x;	p3.x = 600-p3.x; 
				p4.x = 600-p4.x;	p5.x = 600-p5.x;  	p6.x = 600-p6.x;  
				p7.x = 600-p7.x;  	p8.x = 600-p8.x;  	p9.x = 600-p9.x;  
				p10.x = 600-p10.x;  p11.x = 600-p11.x; 	p12.x = 600-p12.x;  
			}
		}
		

		//g.drawLine(300,300,0,400);
		
		//Awning 
		p1 = new Point(146,374);
		p2 = new Point(186,400);
		p3 = new Point(72,409);
		p4 = new Point(130,449);
		
		p5 = new Point(43, 424);
		p6 = new Point(102, 471);
		p7 = new Point(0, 445);
		p8 = new Point(0, 558);
		
		g.setColor(new Color(0xFF4040));
		for (int flip = 0; flip <= 1; flip++) {
			build.add(p1);		build.add(p2);
			build.add(p3);		build.add(p4);
			mutitri(g,build,1);
			build.clear();
		
			build.add(p5);		build.add(p6);
			build.add(p7);		build.add(p8);
			mutitri(g,build,1);
			build.clear();
			if(flip == 0 ) {
				p1.x = 600-p1.x;	p2.x = 600-p2.x;	p3.x = 600-p3.x; 
				p4.x = 600-p4.x;	p5.x = 600-p5.x;  	p6.x = 600-p6.x;  
				p7.x = 600-p7.x;  	p8.x = 600-p8.x;  	
			}
		}
		
		g.setColor(Color.green);
		
		
		p1 = new Point(1,381);
		p2 = new Point(42, 370);
		p3 = new Point(86, 358);
		p4 = new Point(117, 349);
		p5 = new Point(138, 343);
		
		for (int flip = 0; flip <= 1; flip++) {
			ball(g,p1.x,p1.y,Color.red);
			ball(g,p2.x,p2.y,Color.orange);
			ball(g,p3.x,p3.y,Color.green);
			ball(g,p4.x,p4.y,Color.blue);
			ball(g,p5.x,p5.y,Color.magenta);
			if(flip == 0 ) {
				p1.x = 600-p1.x;	p2.x = 600-p2.x;	p3.x = 600-p3.x; 
				p4.x = 600-p4.x;	p5.x = 600-p5.x;  	
			}
		}
		
		///////////////////////////////////////////////////////
		
		
		///////////////
		// plant
	
		int [] plant_x = {149,133,108,72,23,-41};
		int [] plant_y = {317,318,321,326,331,338};
	
	
		g.setColor(Color.green);
	
		int x1,x2,y1,y2,o1,o2,shift;
		for (int j = 0; j < 2; j++) {
			double m ; int c;
			if(j == 0) {
				m = (double)-1/3;
				c = 400;
			} else {
				m = (double) 1/3;
				c = 200;
			}
			for (int i = 1; i < plant_y.length; i++) {
				shift = 0;
				g.setColor(tinsel1);
				x1 = plant_x[i-1];	y1 = plant_y[i-1];	o1 = (int) (m*x1+c);
				x2 = plant_x[i];	y2 = plant_y[i];	o2 = (int) (m*x2+c);
				curve_bezier(g, x1, y1, x1,o1,x2,o2, x2, y2);
				
				shift++;
				g.setColor(tinsel2);
				curve_bezier(g, x1-shift, y1, x1-shift,o1,x2-shift,o2, x2-shift, y2);
				shift++; curve_bezier(g, x1-shift, y1, x1-shift,o1,x2-shift,o2, x2-shift, y2);
				
				shift++;
				g.setColor(tinsel3);
				curve_bezier(g, x1-shift, y1, x1-shift,o1,x2-shift,o2, x2-shift, y2); 
				
				
			}
			
			//for another side
			for (int i = 0; i < plant_x.length; i++) {
				plant_x[i] = 600 - plant_x[i];
			}
		
		}			
		
		//////
		//BG tree pot
		g.setColor(Color.black);
		for(int stack = 0;stack < 30; stack ++) 
			curve_bezier(g,250,440-stack,250,420-stack,350,420-stack,350,440-stack);
		
		//tree + star
		g.setColor(new Color(100,50,0));
		filltri(g,300,100,290,440,310,440);
		g.setColor(new Color(0,64,0));
		filltri(g,300,100,240,380,360,380);
		
		//tree shade.
		for (int i = 0; i <= 120 - 20 ; i+= 20) {
			g.setColor(new Color(20,160-i,0));
			filltri(g,300,100,240+i,380,240+i+20,380);
		}
		g.setColor(Color.black);
		g.drawLine(294,130,313,160);
		g.setColor(Color.magenta);
		g.drawLine(287,160,320,190);
		g.setColor(Color.blue);
		g.drawLine(280,190,326,220);
		g.setColor(Color.cyan);
		g.drawLine(274,220,332,250);
		g.setColor(Color.green);
		g.drawLine(268,250,	339,280);
		g.setColor(Color.yellow);
		g.drawLine(261,280,345,310);
		g.setColor(Color.orange);
		g.drawLine(255,310,351,340);
		g.setColor(Color.red);
		g.drawLine(249,340,358,370);
		g.setColor(Color.pink);
		g.drawLine(242,370,	317,380);
		
		//tree ball
		
		ball(g,290, 160,Color.orange);
		ball(g,276, 283,Color.red);
		ball(g,254,338,Color.blue);
		
		
		//Present
		
		g.setColor(new Color(0x90FF90));
		build.add(new Point(260, 392));
		build.add(new Point(282, 392));
		build.add(new Point(260, 425));
		build.add(new Point(282, 425));
		mutitri(g,build,3);
		build.clear();
		
		g.setColor(new Color(0xFFAA20));
		build.add(new Point(255, 412));
		build.add(new Point(275, 412));
		build.add(new Point(255, 430));
		build.add(new Point(275, 430));
		mutitri(g,build,2);
		build.clear();
		
		g.setColor(new Color(0xA050A0));
		build.add(new Point(278, 404));
		build.add(new Point(306, 404));
		build.add(new Point(275, 440));
		build.add(new Point(308, 440));
		mutitri(g,build,3);
		build.clear();
		
		g.setColor(new Color(0xF0C020));
		build.add(new Point(342, 400));//
		build.add(new Point(313, 404));//
		build.add(new Point(345, 430));
		build.add(new Point(310, 430));
		mutitri(g,build,2);
		build.clear();
		
		
		//Tree Pot
		for(int stack = 0;stack < 30; stack ++) {
			g.setColor(new Color(30+stack*3,0,0));
			curve_bezier(g,250,440-stack,250,460-stack,350,460-stack,350,440-stack);
		}
		
		//Tree details.
		/*
		g.setColor(Color.ORANGE);
		filltri(g, 300, 100, 285, 77, 315, 77);
		g.setColor(Color.ORANGE);
		filltri(g, 285, 92, 315, 92, 300, 70);
		*/
		g.setColor(Color.black);
		g.drawLine(300,85,300,100);
		
		g.setColor(Color.yellow);
		filltri(g, 300, 85, 300, 66, 303, 81);
		filltri(g, 300, 85, 314, 79, 305, 87);
		filltri(g, 300, 85, 309, 100, 300, 90);
		filltri(g, 300, 85, 291, 100, 295, 87);
		filltri(g, 300, 85, 286, 79, 298, 81);
		
		g.setColor(Color.orange);
		filltri(g, 300, 85, 300, 66, 298, 81);
		filltri(g, 300, 85, 314, 79, 303, 81);
		filltri(g, 300, 85, 309, 100, 305, 87);
		filltri(g, 300, 85, 291, 100, 300, 90);
		filltri(g, 300, 85, 286, 79, 295, 87);
		
		//bell
		bell(g,298,136);
		bell(g,310,181);
		bell(g,291,197);
		bell(g,306,237);
		bell(g,277,253);
		bell(g,323,274);
		bell(g,269,314);
		bell(g,308,327);
		bell(g,346,339);
		
		g.setColor(Color.orange);
		g.setColor(new Color(g.getColor().getRGB()- 0x202020));
		curve_bezier(g, 30, 260, 30, 300, 570, 300, 570, 260);
		g.setColor(new Color(g.getColor().getRGB()- 0x202020));
		curve_bezier(g, 60, 264, 60, 300, 540, 300, 540, 264);
		g.setColor(new Color(g.getColor().getRGB()- 0x202020));
		curve_bezier(g, 90, 269, 90, 300, 510, 300, 510, 269);
		g.setColor(new Color(g.getColor().getRGB()- 0x202020));
		curve_bezier(g,120, 273,120, 300, 480, 300, 480, 273);
		
		g.setColor(Color.green);
		plot(g,300,300);
		g.setColor(Color.PINK);
		plot(g, 300, 225);
		g.setColor(Color.CYAN);
		plot(g, 270, 350);
		g.setColor(Color.CYAN);
		plot(g, 325, 355);
		g.setColor(Color.MAGENTA);
		plot(g, 300, 120);
		g.setColor(Color.LIGHT_GRAY);
		plot(g, 300, 180);
		 
		
		g.setColor(Color.green);
		plot(g,300,300);
	}
	
	private static void drawtri (Graphics g,int x1,int y1,int x2,int y2,int x3,int y3) {
		g.drawPolygon(new int[] {x1,x2,x3}, new int[] {y1,y2,y3}, 3);
	}
	
	private static void filltri (Graphics g,int x1,int y1,int x2,int y2,int x3,int y3) {
		g.fillPolygon(new int[] {x1,x2,x3}, new int[] {y1,y2,y3}, 3);
	}
	
	private static void curve_bezier (Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
	    for (int i = 0; i <= 1000; i++) {
	        double t = i / 1000.0;
	        int x = (int)(pow((1-t),3)*x1 + 3*t*pow((1-t),2)*x2 + 3*pow(t,2)*(1-t)*x3 + pow(t,3)*x4);
	        int y = (int)(pow((1-t),3)*y1 + 3*t*pow((1-t),2)*y2 + 3*pow(t,2)*(1-t)*y3 + pow(t,3)*y4);
	        plot (g, x, y);
	    }
	}
	
	private static void plot(Graphics g, int x, int y) {
		g.fillRect(x, y, 1, 1);
	}

	private static double pow(double n,int p) {
		return Math.pow(n,p);
	}
	
	private static void mutitri(Graphics2D g,ArrayList<Point> point,int freq) {
		
		double tap1x = ( point.get(1).x - point.get(0).x )/freq , tap1y = ( point.get(1).y - point.get(0).y )/freq ; 
		double tap2x = ( point.get(3).x - point.get(2).x )/freq , tap2y = ( point.get(3).y - point.get(2).y )/(freq) ; 
		Color paint = g.getColor();
		int tonePlus = - 0x060606;
		filltri(g,point.get(0).x,point.get(0).y,point.get(2).x,point.get(2).y,point.get(2).x + (int) tap2x,point.get(2).y + (int) tap2y);
		for (int i = 1; i <= freq; i++) {
			int t1x = (int) Math.ceil(tap1x*(i-1)) 	, t1y = (int) Math.round(tap1y*(i-1));
			int u1x = (int) Math.ceil(tap1x*i) 		, u1y = (int) Math.ceil(tap1y*i);
			int t2x = (int) Math.ceil(tap2x*i) 		, t2y = (int) Math.ceil(tap2y*i);
			int d2x = (int) Math.ceil(tap2x*(i-1)) 	, d2y = (int) Math.ceil(tap2y*(i-1));
			paint = (new Color(paint.getRGB() + tonePlus));		g.setColor(paint);
			filltri(g,point.get(0).x + t1x,point.get(0).y + t1y,point.get(2).x + d2x,point.get(2).y + d2y,point.get(2).x +t2x,point.get(2).y + t2y);
			paint = (new Color(paint.getRGB() + tonePlus));		g.setColor(paint);
			filltri(g,point.get(0).x + t1x,point.get(0).y + t1y,point.get(0).x +u1x,point.get(0).y + u1y,point.get(2).x +t2x,point.get(2).y + t2y);
		}
		
	}
	
	private static void mounPaint(Graphics2D g,ArrayList<Point> moun) {
		int shade = 20;
		for (int i = 2; i < moun.size(); i++) {
			g.setColor(Color.white);
			shade += 10; 
			if(i != 2) {
				g.setColor(new Color(255-shade,255-shade,255-shade));
				filltri(g,moun.get(0).x,moun.get(0).y,moun.get(i).x,moun.get(i).y,moun.get(i-1).x,moun.get(i-1).y);
				g.setColor(new Color(shade,shade,shade));
				filltri(g,moun.get(1).x,moun.get(1).y,moun.get(i).x,moun.get(i).y,moun.get(i-1).x,moun.get(i-1).y);
			}
			if(i == moun.size()-1) {
				shade += 10; 
				filltri(g,moun.get(0).x,moun.get(0).y,moun.get(i).x,moun.get(i).y,moun.get(1).x,moun.get(1).y);
			}
		}
	}
	
	private static void bell (Graphics2D g,int x,int y) {
		g.setColor(Color.white);
		filltri(g,x-5,y+5,x,y,x+5,y+5);
		g.setColor(new Color(0xFFF010));
		filltri(g,x-5,y+5,x-5,y+12,x+5,y+5);
		g.setColor(Color.orange);
		filltri(g,x-5,y+5,x+5,y+12,x+5,y+5);
		
	}
	
	private static void ball (Graphics2D g,int x,int y,Color c) {
		g.setColor(Color.black);
		filltri(g,x-5,y+5,x,y,x+5,y+5);
		
		Color paint = c ;
		for (int i = 20; i > 5; i--) {
			g.setColor(paint);
			curve_bezier(g, x-5,y+5,x-5,y+i,x+5,y+i,x+5,y+5);
				if(paint.getRed() < 255 - 5) paint = new Color(paint.getRGB() + 0x050000);
				if(paint.getGreen() < 255 - 5) paint = new Color(paint.getRGB() + 0x000500);
				if(paint.getBlue() < 255 - 5) paint = new Color(paint.getRGB() + 0x000005);
				//if(paint.getRGB()  < 0xFFFFFF - 0x050505) paint = new Color(paint.getRGB() + 0x050505);
				//else paint = new Color(0xFFFFFFF);
		}
		
	}
}
