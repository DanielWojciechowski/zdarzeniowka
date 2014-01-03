package zdarzeniowka;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI implements ActionListener, MouseListener{
	private JFrame frame;
	private MenuListModel menuListModel;
	private JList<String> menuList;
	private JPanel menuPanel, dsPanel;
	private Border border;
	private int choice = -1;

	public void addComponents(Container pane){
		border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		pane.setLayout(new GridBagLayout());
		menuView(pane);
		dsView(pane);
	}
	
	public void menuView(Container pane){
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		menuListModel = new MenuListModel();
		menuList = new JList<String>(menuListModel);
		menuList.addMouseListener(this);
		menuPanel.add(menuList,c);
		
		TitledBorder menuBorder = BorderFactory.createTitledBorder(border, "Menu");
		menuBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		menuPanel.setBorder(menuBorder);
		
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		c.insets = new Insets(10,0,0,0);
		c.gridheight = 3;
		c.gridx = 1;
		c.gridy = 0;
		pane.add(menuPanel, c);

	}
	
	public void dsView(Container pane){
		dsPanel = new JPanel();
		dsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.ipadx=50;
		c.ipady=20;
		c.fill = GridBagConstraints.BOTH;
		Insets cInsets1 = new Insets(0,0,30,10);
		Insets cInsets2 = new Insets(0,0,50,10);
		Insets cInsets3 = new Insets(0,0,0,10);
		c.insets=cInsets1;
		int stage=0;
		for(int j=1;j<=6;j++){
			for(int i=1;i<=6;i++){
				dsPanel.add(new JButton(String.valueOf(stage+i)),c);
				c.gridx++;
			}
			if(stage==6){ stage=100; c.insets=cInsets1;}
			else if(stage==106){ stage=200; c.insets=cInsets1;}
			else if(stage==200){ stage+=6; c.insets=cInsets3;}
			else{ stage+=6; c.insets=cInsets2;}
			c.gridx=0;
			c.gridy++;
		}
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		pane.add(dsPanel,c);
	}
	
	public void showGUI(){
		frame = new JFrame("System ewidencyjny sieci komputerowej w DS - AC&DW");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize((new Dimension(1000, 600)));
		frame.setLocation(100, 30);
		addComponents(frame);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		choice = 2;
		System.out.println(choice);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		choice = menuList.getSelectedIndex(); 
		System.out.println(choice);
	}


	@Override
	public void mouseEntered(MouseEvent e) {}


	@Override
	public void mouseExited(MouseEvent e) {}
	

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
