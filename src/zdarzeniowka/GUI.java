package zdarzeniowka;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
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
		File fontFile1 = new File("font/OpenSans-Regular.ttf"),
				fontFile2 = new File("font/OpenSans-Italic.ttf");
		try {
			Font fontH = Font.createFont(Font.TRUETYPE_FONT, fontFile1);
			header = fontH.deriveFont(20f);
			Font fontN = Font.createFont(Font.TRUETYPE_FONT, fontFile2);
			normal = fontN.deriveFont(12f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		
		
		new Font("Open sans", Font.PLAIN, 20);
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
		JLabel label = new JLabel("Jestem ładnym programem.");
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
				panel = new JPanel[3];	
		GridBagConstraints capane = new GridBagConstraints(), cbutton = new GridBagConstraints(),
				c = new GridBagConstraints();
		JButton[] confirmButton = new JButton[3], clearButton = new JButton[3];
		final String OPTION1 = "Dodaj użytkownika", OPTION2 = "Dodaj sprzęt użytkownika", 
				OPTION3 = "Dodaj sprzęt sieciowy";
		String comboBoxItems[] = {OPTION1, OPTION2, OPTION3};
		addingCB = new JComboBox<String>(comboBoxItems);
		addingCB.addItemListener(this);
		addingCB.setFont(normal);
		cardAddingPanel = new JPanel(new CardLayout());
		
		for(int i = 0; i < 4; i++){
			addingPanel[i] = new JPanel();
			addingPanel[i].setLayout(new GridBagLayout());
		}
		panel[0] = new JUserPanel();
		panel[1] = new JUserDevicePanel();
		panel[2] = new JNetworkDevicePanel();
		
		for(int i = 0; i < 3; i++){
			buttonPanel[i] = new JPanel();
			buttonPanel[i].setLayout(new GridBagLayout());
			confirmButton[i] = new JButton("Dodaj");
			clearButton[i] = new JButton("Wyczysc");
			confirmButton[i].setFont(normal);
			clearButton[i].setFont(normal);
			
			cbutton.anchor = GridBagConstraints.LINE_END;
        	cbutton.gridy = 0;
        	cbutton.gridx = 0;
        	cbutton.ipadx = 12;
        	cbutton.insets = new Insets(0, 10, 0, 0);
        	buttonPanel[i].add(confirmButton[i], cbutton);
        	cbutton.gridx = 1;
        	cbutton.ipadx = 0;
        	buttonPanel[i].add(clearButton[i],cbutton);
        
        	c.insets = new Insets(50,0,0,0);
        	c.gridx = 0;
        	c.gridy = 0;
        	c.anchor = GridBagConstraints.CENTER;	
			addingPanel[i+1].add(panel[i], c);
			
        	if (i == 0) {
        		c.insets = new Insets(20, 0, 152, 0);
        	}
        	else {
        		c.insets = new Insets(20, 0, 20, 0);
        	}
        	c.gridy = 2;
        	c.anchor = GridBagConstraints.LINE_END;
         	addingPanel[i+1].add(buttonPanel[i], c);
		}
		
        capane.anchor = GridBagConstraints.NORTH;
        capane.weightx = 1;
    	capane.weighty = 1;
        capane.ipadx = 253;
        capane.gridx = 0;
        capane.gridy = 0;
        capane.insets= new Insets(30, 0, 0, 0);
        addingPanel[0].add(addingCB, capane);
        addingPanel[0].add(cardAddingPanel, capane);
		
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
        JButton[] searchButtonS = new JButton[3], 
        		 deleteButtonS = new JButton[3], showButtonS = new JButton[3];
    	GridBagConstraints c = new GridBagConstraints(), cbutton = new GridBagConstraints(), 
    			cpane = new GridBagConstraints(), csearch = new GridBagConstraints(), 
    			cresult = new GridBagConstraints();
    	Insets insets1 = new Insets(0,20,10,0), insets0 = new Insets(0,0,10,0);
		final String OPTION1 = "Wyszukaj uzytkownika", OPTION2 = "Wyszukaj sprzet uzytkownika", 
				OPTION3 = "Wyszukaj sprzet sieciowy";
		String[] comboBoxItems = {OPTION1, OPTION2, OPTION3}, 
				comboBox1 = {"Imie", "Nazwisko", "Numer pokoju", "Numer albumu"}, 
				comboBox23 = {"Adres MAC", "Adres IP"};
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
        	deleteButtonS[i] = new JButton("Usun");
        	showButtonS[i] = new JButton("Wyświetl");

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
        	csearch.insets = insets0;
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
		JPanel reportPanel = new JPanel(), cbPane = new JPanel(),
				buttonPane = new JPanel();
		JChart report = new JChart(500, 600);
		JScrollPane scrollPane = new JScrollPane(report);
		JButton generateButton = new JButton("Generuj"), 
				saveButton = new JButton("Zapisz do pliku");
		GridBagConstraints crpane = new GridBagConstraints(), cbpane = new GridBagConstraints(),
				cbutton = new GridBagConstraints();
		String[] comboBox0 = {"Jakis okres ", "Jakis okres"},
				comboBox1 = {"Jakies dane", "Jakies dane"};
        JComboBox<String>[] cb = new JComboBox[2];
        JLabel[] label = new JLabel[2];
    	Insets insets1 = new Insets(0,20,10,0), insets0 = new Insets(0,0,10,0);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        reportPanel.setLayout(new GridBagLayout());
        cbPane.setLayout(new GridBagLayout());
        report.setLayout(new GridBagLayout());
        buttonPane.setLayout(new GridBagLayout());
        saveButton.setFont(normal);
        generateButton.setFont(normal);
        cb[0] = new JComboBox<String>(comboBox0);
        cb[1] = new JComboBox<String>(comboBox1);
        cb[0].setFont(normal);
        cb[1].setFont(normal);
        cb[0].setPrototypeDisplayValue("Bla bla bla bla bla bla bla bla bla bla bla blaBla bla bla bla bla b");
        cb[1].setPrototypeDisplayValue("Bla bla bla bla bla bla bla bla bla bla bla blaBla bla bla bla bla b");
        label[0] = new JLabel("Wybierz okres:");
        label[1] = new JLabel("Wybierz dane: ");
        label[0].setFont(normal);
        label[1].setFont(normal);
        
        for (int i = 0; i < 2; i++)
        	{
        	cbpane.insets = insets0;
        	cbpane.gridy = i;
        	cbpane.gridx = 0;
    		cbpane.anchor = GridBagConstraints.LINE_END;
    		cbPane.add(label[i], cbpane);
    		cbpane.insets = insets1;
    		cbpane.anchor = GridBagConstraints.LINE_START;
    		cbpane.gridx = 1;
    		cbPane.add(cb[i], cbpane);
        	}
		
        cbutton.insets = insets0;
        cbutton.ipadx = 30;
        buttonPane.add(generateButton, cbutton);
        cbutton.insets = insets1;
        cbutton.gridx = 1;
        cbutton.ipadx = 0;
        buttonPane.add(saveButton, cbutton);
        
        crpane.insets = new Insets(0, 0, 0, 0);
        crpane.anchor = GridBagConstraints.NORTH;
        reportPanel.add(cbPane, crpane);
        crpane.insets = new Insets(10,0,0,0);
        crpane.gridy = 1;
        crpane.anchor = GridBagConstraints.LINE_END;
        reportPanel.add(buttonPane, crpane);
        crpane.gridy = 2;
        //crpane.gridheight = 2;
        crpane.anchor = GridBagConstraints.CENTER;
        reportPanel.add(scrollPane, crpane);
            
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
		stageBorder = BorderFactory.createTitledBorder(border, "Piętro I");
		stageBorder.setTitleFont(normal);
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel1.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piętro II");
		stageBorder.setTitleFont(normal);
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel2.setBorder(stageBorder);
		tabbedPane.add("Plan DS", dsPanel);	
	}
	
	public void showGUI(){
		frame = new JFrame("System ewidencyjny sieci komputerowej w DS - AC&DW"); 
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icons/cat.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize((new Dimension(600, 500))); 
		frame.setLocation(250, 50);
		
		addComponents();
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);		
	}

	public void showRoomFrame(String buttonText){
		JRoomFrame roomFrame = new JRoomFrame("Pokój nr " + buttonText);
		roomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		roomFrame.setLocation(400, 200);
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
