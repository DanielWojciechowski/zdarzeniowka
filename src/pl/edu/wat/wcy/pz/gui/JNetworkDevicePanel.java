package pl.edu.wat.wcy.pz.gui;
/**
 * 
 * Klasa rozszerzająca JBasicPanel.
 * Przedstawia podstawowy panel sprzetu sieciowego.
 * Może być wykorzystywana na rózne sposoby - zarówno do dodawania, wyświetlania i edytownia.
 * @author Anna Cierniewska
 * 
 */
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

class JNetworkDevicePanel extends JBasicPanel  {
	private static final long serialVersionUID = 8913270095466798762L;
	char[] deviceTypes = {'k','p','r','a','i','s'};

	JNetworkDevicePanel(Font font) {
		super(font);
		initiate();
	}
		
	JNetworkDevicePanel(Font font, boolean editable, JResultFrame rframe){
		super(font, editable, rframe);
		initiate();
		cont.editabling(editable, 2, this);
	}

	private void initiate(){
		this.setLayout(new GridBagLayout());
		Insets insets2 = new Insets(0,28, 10,0);
		labels = new JLabel[6];
		textFields = new JTextField[3];
		textArea = new JTextArea();
	    textArea.setLineWrap(true);
        textArea.setFont(normal);
	    textArea.setEditable(editable);
	    scrollPane = new JScrollPane(textArea);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.setPreferredSize(new Dimension(338, 130));
		String[] stringLabel = {"Adres MAC:", "Adres IP:", "Konfiguracja:", "Id urzadzenia:", "Typ:", "Uwagi:"},
				configuration = {"Zatwierdzona", "Niezatwierdzona"}, type = {"Komputer", "Switch", "Router", "AP", "Inne", "Serwer"};
		cb = new JComboBox[2];
		cb[0] = new JComboBox<String>(configuration);
		cb[1] = new JComboBox<String>(type);
		for(int i = 0; i < 6; i++){
			if (i < 3){
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
		cbot.ipadx = 0;
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 2;
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
		cbot.gridwidth = 3;
		cbot.gridx = 1;
		cbot.ipadx = 4;
		botPanel.add(cb[1], cbot);	
		cbot.ipadx = 0;
		
		cbot.insets = new Insets(20,0,0,0);
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 0;
		cbot.gridy = 4;
		cbot.gridwidth = 1;
		botPanel.add(labels[5], cbot);
		cbot.insets = new Insets(20,20,0,0);
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
	
	void setForm(String mac, String ip, int idDevice, boolean configuration, char type, String otherInfo){
		textFields[0].setText(mac);
		textFields[1].setText(ip);
		textFields[2].setText(String.valueOf(idDevice));
		if(configuration)
			cb[0].setSelectedIndex(1);
		else cb[0].setSelectedIndex(0);
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
			case 's': //serwer
				cb[1].setSelectedIndex(5);
		}
		textArea.setText(otherInfo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cont.contJNetDevPanelAL(e, this);
	}
}
