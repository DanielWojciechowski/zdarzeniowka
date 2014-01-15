package zdarzeniowka;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class JUserDevicePanel extends JBasicPanel{
	private static final long serialVersionUID = 9083184852370768151L;

	public JUserDevicePanel() {
		super();
		this.setLayout(new GridBagLayout());
		labels = new JLabel[7];
		textFields = new JTextField[4];
		textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(editable);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(338, 130));
		Insets insets2 = new Insets(0,24,10,0);
		
		String[] stringLabel = {"Adres MAC:", "Adres IP:","Konfiguracja:", "Id urzadzenia:", "Typ:", "Id użytkownika:", "Uwagi:"},
				configuration = {"Niezatwierdzona", "Zatwierdzona"}, type = {"Komputer", "Switch", "Router", "AP", "Inne"};
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
			ctop.ipady = 0;
			topPanel.add(labels[i], ctop);
			ctop.insets = insets1;
			ctop.ipady = 6;
			ctop.anchor = GridBagConstraints.LINE_START;
			ctop.gridx = 1;
			ctop.gridwidth = 4;
			topPanel.add(textFields[i], ctop);
		}
		cbot.insets = insets0;
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 0;
		cbot.gridy = 2;
		cbot.ipady = 0;
		botPanel.add(labels[2], cbot);
		cbot.insets = insets1;
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 1;
		cbot.ipady = 6;
		botPanel.add(cb[0], cbot);	
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 2;
		cbot.ipady = 0;
		cbot.insets = insets2;
		botPanel.add(labels[3], cbot);
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 3;
		cbot.ipady = 6;
		cbot.insets = insets1;
		textFields[2].setEditable(false);
		botPanel.add(textFields[2], cbot);
		
		cbot.insets = insets0;
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 0;
		cbot.gridy = 3;
		cbot.ipady = 0;
		botPanel.add(labels[4], cbot);
		cbot.insets = insets1;
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 1;
		cbot.ipady = 6;
		botPanel.add(cb[1], cbot);	
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 2;
		cbot.ipady = 0;
		cbot.insets = insets2;
		botPanel.add(labels[5], cbot);
		cbot.anchor = GridBagConstraints.LINE_START;
		cbot.gridx = 3;
		cbot.ipady = 6;
		cbot.insets = insets1;
		botPanel.add(textFields[3], cbot);
		
		cbot.insets = new Insets(20, 0, 10, 0);
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 0;
		cbot.gridy = 4;
		cbot.ipady = 0;
		botPanel.add(labels[6], cbot);
		cbot.insets = new Insets(20, 20, 10, 0);
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
	}

}
