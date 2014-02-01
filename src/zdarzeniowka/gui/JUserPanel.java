package zdarzeniowka.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import zdarzeniowka.db.DBPort;
/**
 * 
 * Klasa rozszerzająca JBasicPanel.
 * Przedstawia podstawowy panel użytkownika.
 * Może być wykorzystywana na rózne sposoby - zarówno do dodawania, wyświetlania i edytownia.
 * @author Anna Cierniewska
 * 
 */
class JUserPanel extends JBasicPanel {
	private static final long serialVersionUID = 4882792304628772453L;
	int oldRoomNo = -1;
	
	JUserPanel(Font font){
		super(font);	
		initiate();
	}
	
	JUserPanel(Font font, boolean editable, JDSPanel dsPanel, JRoomFrame frame){
		super(font, editable, dsPanel, frame);
		initiate();
		cont.editabling(editable, 3, this);
	}
	
	JUserPanel(Font font, boolean editable, JDSPanel dsPanel, JResultFrame frame){
		super(font, editable, dsPanel, frame);
		initiate();
		cont.editabling(editable, 3, this);
	}
	
	private void initiate(){
		this.setLayout(new GridBagLayout());
		labels = new JLabel[7];
		textFields = new JTextField[7];
		String[] stringLabel = {"Imię:", "Nazwisko:", "Adres e-mail:", "Id użytkownika:", 
				"Numer pokoju:", "Numer albumu:", "Numer portu:"};
		for(int i = 0; i < 7; i++){
			if (i < 3){ 
				textFields[i] = new JTextField(27);
			}
			else {
					textFields[i] = new JTextField(8);
			}
			textFields[i].setFont(normal);
			labels[i] = new JLabel(stringLabel[i]);
			labels[i].setFont(normal);
		}
		textFields[3].setEditable(false);
		for (int i = 0; i < 3; i++){
        	ctop.insets = insets0;
			ctop.gridwidth = 1;
        	ctop.anchor = GridBagConstraints.LINE_END;
			ctop.gridx = 0;
			ctop.gridy = i;
			topPanel.add(labels[i], ctop);
			ctop.insets = insets1;
			ctop.anchor = GridBagConstraints.LINE_START;
			ctop.gridx = 1;
			ctop.gridwidth = 4;
			topPanel.add(textFields[i], ctop);
		}
		for (int i = 3; i < 7; i += 2){
			cbot.insets = insets0;
			cbot.anchor = GridBagConstraints.LINE_END;
			cbot.gridx = 0;
			cbot.gridy = i;
			botPanel.add(labels[i], cbot);
			cbot.insets = insets1;
			cbot.anchor = GridBagConstraints.LINE_START;
			cbot.gridx = 1;
			botPanel.add(textFields[i], cbot);	
			cbot.anchor = GridBagConstraints.LINE_END;
			cbot.gridx = 2;
			cbot.insets = new Insets(0,15,10,0);
			cbot.gridy = i;
			botPanel.add(labels[i+1], cbot);
			cbot.anchor = GridBagConstraints.LINE_START;
			cbot.insets = insets1;
			cbot.gridx = 3;
			botPanel.add(textFields[i+1], cbot);	
		}
		cpane.anchor = GridBagConstraints.LINE_END;
    	cpane.gridx = 0;
    	this.add(topPanel, cpane);
		cpane.gridy = 1;
		cpane.insets = new Insets(20,0,0,0);
		this.add(botPanel, cpane);	
		
		if (buttonPanel != null){
			cpane.gridy = 2;
			this.add(buttonPanel, cpane);
		}
		
	}
	
	void setForm(String name, String lastName, String email, int userId, int roomNo, int albumNo, DBPort port){
		textFields[0].setText(name);
		textFields[1].setText(lastName);
		textFields[2].setText(email);
		textFields[3].setText(String.valueOf(userId));
		textFields[4].setText(String.valueOf(roomNo));
		textFields[5].setText(String.valueOf(albumNo));
		textFields[6].setText(String.valueOf(port.getPortNo()));	
	}
	
	String getUserId(){
		return textFields[3].getText();
	}
	
	@Override
	public String getRoomNo(){
		return textFields[4].getText();
	}
	
	public void actionPerformed(ActionEvent e) {
		cont.contJUserPanelAL(e, this);
	}
}
