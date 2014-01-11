package zdarzeniowka;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GUI implements ItemListener, ActionListener{
	private JPanel cardSearchPanel, cardAddingPanel;
	private JFrame frame;
	private JTabbedPane tabbedPane;
	private Border border;
	private DefaultListModel<String>[] searchListModel;
	private Font header, normal;
	private JComboBox<String> addingCB;
	
	private void initiate(){
		header = new Font("Open sans", Font.PLAIN, 20);
		normal = new Font("Open sans", Font.PLAIN, 13);
		border = BorderFactory.createEmptyBorder();
	}
	
	public void addComponents(){
		initiate();
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(normal);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel topPane = new JPanel();
		JLabel label = new JLabel("Jestem ³adnym programem.");
		JLabel label2 = new JLabel("Version 1. beta");
		label.setFont(header);
		label2.setFont(new Font("Open sans", Font.ITALIC, 10));
		topPane.setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(0, 3, 0, 0);
		topPane.add(label, c);
		c.gridy = 1;
		topPane.add(label2,c);
		c.gridy = 0;
		frame.add(topPane, c);
	
		dsView();
		addingView();
		searchView();
		reportView();
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		frame.add(tabbedPane, c);
	}
	
	public void addingView(){
		JPanel[] addingPanel = new JPanel[4], buttonPanel = new JPanel[3], 
				topPanel = new JPanel[3], bottomPanel = new JPanel[3] ;
		JLabel[] label = new JLabel[21];
		JTextField[] textField = new JTextField[21];
		JTextArea[] textArea = new JTextArea[2];
		GridBagConstraints cpane = new GridBagConstraints(), cbutton = new GridBagConstraints(),
				c = new GridBagConstraints(), ctop = new GridBagConstraints(),
				cbot = new GridBagConstraints();;
		JButton[] confirmButton = new JButton[3], clearButton = new JButton[3];
		final String OPTION1 = "Dodaj u¿ytkownika", OPTION2 = "Dodaj sprzêt u¿ytkownika", 
				OPTION3 = "Dodaj sprzêt sieciowy";
		String[] stringLabel = {"Imiê:", "Nazwisko:", "Adres e-mail:", "Id u¿ytkownika:", 
				"Numer pokoju:", "Numer albumu:", "Numer portu:", "Adres MAC:", "Adres IP:",
				"Typ:","Konfiguracja:", "Id urzadzenia:", "Id u¿ytkownika:", "Uwagi:", 
				"Adres MAC:", "Adres IP:", "Typ:", "Konfiguracja:", "Id urzadzenia:", 
				"Numer portu", "Uwagi:"};
		String comboBoxItems[] = {OPTION1, OPTION2, OPTION3};
    	Insets insets1 = new Insets(0,20,10,0), insets0 = new Insets(0,0,10,0);
		addingCB = new JComboBox<String>(comboBoxItems);
		addingCB.addItemListener(this);
		addingCB.setFont(normal);
		cardAddingPanel = new JPanel(new CardLayout());
		
		for(int i = 0; i < 4; i++){
			addingPanel[i] = new JPanel();
			addingPanel[i].setLayout(new GridBagLayout());
		}
		for(int i = 0; i < 3; i++){
			topPanel[i] = new JPanel();
			bottomPanel[i] = new JPanel();
			buttonPanel[i] = new JPanel();
			topPanel[i].setLayout(new GridBagLayout());
			bottomPanel[i].setLayout(new GridBagLayout());
			buttonPanel[i].setLayout(new GridBagLayout());
			confirmButton[i] = new JButton("Dodaj");
			clearButton[i] = new JButton("Wyczysc");
			confirmButton[i].setFont(normal);
			clearButton[i].setFont(normal);
			
			for (int j = i*7; j < ((i+1)*7); j++){
				label[j] = new JLabel(stringLabel[j]);
				label[j].setFont(normal);
				if ((i == 0 && j < 3) || (i > 0 && j < (i)*7 + 2)){
					textField[j] = new JTextField(30);
				}
				else {
					textField[j] = new JTextField(10);
				}
				textField[j].setFont(normal);
			}

        	c.gridx = 0;
        	c.gridy = 0;
        	c.anchor = GridBagConstraints.CENTER;	
			if (i == 0){
				c.insets = new Insets(50,0,0,0);
				for(int j = 0; j < 3; j++){
		        	ctop.insets = insets0;
		        	ctop.anchor = GridBagConstraints.LINE_END;
					ctop.gridx = 0;
					ctop.gridy = j;
					topPanel[i].add(label[j], ctop);
					ctop.insets = insets1;
					ctop.anchor = GridBagConstraints.LINE_START;
					ctop.gridx = 1;
					topPanel[i].add(textField[j], ctop);
				}
				for (int j = 3; j < 7; j += 2){
					cbot.insets = insets0;
					cbot.anchor = GridBagConstraints.LINE_END;
					cbot.gridx = 0;
					cbot.gridy = j;
					bottomPanel[i].add(label[i+j], cbot);
					cbot.insets = insets1;
					cbot.anchor = GridBagConstraints.LINE_START;
					cbot.gridx = 1;
					bottomPanel[i].add(textField[i+j], cbot);	
					cbot.anchor = GridBagConstraints.LINE_END;
					cbot.gridx = 2;
					cbot.gridy = j;
					bottomPanel[i].add(label[i+j+1], cbot);
					cbot.anchor = GridBagConstraints.LINE_START;
					cbot.gridx = 3;
					bottomPanel[i].add(textField[i+j+1], cbot);	
				}
	
			}
			else{
				textArea[i-1] = new JTextArea();
				textArea[i-1].setPreferredSize(new Dimension(400, 100));
		        textArea[i-1].setLineWrap(true);
				for(int j = 0; j < 2; j++){
					ctop.insets = insets0;
					ctop.weightx = 0;
					ctop.anchor = GridBagConstraints.LINE_END;
					ctop.gridx = 0;
					ctop.gridy = j;
					topPanel[i].add(label[7*i+j], ctop);
					ctop.insets = insets1;
					ctop.anchor = GridBagConstraints.LINE_START;
					ctop.gridx = 1;
					ctop.weightx = 3;
					topPanel[i].add(textField[7*i+j], ctop);				
				}

				cbot.gridwidth = 1;
				for(int j = 2; j < 6; j += 2){
					cbot.insets = insets0;
					cbot.anchor = GridBagConstraints.LINE_END;
					cbot.gridx = 0;
					cbot.gridy = j;
					bottomPanel[i].add(label[7*i+j], cbot);
					cbot.insets = insets1;
					cbot.anchor = GridBagConstraints.LINE_START;
					cbot.gridx = 1;
					bottomPanel[i].add(textField[7*i+j], cbot);	
					cbot.anchor = GridBagConstraints.LINE_END;
					cbot.gridx = 2;
					bottomPanel[i].add(label[7*i+j+1], cbot);
					cbot.anchor = GridBagConstraints.LINE_START;
					cbot.gridx = 3;
					bottomPanel[i].add(textField[7*i+j+1], cbot);	
				}

				cbot.anchor = GridBagConstraints.LINE_END;
				cbot.gridx = 0;
				cbot.gridy = 5;
				cbot.insets = insets0;
				bottomPanel[i].add(label[7*i+6], cbot);
				cbot.anchor = GridBagConstraints.LINE_START;
				cbot.gridx = 1;
				cbot.insets = insets1;
				cbot.gridwidth = 3;
				bottomPanel[i].add(textArea[i-1], cbot);
			}

			addingPanel[i+1].add(topPanel[i], c);
			c.gridy = 1;
			c.insets = new Insets(0,0,0,0);
			addingPanel[i+1].add(bottomPanel[i], c);
			
			cbutton.anchor = GridBagConstraints.FIRST_LINE_END;
        	cbutton.gridy = 0;
        	cbutton.gridx = 0;
        	cbutton.ipadx = 12;
        	cbutton.insets = new Insets(0, 10, 0, 0);
        	buttonPanel[i].add(confirmButton[i], cbutton);
        	cbutton.gridx = 1;
        	cbutton.ipadx = 0;
        	buttonPanel[i].add(clearButton[i],cbutton);
        	c.insets = new Insets(80, 0, 0, 0);
        	c.gridy = 3;
        	c.anchor = GridBagConstraints.LINE_END;
         	addingPanel[i+1].add(buttonPanel[i], c);
		}
		
        cpane.anchor = GridBagConstraints.NORTH;
        cpane.weightx = 1;
    	cpane.weighty = 1;
        cpane.ipadx = 253;
        cpane.gridx = 0;
        cpane.gridy = 0;
        cpane.insets= new Insets(30, 0, 0, 0);
        addingPanel[0].add(addingCB, cpane);
        addingPanel[0].add(cardAddingPanel, cpane);
		
        cardAddingPanel.add(addingPanel[1], OPTION1);
        cardAddingPanel.add(addingPanel[2], OPTION2);
        cardAddingPanel.add(addingPanel[3], OPTION3);
        
		tabbedPane.add("Dodaj", addingPanel[0]);
	}
	
	public void searchView(){
		searchListModel = new DefaultListModel[3];
		JList<String>[] list = new JList[3];
		JScrollPane[] scrollPane = new JScrollPane[3];
		JPanel[] searchPanel = new JPanel[4], searchPane = new JPanel[3], 
				resultPane = new JPanel[3], buttonPanel = new JPanel[3];
        JLabel[] label = new JLabel[3], label2 = new JLabel[3], result = new JLabel[3];
        JTextField[] textField = new JTextField[3];
        JButton[] searchButtonS = new JButton[3], editButtonS = new JButton[3], 
        		 deleteButtonS = new JButton[3], showButtonS = new JButton[3];
    	GridBagConstraints c = new GridBagConstraints(), cbutton = new GridBagConstraints(), 
    			cpane = new GridBagConstraints(), csearch = new GridBagConstraints(), 
    			cresult = new GridBagConstraints();
    	Insets insets1 = new Insets(0,20,10,0), insets0 = new Insets(0,0,10,0);
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
        	cb[i].setFont(normal);
        	if (i > 0) {
        		cb[i].addActionListener(this);
        	}
    		searchPanel[i] = new JPanel();
    		searchPanel[i].setLayout(new GridBagLayout());
        }        
        
        for (int i = 0; i < 3; i++){
    		searchListModel[i] = new DefaultListModel<String>();
    		list[i] = new JList<String>(searchListModel[i]);
    		list[i].setPrototypeCellValue("Mam nadzieje, ze ta   "
    				+ " glupia funkcja zadziala mehehehhehe        ");
    		list[i].setFont(normal);
    		scrollPane[i] = new JScrollPane(list[i]);  
    		
        	label[i] = new JLabel("Wybierz:");
        	label2[i] = new JLabel("Wpisz:");
        	result[i] = new JLabel("Wyniki:");
        	textField[i] = new JTextField(30);
        	searchButtonS[i] = new JButton("Szukaj!");
        	editButtonS[i] = new JButton("Edytuj");
        	deleteButtonS[i] = new JButton("Usun");
        	showButtonS[i] = new JButton("Wyœwietl");

        	buttonPanel[i] = new JPanel();
        	searchPane[i] = new JPanel();
        	resultPane[i] = new JPanel();
        	buttonPanel[i].setLayout(new GridBagLayout());
        	searchPane[i].setLayout(new GridBagLayout());
        	resultPane[i].setLayout(new GridBagLayout());
        	
        	label[i].setFont(normal);
        	label2[i].setFont(normal);
        	result[i].setFont(normal);
        	deleteButtonS[i].setFont(normal);
        	searchButtonS[i].setFont(normal);
        	editButtonS[i].setFont(normal);
        	showButtonS[i].setFont(normal);
        	textField[i].setFont(normal);
        	       	
        	c.insets = new Insets(50,0,0,0);
        	c.gridx = 0;
        	c.gridy = 0;
        	c.anchor = GridBagConstraints.CENTER;
        	csearch.insets = insets0;
        	csearch.anchor = GridBagConstraints.LINE_END;
        	csearch.gridx = 0;
        	csearch.gridy = 0;
        	searchPane[i].add(label[i], csearch);
        	csearch.anchor = GridBagConstraints.LINE_START;
        	csearch.gridx = 1;
        	csearch.insets = insets1;
        	if(i == 0){
        		csearch.ipadx = 243;
        	}
        	else {
        		csearch.ipadx = 270;
        	}
        	searchPane[i].add(cb[i+1], csearch);
        	csearch.ipadx = 0;
        	csearch.gridx = 0;
        	csearch.gridy = 1;
        	csearch.insets = new Insets(0,0,0,0);
        	csearch.anchor = GridBagConstraints.LINE_END;
        	searchPane[i].add(label2[i], csearch);
        	csearch.gridx = 1;
        	csearch.insets = insets1;
        	csearch.ipady = 6;
        	csearch.anchor = GridBagConstraints.LINE_START;
        	searchPane[i].add(textField[i], csearch);
        	csearch.ipadx = 0;
        	csearch.ipady = 0;
        	csearch.insets = insets0;
        	csearch.anchor = GridBagConstraints.LINE_END;
        	csearch.gridy = 2;
        	csearch.gridx = 1;
        	searchPane[i].add(searchButtonS[i], csearch);
        	c.gridy = 1;
        	searchPanel[i+1].add(searchPane[i], c);        	
        	
        	c.insets = new Insets(0,18,0,0);
        	cresult.gridheight = 0;
        	cresult.ipadx = 0;
        	cresult.ipady = 0;
        	cresult.anchor = GridBagConstraints.LINE_END;
        	cresult.gridx = 0;
        	cresult.gridy = 0;
        	cresult.insets = new Insets(0, 0, 0, 0);
        	resultPane[i].add(result[i], cresult);
        	cresult.insets = insets1;
        	cresult.anchor = GridBagConstraints.LINE_START;
        	cresult.gridx = 1;
        	cresult.ipady = 75;
        	resultPane[i].add(scrollPane[i], cresult);
        	c.gridy = 2;
        	c.insets = new Insets(10,14,0,0);
        	searchPanel[i+1].add(resultPane[i], c);
        	
        	cbutton.anchor = GridBagConstraints.FIRST_LINE_END;
        	cbutton.gridy = 0;
        	cbutton.gridx = 0;
        	cbutton.ipadx = 0;
        	cbutton.insets = new Insets(0, 10, 0, 0);
        	buttonPanel[i].add(showButtonS[i], cbutton);
        	cbutton.gridx = 1;
        	cbutton.ipadx = 15;
        	buttonPanel[i].add(editButtonS[i],cbutton);
        	cbutton.gridx = 2;
        	cbutton.ipadx = 20;
        	buttonPanel[i].add(deleteButtonS[i], cbutton);
        	
        	c.insets = new Insets(0, 0, 0, 0);
        	c.gridy = 3;
        	c.anchor = GridBagConstraints.LINE_END;
         	searchPanel[i+1].add(buttonPanel[i], c);
        }
        
        cpane.anchor = GridBagConstraints.NORTH;
        cpane.weightx = 1;
    	cpane.weighty = 1;
        cpane.ipadx = 232;
        cpane.gridx = 0;
        cpane.gridy = 0;
        cpane.insets= new Insets(30, 0, 0, 0);
        searchPanel[0].add(cb[0], cpane);
        searchPanel[0].add(cardSearchPanel, cpane);
		
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
		Insets cInsets1 = new Insets(0,0,30,10), cInsets2 = new Insets(0,0,10,10), 
				cInsets3 = new Insets(0,0,0,0), cInsets4 = new Insets(0,0,10,0);
		int stage=0;
		for(int j=1;j<=2;j++){
			if (j == 2) {
				c.insets = cInsets2;
			}
			else {
				c.insets = cInsets1;
			}
			for(int i=1;i<=6;i++){
				JButton b = new JButton(String.valueOf(stage+i));
				b.addActionListener(this);
				dsPanel0.add(b,c);
				c.gridx++;
			}
			stage+=6; 
			c.gridx=0;
			c.gridy++;
		}
		c.ipadx=50;
		stage=100;
		
		for(int j=1;j<=2;j++){
			if (j == 2) {
				c.insets = cInsets2;
			}
			else {
				c.insets = cInsets1;
			}
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
			if (j == 2) {
				c.insets = cInsets2;
			}
			else {
				c.insets = cInsets1;
			}
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
		c.insets = cInsets3;
		dsPanel.add(dsPanel0,c);
		c.gridy = 1;
		c.insets = cInsets4;
		dsPanel.add(new JSeparator(JSeparator.HORIZONTAL),c);
		c.gridy = 2;
		c.insets = cInsets3;
		dsPanel.add(dsPanel1,c);
		c.insets = cInsets4;
		c.gridy = 3;
		dsPanel.add(new JSeparator(JSeparator.HORIZONTAL),c);
		c.gridy = 4;
		c.insets = cInsets3;
		dsPanel.add(dsPanel2,c);
		c.gridy = 0;
		c.gridy = 0;
		c.insets = cInsets4;

		//pane.add(dsPanel,c);
		TitledBorder stageBorder = BorderFactory.createTitledBorder(border, "Parter");
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		stageBorder.setTitleFont(normal);
		dsPanel0.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piêtro I");
		stageBorder.setTitleFont(normal);
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel1.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piêtro II");
		stageBorder.setTitleFont(normal);
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel2.setBorder(stageBorder);
		tabbedPane.add("Plan DS", dsPanel);	
	}
	
	public void showGUI(){
		frame = new JFrame("System ewidencyjny sieci komputerowej w DS - AC&DW"); 
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("cat.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize((new Dimension(600, 500))); 
		frame.setLocation(250, 50);
		
		addComponents();
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);		
	}

	public void showRoomFrame(String buttonText){
		RoomJFrame roomFrame = new RoomJFrame("Pokój nr " + buttonText);
		roomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		roomFrame.setPreferredSize((new Dimension(200, 130)));
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
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		if (source == addingCB){
			CardLayout cl = (CardLayout)(cardAddingPanel.getLayout());
	        cl.show(cardAddingPanel, (String)e.getItem());
	        
		}
		else
		{
			CardLayout cl = (CardLayout)(cardSearchPanel.getLayout());
	        cl.show(cardSearchPanel, (String)e.getItem());
	        for(int i = 0; i < 3; i++){
	        	searchListModel[i].clear();
	        }
		}
	}
	

}
