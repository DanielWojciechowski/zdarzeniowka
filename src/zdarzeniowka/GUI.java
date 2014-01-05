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

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI implements ItemListener, ActionListener{
	private JPanel cardSearchPanel;
	private JFrame frame;
	private JTabbedPane tabbedPane;
	private Border border;
	private int choice;

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
		cardSearchPanel = new JPanel(new CardLayout());
		JPanel searchPanel = new JPanel();
		JPanel searchPanel1 = new JPanel();
		JPanel searchPanel2 = new JPanel();
		JPanel searchPanel3 = new JPanel();
		searchPanel.setLayout(new GridBagLayout());
		searchPanel1.setLayout(new GridBagLayout());
		searchPanel2.setLayout(new GridBagLayout());
		searchPanel3.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		final String OPTION1 = "Wyszukaj uzytkownika", OPTION2 = "Wyszukaj sprzet uzytkownika", 
				OPTION3 = "Wyszukaj sprzet sieciowy";
		String comboBoxItems[] = {OPTION1, OPTION2, OPTION3};
        String comboBox1[] = {"Imie", "Nazwisko", "Numer pokoju", "Numer albumu"};
        String comboBox23[] = {"Adres MAC", "Adres IP"};
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
        JComboBox<String> cb1 = new JComboBox<String>(comboBox1);
        JComboBox<String> cb2 = new JComboBox<String>(comboBox23);
        JComboBox<String> cb3 = new JComboBox<String>(comboBox23);
        cb.setEditable(false);
        cb.addItemListener(this);
        cb1.setEditable(false);
        cb1.addActionListener(this);
        cb2.setEditable(false);
        cb2.addActionListener(this);
        cb3.setEditable(false);
        cb3.addActionListener(this);
        
        
        JLabel label1 = new JLabel("Po czym chcesz szukac?"), label2 = new JLabel("Po czym chcesz szukac?"), 
        		label3 = new JLabel("Po czym chcesz szukac?"),
        		result1 = new JLabel("Lista wynikow"), result2 = new JLabel("Lista wynikow"), 
        		result3 = new JLabel("Lista wynikow");
     	JTextField textField1 = new JTextField(15), textField2 = new JTextField(15), 
     			textField3 = new JTextField(15);
     	JButton searchButton1 = new JButton("Szukaj!"), searchButton2 = new JButton("Szukaj!"), 
     			searchButton3 = new JButton("Szukaj!");
     	//Wyszukaj uzytkownika
     	c.gridy = 1;
     	c.insets = new Insets(10, 10, 0, 0);
     	searchPanel1.add(label1, c);
     	c.gridy = 2;
     	searchPanel1.add(cb1, c);
     	c.gridy = 3;
     	c.ipady = 10;
     	searchPanel1.add(textField1,c);
     	c.ipady = 0;
     	c.gridy = 4;
     	searchPanel1.add(searchButton1,c);
     	c.gridy = 5;
     	searchPanel1.add(result1, c);
     	//Wyszukaj sprzet uzytkownika
     	c.gridy = 1;
     	searchPanel2.add(label2, c);
     	c.gridy = 2;
     	searchPanel2.add(cb2, c);
     	c.gridy = 3;
     	c.ipady = 10;
     	searchPanel2.add(textField2,c);
     	c.ipady = 0;
     	c.gridy = 4;
     	searchPanel2.add(searchButton2,c);
     	c.gridy = 5;
     	searchPanel2.add(result2, c);
    	//Wyszukaj sprzet
     	c.gridy = 1;
     	searchPanel3.add(label3, c);
     	c.gridy = 2;
     	searchPanel3.add(cb3, c);
     	c.gridy = 3;
     	c.ipady = 10;
     	searchPanel3.add(textField3,c);
     	c.ipady = 0;
     	c.gridy = 4;
     	searchPanel3.add(searchButton3,c);
     	c.gridy = 5;
     	searchPanel3.add(result3, c);
        
        
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        searchPanel.add(cb, c);
        c.gridy = 1;
        searchPanel.add(cardSearchPanel, c);
        
        cardSearchPanel.add(searchPanel1, OPTION1);
        cardSearchPanel.add(searchPanel2, OPTION2);
        cardSearchPanel.add(searchPanel3, OPTION3);
        
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
		Object source = e.getSource();
		if (source instanceof JButton){
			String buttonText = ((JButton) source).getText();
			System.out.println(buttonText);
			showRoomFrame(buttonText);
		}
		else if (source instanceof JComboBox<?>){
			String name = (String)((JComboBox<?>) source).getSelectedItem();
			System.out.println(name);
		}
			
	}

}
