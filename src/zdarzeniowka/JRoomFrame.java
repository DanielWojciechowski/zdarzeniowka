package zdarzeniowka;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JRoomFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8038078091109185534L;
	private JTabbedPane tabbedPane;
	private JPanel[] pane, buttonPanel;
	private JUserPanel[] userPanel;
	private JButton[] editButton, deleteButton, showDeviceButton, okButton;
	private GridBagConstraints c;
	private Font normal;
	
	
	public JRoomFrame(String text, int usersInRoom){
		super(text);
		File fontFile = new File("font/OpenSans-Italic.ttf");
		try {
			Font fontN = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			normal = fontN.deriveFont(12f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(normal);
		pane = new JPanel[usersInRoom];
		for (int i = 0; i < usersInRoom; i++){
			pane[i] = new JPanel();
			tabbedPane.add("Osoba",pane[i]);	
		}
		this.add(tabbedPane);
	}
	
	public JRoomFrame(String text){
		super(text);
		normal = new Font("Open sans", Font.PLAIN, 13);
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(normal);
		pane = new JPanel[3];
		userPanel = new JUserPanel[3];
		buttonPanel = new JPanel[3];
		editButton = new JButton[3];
		deleteButton = new JButton[3];
		showDeviceButton = new JButton[3];
		okButton = new JButton[3];
		c = new GridBagConstraints();
		for (int i = 0; i < 3; i++){
			c.insets = new Insets(0, 0, 10, 10);
			pane[i] = new JPanel();
			userPanel[i] = new JUserPanel();
			userPanel[i].editabling(false);
			buttonPanel[i] = new JPanel();
			pane[i].setLayout(new GridBagLayout());
			buttonPanel[i].setLayout(new GridBagLayout());
			editButton[i] = new JButton("Edytuj");
			deleteButton[i] = new JButton("Usun");
			showDeviceButton[i] = new JButton("Urzadzenia uzytkownika");
			okButton[i] = new JButton("Akcpetuj");
			editButton[i].setFont(normal);
			editButton[i].addActionListener(this);
			deleteButton[i].setFont(normal);
			deleteButton[i].addActionListener(this);
			showDeviceButton[i].setFont(normal);
			showDeviceButton[i].addActionListener(this);
			okButton[i].setFont(normal);
			okButton[i].addActionListener(this);
			c.gridx = 0;
			c.gridy = 0;
			c.anchor = GridBagConstraints.LINE_END;
			buttonPanel[i].add(editButton[i], c);
			c.gridx = 1;
			buttonPanel[i].add(okButton[i], c);
			c.gridx = 2;
			buttonPanel[i].add(deleteButton[i], c);
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 4;
			buttonPanel[i].add(showDeviceButton[i],c);
			
			c.gridy = 0;
			c.gridwidth = 1;
			c.insets = new Insets(10,10,0,10);
			pane[i].add(userPanel[i], c);
			c.gridy = 1;
			c.insets = new Insets(20,0,0,16);
			pane[i].add(buttonPanel[i],c);
			
			tabbedPane.add("Osoba",pane[i]);	
		}
		this.add(tabbedPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		for (int i = 0; i < editButton.length; i++){
			if (source == editButton[i]){
				userPanel[i].editabling(true);
			}
			if (source == okButton[i]){
				int n = JOptionPane.showConfirmDialog(
					    this,
					    "Czy na pewno chcesz potwierdzić?",
					    "Potwierdź zmiany.",
					    JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					userPanel[i].editabling(false);
				}
			}
		}
	}
	
	

}
