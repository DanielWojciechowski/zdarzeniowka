package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

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
	
	Logger  log = Logger.getLogger(JRoomFrame.class);
	public JRoomFrame(Font font, String text){
		super(text);
		List<DBUser> userList = new LinkedList<DBUser>();
		initiate(userList, font);
	}
	
	public JRoomFrame(Font font, String text, List<DBUser> userList){
		super(text);
		if(userList.size() <= 0 || userList.size() > 3) {
            throw new IllegalArgumentException("Too many users in room!");
        }
		initiate(userList, font);
	}
	
	private void initiate(List<DBUser> userList, Font font){
		this.setLayout(new GridBagLayout());


		Dimension d;
		userDevicePanel = new LinkedList<JUserDevicePanel>();
		cardDevice = new JPanel(new GridBagLayout());
		cardUser = new JPanel(new GridBagLayout());
		upane = new JPanel[userList.size()];
		dpane = new JPanel[userList.size()];
		device = new byte[userList.size()];
		userPanel = new JUserPanel[userList.size()];
		scrollpane = new JScrollPane[userList.size()];
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
		
		for (int i = 0; i < userList.size(); i++){
			DBUser user = userList.get(i);
			device[i] = (byte) userList.get(i).getDevices().size();
			cpane.ipadx = 0;
			upane[i] = new JPanel();
			upane[i].setLayout(new GridBagLayout());
			dpane[i] = new JPanel();
			dpane[i].setLayout(new GridBagLayout());
			userPanel[i] = new JUserPanel(normal, false);
			cpane.gridy = 0;
			cpane.gridwidth = 1;
			upane[i].add(userPanel[i], cpane);
			tabbedUserPane.add("Użytkownik "+String.valueOf(user.getIdUser()),upane[i]);

			d = new Dimension(458, 260);
			userPanel[i].setForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getIdUser(), user.getRoomNo(), user.getAlbumNo(), user.getPort());

			int j1=0;
			for(Iterator<DBUserDevice> iter = user.getDevices().iterator(); iter.hasNext();){
				userDevicePanel.add(new JUserDevicePanel(normal, false));
				DBUserDevice dev = (DBUserDevice) iter.next();
				userDevicePanel.get(deviceIndex).setForm(dev.getMac(), dev.getIp(), dev.getIdDevice(), user.getIdUser(), dev.isConfiguration(), dev.getType(), dev.getOtherInfo());
				csrane.gridy = 2*j1;
				dpane[i].add(userDevicePanel.get(deviceIndex), csrane);
				csrane.gridy = 2*j1 + 1;
				JSeparator separator = new JSeparator();
				if (j1 < device[i] - 1){
					separator.setPreferredSize(new Dimension(435,1));
					dpane[i].add(separator, csrane);
				}
				deviceIndex++;
				j1++;
			}
			
			scrollpane[i] = new JScrollPane(dpane[i]);
			scrollpane[i].setPreferredSize(d);
			scrollpane[i].setMinimumSize(d);
			scrollpane[i].setMaximumSize(d);
			scrollpane[i].setBorder(null);
			tabbedDevicePane.add("Użytkownik "+String.valueOf(user.getIdUser()),scrollpane[i]);
			
		}
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.insets = new Insets(10,0,0,10);
		c.ipadx = 10;
		cardUser.add(showDeviceButton,c);
		c.ipadx = 0;
		c.insets = new Insets(12,0,0,13);
		cardDevice.add(showUserButton,c);
		
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
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
