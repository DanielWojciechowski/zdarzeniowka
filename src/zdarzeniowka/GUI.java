package zdarzeniowka;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;


public class GUI {
	JFrame frame;

	public void addComponents(Container pane){
		dsView(pane);
	}
	
	public void showGUI(){
		frame = new JFrame("System ewidencyjny sieci komputerowej w DS - AC&DW");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(850, 700));
		frame.setLocation(100, 30);
		
		addComponents(frame);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
	public void dsView(Container pane){
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.ipadx=50;
		c.ipady=50;
		c.fill = GridBagConstraints.BOTH;
		Insets cInsets1 = new Insets(0,0,30,10);
		Insets cInsets2 = new Insets(0,0,50,10);
		c.insets=cInsets1;
		
		int stage=0;
		for(int j=1;j<=6;j++){
			for(int i=1;i<=6;i++){
				pane.add(new JButton(String.valueOf(stage+i)),c);
				c.gridx++;
			}
			if(stage==6){ stage=100; c.insets=cInsets1;}
			else if(stage==106){ stage=200; c.insets=cInsets1;}
			else{ stage+=6; c.insets=cInsets2;}
			
			c.gridx=0;
			c.gridy++;
					
		}
		
	}
	

}
