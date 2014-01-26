package zdarzeniowka.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class JUserDevicePanel extends JBasicPanel{
	private static final long serialVersionUID = 9083184852370768151L;
	char[] deviceTypes = {'k','p','r','a','i','s'};
	
	public JUserDevicePanel(Font font, boolean editable, JResultFrame rframe){
		super(font, editable, rframe);
		paint();
		cont.editabling(editable, 2, this);
	}
	
	public JUserDevicePanel(Font font, boolean editable, JRoomFrame frame){
		super(font, editable, frame);
		paint();
		cont.editabling(editable, 2, this);
	}
	
	public JUserDevicePanel(Font font) {
		super(font);
		paint();
	}
	
	public void paint(){
		this.setLayout(new GridBagLayout());
		labels = new JLabel[7];
		textFields = new JTextField[4];
		textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(editable);
        textArea.setFont(normal);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(338, 130));
		Insets insets2 = new Insets(0,17,10,0);
		
		String[] stringLabel = {"Adres MAC:", "Adres IP:","Konfiguracja:", "Id urzadzenia:", "Typ:", "Id użytkownika:", "Uwagi:"},
				configuration = {"Zatwierdzona", "Niezatwierdzona"}, type = {"Komputer", "Switch", "Router", "AP", "Inne"};
		cb = new JComboBox[2];
		cb[0] = new JComboBox<String>(configuration);
		cb[1] = new JComboBox<String>(type);
		for(int i = 0; i < 7; i++){
			if (i < 4){
				if (i < 2){
					textFields[i] = new JTextField(28);	
					cb[i].setPrototypeDisplayValue("Such wow w");
					cb[i].setFont(normal);
				}
				else {
					textFields[i] = new JTextField(8);
				}
				textFields[i].setFont(normal);
				}
			labels[i] = new JLabel(stringLabel[i]);
			labels[i].setFont(normal);		
		}
		for (int i = 0; i < 2; i++){
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
		cbot.insets = insets0;
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 0;
		cbot.gridy = 2;
		botPanel.add(labels[2], cbot);
		cbot.insets = insets1;
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 1;
		cbot.ipadx = 4;
		botPanel.add(cb[0], cbot);	
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 2;
		cbot.ipadx = 0;
		cbot.insets = insets2;
		botPanel.add(labels[3], cbot);
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 3;
		cbot.insets = insets1;
		textFields[2].setEditable(false);
		botPanel.add(textFields[2], cbot);
		
		cbot.insets = insets0;
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 0;
		cbot.gridy = 3;
		botPanel.add(labels[4], cbot);
		cbot.insets = insets1;
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 1;
		cbot.ipadx = 4;
		botPanel.add(cb[1], cbot);	
		cbot.ipadx = 0;
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 2;
		cbot.insets = insets2;
		botPanel.add(labels[5], cbot);
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 3;
		cbot.insets = insets1;
		botPanel.add(textFields[3], cbot);
		
		cbot.insets = new Insets(20, 0, 0, 0);
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 0;
		cbot.gridy = 4;
		cbot.ipady = 0;
		botPanel.add(labels[6], cbot);
		cbot.insets = new Insets(20, 20, 0, 0);
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 1;
		cbot.gridwidth = 3;
		botPanel.add(scrollPane, cbot);	
		
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
	
	public int getUserId(){
		return Integer.parseInt(textFields[3].getText());
	}
	
	public int getDeviceId(){
		return Integer.parseInt(textFields[2].getText());
	}
	
	public void setForm(String mac, String ip, int idDevice, int idUser, boolean configuration, char type, String otherInfo){
		textFields[0].setText(mac);
		textFields[1].setText(ip);
		textFields[2].setText(String.valueOf(idDevice));
		textFields[3].setText(String.valueOf(idUser));
		if(configuration)
			cb[0].setSelectedIndex(0);
		else cb[0].setSelectedIndex(1);
		switch (type){
			case 'k': //komp
				cb[1].setSelectedIndex(0);
				break;
			case 'p': //switch
				cb[1].setSelectedIndex(1);
				break;
			case 'r': //router
				cb[1].setSelectedIndex(2);
				break;
			case 'a': //ap
				cb[1].setSelectedIndex(3);
				break;
			case 'i': //inne
				cb[1].setSelectedIndex(4);
		}
		textArea.setText(otherInfo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cont.contJUserDevPanelAL(e, this);
	}


}
