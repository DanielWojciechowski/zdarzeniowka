package zdarzeniowka.gui;

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
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * Klasa wyświetla główne okno programu.
 * Odpowiada za ustawienia kolorów, fontów, ikon w programie oraz powoływane są 
 * w niej instancje obiektów składające się na całościowe GUI.
 * 
 * @author Anna Cierniewska
 * @author Daniel Wojciechowski
 * 
 */
public class GUI implements ActionListener{

	private JFrame frame;
	private static JAddPanel addPanel;
	private static JSearchPanel searchPanel;
	private static JDSPanel dsPanel;
	private static JReportPanel reportPanel;
	private static JTabbedPane tabbedPane;
	private Font header, normal, italic;
	private Icon add, search, report, ds;
	private static String path = "icons/cat.png";
	private JButton button;
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
		
		dsPanel.setComponentsBackground(Color.white);
		addPanel.setComponentsBackground(Color.white);
		searchPanel.setComponentsBackground(Color.white);
		reportPanel.setColor(Color.white);
		
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
		c.insets = new Insets(7, 255, 0, 0);
		topPane.add(button, c);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 3, 0, 0);
		topPane.add(label2,c);
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		frame.add(topPane, c);
		
		tabbedPane.addTab("Plan DS", ds, dsPanel,"Wyświetl widok akademika.");	
		tabbedPane.addTab("Dodaj", add, addPanel, "Dodaj użytkownika lub sprzęt.");
		tabbedPane.addTab("Wyszukaj", search, searchPanel, "Wyszukaj użytkownika lub sprzęt.");
		tabbedPane.addTab("Raporty", report, reportPanel, "Monitoruj sieć i wegeneruj raport.");
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 0, 0);
		frame.add(tabbedPane, c);
	}
	
	public void showGUI(){
		Dimension d = new Dimension(720, 630);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		cont.controlGUI(e, button, normal, path);	
	}
}
