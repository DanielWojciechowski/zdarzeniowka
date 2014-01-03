package zdarzeniowka;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;

public class GUI implements ActionListener{
	private JFrame frame;
	private JLayeredPane layeredPane;
	private JButton menuButton;
	private MenuListModel menuListModel;
	private JList<String> menuList;
	private boolean menuVisibility;
	

	public void addComponents(Container pane){

		pane.setLayout(new GridBagLayout());
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(new GridLayout(2,3));
		GridBagConstraints c = new GridBagConstraints();
		
		menuButton = new JButton("Menu");
		menuButton.addActionListener(this);
		layeredPane.add(menuButton, JLayeredPane.PALETTE_LAYER);
		menuListModel = new MenuListModel();
		menuList = new JList<>(menuListModel);
		menuList.setVisible(false);
		layeredPane.add(menuList, JLayeredPane.POPUP_LAYER);
		
		
	
		pane.add(layeredPane, c);
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == menuButton){
			menuVisibility = !menuVisibility;
			menuList.setVisible(menuVisibility);
		}	
	}
	

}
