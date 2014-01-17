package zdarzeniowka;

import java.awt.CardLayout;
import java.awt.Color;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

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
	
	
	public JRoomFrame(Font font, String text){
		super(text);
		initiate(3, font);
	}
	
	public JRoomFrame(Font font, String text, int value){
		super(text);
		if(value < 0 || value > 4) {
            throw new IllegalArgumentException("Too many users in room!");
        }
		initiate(value, font);
	}
	
	public void initiate(int index, Font font){
		this.setLayout(new GridBagLayout());
		Dimension d = new Dimension(455,278);
		userDevicePanel = new LinkedList<JUserDevicePanel>();
		cardDevice = new JPanel(new GridBagLayout());
		cardUser = new JPanel(new GridBagLayout());
		upane = new JPanel[index];
		dpane = new JPanel[index];
		device = new byte[index];
		userPanel = new JUserPanel[index];
		scrollpane = new JScrollPane[index];
		normal = font;
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
		csrane.insets = new Insets(10, 0, 20, 0);
		cpane.insets = new Insets(10,5,0,5);
		cpane.fill = GridBagConstraints.BOTH;
		cpane.anchor = GridBagConstraints.LAST_LINE_END;
		int deviceIndex = 0;
		for (int i = 0; i < index; i++){
			cpane.ipadx = 0;
			device[i] = 5; //tu ma brać ile jest urzadzen itego uzytkownika
			upane[i] = new JPanel();
			upane[i].setLayout(new GridBagLayout());
			dpane[i] = new JPanel();
			dpane[i].setLayout(new GridBagLayout());
			userPanel[i] = new JUserPanel(normal, false);
			cpane.gridy = 0;
			cpane.gridwidth = 1;
			upane[i].add(userPanel[i], cpane);
			tabbedUserPane.add("Id osoby",upane[i]);	
			
			for(int j = 0; j < device[i]; j++){
				userDevicePanel.add(new JUserDevicePanel(normal, false));
				csrane.gridy = 2*j;
				dpane[i].add(userDevicePanel.get(deviceIndex), csrane);
				csrane.gridy = 2*j + 1;
				JSeparator separator = new JSeparator();
				if (j < device[i] - 1){
					separator.setPreferredSize(new Dimension(435,1));
				}
				dpane[i].add(separator, csrane);
				deviceIndex++;
			}
			scrollpane[i] = new JScrollPane(dpane[i]);
			scrollpane[i].setPreferredSize(d);
			scrollpane[i].setMinimumSize(d);
			scrollpane[i].setMaximumSize(d);
			scrollpane[i].setBorder(null);
			tabbedDevicePane.add("Id osoby",scrollpane[i]);
			
		}
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.insets = new Insets(10,0,0,10);
		c.ipadx = 8;
		cardUser.add(showDeviceButton,c);
		c.ipadx = 0;
		c.insets = new Insets(12,0,0,10);
		cardDevice.add(showUserButton,c);
		
		c.gridy = 1;
		c.insets = new Insets(12,0,0,0);
		cardUser.add(tabbedUserPane,c);
		cardDevice.add(tabbedDevicePane,c);
		this.add(cardUser);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		
		if (source == showDeviceButton){
			int index = tabbedUserPane.getSelectedIndex();
			this.remove(cardUser);
			tabbedDevicePane.setSelectedIndex(index);
			this.setContentPane(cardDevice);
			this.invalidate();
			this.validate();
		}
		else if (source == showUserButton) {
			int index = tabbedDevicePane.getSelectedIndex();
			this.remove(cardDevice);
			tabbedUserPane.setSelectedIndex(index);
			this.setContentPane(cardUser);
			this.invalidate();
			this.validate();
		}
	}


	
	

}
