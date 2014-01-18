package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class JNetworkDevicePanel extends JBasicPanel  {
	private static final long serialVersionUID = 8913270095466798762L;
	private char[] deviceTypes = {'k','p','r','a','i','s'};

	public JNetworkDevicePanel(Font font) {
		super(font);
		paint();
	}
		
	public JNetworkDevicePanel(Font font, boolean editable){
		super(font, editable);
		paint();
		editabling(editable, 2);
	}

	public void paint(){
		this.setLayout(new GridBagLayout());
		Insets insets2 = new Insets(0,35,10,0);
		labels = new JLabel[6];
		textFields = new JTextField[3];
		textArea = new JTextArea();
	    textArea.setLineWrap(true);
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
		cbot.gridwidth = 3;
		cbot.gridx = 1;
		cbot.ipady = 6;
		botPanel.add(cb[1], cbot);	
		
		cbot.insets = new Insets(20,0,10,0);
		cbot.anchor = GridBagConstraints.LINE_END;
		cbot.gridx = 0;
		cbot.gridy = 4;
		cbot.ipady = 0;
		cbot.gridwidth = 1;
		botPanel.add(labels[5], cbot);
		cbot.insets = new Insets(20,20,10,0);
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
	
	public void setForm(String mac, String ip, int idDevice, int idUser, boolean configuration, char type, String otherInfo){
		textFields[0].setText(mac);
		textFields[1].setText(ip);
		textFields[2].setText(String.valueOf(idDevice));
		textFields[3].setText(String.valueOf(idUser));
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
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		if (source == editButton){
			this.editabling(true, 2);
		}
		if (source == okButton){
			Object[] options = {"Tak","Nie",};
			if(checkForm(2)){
				int n = JOptionPane.showOptionDialog(
					    this,
					    "Czy na pewno chcesz potwierdzić?",
					    "Potwierdź zmiany.",
					    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
	                    options[1]);
				if (n == 0) {
					this.editabling(false, 2);
					
					SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
			            @Override
			            protected Boolean doInBackground() throws Exception {
			    			dbUtil = new DBUtil();
			    			JTextField[] tf = textFields;
			    			boolean conf = (cb[0].getSelectedIndex() == 0);
			    			int typeInd = cb[0].getSelectedIndex();
			    			String s = textArea.getText();
			    			return dbUtil.updateNetworkDevice(tf[0].getText(), tf[1].getText(), deviceTypes[typeInd], conf, s, Integer.parseInt(tf[2].getText()));
			    		}
			            
			            @Override
			            protected void done() {
			            	Boolean result = null;
			            	try {
			            		result = this.get();
							} catch (InterruptedException | ExecutionException e1) {
								log.error("Błąd SWING Workera");
								e1.printStackTrace();
							}	
			            	if (result == null){
			            		JOptionPane.showMessageDialog(topPanel, "Aktualizacja danych nie powiodła się!", "Błąd aktualizacji", 
			        					JOptionPane.ERROR_MESSAGE);
			        			log.error("Błąd aktualizacji");
			            	}
			            }
			       };
			       	worker.execute();
				}
			}
		}
		
	}

}
