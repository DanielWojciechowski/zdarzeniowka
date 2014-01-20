package zdarzeniowka;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class JUserPanel extends JBasicPanel {
	private static final long serialVersionUID = 4882792304628772453L;
	private int oldRoomNo = -1;
	
	public JUserPanel(Font font){
		super(font);	
		paint();
	}
	
	public JUserPanel(Font font, boolean editable, JDSPanel dsPanel, JRoomFrame frame){
		super(font, editable, dsPanel, frame);
		paint();
		editabling(editable, 3);
	}
	
	public JUserPanel(Font font, boolean editable, JDSPanel dsPanel, JResultFrame frame){
		super(font, editable, dsPanel, frame);
		paint();
		editabling(editable, 3);
	}
	
	public void paint(){
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
	
	public void setForm(String name, String lastName, String email, int userId, int roomNo, int albumNo, DBPort port){
		textFields[0].setText(name);
		textFields[1].setText(lastName);
		textFields[2].setText(email);
		textFields[3].setText(String.valueOf(userId));
		textFields[4].setText(String.valueOf(roomNo));
		textFields[5].setText(String.valueOf(albumNo));
		textFields[6].setText(String.valueOf(port.getPortNo()));	
	}
	
	public String getUserId(){
		return textFields[3].getText();
	}
	
	@Override
	public String getRoomNo(){
		return textFields[4].getText();
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
			if (source == editButton){
				oldRoomNo = Integer.parseInt(this.textFields[4].getText());
				this.editabling(true, 3);
			}
			if (source == okButton){
				Object[] options = {"Tak","Nie",};
				if(checkForm(0)){
					int n = JOptionPane.showOptionDialog(
						    this,
						    "Czy na pewno chcesz potwierdzić?",
						    "Potwierdź zmiany.",
						    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
		                    options[1]);
					if (n == 0) {
						this.editabling(false, 3);
						
						SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
				            @Override
				            protected Boolean doInBackground() throws Exception {
				    			dbUtil = new DBUtil();
					    			JTextField[] tf = textFields;
					    			return dbUtil.updateUser(tf[0].getText(), tf[1].getText(), tf[2].getText(), Integer.parseInt(tf[4].getText()), Integer.parseInt(tf[5].getText()), Integer.parseInt(tf[6].getText()), Integer.parseInt(tf[3].getText()));
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
					    int roomNo = Integer.parseInt(this.textFields[4].getText());

			       		log.info("Stary numer " + oldRoomNo + "nowy numer " + roomNo);
				       	if ((oldRoomNo != roomNo) && (frame != null)){
				       		for (int i = 0; i < dsPanel.getRoomButton().size(); i++){
				       			if (Integer.parseInt(dsPanel.getRoomButton().get(i).getText()) == roomNo){
					       			dsPanel.increaseCountTabAt(roomNo);
									dsPanel.getRoomButton().get(i).increaseUsersInRoom();
						       		dsPanel.getRoomButton().get(i).repaint();
				       			}
				       			else if (Integer.parseInt(dsPanel.getRoomButton().get(i).getText()) == oldRoomNo){
				       				dsPanel.decreaseCountTabAt(oldRoomNo);
									dsPanel.getRoomButton().get(i).decreaseUsersInRoom();
						       		dsPanel.getRoomButton().get(i).repaint();
				       			}
				       		}
				       		frame.refreshUsers(Integer.parseInt(this.getUserId()));
				       		frame.repaint();
				       	}
					}
				}
			}	
			else if(source == deleteButton){
				int id = Integer.parseInt(textFields[3].getText());
				log.info(this.getClass());
				boolean removed = remove((Object)this);
	    		String roomNo = this.textFields[4].getText();
	    		log.info(roomNo);
	    		if (removed){
	    			for (int i = 0; i < dsPanel.getRoomButton().size(); i++){
		    			if (Integer.parseInt(dsPanel.getRoomButton().get(i).getText()) == Integer.parseInt(roomNo)){
		    				dsPanel.decreaseCountTabAt(Integer.parseInt(roomNo));
		    				dsPanel.getRoomButton().get(i).decreaseUsersInRoom();
		    			}
		    		}
		    		if (frame != null){
		    			frame.refreshUsers(id);
		    			frame.revalidate();
		    		}
		    		else if (rframe != null){
		    			rframe.deleteFromResultTable();
		    			rframe.dispose();
		    		}
		  
		    		
				}

	    	}
		}
}
