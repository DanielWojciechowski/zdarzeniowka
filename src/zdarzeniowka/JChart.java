package zdarzeniowka;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class JChart extends JComponent{
	private static final long serialVersionUID = -5878170992402917036L;
	public int x1, x2, y1, y2;
	
	public JChart(int height, int width){
		super();
		Dimension d = new Dimension(height, width);
		setMinimumSize(d);
		setPreferredSize(d);
		//setMaximumSize(d);
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
		    //g.drawLine(x1, y1, x2, y2);
		    g.drawLine(0, 0, 500, 300);
		  }
	
	
	

}
