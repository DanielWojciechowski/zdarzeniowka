package zdarzeniowka;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class JRoomFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8038078091109185534L;
	private JTabbedPane tabbedUserPane, tabbedDevicePane;
	private byte[] device;
	private JPanel[] upane, dpane;
	private JPanel cardUser, cardDevice;
	private JScrollPane[] scrollpane;
	private JUserPanel[] userPanel;
	private List<JUserDevicePanel> userDevicePanel;
	private JButton showDeviceButton, showUserButton;
	private GridBagConstraints c, cpane, csrane;
	private Font normal;
	
	public JRoomFrame(String text){
		super(text);
		this.setLayout(new GridBagLayout());
		Dimension d = new Dimension(455,280);
		/*this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);*/
		userDevicePanel = new LinkedList<JUserDevicePanel>();
		cardDevice = new JPanel(new GridBagLayout());
		cardUser = new JPanel(new GridBagLayout());
		upane = new JPanel[3];
		dpane = new JPanel[3];
		device = new byte[3];
		userPanel = new JUserPanel[3];
		scrollpane = new JScrollPane[3];
		normal = new Font("Open sans", Font.PLAIN, 13);
		tabbedUserPane = new JTabbedPane();
		tabbedUserPane.setFont(normal);
		tabbedDevicePane = new JTabbedPane();
		tabbedDevicePane.setFont(normal);
		
		showDeviceButton = new JButton("Urzadzenia");
		showDeviceButton.addActionListener(this);
		showDeviceButton.setFont(normal);
		showUserButton = new JButton("Uzytkownicy");
		showUserButton.addActionListener(this);
		showUserButton.setFont(normal);	
		cpane = new GridBagConstraints();
		csrane = new GridBagConstraints();
		c = new GridBagConstraints(); 
		int index = 0;
		for (int i = 0; i < 3; i++){
			device[i] = 3; //tu ma brać ile jest urzadzen itego uzytkownika
			upane[i] = new JPanel();
			upane[i].setLayout(new GridBagLayout());
			userPanel[i] = new JUserPanel(false);
			cpane.gridy = 0;
			cpane.gridwidth = 1;
			cpane.fill = GridBagConstraints.BOTH;
			cpane.anchor = GridBagConstraints.LAST_LINE_END;
			cpane.insets = new Insets(10,5,0,5);
			upane[i].add(userPanel[i], cpane);
			tabbedUserPane.add("Osoba",upane[i]);	
			
			for(int j = 0; j < device[i]; j++){
				userDevicePanel.add(new JUserDevicePanel(false));
				scrollpane[i] = new JScrollPane(userDevicePanel.get(index));
				scrollpane[i].setPreferredSize(d);
				scrollpane[i].setMinimumSize(d);
				scrollpane[i].setMaximumSize(d);
				scrollpane[i].setBorder(null);
				tabbedDevicePane.add("Sprzet "+i,scrollpane[i]);
				index++;
			}
			
		}
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.insets = new Insets(10,0,0,0);
		cardUser.add(showDeviceButton,c);
		cardDevice.add(showUserButton,c);
		c.gridy = 1;
		cardUser.add(tabbedUserPane,c);
		cardDevice.add(tabbedDevicePane,c);
		this.add(cardUser);
	
		//cardDevice.add(tabbedDevicePane);
		//cardDevice.add(showUserButton);
		//c.anchor = GridBagConstraints.LINE_END;
		//this.add(showDeviceButton,c);
		//c.gridy = 1;
		//this.add(cardUser);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		if (source == showDeviceButton){
			this.remove(cardUser);
			this.setContentPane(cardDevice);
			this.invalidate();
			this.validate();
		}
		else if (source == showUserButton) {
			this.remove(cardDevice);
			this.setContentPane(cardUser);
			this.invalidate();
			this.validate();
		}
	}


	
	

}
