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
		JPanel dsPanel0 = new JPanel();
		JPanel dsPanel1 = new JPanel();
		JPanel dsPanel2 = new JPanel();
		dsPanel0.setLayout(new GridBagLayout());
		dsPanel1.setLayout(new GridBagLayout());
		dsPanel2.setLayout(new GridBagLayout());
		dsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.ipadx=60;
		c.ipady=20;
		c.fill = GridBagConstraints.BOTH;
		Insets cInsets1 = new Insets(0,0,30,10);
		Insets cInsets2 = new Insets(0,0,0,0);
		c.insets=cInsets1;
		int stage=0;
		for(int j=1;j<=2;j++){
			for(int i=1;i<=6;i++){
				JButton b = new JButton(String.valueOf(stage+i));
				b.addActionListener(this);
				dsPanel0.add(b,c);
				c.gridx++;
			}
			stage+=6; 
			c.insets=cInsets1;
			c.gridx=0;
			c.gridy++;
		}
		c.ipadx=50;
		stage=100;
		for(int j=1;j<=2;j++){
			for(int i=1;i<=6;i++){
				JButton b = new JButton(String.valueOf(stage+i));
				b.addActionListener(this);
				dsPanel1.add(b,c);
				c.gridx++;
			}
			stage+=6; 
			c.gridx=0;
			c.gridy++;
		}
		stage=200;
		for(int j=1;j<=2;j++){
			for(int i=1;i<=6;i++){
				JButton b = new JButton(String.valueOf(stage+i));
				b.addActionListener(this);
				dsPanel2.add(b,c);
				c.gridx++;
			}
			stage+=6;
			c.gridx=0;
			c.gridy++;
		}
		
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx=0;
		c.ipady=0;
		c.insets = cInsets2;
		//c.anchor = GridBagConstraints.FIRST_LINE_START;
		dsPanel.add(dsPanel0,c);
		c.gridy = 1;
		dsPanel.add(dsPanel1,c);
		c.gridy = 2;
		dsPanel.add(dsPanel2,c);
		c.gridy = 0;
		pane.add(dsPanel,c);
		
		TitledBorder stageBorder = BorderFactory.createTitledBorder(border, "Parter");
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel0.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piêtro I");
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel1.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piêtro II");
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel2.setBorder(stageBorder);
		
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
	
	public void showRoomFrame(String buttonText){
		JFrame roomFrame = new JFrame(buttonText);
		roomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		roomFrame.setPreferredSize((new Dimension(200, 300)));
		roomFrame.setLocation(300, 300);
		roomFrame.setResizable(false);
		roomFrame.pack();
		roomFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		String buttonText = source.getText();
		System.out.println(buttonText);
		showRoomFrame(buttonText);
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
