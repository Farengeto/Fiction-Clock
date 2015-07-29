import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Clock extends JPanel{
	TimeZone t;
	long r;
	long f;
	long FUC;
	String FTime;
	String cyr;
	String ram;
	String di;
	String bun;
	Color A = new Color(17,17,34);
	private BufferedImage image;
	private BufferedImage image2;
	int offset = 0;
	
	public Clock(){
		t = TimeZone.getTimeZone("GMT");
		java.util.TimeZone.setDefault(t);
		//time();
		try {                
			image = ImageIO.read(getClass().getResource("/Background_Tile.png"));
			//image = new Image(Clock.class.getResourceAsStream("res/Background_Tile.png"))
			image2 = ImageIO.read(getClass().getResource("/Wiki-wordmark.png"));
		} catch (IOException ex) {}
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP){
					offset++;
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN){
					offset--;
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT){
					offset -= 24;
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
					offset += 24;
				}
				else if (e.getKeyCode() == KeyEvent.VK_COMMA){
					offset -= 8766;
				}
				else if (e.getKeyCode() == KeyEvent.VK_PERIOD){
					offset += 8766;
				}
			}
		});
		setFocusable(true);
	}
	
	public static void main(String [] args) throws InterruptedException {
		JFrame frame = new JFrame("SW Clock");
		Clock game = new Clock();
		frame.add(game);
		frame.setSize(750+16, 405+39);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.repaint();
		while(true){
			game.time();
			game.repaint();
			Thread.sleep(10);
		}
	}
	
	public void time(){
		r = new Date().getTime() + ((long)(offset)*(long)3600000.0/(long)7.0);
		f = (long)((long)(r * 7) + (long)(16443734400000.0));
		FUC = (f - (long)25667452800000.0)/497;
		FTime = FUC(FUC);
		Cyr(f);
		DI();
		Bun();
	}
	  
	public String FUC(long FUC){
		int FUCy = (int)(FUC/134217728.0);
		FUC = (long)(FUC%134217728.0);
		String u = "";
		if(FUC < 0){
			FUC=(long)(134217728.0+FUC);
			FUCy = Math.abs(FUCy-1);
			u = " BFU";
		}
		else
			u = " FUC";
		int FUCs = (int)Math.abs(FUC%64);
		FUC = FUC / 64;
		int FUCm = (int)Math.abs(FUC%(64));
		FUC = FUC / 64;
		int FUCh = (int)Math.abs(FUC%(64));
	    FUC = FUC / 64;
	    int FUCd = (int)Math.abs(FUC%(64))+1;
	    FUC = FUC / 64;
	    int FUCM = (int)Math.abs(FUC%(8))+1;
	    //int FUCy = (int)(FUC / 8);
	    return(Integer.toString(FUCy,8) + "." + Integer.toString(FUCM,8) + "." + Integer.toString(FUCd,8) + "." + Integer.toString(FUCh,8) + "=" + Integer.toString(FUCm,8) + "-" + Integer.toString(FUCs,8)+u);
	}
	
	public void Cyr(long f){
		String s = new Date(f).toString();
		int year = Integer.parseInt(s.substring(24));
		if(year < 2790)
			ram = -(year-2790) + " BQF";
		else
			ram = (year-2790) + " AQF";
		Calendar c = new GregorianCalendar();
		c.clear();
		c.set(year,0,1);
		long t = f-c.getTimeInMillis();
		int month = (int)Math.ceil(t*6.0/31557600000.0);
		if(year < 2797)
			cyr = (2797-year) + " B";
		else
			cyr = (year-2796) +  " ";
		//cyr = cyr + "NE, Month " + month;
		cyr = cyr + "NE";
		switch(month){
		case 1:
			cyr = "Ianuaria " + cyr;
			break;
		case 2:
			cyr = "Martex " + cyr;
			break;
		case 3:
			cyr = "Iunius " + cyr;
			break;
		case 4:
			cyr = "Novemex " + cyr;
			break;
		case 5:
			cyr = "Dekemurios " + cyr;
			break;
		case 6:
			cyr = "Neochios " + cyr;
			break;
		default:
			break;
		}
	}
	  
	public void Bun(){
		String s = new Date(f).toString();
		int y = Integer.parseInt(s.substring(24));
		y = y - y%4;
		int ty = (y-2800)*4+1892;
		Calendar ca = new GregorianCalendar();
		ca.clear();
		ca.set(y,2,1);
		long d = ca.getTimeInMillis();
		long t = (f-d);
		ca.clear();
		ca.set(y+4,2,1);
		long l = (long)((ca.getTimeInMillis()-d)/4.0);
		int c = (int)(t/l);
		ty = ty + c*4;
		t = t%l;
		if(t<0)
			t = l+t;
		int m = (int)(t*365/l)+1;
		m = m % 365;
		if(m <= 0){
			m += 365;
		}
		if(m <= 31 && m>0)
			bun = m + " Marzo, " + ty + " T" + (ty/4) + "-A";
		else if(m <= 61)
			bun = m-31 + " Aprellus, " + (ty+1) + " T" + (ty/4) + "-B";
		else if(m <= 92)
			bun = m-61 + " Mahya, " + (ty+1) + " T" + (ty/4) + "-B";
		else if(m <= 122)
			bun = m-92 + " Junien, " + (ty+1) + " T" + (ty/4) + "-B";
		else if(m <= 153)
			bun = m-122 + " Juliara, " + (ty+2) + " T" + (ty/4) + "-C";
		else if(m <= 184)
			bun = m-153 + " Aegosto, " + (ty+2) + " T" + (ty/4) + "-C";
		else if(m <= 214)
			bun = m-184 + " Setempa, " + (ty+2) + " T" + (ty/4) + "-C";
		else if(m <= 245)
			bun = m-214 + " Octrobes, " + (ty+3) + " T" + (ty/4) + "-D";
		else if(m <= 275)
			bun = m-245 + " Nuevena, " + (ty+3) + " T" + (ty/4) + "-D";
		else if(m <= 306)
			bun = m-275 + " Dicemzo, " + (ty+3) + " T" + (ty/4) + "-D";
		else if(m <= 337)
			bun = m-306 + " Janeau, " + (ty+4) + " T" + ((ty/4)+1) + "-A";
		else if(m <= 365)
			bun = m-337 + " Februn, " + (ty+4) + " T" + ((ty/4)+1) + "-A";
		else
			bun = "";
		l = l/365;
		t = t%l;
		if(t < 0)
			t = l+t;
		int h = (int)(t*24/l);
		l = l/24;
		t = t%l;
		int mi = (int)(t*60/l);
		l = l/60;
		t = t%l;
		int se = (int)(t*60/l);
		bun = h + ":" + mi + ":" + se + " " + bun;
		
	}
	
	public void DI(){
		long t = (long)(f-25510953600000.0);
		int y = (int)(t/36288000000.0)+219490;
		t = (long)(t%36288000000.0);
		if(t < 0){
			t = (long)(36288000000.0+t);
		}
		int m  = (int)(t/3024000000.0)+1;
		t = (long)(t%3024000000.0);
		int d = (int)(t/100800000.0)+1;
		di = "ID." + y%1000 + "." + y/1000 + "." + m + "." + d;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(image,   0,   0, null);
		g.drawImage(image, 400,   0, null);
		g.drawImage(image,   0, 400, null);
		g.drawImage(image, 400, 400, null);
		g.setColor(A);
		g.fillRect(25,25,700,355);
		g.setColor(Color.GRAY);
		g.drawRect(25,25,700,355);  
		g.drawImage(image2, 250, 25, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.PLAIN, 20));
		g.drawString("Current Time:", 50, 120);
		g.drawString("" + new Date(r), 300,120);
		g.drawString("Gigaquadrant Time:", 50, 160);
		g.drawString("" + new Date(f), 300, 160);
		g.drawString("Draconid Calendar:", 50, 200);
		g.drawString(di, 300, 200);
		g.drawString("Cyrannian Calendar:", 50, 240);
		g.drawString(cyr, 300, 240);
		g.drawString("Rambo Calendar:", 50, 280);
		g.drawString(ram, 300, 280);
		g.drawString("Bunsen Time:", 50, 320);
		g.drawString(bun, 300, 320);
		g.drawString("Farengeto Time:", 50, 360);
		g.drawString(FTime, 300, 360);
	}
}