package zdarzeniowka.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import zdarzeniowka.db.DBPort;
import zdarzeniowka.db.DBUtil;

abstract class JBasicPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 5020407406483635930L;
	protected JPanel topPanel, botPanel, buttonPanel;
	protected JScrollPane scrollPane;
	protected JTextArea textArea;
	protected JComboBox<String>[] cb;
	protected JLabel[] labels;
	protected JTextField[] textFields; 
	protected JButton editButton, deleteButton, okButton;
	protected GridBagConstraints cpane, ctop, cbot, cbutton;
	protected Insets insets1, insets0;
	protected Font normal;
	protected boolean editable;
	protected JDSPanel dsPanel;
	protected JRoomFrame frame;
	protected JResultFrame rframe;
	Logger  log = Logger.getLogger(JBasicPanel.class);
	DBUtil dbUtil = new DBUtil();
	Controller cont = new Controller();
	
	JBasicPanel(Font font) {
		super();
		initiate(font);
	}
	
	JBasicPanel(Font font, boolean whatever, JDSPanel dsPanel, JRoomFrame frame){
		super();
		initiate(font);
		addButtons();
		this.dsPanel = dsPanel;
		this.frame = frame;	
	}
	
	JBasicPanel(Font font, boolean whatever, JDSPanel dsPanel, JResultFrame rframe){
		super();
		initiate(font);
		addButtons();
		this.dsPanel = dsPanel;
		this.rframe = rframe;	
	}
	
	JBasicPanel(Font font, boolean whatever, JResultFrame rframe){
		super();
		this.rframe = rframe;	
		initiate(font);
		addButtons();
	}

	JBasicPanel(Font font, boolean whatever, JRoomFrame frame){
		super();
		this.frame = frame;	
		initiate(font);
		addButtons();
	}

	JBasicPanel(Font font, boolean whatever){
		super();
		initiate(font);
		addButtons();
	}
	
	private void initiate(Font font){
		normal = font;
		editable = true;
		topPanel = new JPanel();
		botPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		botPanel.setLayout(new GridBagLayout());
		cpane = new GridBagConstraints();
		ctop = new GridBagConstraints();
		cbot = new GridBagConstraints();
		insets1 = new Insets(0,20,10,0);
		insets0 = new Insets(0,0,10,0);
	}
	
	private void addButtons(){
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		cbutton = new GridBagConstraints();
		editButton = new JButton("Edytuj");
		deleteButton = new JButton("Usuń");
		okButton = new JButton("Akceptuj");
		editButton.setFont(normal);
		deleteButton.setFont(normal);
		okButton.setFont(normal);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);
		okButton.addActionListener(this);
		cbutton.insets = insets1;
		cbutton.gridx = 0;
		cbutton.gridy = 0;
		cbutton.anchor = GridBagConstraints.LINE_END;
		buttonPanel.add(editButton, cbutton);
		cbutton.gridx = 1;
		buttonPanel.add(okButton, cbutton);
		cbutton.gridx = 2;
		buttonPanel.add(deleteButton, cbutton);
	}
	
	 void setForm(String name, String lastName, String email, int userId, int roomNo, int albumNo, 
			 DBPort port) {}

	 void setForm(String mac, String ip, int idDevice, int idUser, boolean configuration, char type, 
			 String otherInfo){}

	
	void setForm(String mac, String ip, int idDevice, boolean configuration, char type, String otherInfo){}
	

	void setComponentsBackground(Color bg){
		this.setBackground(bg);
		topPanel.setBackground(bg);
		botPanel.setBackground(bg);
		if (buttonPanel != null){
			buttonPanel.setBackground(bg);
		}
	}
	
	void setTextField(int index, String text){
		textFields[index].setText(text);		
	}

	String getRoomNo(){
		return null;
	}
}
