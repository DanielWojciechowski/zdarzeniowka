package zdarzeniowka;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

public class JRoomFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8038078091109185534L;
	JTabbedPane tabbedUserPane;
	JTabbedPane tabbedDevicePane;
	private byte[] device;
	private JPanel[] upane, dpane;
	JPanel cardUser;
	JPanel cardDevice;
	private JScrollPane[] scrollpane;
	private JUserPanel[] userPanel;
	private List<JUserDevicePanel> userDevicePanel;
	private List<DBUser> userList;
	JButton showDeviceButton;
	JButton showUserButton;
	private GridBagConstraints c, cpane, csrane;
	private Font normal;	
	private JDSPanel dsPanel; 
	private Logger  log = Logger.getLogger(JRoomFrame.class);
	private Controller cont = new Controller();
	
	public JRoomFrame(Font font, String text, List<DBUser> userList, JDSPanel dsPanel){
		super(text);
		super.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.getPath()));
		this.dsPanel = dsPanel;
		if(userList.size() <= 0 || userList.size() > 3) {
            throw new IllegalArgumentException("Too many users in room!");
        }
		this.userList = userList;
		initiate(font);
	}
	
	private void initiate(Font font){
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
			userPanel[i] = new JUserPanel(normal, false, dsPanel, this);
			cpane.gridy = 0;
			cpane.gridwidth = 1;
			upane[i].add(userPanel[i], cpane);
			tabbedUserPane.add("Użytkownik "+String.valueOf(user.getIdUser()),upane[i]);

			d = new Dimension(458, 260);
			userPanel[i].setForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getIdUser(), user.getRoomNo(), user.getAlbumNo(), user.getPort());

			int j1=0;
			for(Iterator<DBUserDevice> iter = user.getDevices().iterator(); iter.hasNext();){
				userDevicePanel.add(new JUserDevicePanel(normal, false, this));
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
	
	public void setComponentsBackground(Color bg){
        for (int i = 0; i < userList.size(); i++){
                upane[i].setBackground(bg);
                dpane[i].setBackground(bg);
                userPanel[i].setComponentsBackground(bg);
        }
        for (int i = 0; i < userDevicePanel.size(); i++){
                userDevicePanel.get(i).setComponentsBackground(bg);
        }
	}
	
	public void refreshUsers(int id){
		for (int i = 0; i < userList.size(); i++){
				if (id == userList.get(i).getIdUser()){
					this.userList.remove(i);			
					initiate(normal);
				}	
			}
		if (userList.size() == 0)
			this.dispose();
		else {
			this.setContentPane(cardUser);
		}
		this.revalidate();
	}
	
	public void moveUsers(int id){

	}
	
	public void refreshDevices(JUserDevicePanel userDevicePanel){
		int numberOfDevices = - 1, userId = userDevicePanel.getUserId();
		DBUser user = null;
		for (int i = 0; i < userList.size(); i++){
			user = userList.get(i);
			if (user.getIdUser() == userId){
				List<DBUserDevice> devices = new ArrayList<DBUserDevice>();
				devices.addAll(user.getDevices());
				numberOfDevices = devices.size();
				for (int j = 0; j < devices.size(); j++){
					if (devices.get(j).getIdDevice() == userDevicePanel.getDeviceId()){
						devices.remove(j);
						log.info(devices.size());
						Set<DBUserDevice> dbd = new HashSet<DBUserDevice>(devices);
						user.setDevices(dbd);
						log.info(user.getDevices().size());
						userList.set(i, user);
						initiate(normal);
					}
				}
				numberOfDevices--;
			}
		}
		if (numberOfDevices == 0){
			this.setContentPane(cardUser);
		}
		else {
			this.setContentPane(cardDevice);
		}
		this.revalidate();
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cont.contJRoomFrameAL(e, this);
	}
}
