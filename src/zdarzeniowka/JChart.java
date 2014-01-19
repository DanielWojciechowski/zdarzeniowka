package zdarzeniowka;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JChart extends JComponent{
	private static final long serialVersionUID = -5878170992402917036L;
	private int x1, x2, y1, y2, x11, x22, y11, y22;
	private Color color1, color2;
	
	
	public JChart(int height, int width){
		super();
		Dimension d = new Dimension(height, width);
		setMinimumSize(d);
		setPreferredSize(d);
		color1 = Color.red;
		color2 = Color.blue;
		x11 = 0;
		x22 = 0;
		y11 = 0;
		y22 = 0;
		x1 = 20;
		x2 = 20;
		y1 = 40;
		y2 = 30;
		
	}
	
	public void setY1(int value){
		this.y11 = this.y1;
		this.x11 = this.x1;
		this.x1 += 50;
		this.y1 = value;
	}
	
	public void setY2(int value){
		this.y22 = this.y2;
		this.x22 = this.x2;
		this.x2 += 50;
		this.y2 = value;
	}
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = this.getHeight();
		int width = this.getWidth();
		g.setColor(Color.white);
		g.drawRect(0, 0, width-1, height-1);
		int hor = 50, vert = 20;
		while (hor < height) {
	       g.drawLine(0, hor, width, hor);
	       hor += 50;
	    }
	    while (vert < width) {
	       g.drawLine(vert, 0 , vert, height);
	       vert += 20;
	    }
	    g.setColor(color1);
	    g.drawLine(x11, height-y11, x1, height-y1);

	    g.setColor(color2);
	    g.drawLine(x22, height-y22, x2, height-y2);
	    if(x1 > width || x2 > width){
	    	this.setSize(x1+width, height);
	    }
	  }
	
	
	

}
