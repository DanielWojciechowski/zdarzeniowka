package zdarzeniowka;

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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUI implements ItemListener, ActionListener{
	private JPanel cardSearchPanel;
	private JFrame frame;
	private JTabbedPane tabbedPane;
	private Border border;
	private DefaultListModel<String>[] searchListModel;
	boolean meh = true;

	public void addComponents(Container pane){
		tabbedPane = new JTabbedPane();
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
		searchListModel = new DefaultListModel[3];
		JList<String>[] list = new JList[3];
		JScrollPane[] scrollPane = new JScrollPane[3];
		JPanel[] searchPanel = new JPanel[4];
		GridBagConstraints c = new GridBagConstraints();
		final String OPTION1 = "Wyszukaj uzytkownika", OPTION2 = "Wyszukaj sprzet uzytkownika", 
				OPTION3 = "Wyszukaj sprzet sieciowy";
		String comboBoxItems[] = {OPTION1, OPTION2, OPTION3};
        String comboBox1[] = {"Imie", "Nazwisko", "Numer pokoju", "Numer albumu"};
        String comboBox23[] = {"Adres MAC", "Adres IP"};
        JComboBox<String>[] cb = new JComboBox[4];
		cardSearchPanel = new JPanel(new CardLayout());
        cb[0]= new JComboBox<String>(comboBoxItems);
        cb[1] = new JComboBox<String>(comboBox1);
        cb[2] = new JComboBox<String>(comboBox23);
        cb[3] = new JComboBox<String>(comboBox23);
        cb[0].addItemListener(this);
        
        for (int i = 0; i < 4; i++){
        	if (i > 0) {
        		cb[i].addActionListener(this);
        	}
        	if (i < 3) {
        		searchListModel[i] = new DefaultListModel<String>();
        		list[i] = new JList<String>(searchListModel[i]);
        		list[i].setPrototypeCellValue("Mam nadzieje, ze ta glupia funkcja zadziala");
                scrollPane[i] = new JScrollPane(list[i]);
                
        	}
    		searchPanel[i] = new JPanel();
    		searchPanel[i].setLayout(new GridBagLayout());
        	cb[i].setEditable(false);
        
        }        
        
        JLabel[] label = new JLabel[3], result = new JLabel[3];
        JTextField[] textField = new JTextField[3];
         JButton[] searchButtonS = new JButton[3], editButtonS = new JButton[3], 
        		 deleteButtonS = new JButton[3];
        JPanel[] buttonPanel = new JPanel[3];
    	GridBagConstraints cbutton = new GridBagConstraints();
        for (int i = 0; i < 3; i++){
        	label[i] = new JLabel("Po czym chcesz szukac?");
        	result[i] = new JLabel("Lista wynikow");
        	textField[i] = new JTextField(15);
        	searchButtonS[i] = new JButton("Szukaj!");
        	editButtonS[i] = new JButton("Edytuj");
        	deleteButtonS[i] = new JButton("Usun");
        	/*searchButtonS[i].setEnabled(false);
        	editButtonS[i].setEnabled(false);
        	deleteButtonS[i].setEnabled(false);*/
        	buttonPanel[i] = new JPanel();
        	buttonPanel[i].setLayout(new GridBagLayout());
        	
        	cbutton.anchor = GridBagConstraints.CENTER;
        	cbutton.gridy = 0;
        	cbutton.gridx = 0;
        	buttonPanel[i].add(editButtonS[i], cbutton);
        	cbutton.gridx = 1;
        	buttonPanel[i].add(deleteButtonS[i],cbutton);
        	c.anchor = GridBagConstraints.NORTH;
        	c.gridx = 0;
        	c.gridy = 1;
        	c.ipadx = 10;
        	c.ipady = 0;
        	c.gridwidth = 0;
            c.insets= new Insets(50, 10, 0, 0);
         	searchPanel[i+1].add(label[i], c);
            c.insets= new Insets(10, 10, 0, 0);
         	c.gridy = 2;
         	searchPanel[i+1].add(cb[i+1], c);
         	c.gridy = 3;
         	c.ipady = 10;
         	searchPanel[i+1].add(textField[i],c);
         	c.ipady = 0;
         	c.gridy = 4;
         	searchPanel[i+1].add(searchButtonS[i],c);
         	c.gridy = 5;
         	searchPanel[i+1].add(result[i], c);
         	c.gridy = 6;
         	searchPanel[i+1].add(scrollPane[i], c);
         	c.gridy = 7;
         	searchPanel[i+1].add(buttonPanel[i], c);
        }
        
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1;
    	c.weighty = 1;
        c.ipadx = 300;
        c.gridx = 0;
        c.gridy = 0;
        c.insets= new Insets(20, 40, 0, 0);
        searchPanel[0].add(cb[0], c);
        searchPanel[0].add(cardSearchPanel, c);
        
        cardSearchPanel.add(searchPanel[1], OPTION1);
        cardSearchPanel.add(searchPanel[2], OPTION2);
        cardSearchPanel.add(searchPanel[3], OPTION3);
        
		tabbedPane.add("Wyszukaj", searchPanel[0]);
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
        for(int i = 0; i < 3; i++){
        	searchListModel[i].clear();
        }
		
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
			searchListModel[1].addElement(name);
		}
			
	}

}
