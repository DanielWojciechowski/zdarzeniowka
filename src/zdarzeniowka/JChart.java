package zdarzeniowka;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

public class JChart extends JComponent{
	private static final long serialVersionUID = -5878170992402917036L;
	private Color color1, color2;
	private Logger  log = Logger.getLogger(JChart.class);
	private ArrayList<Integer> px = new ArrayList<Integer>();
	private ArrayList<Integer> py = new ArrayList<Integer>();
	private int sum = 0, avg = 0;
	private int height, width;
	
	public JChart(int height, int width){
		super();
		Dimension d = new Dimension(height, width);
		setMinimumSize(d);
		setPreferredSize(d);
		color1 = Color.red;
		color2 = Color.blue;
		px.add(0);
		py.add(0);
	}
	
	public void setY1(int value,int time){
		px.add((px.get(px.size()-1))+5*time);
		py.add(0,value);
		sum+=value;
		this.repaint();
		log.info("Rozmiar " + px.size());
		log.info(px.get(0));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		width = this.getWidth();
		height = this.getHeight();
		g.setColor(Color.white);
		g.drawRect(0, 0, width-1, height-1);
		int hor = 40, vert = 30;
		while (hor < height) {
	       g.drawLine(0, hor, width, hor);
	       hor += 40;
	    }
	    while (vert < width) {
	       g.drawLine(vert, 0 , vert, height);
	       vert += 30;
	    }
	    g.setColor(color2);

	    int[] ipx = convertIntegers(px);
	    int[] ipy = convertIntegers(py);
	    ipy = heightChanger(ipy, height);
	    ipx = convertIntegers(px);
	    g.setColor(color2);	 
	    avg = sum/(ipx.length);
	    log.info("AVG "+avg);
	    g.drawLine(0, height-avg*20, width-1, height-avg*20);
	    g.setColor(color1);
	    g.drawPolyline(ipx, ipy, px.size());	    
   	
	  }
	public static int[] convertIntegers(List<Integer> integers){
		
	    int[] ret = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++){
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
	public int[] heightChanger(int[] tab, int height){
		for(int i = 0; i < tab.length; i++)
			tab[i] = height - tab[i]*20;
		return tab;
	}
}
