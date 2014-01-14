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

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private Border border;
	private Font header, normal;
	
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
		JAddPanel addPanel = new JAddPanel();
		tabbedPane.add("Dodaj", addPanel);
	}
	
	public void searchView(){
		JSearchPanel searchPanel = new JSearchPanel();
		tabbedPane.add("Wyszukaj", searchPanel);
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

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
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
			
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
	}
	

}
