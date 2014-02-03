package pl.edu.wat.wcy.pz.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * Klasa wyświetla główne okno programu.
 * Odpowiada za ustawienia kolorów, fontów, ikon w programie oraz powoływane są 
 * w niej instancje obiektów składające się na całościowe GUI.
 * 
 * @author Anna Cierniewska
 * 
 */
public class GUI implements ActionListener{
	private static final Color COLOR = Color.white; 
	private static JLabel infoLabel, timeLabel;
	private static JAddPanel addPanel;
	private static JSearchPanel searchPanel;
	private static JDSPanel dsPanel;
	private static JReportPanel reportPanel;
	private static JTabbedPane tabbedPane;
	private static String path = "icons/cat.png";
	private JFrame frame;
	private JPanel botPanel;
	private Font header, normal, italic;
	private Icon add, search, report, ds;
	private JButton button;
	private JSeparator sep;
	private Controller cont = new Controller();
	
	private void initiate(){
		File fontFile1 = new File("font/OpenSans-Regular.ttf"),
				fontFile2 = new File("font/OpenSans-Italic.ttf");
		try {
			Font fontH = Font.createFont(Font.TRUETYPE_FONT, fontFile1);
			header = fontH.deriveFont(20f);
			Font fontN = Font.createFont(Font.TRUETYPE_FONT, fontFile1);
			normal = fontN.deriveFont(13f);
			Font fontI = Font.createFont(Font.TRUETYPE_FONT, fontFile2);
			italic = fontI.deriveFont(10f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}
			
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(normal);
		dsPanel = new JDSPanel(normal);
		addPanel= new JAddPanel(normal, dsPanel);
		searchPanel = new JSearchPanel(normal, dsPanel);
		reportPanel = new JReportPanel(normal);
		
		ds = new ImageIcon("icons/ds.png");
		add = new ImageIcon("icons/add.png");
		search = new ImageIcon("icons/search.png");
		report = new ImageIcon("icons/chart.png");
		
		button = new JButton(new ImageIcon("icons/ask.png"));
		button.setHorizontalAlignment(SwingConstants.CENTER );
		button.setVerticalAlignment(SwingConstants.CENTER );
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		button.setFocusPainted(false); 
		button.addActionListener(this);
		
		dsPanel.setComponentsBackground(COLOR);
		addPanel.setComponentsBackground(COLOR);
		searchPanel.setComponentsBackground(COLOR);
		reportPanel.setColor(COLOR);
		
		infoLabel = new JLabel(" ");
		infoLabel.setFont(normal);
		timeLabel = new JLabel("");
		timeLabel.setFont(normal);
		cont.setTimeLabel(timeLabel);
		//infoLabel.setBorder(BorderFactory.createLineBorder(Color.pink));
		GridBagConstraints b = new GridBagConstraints();
		b.anchor = GridBagConstraints.CENTER;
		Dimension d = new Dimension(720, 30);
		botPanel = new JPanel(new GridBagLayout());
		botPanel.setPreferredSize(d);
		botPanel.setMinimumSize(d);
		botPanel.setMaximumSize(d);
		botPanel.add(infoLabel, b);
		b.gridx = 1;
		b.anchor = GridBagConstraints.LINE_END;
		botPanel.add(timeLabel, b);
		botPanel.setOpaque(false);
		
		sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		sep.setOpaque(false);
		frame.setLayout(new GridBagLayout());
	}
	
	private void addComponents(){
		initiate();
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel topPane = new JPanel();
		JLabel label = new JLabel("System ewidencyjny sieci komputerowej w DS"), 
				label2 = new JLabel("Version 1.1 stable");
		
		label.setFont(header);
		label2.setFont(italic);
		topPane.setLayout(new GridBagLayout());
		topPane.setOpaque(false);
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(2, 3, 0, 0);
		topPane.add(label, c);
		c.gridx = 1;
		c.insets = new Insets(7, 245, 0, 0);
		topPane.add(button, c);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 3, 0, 0);
		topPane.add(label2,c);
		c.gridy = 0;
		c.insets = new Insets(1, 16, 0, 0);
		frame.add(topPane, c);
		
		tabbedPane.addTab("Plan DS", ds, dsPanel,"Wyświetl widok akademika.");	
		tabbedPane.addTab("Dodaj", add, addPanel, "Dodaj użytkownika lub sprzęt.");
		tabbedPane.addTab("Wyszukaj", search, searchPanel, "Wyszukaj użytkownika lub sprzęt.");
		tabbedPane.addTab("Raporty", report, reportPanel, "Monitoruj sieć i wegeneruj raport.");
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		c.gridheight = 5;
		c.insets = new Insets(4, 0, 0, 0);
		frame.add(tabbedPane, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 6;
		c.gridwidth = 2;
		frame.add(sep,c);
		c.insets = new Insets(0, 0, 0, 0);
		c.gridy = 7;
		frame.add(botPanel, c);
	}
	
	public void showGUI(){
		Dimension d = new Dimension(720, 642);
		frame = new JFrame("AC&DW"); 
		frame.setContentPane(new JLabel(new ImageIcon("background/2.png")));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(path));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(d); 
		frame.setPreferredSize(d); 
		frame.setMaximumSize(d); 
		frame.setLocation(300, 60);
		addComponents();
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);		
	}
	
	static String getPath(){
		return path;
	}
	
	void setInfo(String txt){
		infoLabel.setText(txt);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cont.controlGUI(e, button, normal, path);	
	}
}
