package pl.edu.wat.wcy.pz.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import pl.edu.wat.wcy.pz.db.DBNetworkDevice;
import pl.edu.wat.wcy.pz.db.DBUser;
import pl.edu.wat.wcy.pz.db.DBUserDevice;
import pl.edu.wat.wcy.pz.db.DBUtil;
/**
 * 
 * Okno, w którym znajduje się panel wyświetlonego i wyszukanego wcześniej profilu - użytkownika,
 * sprzętu sieciowego lub sprzętu użytkownika.
 * @author Anna Cierniewska
 * @author Daniel Wojciechowski
 * 
 */
class JResultFrame extends JFrame{
	private static final long serialVersionUID = -7670041249218891721L;
	private JBasicPanel result;
	private JScrollPane scrollPane;
	private GridBagConstraints c;
	private DBUtil util;
	JMyTable resultTable; 
	
	JResultFrame(Font font, String txt, DBUser user, JDSPanel dsPanel, JMyTable resultTable){
		super(txt);
		super.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.getPath()));
		this.resultTable = resultTable;
		result = new JUserPanel(font, false, dsPanel, this);
		result.setForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getIdUser(), user.getRoomNo(), user.getAlbumNo(), user.getPort());
		initiate(false);
	}
	
	JResultFrame(Font font, String txt, DBUserDevice userDevice, JMyTable resultTable){
		super(txt);
		super.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.getPath()));
		this.resultTable = resultTable;
		util = new DBUtil();
		result = new JUserDevicePanel(font, false, this);
		result.setForm(userDevice.getMac(), userDevice.getIp(), userDevice.getIdDevice(), util.getDeviceUser(userDevice.getIdDevice()), userDevice.isConfiguration(), userDevice.getType(), userDevice.getOtherInfo());
		initiate(true);
	}
	
	JResultFrame(Font font, String txt, DBNetworkDevice networkDevice, JMyTable resultTable){
		super(txt);
		this.resultTable = resultTable;
		super.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.getPath()));
		result = new JNetworkDevicePanel(font, false, this);
		result.setForm(networkDevice.getMac(), networkDevice.getIp(), networkDevice.getIdDevice(), networkDevice.isConfiguration(), networkDevice.getType(), networkDevice.getOtherInfo());
		initiate(true);
	}
	
	private void initiate(boolean scrollPaneNeeded){
		this.setLayout(new GridBagLayout());
		Dimension d = new Dimension(460,286);
		c = new GridBagConstraints();
		
		if (result instanceof JUserPanel){
			c.insets = new Insets(10, 10, 10, 10);
			this.add(result, c);	
		}
		else{
			scrollPane = new JScrollPane(result);
			scrollPane.setPreferredSize(d);
			scrollPane.setMinimumSize(d);
			scrollPane.setMaximumSize(d);
			scrollPane.setBorder(null);
			this.add(scrollPane, c);
		}
	}
	
	JBasicPanel getResult(){
		return result;
	}
	
	void setComponentsBackground(Color bg){
		this.getContentPane().setBackground(bg);
		if (scrollPane != null){
			scrollPane.getViewport().setBackground(bg);
		}
		result.setComponentsBackground(bg);	

	}
}
