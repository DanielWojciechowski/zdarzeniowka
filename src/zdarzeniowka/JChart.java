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
	private Color color1, color2, background, line;
	private Logger  log = Logger.getLogger(JChart.class);
	private ArrayList<Integer> px = new ArrayList<Integer>();
	private ArrayList<Integer> py = new ArrayList<Integer>();
	private int sum = 0, avg = 0;
	private int height, width;
	private final static int SCALE = 10;
	
	public JChart(int height, int width){
		super();
		Dimension d = new Dimension(height, width);
		setMinimumSize(d);
		setPreferredSize(d);
		color1 = Color.red;
		color2 = Color.blue;
		background = new Color(0,0,0,5);
		line = new Color(128, 128, 128, 100);
		px.add(0);
		py.add(0);
	}
	
	public void setY1(int value,int time){
		px.add((px.get(px.size()-1))+time*5);
		py.add(0,value);
		sum+=value;
		if (px.size() >= 91){
			sum-= py.get(py.size()-1);
			py.remove(py.size()-1);
			px.remove(px.size()-1);
		}

		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		width = this.getWidth();
		height = this.getHeight();
		g.setColor(line);
		g.drawRect(0, 0, width-1, height-1);
		g.setColor(background);
		g.fillRect(1, 1, width-2, height-2);

		g.setColor(line);
		int hor = 40, vert = 25;
		while (hor < height) {
	       g.drawLine(0, hor, width, hor);
	       hor += 40;
	    }
	    while (vert < width) {
	       g.drawLine(vert, 0 , vert, height);
	       vert += 25;
	    }
	  
	    int[] ipx = convertIntegers(px);
	    int[] ipy = convertIntegers(py);
	    ipy = heightChanger(ipy, height);
	    ipx = convertIntegers(px);
	    g.setColor(color2);	 
	    avg = sum/(ipx.length);

	    log.debug("AVG "+avg);
	    g.setColor(color1);
	    log.debug("IPX size " + ipx.length + "ipy size " + ipy.length);
	    g.drawPolyline(ipx, ipy, ipx.length);	  
	    g.drawString("Transfer = " +py.get(0)+ "MB/s", width - 123, 15);
	    g.setColor(color2);
	    g.drawLine(0, height-avg*SCALE, width-1, height-avg*SCALE);
	    g.drawString("Średni transfer = " + String.valueOf(avg) + "MB/s", width - 123, 35);

   	
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
			tab[i] = height - tab[i]*SCALE;
		return tab;
	}
}
