package zdarzeniowka;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;


public class GUI {
	JFrame frame;

	public void addComponents(Container pane){
		
	}
	
	public void showGUI(){
		frame = new JFrame("System ewidencyjny sieci komputerowej w DS - AC&DW");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(950, 700));
		addComponents(frame);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		
	}
	

}
