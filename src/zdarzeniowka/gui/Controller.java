package zdarzeniowka.gui;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import zdarzeniowka.db.DBNetworkDevice;
import zdarzeniowka.db.DBUser;
import zdarzeniowka.db.DBUserDevice;
import zdarzeniowka.db.DBUtil;
import zdarzeniowka.gen.Generator;

public class Controller{
	private DBUtil dbUtil = new DBUtil();
	private Logger log = Logger.getLogger(Controller.class);
	
	
	public void contDSPanelAL(ActionEvent e, final JDSPanel ds) {
		final Object source = e.getSource();
		if (source instanceof JRoomButton){
			final String buttonText = ((JRoomButton) source).getText();
			SwingWorker<List<DBUser>, Void> worker = new SwingWorker<List<DBUser>, Void>(){
	            @Override
	            protected List<DBUser> doInBackground() throws Exception {
	            	List<DBUser> list = castList(DBUser.class, dbUtil.findUserOrDevice("DBUser", "roomNo",((JRoomButton) source).getText()));
					return list;
	            }
	            @Override
	            protected void done() {
	            	try {
						ds.showRoomFrame(buttonText, get());
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
	            }
			};
			worker.execute();			
		}
	}
	
	public void contJAddPanelISC(ItemEvent e, JAddPanel addPanel) {
		if(e.getStateChange() == ItemEvent.DESELECTED){
			String item = (String) e.getItem();
			if(item == addPanel.comboBoxItems[0])
				((JBasicPanel) addPanel.panel[0]).clearForm(0);
			else if(item == addPanel.comboBoxItems[1])
				((JBasicPanel) addPanel.panel[1]).clearForm(1);
			else if(item == addPanel.comboBoxItems[2])
				((JBasicPanel) addPanel.panel[2]).clearForm(2);
		}
		
		Object source = e.getSource();
		if (source == addPanel.addingCB){
			CardLayout cl = (CardLayout)(addPanel.cardAddingPanel.getLayout());
	        cl.show(addPanel.cardAddingPanel, (String)e.getItem());
		}
	}
	public void contJAddPanelAL(ActionEvent e, final JAddPanel addPanel) {
		Object source = e.getSource();
		final int tmp = addPanel.addingCB.getSelectedIndex();
		if (source == addPanel.confirmButton[tmp]){
			if(((JBasicPanel) addPanel.panel[tmp]).checkForm(tmp)){
				SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>(){
		            @Override
		            protected Integer doInBackground() throws Exception {
		    			dbUtil = new DBUtil();
		    			if(tmp == 0){
			    			JTextField[] tf = ((JUserPanel)addPanel.panel[tmp]).textFields;
			    			return dbUtil.addUser(tf[0].getText(), tf[1].getText(), tf[2].getText(), Integer.parseInt(tf[4].getText()), Integer.parseInt(tf[5].getText()), Integer.parseInt(tf[6].getText()));
		    			}
		    			else if(tmp == 1){
			    			JTextField[] tf = ((JUserDevicePanel)addPanel.panel[tmp]).textFields;
			    			boolean conf = (((JUserDevicePanel)addPanel.panel[tmp]).cb[0].getSelectedIndex() == 0);
			    			int typeInd = ((JUserDevicePanel)addPanel.panel[tmp]).cb[0].getSelectedIndex();
			    			String s = ((JUserDevicePanel)addPanel.panel[tmp]).textArea.getText();
			    			return dbUtil.addUserDevice(tf[0].getText(), tf[1].getText(), addPanel.deviceTypes[typeInd], conf, s, Integer.parseInt(tf[3].getText()));
		    			}
		    			else if(tmp == 2){
			    			JTextField[] tf = ((JNetworkDevicePanel)addPanel.panel[tmp]).textFields;
			    			boolean conf = (((JNetworkDevicePanel)addPanel.panel[tmp]).cb[0].getSelectedIndex() == 0);
			    			int typeInd = ((JNetworkDevicePanel)addPanel.panel[tmp]).cb[0].getSelectedIndex();  			
			    			String s = ((JNetworkDevicePanel)addPanel.panel[tmp]).textArea.getText();
			    			return dbUtil.addNetworkDevice(tf[0].getText(), tf[1].getText(), addPanel.deviceTypes[typeInd], conf, s);
		    			}
						return null;
		    		}
		            @Override
		            protected void done() {
		            	Integer id = null;
		            	try {
							id = this.get();
						} catch (InterruptedException | ExecutionException e1) {
							log.error("Błąd SWING Workera");
							e1.printStackTrace();
						}
		            	if (id != null){
		            		if(tmp == 0){
			            		((JUserPanel)addPanel.panel[tmp]).textFields[3].setText(String.valueOf(id));
			            		String roomNo = addPanel.panel[0].getRoomNo();
		    					for (int i = 0; i < addPanel.dsPanel.getRoomButton().size(); i++){
		    						if (Integer.parseInt(addPanel.dsPanel.getRoomButton().get(i).getText()) == Integer.parseInt(roomNo)){
		    							addPanel.dsPanel.increaseCountTabAt(Integer.parseInt(roomNo));
		    							addPanel.dsPanel.getRoomButton().get(i).increaseUsersInRoom();
		    						}
		    					}
		            		}
			            	else if(tmp == 1)
			            		((JUserDevicePanel)addPanel.panel[tmp]).textFields[2].setText(String.valueOf(id));
			            	else if(tmp == 2)
			            		((JNetworkDevicePanel)addPanel.panel[tmp]).textFields[2].setText(String.valueOf(id));		            	
		            	}
		            	else{
		            		JOptionPane.showMessageDialog(addPanel.cardAddingPanel, "Dodawanie nie powiodło się! Sprawdź poprawność danych.", 
		            				"Błąd dodawania", JOptionPane.ERROR_MESSAGE);
		        			log.error("Błąd dodawania");
		            	}
		            }
		       };
		       	worker.execute();
/*
		       	boolean tmp2 = false;
		       	try {
		       		if (worker.get() != null){
		       			tmp2 = true;
		       		}
					
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
		       	if (tmp == 0 && tmp2){
					String roomNo = addPanel.panel[0].getRoomNo();
					for (int i = 0; i < addPanel.dsPanel.getRoomButton().size(); i++){
						if (Integer.parseInt(addPanel.dsPanel.getRoomButton().get(i).getText()) == Integer.parseInt(roomNo)){
							addPanel.dsPanel.increaseCountTabAt(Integer.parseInt(roomNo));
							addPanel.dsPanel.getRoomButton().get(i).increaseUsersInRoom();
						}
					}
				}*/
			}
			else{}
		}
		else if(source == addPanel.clearButton[tmp]){
			log.info("Naciśnięto przycisk Wyczyść!");
			((JBasicPanel) addPanel.panel[tmp]).clearForm(tmp);
		}
	}
	
	public void contJSearchPanelAL(ActionEvent e, final JSearchPanel searchPanel) {
		Object source = e.getSource();
		final int tmp = searchPanel.cb[0].getSelectedIndex();
		if (source == searchPanel.searchButton[tmp]){
			log.info("Wciśnięto przycisk szukaj");
			final int tmp2 = searchPanel.cb[tmp+1].getSelectedIndex();
			
			SwingWorker<String, Void> worker = new SwingWorker<String, Void>(){
	            @Override
	            protected String doInBackground() throws Exception {
	    			if(tmp == 0)
	    				searchPanel.userResultList = castList(DBUser.class, dbUtil.findUserOrDevice(searchPanel.category[tmp], searchPanel.criterium1[tmp2], searchPanel.textField[tmp].getText()));
	    			else if(tmp == 1)
	    				searchPanel.userDeviceResultList = castList(DBUserDevice.class, dbUtil.findUserOrDevice(searchPanel.category[tmp], searchPanel.criterium2[tmp2], searchPanel.textField[tmp].getText()));
	    			else if(tmp == 2)
	    				searchPanel.networkDeviceResultList = castList(DBNetworkDevice.class, dbUtil.findUserOrDevice(searchPanel.category[tmp], searchPanel.criterium2[tmp2], searchPanel.textField[tmp].getText()));
	    			return searchPanel.category[tmp];
	            }
	            @Override
	            protected void done() {
	            	String cat = null;
					try {
						cat = this.get();
					} catch (InterruptedException | ExecutionException e) {
						log.error("Błąd SWING Workera");
						e.printStackTrace();
					}
	            	if(cat == searchPanel.category[0]){
	            		log.info("Listowanie Userów, liczba wyników: "+ searchPanel.userResultList.size());
	            		searchPanel.userModel.setRowCount(0);
	            		for(Iterator<DBUser> iter = searchPanel.userResultList.iterator(); iter.hasNext();){
	            			DBUser user = iter.next();
	            			searchPanel.userModel.addRow(new Object[]{String.valueOf(user.getIdUser()),
	            					user.getFirstName(),user.getLastName(),
	            					String.valueOf(user.getRoomNo()),
	            					String.valueOf(user.getAlbumNo())});
	            		}
	            	}
	            	else if(cat == searchPanel.category[1]){
	            		log.info("Listowanie Urządzeń Usera, liczba wyników: "+ searchPanel.userDeviceResultList.size());
	            		searchPanel.deviceModel.setRowCount(0);
	            		for(Iterator<DBUserDevice> iter = searchPanel.userDeviceResultList.iterator(); iter.hasNext();){
	            			DBUserDevice device = iter.next();
	            			searchPanel.deviceModel.addRow(new Object[]{String.valueOf(device.getIdDevice()),
	            					device.getIp(),device.getMac(),
	            					String.valueOf(device.getType()),});
	            		}
	            	}
	            	else if(cat == searchPanel.category[2]){
	            		log.info("Listowanie Urządzeń Sieciowych, liczba wyników: "+ searchPanel.networkDeviceResultList.size());
	            		searchPanel.deviceModel.setRowCount(0);
	            		for(Iterator<DBNetworkDevice> iter = searchPanel.networkDeviceResultList.iterator(); iter.hasNext();){
	            			DBNetworkDevice device = iter.next();
	            			searchPanel.deviceModel.addRow(new Object[]{String.valueOf(device.getIdDevice()),
	            					device.getIp(),device.getMac(),
	            					String.valueOf(device.getType()),});
	            		}
	            	}
	            	JSearchPanel.resultTable.repaint();
	            }
	       };
	       	worker.execute();
		}
		else if(source == searchPanel.deleteButton){
			log.info("Wciśnięto przycisk usuń");
			final int selectedRow = JSearchPanel.resultTable.getSelectedRow();
			if(selectedRow != -1){
				Object[] options = {"Tak","Nie",};
				int n = JOptionPane.showOptionDialog(searchPanel, "Czy na pewno chcesz usunąć wybrany rekord?", 
						"Potwierdź usuwanie", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, 
						null, options, options[1]);
				if (n == 0) {
					final int index = Integer.parseInt((String) JSearchPanel.resultTable.getValueAt(selectedRow, 0));
					final int cat = searchPanel.cb[0].getSelectedIndex();
					
					SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
			            @Override
			            protected Boolean doInBackground() throws Exception {
			            	return dbUtil.removeUserOrDevice(searchPanel.category[cat], index);
			            }
			            @Override
			            protected void done() {
			            	boolean conf = false;
							try {
								conf = get();
							} catch (InterruptedException | ExecutionException e) {
								log.error("Błąd SWING Workera");
								e.printStackTrace();
							}
			            	if(conf){
			            		log.info("Usunięto!");
			            		if(cat == 0) {
			    					String roomNo = (String) searchPanel.userModel.getValueAt(selectedRow, 3);
			    					searchPanel.userModel.removeRow(selectedRow);
			    					log.info(roomNo);
			    					for (int i = 0; i < searchPanel.dsPanel.getRoomButton().size(); i++){
			    						if (Integer.parseInt(searchPanel.dsPanel.getRoomButton().get(i).getText()) == Integer.parseInt(roomNo)){
			    							searchPanel.dsPanel.decreaseCountTabAt(Integer.parseInt(roomNo));
			    							searchPanel.dsPanel.getRoomButton().get(i).decreaseUsersInRoom();
			    						}
			    					}
			    				}
			            		else if(cat == 1 || cat == 2) 
			            			searchPanel.deviceModel.removeRow(selectedRow);
			            	}
			            	else{
			            		log.error("Usuwanie nie powiodło się");
			            		JOptionPane.showMessageDialog(searchPanel.cardSearchPanel,
			            			    "Usuwanie wybranego rekordu nie powiodło się!",
			            			    "Błąd usuwania",
			            			    JOptionPane.ERROR_MESSAGE);
			            	}
			            }
			       };
			       
			       	worker.execute();
				}
			}
		}
		else if(source == searchPanel.showButton){
			log.info("Wciśnięto przycisk wyswietl");
			final int selectedRow = JSearchPanel.resultTable.getSelectedRow();
			if(selectedRow != -1){
				int row = JSearchPanel.resultTable.getSelectedRow();
				int cat = searchPanel.cb[0].getSelectedIndex();
				if(cat == 0)
					searchPanel.showResultFrame(searchPanel.userResultList.get(row));
				else if(cat == 1)
					searchPanel.showResultFrame(searchPanel.userDeviceResultList.get(row));
				else if(cat == 2)
					searchPanel.showResultFrame(searchPanel.networkDeviceResultList.get(row));
			}
		}
		
	}
	
	public void contJNetDevPanelAL(ActionEvent e, final JNetworkDevicePanel netDevPanel) {
		JButton source = (JButton)e.getSource();
		if (source == netDevPanel.editButton){
			netDevPanel.editabling(true, 2);
		}
		else if (source == netDevPanel.okButton){
			Object[] options = {"Tak","Nie",};
			if(netDevPanel.checkForm(2)){
				int n = JOptionPane.showOptionDialog(
						netDevPanel,
					    "Czy na pewno chcesz potwierdzić?",
					    "Potwierdź zmiany.",
					    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
	                    options[1]);
				if (n == 0) {
					netDevPanel.editabling(false, 2);
					
					SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
			            @Override
			            protected Boolean doInBackground() throws Exception {
			    			dbUtil = new DBUtil();
			    			JTextField[] tf = netDevPanel.textFields;
			    			boolean conf = (netDevPanel.cb[0].getSelectedIndex() == 0);
			    			int typeInd = netDevPanel.cb[0].getSelectedIndex();
			    			String s = netDevPanel.textArea.getText();
			    			return dbUtil.updateNetworkDevice(tf[0].getText(), tf[1].getText(), netDevPanel.deviceTypes[typeInd], conf, s, Integer.parseInt(tf[2].getText()));
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
			            		JOptionPane.showMessageDialog(netDevPanel.topPanel, "Aktualizacja danych nie powiodła się!", "Błąd aktualizacji", 
			        					JOptionPane.ERROR_MESSAGE);
			        			log.error("Błąd aktualizacji");
			            	}
			            }
			       };
			       	worker.execute();
				}
			}
		}
		else if (source == netDevPanel.deleteButton){
			remove((Object)netDevPanel);
			if (netDevPanel.rframe != null){
				netDevPanel.rframe.deleteFromResultTable();
				netDevPanel.rframe.dispose();
    		}
		}
		
	}
	
	public void contJRoomFrameAL(ActionEvent e, JRoomFrame roomFrame) {
		JButton source = (JButton)e.getSource();
		
		if (source == roomFrame.showDeviceButton){
			int index = roomFrame.tabbedUserPane.getSelectedIndex();
			roomFrame.remove(roomFrame.cardUser);
			roomFrame.tabbedDevicePane.setSelectedIndex(index);
			roomFrame.setContentPane(roomFrame.cardDevice);
			roomFrame.invalidate();
			roomFrame.validate();
		}
		else if (source == roomFrame.showUserButton) {
			int index = roomFrame.tabbedDevicePane.getSelectedIndex();
			roomFrame.remove(roomFrame.cardDevice);
			roomFrame.tabbedUserPane.setSelectedIndex(index);
			roomFrame.setContentPane(roomFrame.cardUser);
			roomFrame.invalidate();
			roomFrame.validate();
		}
	}
	
	public void contJUserDevPanelAL(ActionEvent e, final JUserDevicePanel usrDevPanel) {
		JButton source = (JButton)e.getSource();
		if (source == usrDevPanel.editButton){
			usrDevPanel.editabling(true, 2);
		}
		if (source == usrDevPanel.okButton){
			Object[] options = {"Tak","Nie",};
			if(usrDevPanel.checkForm(1)){
				int n = JOptionPane.showOptionDialog(
						usrDevPanel,
					    "Czy na pewno chcesz potwierdzić?",
					    "Potwierdź zmiany.",
					    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
	                    options[1]);
				if (n == 0) {
					usrDevPanel.editabling(false, 2);
					
					SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
			            @Override
			            protected Boolean doInBackground() throws Exception {
			    			dbUtil = new DBUtil();
			    			JTextField[] tf = usrDevPanel.textFields;
			    			boolean conf = (usrDevPanel.cb[0].getSelectedIndex() == 0);
			    			int typeInd = usrDevPanel.cb[0].getSelectedIndex();
			    			String s = usrDevPanel.textArea.getText();
			    			return dbUtil.updateUserDevice(tf[0].getText(), tf[1].getText(), usrDevPanel.deviceTypes[typeInd], conf, s, Integer.parseInt(tf[3].getText()),Integer.parseInt(tf[2].getText()));
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
			            		JOptionPane.showMessageDialog(usrDevPanel.topPanel, "Aktualizacja danych nie powiodła się!", "Błąd aktualizacji", 
			        					JOptionPane.ERROR_MESSAGE);
			        			log.error("Błąd aktualizacji");
			            	}
			            }
			       };
			       	worker.execute();
				}
			}
		}
		else if(source == usrDevPanel.deleteButton){
			log.info(this.getClass());
			JUserDevicePanel d = usrDevPanel;
			remove((Object)usrDevPanel);
			if (usrDevPanel.rframe != null){
				usrDevPanel.rframe.deleteFromResultTable();
				usrDevPanel.rframe.dispose();
    		}
			else if (usrDevPanel.frame != null){
				usrDevPanel.frame.refreshDevices(d);
				usrDevPanel.frame.revalidate();
    		}
		}
		
	}
	
	public void contJUserPanelAL(ActionEvent e, final JUserPanel usrPanel) {
		JButton source = (JButton) e.getSource();
		if (source == usrPanel.editButton){
			usrPanel.oldRoomNo = Integer.parseInt(usrPanel.textFields[4].getText());
			usrPanel.editabling(true, 3);
		}
		if (source == usrPanel.okButton){
			Object[] options = {"Tak","Nie",};
			if(usrPanel.checkForm(0)){
				int n = JOptionPane.showOptionDialog(
						usrPanel,
					    "Czy na pewno chcesz potwierdzić?",
					    "Potwierdź zmiany.",
					    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
	                    options[1]);
				if (n == 0) {
					usrPanel.editabling(false, 3);
		
					SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
			            @Override
			            protected Boolean doInBackground() throws Exception {
			    			dbUtil = new DBUtil();
				    			JTextField[] tf = usrPanel.textFields;
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
			            		JOptionPane.showMessageDialog(usrPanel.topPanel, "Aktualizacja danych nie powiodła się!", "Błąd aktualizacji", 
			        					JOptionPane.ERROR_MESSAGE);
			        			log.error("Błąd aktualizacji");
			            	}
			            }
    		        };
			       	worker.execute();
				    int roomNo = Integer.parseInt(usrPanel.textFields[4].getText());
		       		log.info("Stary numer " + usrPanel.oldRoomNo + "nowy numer " + roomNo);
			       	if ((usrPanel.oldRoomNo != roomNo) && (usrPanel.frame != null)){
			       		for (int i = 0; i < usrPanel.dsPanel.getRoomButton().size(); i++){
			       			if (Integer.parseInt(usrPanel.dsPanel.getRoomButton().get(i).getText()) == roomNo){
			       				usrPanel.dsPanel.increaseCountTabAt(roomNo);
			       				usrPanel.dsPanel.getRoomButton().get(i).increaseUsersInRoom();
			       				usrPanel.dsPanel.getRoomButton().get(i).repaint();
			       			}
			       			else if (Integer.parseInt(usrPanel.dsPanel.getRoomButton().get(i).getText()) == usrPanel.oldRoomNo){
			       				usrPanel.dsPanel.decreaseCountTabAt(usrPanel.oldRoomNo);
			       				usrPanel.dsPanel.getRoomButton().get(i).decreaseUsersInRoom();
			       				usrPanel.dsPanel.getRoomButton().get(i).repaint();
			       			}
			       		}
			       		usrPanel.frame.refreshUsers(Integer.parseInt(usrPanel.getUserId()));
			       		usrPanel.frame.repaint();
			       	}
				}
			}
		}	
		else if(source == usrPanel.deleteButton){
			int id = Integer.parseInt(usrPanel.textFields[3].getText());
			log.info(this.getClass());
			boolean removed = remove((Object)usrPanel);
    		String roomNo = usrPanel.textFields[4].getText();
    		log.info(roomNo);
    		if (removed){
    			for (int i = 0; i < usrPanel.dsPanel.getRoomButton().size(); i++){
	    			if (Integer.parseInt(usrPanel.dsPanel.getRoomButton().get(i).getText()) == Integer.parseInt(roomNo)){
	    				usrPanel.dsPanel.decreaseCountTabAt(Integer.parseInt(roomNo));
	    				usrPanel.dsPanel.getRoomButton().get(i).decreaseUsersInRoom();
	    			}
	    		}
	    		if (usrPanel.frame != null){
	    			usrPanel.frame.refreshUsers(id);
	    			usrPanel.frame.revalidate();
	    		}
	    		else if (usrPanel.rframe != null){
	    			usrPanel.rframe.deleteFromResultTable();
	    			usrPanel.rframe.dispose();
	    		}
			}
	   	}
	}
	
	public boolean remove(final Object obj){
		boolean removed = false;
		log.info("start DELETING");
		Object[] options = {"Tak","Nie",};
		int n = JOptionPane.showOptionDialog((JBasicPanel)obj, "Czy na pewno chcesz usunąć?", 
				"Potwierdź usuwanie", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, 
				null, options, options[1]);
		if (n == 0) {
			final int index = getObjIndex(obj);
			final String cat = getObjCategory(obj);
			log.info(index + " "+ cat);
			
			SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
	            @Override
	            protected Boolean doInBackground() throws Exception {
	            	return dbUtil.removeUserOrDevice(cat, index);
	            }
	            @Override
	            protected void done() {
	            	boolean conf = false;
					try {
						conf = get();
					} catch (InterruptedException | ExecutionException e) {
						log.error("Błąd SWING Workera");
						e.printStackTrace();
					}
	            	if(conf){
	            		log.info("Usunięto!");
	            	}
	            	else{
	            		log.error("Usuwanie nie powiodło się");
	            		JOptionPane.showMessageDialog((JBasicPanel)obj,
	            			    "Usuwanie nie powiodło się!",
	            			    "Błąd usuwania",
	            			    JOptionPane.ERROR_MESSAGE);
	            	}
	            }
	       };
	       	worker.execute();
	       	try {
				if (worker.get()){
					removed = true;
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return removed;
	}
	
	public void contJReportPanelAL(ActionEvent e, JReportPanel repPanel) {
		JButton source = (JButton) e.getSource();
		Date start = (Date) repPanel.spinner[0].getValue();
		Date end = (Date) repPanel.spinner[1].getValue();
		char data;
		if (repPanel.cb.getSelectedIndex() == 0){
			data = '0';
		}
		else {
			data = '1';
		}
		log.info(end.toString() + " " + start.toString() + " "+ data);
		if (source == repPanel.saveButton){
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(repPanel) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
            	String fileName = file.getPath();
				if(!fileName.endsWith(".txt")) {	
					file = new File(fileName + ".txt");
				}
                final char choice = data;
                FileWriter out = null;
                try {
                	out = new FileWriter(file);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
          
                final FileWriter save = out;
                final String d1 = repPanel.sdf.format(start);
				final String d2 = repPanel.sdf.format(end.getTime() + 86400000);
				log.info("Formated date " +d1 +" "+ d2);
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
        		    @Override
        		    protected Void doInBackground() throws Exception {
        		    	List<Object[]> resultList = null;
        		    	if(choice == '0'){
        		    		resultList = dbUtil.report1(d1, d2);
        		    		save.write("Data:\t\t| Transfer:" + System.getProperty("line.separator"));
            		    	save.write("________________|__________" + System.getProperty("line.separator"));
        		    	}
        		    	else if(choice == '1'){
        		    		resultList = dbUtil.report2(d1, d2);
        		    		save.write("Użytk:\t| Transfer:" + System.getProperty("line.separator"));
            		    	save.write("________|___________" + System.getProperty("line.separator"));
        		    	}

        		    	for(Object[] counter: resultList){
        		    		save.write(counter[0]+"\t| "+Math.floor(((Double)counter[1]*100))/100 + System.getProperty("line.separator"));
        		    	}
        		    	
        		    	save.close();
        		    	JOptionPane.showMessageDialog(new JFrame(), "Zapisano do pliku.");
        		    	return null;
        		    }
                };
                    worker.execute();
			}
		}
	}
	
	public void contJReportPanelStartGen(final JReportPanel repPanel, final Font font){
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	        @Override
	        protected Void doInBackground() throws Exception {
	        	repPanel.latestDate = dbUtil.getEarliestDate();
	        	repPanel.earliestDate = dbUtil.getLatestDate();
	        	repPanel.paint(font);
	            List<Integer> tab = dbUtil.getAllUsersIds();
                Generator g = new Generator(tab, repPanel.chart);
                g.run();
                        return null;
	        }
        };
        worker.execute();
	}
	
	public int getObjIndex(Object obj){
		int index = 0;
		if(obj.getClass() == JUserPanel.class)
			index = Integer.parseInt(((JUserPanel)obj).textFields[3].getText());
		else if(obj.getClass() == JUserDevicePanel.class)
			index = Integer.parseInt(((JUserDevicePanel)obj).textFields[2].getText());
		else if(obj.getClass() == JNetworkDevicePanel.class)
			index = Integer.parseInt(((JNetworkDevicePanel)obj).textFields[2].getText());
		return index;
	}
	
	public String getObjCategory(Object obj){
		String cat = null;
		if(obj.getClass() == JUserPanel.class)
			cat = "DBUser";
		else if(obj.getClass() == JUserDevicePanel.class)
			cat = "DBUserDevice";
		else if(obj.getClass() == JNetworkDevicePanel.class)
			cat = "DBNetworkDevice";
		return cat;
	}
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}

}
