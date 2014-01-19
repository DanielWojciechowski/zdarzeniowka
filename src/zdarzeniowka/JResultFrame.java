package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class JResultFrame extends JFrame{
	private static final long serialVersionUID = -7670041249218891721L;
	private JBasicPanel result;
	private JScrollPane scrollPane;
	private GridBagConstraints c;
	private DBUtil util;

	public JResultFrame(Font font, String txt, DBUser user){
		super(txt);
		result = new JUserPanel(font, false);
		result.setForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getIdUser(), user.getRoomNo(), user.getAlbumNo(), user.getPort());
		initiate(false);
	}
	
	public JResultFrame(Font font, String txt, DBUserDevice userDevice){
		super(txt);
		util = new DBUtil();
		result = new JUserDevicePanel(font, false);
		result.setForm(userDevice.getMac(), userDevice.getIp(), userDevice.getIdDevice(), util.getDeviceUser(userDevice.getIdDevice()), userDevice.isConfiguration(), userDevice.getType(), userDevice.getOtherInfo());
		initiate(true);
	}
	
	public JResultFrame(Font font, String txt, DBNetworkDevice networkDevice){
		super(txt);
		result = new JNetworkDevicePanel(font, false);
		result.setForm(networkDevice.getMac(), networkDevice.getIp(), networkDevice.getIdDevice(), networkDevice.isConfiguration(), networkDevice.getType(), networkDevice.getOtherInfo());
		initiate(true);
	}
	
	private void initiate(boolean scrollPaneNeeded){
		this.setLayout(new GridBagLayout());
		Dimension d = new Dimension(460,286);
		c = new GridBagConstraints();
		
		if (result instanceof JUserPanel){
			c.insets = new Insets(10, 10, 10, 0);
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
	
	public JBasicPanel getResult(){
		return result;
	}
	

}
