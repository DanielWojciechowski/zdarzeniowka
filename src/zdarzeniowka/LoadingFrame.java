package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class LoadingFrame extends JFrame{
	private static final long serialVersionUID = 6749328366168616302L;
	private JProgressBar progressBar;
	private JLabel label1, label2;
	private Dimension d = new Dimension(200,140);
	private GridBagConstraints c;
	private Font font;
	
	public LoadingFrame(){
		super("AC&DW");
		File fontFile1 = new File("font/OpenSans-Regular.ttf");
		try {
			Font fontH = Font.createFont(Font.TRUETYPE_FONT, fontFile1);
			font = fontH.deriveFont(13f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
		this.setLayout(new GridBagLayout());
		this.setLocation(520, 300);
		c = new GridBagConstraints();
		progressBar = new JProgressBar(40, 290);
		progressBar.setIndeterminate(true);
		label1 = new JLabel("Ładowanie bazy danych!");
		label1.setFont(font);
		label2 = new JLabel("Proszę czekać...");
		label2.setFont(font);
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 10, 0, 10);
		this.add(label1, c);
		//c.insets = new Insets(10, 10, 10, 10);
		c.gridy = 1;
		this.add(label2, c);
		c.gridy = 2;
		c.ipady = 5;
		this.add(progressBar, c);
		
		
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

}
