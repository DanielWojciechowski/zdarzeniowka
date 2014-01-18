package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class JResultPane extends JFrame{
	private static final long serialVersionUID = -7670041249218891721L;
	private JBasicPanel result;
	private JScrollPane scrollPane;
	private GridBagConstraints c;
	private DBUtil util;

	public JResultPane(Font font, String txt, DBUser user){
		super(txt);
		result = new JUserPanel(font, false);
		result.setForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getIdUser(), user.getRoomNo(), user.getAlbumNo(), user.getPort());
		initiate(false);
	}
	
	public JResultPane(Font font, String txt, DBUserDevice userDevice){
		super(txt);
		util = new DBUtil();
		result = new JUserDevicePanel(font, false);
		result.setForm(userDevice.getMac(), userDevice.getIp(), userDevice.getIdDevice(), util.getDeviceUser(userDevice.getIdDevice()), userDevice.isConfiguration(), userDevice.getType(), userDevice.getOtherInfo());
		initiate(true);
	}
	
	public JResultPane(Font font, String txt, DBNetworkDevice networkDevice){
		super(txt);
		result = new JNetworkDevicePanel(font, false);
		result.setForm(networkDevice.getMac(), networkDevice.getIp(), networkDevice.getIdDevice(), networkDevice.isConfiguration(), networkDevice.getType(), networkDevice.getOtherInfo());
		initiate(true);
	}
	
	private void initiate(boolean scrollPaneNeeded){
		this.setLayout(new GridBagLayout());
		Dimension d = new Dimension(455,278);
		c = new GridBagConstraints();
		if (result instanceof JUserPanel){
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
