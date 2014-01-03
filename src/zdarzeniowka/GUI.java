package zdarzeniowka;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == menuButton){
			menuVisibility = !menuVisibility;
			menuList.setVisible(menuVisibility);
		}	
	}
	

}
