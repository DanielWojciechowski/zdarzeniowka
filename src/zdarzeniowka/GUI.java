package zdarzeniowka;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI implements ItemListener, ActionListener{
	JPanel cardSearchPanel;
	private JFrame frame;
	private JTabbedPane tabbedPane;
	private Border border;

	public void addComponents(Container pane){
		tabbedPane = new JTabbedPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		
		dsView();
		addingView();
		searchView();
		reportView();
		
		pane.add(tabbedPane, c);
	}
	
	public void addingView(){
		JPanel addingPanel = new JPanel();
		addingPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		tabbedPane.add("Dodaj", addingPanel);
	}
	
	public void searchView(){
		JPanel searchPanel = new JPanel();
		JPanel searchPanel1 = new JPanel();
		JPanel searchPanel2 = new JPanel();
		JPanel searchPanel3 = new JPanel();
		
		cardSearchPanel = new JPanel(new CardLayout());
		final String OPTION1 = "Wyszukaj uzytkownika", OPTION2 = "Wyszukaj sprzet uzytkownika", OPTION3 = "Wyszukaj sprzet sieciowy";		
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { OPTION1, OPTION2, OPTION3};
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        searchPanel.add(comboBoxPane, BorderLayout.PAGE_START);
        searchPanel.add(cardSearchPanel, BorderLayout.CENTER);
               
		tabbedPane.add("Wyszukaj", searchPanel);
	}
	
	public void reportView(){
		JPanel reportPanel = new JPanel();
		reportPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		tabbedPane.add("Raporty", reportPanel);
	}
	public void dsView(){
		JPanel dsPanel = new JPanel();
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
		dsPanel.add(dsPanel0,c);
		c.gridy = 1;
		dsPanel.add(dsPanel1,c);
		c.gridy = 2;
		dsPanel.add(dsPanel2,c);
		c.gridy = 0;

		//pane.add(dsPanel,c);
		

		TitledBorder stageBorder = BorderFactory.createTitledBorder(border, "Parter");
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel0.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piêtro I");
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel1.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piêtro II");
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel2.setBorder(stageBorder);
		tabbedPane.add("Plan DS", dsPanel);	
	}
	
	public void showGUI(){
		frame = new JFrame("System ewidencyjny sieci komputerowej w DS - AC&DW");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize((new Dimension(600, 600))); 
		frame.setLocation(250, 50);
		addComponents(frame);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cardSearchPanel.getLayout());
        cl.show(cardSearchPanel, (String)e.getItem());
		
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

}
