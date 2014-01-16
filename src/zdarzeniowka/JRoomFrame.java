package zdarzeniowka;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JRoomFrame extends JFrame implements ItemListener {
	private static final long serialVersionUID = -8038078091109185534L;
	private JTabbedPane tabbedPane;
	private JPanel[] pane;
	private JUserPanel[] userPanel;
	private JUserDevicePanel[] userDevicePanel;
	private JButton[] showDeviceButton;
	private GridBagConstraints c;
	private Font normal;
	
	public JRoomFrame(String text){
		super(text);
		this.setLayout(new CardLayout());
		normal = new Font("Open sans", Font.PLAIN, 13);
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(normal);
		pane = new JPanel[3];
		userPanel = new JUserPanel[3];
		showDeviceButton = new JButton[3];
		c = new GridBagConstraints();
		for (int i = 0; i < 3; i++){
			c.insets = new Insets(0, 0, 10, 10);
			pane[i] = new JPanel();
			userPanel[i] = new JUserPanel(false);
			pane[i].setLayout(new GridBagLayout());
			showDeviceButton[i] = new JButton("Urzadzenia uzytkownika");
			showDeviceButton[i].addItemListener(this);
			showDeviceButton[i].setFont(normal);			
			c.gridy = 0;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.LINE_END;
			c.insets = new Insets(10,0,0,0);
			pane[i].add(userPanel[i], c);
			c.gridy = 1;
			pane[i].add(showDeviceButton[i],c);
			c.gridy = 0;
			tabbedPane.add("Osoba",pane[i]);	
		}
		this.add(tabbedPane);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();		
	}
	
	

}
