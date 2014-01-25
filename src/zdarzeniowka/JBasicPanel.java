package zdarzeniowka;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public abstract class JBasicPanel extends JPanel implements ActionListener{
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
	
	public JBasicPanel(Font font) {
		super();
		initiate(font);
	}
	
	public JBasicPanel(Font font, boolean whatever, JDSPanel dsPanel, JRoomFrame frame){
		super();
		initiate(font);
		addButtons();
		this.dsPanel = dsPanel;
		this.frame = frame;	
	}
	
	public JBasicPanel(Font font, boolean whatever, JDSPanel dsPanel, JResultFrame rframe){
		super();
		initiate(font);
		addButtons();
		this.dsPanel = dsPanel;
		this.rframe = rframe;	
	}
	
	public JBasicPanel(Font font, boolean whatever, JResultFrame rframe){
		super();
		this.rframe = rframe;	
		initiate(font);
		addButtons();
	}

	public JBasicPanel(Font font, boolean whatever, JRoomFrame frame){
		super();
		this.frame = frame;	
		initiate(font);
		addButtons();
	}

	public JBasicPanel(Font font, boolean whatever){
		super();
		initiate(font);
		addButtons();
	}
	
	
	 public void setForm(String name, String lastName, String email, int userId, int roomNo, int albumNo, 
			 DBPort port) {}

	 public void setForm(String mac, String ip, int idDevice, int idUser, boolean configuration, char type, 
			 String otherInfo){}

	
	public void setForm(String mac, String ip, int idDevice, boolean configuration, char type, String otherInfo){}
	
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
	
	public void setTextField(int index, String text){ //ma rzucac wyjatek jezeli indeks przekracza tablice
		textFields[index].setText(text);		
	}
		
	public void editabling(boolean value, int id){
		editable = value;
		for (int i = 0; i < textFields.length; i++){
			if (i != id){
				textFields[i].setEditable(editable);
			}
			
		}
		if (cb != null){
			for (int i = 0; i < cb.length; i++){
				cb[i].setEnabled(editable);
			}
			
		}
		if (textArea != null)
			textArea.setEditable(editable);
		okButton.setEnabled(editable);
	}
	
	public void clearForm(int n){
		for(int i=0; i<textFields.length; i++)
			textFields[i].setText("");
		if(n == 1 || n == 2){
			textArea.setText("");
			for(int i=0; i<cb.length; i++)
				cb[i].setSelectedIndex(0);
		}
	}
	
	public boolean checkForm(int n){
		LinkedList<Boolean> checker = new LinkedList<Boolean>();
		if(n == 0){
			checker.add(textFields[0].getText().matches("[a-zA-Z]+"));
			checker.add(textFields[1].getText().matches("[a-zA-Z]+"));
			checker.add(textFields[2].getText().matches("^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))?$"));
			int tmp = 0;
			try{
				tmp = Integer.parseInt(textFields[4].getText());
			}catch(java.lang.NumberFormatException e){
				checker.add(false);
			}
			checker.add(((tmp>0 && tmp<=12) || (tmp>100 && tmp<=112) || (tmp>200 && tmp<=212)) && (dbUtil.countUsersInRoom(tmp)<3));
			tmp = 0;
			try{
				tmp = Integer.parseInt(textFields[5].getText());
			}catch(java.lang.NumberFormatException e){
				checker.add(false);
			}
			checker.add(tmp>0 && tmp<99999);
			tmp = 0;
			try{
				tmp = Integer.parseInt(textFields[6].getText());
			}catch(java.lang.NumberFormatException e){
				checker.add(false);
			}
			checker.add(tmp>0 && tmp<=108);
		} 
		else if(n == 1 || n == 2){
			checker.add(textFields[0].getText().matches("^(([0-9A-F]{2}[:-]){5}([0-9A-F]{2}))?$"));
			checker.add(textFields[1].getText().matches("^((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))?$"));
			if(n == 1){
				int tmp = 0;
				try{
					tmp = Integer.parseInt(textFields[3].getText());
				}catch(java.lang.NumberFormatException e){
					checker.add(false);
				}
				checker.add(tmp>0);
			}
		}
		
		if(checker.contains(false)){
			JOptionPane.showMessageDialog(this, "Podane dane są nieprawidłowe!", "Błąd danych", 
					JOptionPane.ERROR_MESSAGE);
			log.error("Błąd danych");
			return false;
		}
		else{
			log.error("Dodawanie w toku");
			return true;
		}
	}

	public String getRoomNo(){
		return null;
	}
}
