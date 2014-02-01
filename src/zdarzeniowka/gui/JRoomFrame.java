package zdarzeniowka.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import zdarzeniowka.db.DBUser;
import zdarzeniowka.db.DBUserDevice;
/**
 * 
 * Okno, w którym znajduje się panel wybranego profilu pokoju, a w nim
 * widok profile użytkowników oraz widok ich sprzętów.
 * Klasa umożliwia wybór widoku.
 * @author Anna Cierniewska
 * 
 */
class JRoomFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8038078091109185534L;
	private byte[] device;
	private JPanel[] upane, dpane;	
	private JScrollPane[] scrollpane;
	private JUserPanel[] userPanel;
	private List<JUserDevicePanel> userDevicePanel;
	private GridBagConstraints c, cpane, csrane;
	private JDSPanel dsPanel; 
	private Controller cont = new Controller();
	JTabbedPane tabbedUserPane;
	JTabbedPane tabbedDevicePane;
	JPanel cardUser;
	JPanel cardDevice;
	List<DBUser> userList;
	JButton showDeviceButton;
	JButton showUserButton;
	Font normal;	
	Color color;
	
	JRoomFrame(Font font, String text, List<DBUser> userList, JDSPanel dsPanel){
		super(text);
		this.normal = font;
		super.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.getPath()));
		super.setContentPane(new JLabel(new ImageIcon("background/room.png")));
		this.dsPanel = dsPanel;
		if(userList.size() <= 0 || userList.size() > 3) {
			JOptionPane.showMessageDialog(dsPanel,
    			    "Brak użytkowników w wybranym pokoju.",
    			    "Nie można otworzyć okna!",
    			    JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Invalid number of users in room");    
        }
		this.userList = userList;
		initiate(this.normal);
	}

	@Override
	public void repaint(){
		super.setContentPane(new JLabel(new ImageIcon("background/room.png")));
		super.repaint();
	}
	
	void initiate(Font font){
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
		this.getContentPane().add(cardUser);	
	}
	
	void setComponentsBackground(Color bg){
		color = bg;
		cardUser.setOpaque(false);
		cardDevice.setOpaque(false);
        for (int i = 0; i < userList.size(); i++){
                upane[i].setBackground(bg);
                dpane[i].setBackground(bg);
                userPanel[i].setComponentsBackground(bg);
        }
        for (int i = 0; i < userDevicePanel.size(); i++){
                userDevicePanel.get(i).setComponentsBackground(bg);
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cont.contJRoomFrameAL(e, this);
	}
}
