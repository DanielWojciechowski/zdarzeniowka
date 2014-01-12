package zdarzeniowka;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class JUserPanel extends JBasicPanel {
	private static final long serialVersionUID = 4882792304628772453L;
	
	public JUserPanel(){
		super();		
		this.setLayout(new GridBagLayout());
		labels = new JLabel[7];
		textFields = new JTextField[7];
		String[] stringLabel = {"Imiê:", "Nazwisko:", "Adres e-mail:", "Id u¿ytkownika:", 
				"Numer pokoju:", "Numer albumu:", "Numer portu:"};
		for(int i = 0; i < 7; i++){
			if (i < 3){
				textFields[i] = new JTextField(27);
			}
			else {
					textFields[i] = new JTextField(8);
			}
			textFields[i].setFont(normal);
			textFields[i].setEditable(editable);
			labels[i] = new JLabel(stringLabel[i]);
			labels[i].setFont(normal);
		}
		for (int i = 0; i < 3; i++){
        	ctop.insets = insets0;
			ctop.gridwidth = 1;
        	ctop.anchor = GridBagConstraints.LINE_END;
			ctop.gridx = 0;
			ctop.gridy = i;
			ctop.ipady = 0;
			topPanel.add(labels[i], ctop);
			ctop.insets = insets1;
			ctop.ipady = 5;
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
			cbot.ipady = 0;
			botPanel.add(labels[i], cbot);
			cbot.insets = insets1;
			cbot.anchor = GridBagConstraints.LINE_START;
			cbot.gridx = 1;
			cbot.ipady = 6;
			botPanel.add(textFields[i], cbot);	
			cbot.anchor = GridBagConstraints.LINE_END;
			cbot.gridx = 2;
			cbot.gridy = i;
			cbot.ipady = 0;
			botPanel.add(labels[i+1], cbot);
			cbot.anchor = GridBagConstraints.LINE_START;
			cbot.gridx = 3;
			cbot.ipady = 6;
			botPanel.add(textFields[i+1], cbot);	
		}
		
    	cpane.anchor = GridBagConstraints.CENTER;
    	cpane.gridx = 0;
    	this.add(topPanel, cpane);
		cpane.gridy = 1;
		cpane.insets = new Insets(20,0,0,15);
		this.add(botPanel, cpane);	
	}

}
