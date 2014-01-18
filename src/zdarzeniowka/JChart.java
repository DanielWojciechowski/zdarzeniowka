package zdarzeniowka;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class JChart extends JComponent{
	private static final long serialVersionUID = -5878170992402917036L;
	private int x1, x2, y1, y2, x11, x22, y11, y22;
	
	public JChart(int height, int width){
		super();
		Dimension d = new Dimension(height, width);
		setMinimumSize(d);
		setPreferredSize(d);
		//setMaximumSize(d);
		x11 = 0;
		x22 = 0;
		y11 = 0;
		y22 = 0;
		x1 = 10;
		x2 = 30;
		y1 = 40;
		y2 = 30;
	}
	
	public void setX1(int value){
		this.x11 = this.x1;
		this.x1 = value;
	}
	
	public void setX2(int value){
		this.x22 = this.x2;
		this.x2 = value;
	}
	
	public void setY1(int value){
		this.y11 = this.y1;
		this.y1 = value;
	}
	
	public void setY2(int value){
		this.y22 = this.y2;
		this.y2 = value;
	}
	
	public void set(int x1, int y1){
		this.x11 = this.x1;
		this.y11 = this.y1;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	public void set2(int x2, int y2){
		this.x22 = this.x2;
		this.y22 = this.y2;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = this.getHeight();
		int width = this.getWidth();
		g.setColor(Color.white);
		g.drawRect(0, 0, width-1, height-1);
		int hor = 10, vert = 10;
		while (hor < height) {
	       g.drawLine(1, hor, width-1, hor);
	       hor += 10;
	    }
	    while (vert < width) {
	       g.drawLine(vert, 1 , vert, height-1);
	       vert += 10;
	    }
	    g.setColor(Color.red);
	    g.drawLine(x11, y11, x1, y1);

	    g.setColor(Color.blue);
	    g.drawLine(x22, y22, x2, y2);
	   // g.drawLine(0, 0, 500, 300);
	  }
	
	
	

}
