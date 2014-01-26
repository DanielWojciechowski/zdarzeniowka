package zdarzeniowka.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

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
				clearForm(0,(JBasicPanel) addPanel.panel[0]);
			else if(item == addPanel.comboBoxItems[1])
				clearForm(1,(JBasicPanel) addPanel.panel[1]);
			else if(item == addPanel.comboBoxItems[2])
				clearForm(2,(JBasicPanel) addPanel.panel[2]);
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
			
				SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>(){
		            @Override
		            protected Integer doInBackground() throws Exception {
		    			dbUtil = new DBUtil();
		    			if(checkForm(tmp, (JBasicPanel)addPanel.panel[tmp])){
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
		    			}
		    			else 
		    				return -1;
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
		            	if (id != null && id > 0){
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
		            	else if(id == null){
		            		JOptionPane.showMessageDialog(addPanel.cardAddingPanel, "Dodawanie nie powiodło się! Sprawdź poprawność danych.", 
		            				"Błąd dodawania", JOptionPane.ERROR_MESSAGE);
		        			log.error("Błąd dodawania");
		            	}
		            }
		       };
		       	worker.execute();
			//}
		}
		else if(source == addPanel.clearButton[tmp]){
			log.info("Naciśnięto przycisk Wyczyść!");
			clearForm(tmp,(JBasicPanel) addPanel.panel[tmp]);
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
	
	public void contJSearchPanelISC(ItemEvent e, JSearchPanel searchPanel) {
		if(e.getStateChange() == ItemEvent.DESELECTED){
			String item = (String) e.getItem();
			if(item == searchPanel.comboBoxItems[0])
				clearForm(0, searchPanel);
			else if(item == searchPanel.comboBoxItems[1])
				clearForm(1, searchPanel);
			else if(item == searchPanel.comboBoxItems[2])
				clearForm(2, searchPanel);
		}
		CardLayout cl = (CardLayout)(searchPanel.cardSearchPanel.getLayout());
        cl.show(searchPanel.cardSearchPanel, (String)e.getItem());
        int tmp = searchPanel.cb[0].getSelectedIndex();
		if (tmp == 1 || tmp == 2){
			JSearchPanel.resultTable.setModel(searchPanel.deviceModel);
			searchPanel.userModel.setRowCount(0);
		}
		else if(tmp == 0) {
			JSearchPanel.resultTable.setModel(searchPanel.userModel);
			searchPanel.deviceModel.setRowCount(0);
		}
	}	
	
	public void contJNetDevPanelAL(ActionEvent e, final JNetworkDevicePanel netDevPanel) {
		JButton source = (JButton)e.getSource();
		if (source == netDevPanel.editButton){
			editabling(true, 2, netDevPanel);
		}
		else if (source == netDevPanel.okButton){
			Object[] options = {"Tak","Nie",};
			if(checkForm(2, netDevPanel)){
				int n = JOptionPane.showOptionDialog(
						netDevPanel,
					    "Czy na pewno chcesz potwierdzić?",
					    "Potwierdź zmiany.",
					    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
	                    options[1]);
				if (n == 0) {
					editabling(false, 2, netDevPanel);
					
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
				deleteFromResultTable(netDevPanel.rframe);
				netDevPanel.rframe.dispose();
    		}
		}
		
	}
	
	public void contJRoomFrameAL(ActionEvent e, JRoomFrame roomFrame) {
		JButton source = (JButton)e.getSource();
		Color c = roomFrame.color;
		if (source == roomFrame.showDeviceButton){
			int index = roomFrame.tabbedUserPane.getSelectedIndex();
			roomFrame.getContentPane().remove(roomFrame.cardUser);
			roomFrame.tabbedDevicePane.setSelectedIndex(index);
			roomFrame.getContentPane().add(roomFrame.cardDevice);
			roomFrame.revalidate();
			roomFrame.setComponentsBackground(c);
			roomFrame.getContentPane().repaint();
		}
		else if (source == roomFrame.showUserButton) {
			int index = roomFrame.tabbedDevicePane.getSelectedIndex();
			roomFrame.getContentPane().remove(roomFrame.cardDevice);
			roomFrame.tabbedUserPane.setSelectedIndex(index);
			roomFrame.getContentPane().add(roomFrame.cardUser);
			roomFrame.revalidate();
			roomFrame.setComponentsBackground(c);
			roomFrame.getContentPane().repaint();
		}
	}
	
	public void contJUserDevPanelAL(ActionEvent e, final JUserDevicePanel usrDevPanel) {
		JButton source = (JButton)e.getSource();
		if (source == usrDevPanel.editButton){
			editabling(true, 2, usrDevPanel);
		}
		if (source == usrDevPanel.okButton){
			Object[] options = {"Tak","Nie",};
			if(checkForm(1, usrDevPanel)){
				int n = JOptionPane.showOptionDialog(
						usrDevPanel,
					    "Czy na pewno chcesz potwierdzić?",
					    "Potwierdź zmiany.",
					    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
	                    options[1]);
				if (n == 0) {
					editabling(false, 2, usrDevPanel);
					
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
				deleteFromResultTable(usrDevPanel.rframe);
				usrDevPanel.rframe.dispose();
    		}
			else if (usrDevPanel.frame != null){
				refreshDevices(d, usrDevPanel.frame);
    			usrDevPanel.frame.revalidate();
    			usrDevPanel.frame.getContentPane().repaint();
    		}
		}
		
	}
	
	public void contJUserPanelAL(ActionEvent e, final JUserPanel usrPanel) {
		JButton source = (JButton) e.getSource();
		if (source == usrPanel.editButton){
			usrPanel.oldRoomNo = Integer.parseInt(usrPanel.textFields[4].getText());
			editabling(true, 3, usrPanel);
		}
		if (source == usrPanel.okButton){
			Object[] options = {"Tak","Nie",};
			if(checkForm(0, usrPanel)){
				int n = JOptionPane.showOptionDialog(
						usrPanel,
					    "Czy na pewno chcesz potwierdzić?",
					    "Potwierdź zmiany.",
					    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
	                    options[1]);
				if (n == 0) {
					editabling(false, 3, usrPanel);
		
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
			       		refreshUsers(Integer.parseInt(usrPanel.getUserId()), usrPanel.frame);
		    			usrPanel.frame.revalidate();
		    			usrPanel.frame.getContentPane().repaint();
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
	    			refreshUsers(id, usrPanel.frame);
	    			usrPanel.frame.revalidate();
	    			usrPanel.frame.getContentPane().repaint();
	    		}
	    		else if (usrPanel.rframe != null){
	    			deleteFromResultTable(usrPanel.rframe);
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
	
	public void contJReportPanelAL(ActionEvent e, final JReportPanel repPanel) {
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
			boolean flag = true;
			while(flag){
				if (fileChooser.showSaveDialog(repPanel) == JFileChooser.APPROVE_OPTION) {
	                File file = fileChooser.getSelectedFile();
		    		Object[] options = {"Tak","Nie",};
		    		int result = JOptionPane.showOptionDialog(repPanel, "Plik już istnieje, nadpisać?", 
		    				"Plik już istnieje", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,
	                        options[1]);
		    		if(result == 0){
		    			flag = false;
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
		        		    	JOptionPane.showMessageDialog(repPanel, "Zapisano raport do pliku", "Zapisano!",
			            			    JOptionPane.INFORMATION_MESSAGE);
		        		    	return null;
		        		    }
		                };
		                    worker.execute();
		    		}
				}
				else
					flag = false;
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
	
	public boolean checkForm(int n, JBasicPanel panel){
		LinkedList<Boolean> checker = new LinkedList<Boolean>();
		if(n == 0){
			checker.add(panel.textFields[0].getText().matches("[a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]+"));
			checker.add(panel.textFields[1].getText().matches("[a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]+"));
			checker.add(panel.textFields[2].getText().matches("^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))?$"));
			int tmp = 0;
			try{
				tmp = Integer.parseInt(panel.textFields[4].getText());
			}catch(java.lang.NumberFormatException e){
				checker.add(false);
			}
			checker.add(((tmp>0 && tmp<=12) || (tmp>100 && tmp<=112) || (tmp>200 && tmp<=212)) && (dbUtil.countUsersInRoom(tmp)<3));
			tmp = 0;
			try{
				tmp = Integer.parseInt(panel.textFields[5].getText());
			}catch(java.lang.NumberFormatException e){
				checker.add(false);
			}
			checker.add(tmp>=10000 && tmp<=99999);
			tmp = 0;
			try{
				tmp = Integer.parseInt(panel.textFields[6].getText());
			}catch(java.lang.NumberFormatException e){
				checker.add(false);
			}
			checker.add(tmp>0 && tmp<=108);
		} 
		else if(n == 1 || n == 2){
			checker.add(panel.textFields[0].getText().matches("^(([0-9A-F]{2}[:-]){5}([0-9A-F]{2}))?$"));
			checker.add(panel.textFields[1].getText().matches("^((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))?$"));
			if(n == 1){
				int tmp = 0;
				try{
					tmp = Integer.parseInt(panel.textFields[3].getText());
				}catch(java.lang.NumberFormatException e){
					checker.add(false);
				}
				checker.add(tmp>0);
			}
		}
		
		if(checker.contains(false)){
			JOptionPane.showMessageDialog(panel, "Podane dane są nieprawidłowe!", "Błąd danych", 
					JOptionPane.ERROR_MESSAGE);
			log.error("Błąd danych");
			return false;
		}
		else{
			log.error("Dodawanie w toku");
			return true;
		}
	}
	
	public void clearForm(int n, JBasicPanel panel){
		for(int i=0; i<panel.textFields.length; i++)
			panel.textFields[i].setText("");
		if(n == 1 || n == 2){
			panel.textArea.setText("");
			for(int i=0; i<panel.cb.length; i++)
				panel.cb[i].setSelectedIndex(0);
		}
	}
	
	public void editabling(boolean value, int id, JBasicPanel panel){
		panel.editable = value;
		for (int i = 0; i < panel.textFields.length; i++){
			if (i != id){
				panel.textFields[i].setEditable(panel.editable);
			}
			
		}
		if (panel.cb != null){
			for (int i = 0; i < panel.cb.length; i++){
				panel.cb[i].setEnabled(panel.editable);
			}
			
		}
		if (panel.textArea != null)
			panel.textArea.setEditable(panel.editable);
		panel.okButton.setEnabled(panel.editable);
	}
	
	public void deleteFromResultTable(JResultFrame resFrame){
		DefaultTableModel tableModel = (DefaultTableModel) resFrame.resultTable.getModel();
		tableModel.removeRow(resFrame.resultTable.getSelectedRow());
	}
	
	public void refreshDevices(JUserDevicePanel userDevicePanel, JRoomFrame roomFrame){
		Color c = roomFrame.color;
        int numberOfDevices = - 1, userId = userDevicePanel.getUserId();
        DBUser user = null;
        for (int i = 0; i < roomFrame.userList.size(); i++){
                user = roomFrame.userList.get(i);
                if (user.getIdUser() == userId){
                        List<DBUserDevice> devices = new ArrayList<DBUserDevice>();
                        devices.addAll(user.getDevices());
                        numberOfDevices = devices.size();
                        for (int j = 0; j < devices.size(); j++){
                                if (devices.get(j).getIdDevice() == userDevicePanel.getDeviceId()){
                                        devices.remove(j);
                                        log.info(devices.size());
                                        Set<DBUserDevice> dbd = new HashSet<DBUserDevice>(devices);
                                        user.setDevices(dbd);
                                        log.info(user.getDevices().size());
                                        roomFrame.userList.set(i, user);
                                        roomFrame.getContentPane().remove(roomFrame.cardDevice);
                                        roomFrame.getContentPane().revalidate();
                                        roomFrame.revalidate();
                                        roomFrame.getContentPane().repaint();
                                        roomFrame.repaint();
                                        roomFrame.initiate(roomFrame.font);
                                }
                        }
                        numberOfDevices--;
                }
        }
        if (numberOfDevices == 0){
                roomFrame.getContentPane().add(roomFrame.cardUser);
               
        }
        else {
        	roomFrame.getContentPane().remove(roomFrame.cardUser);
        	roomFrame.getContentPane().revalidate();
        	roomFrame.revalidate();
        	roomFrame.getContentPane().add(roomFrame.cardDevice);
        }
        roomFrame.setComponentsBackground(c);
        
	}
	
	public void refreshUsers(int id, JRoomFrame roomFrame){
		Color c = roomFrame.color;
		 for (int i = 0; i < roomFrame.userList.size(); i++){
             if (id == roomFrame.userList.get(i).getIdUser()){
                     roomFrame.userList.remove(i);
                     roomFrame.getContentPane().remove(roomFrame);
                     roomFrame.getContentPane().revalidate();
                     roomFrame.revalidate();
                     roomFrame.getContentPane().repaint();
                     roomFrame.repaint();
                     roomFrame.initiate(roomFrame.normal);
                     roomFrame.setComponentsBackground(c);
                     
         }        
     }
     if (roomFrame.userList.size() == 0)
             roomFrame.dispose();
	}
	
	void clearForm(int n, JSearchPanel searchPanel){
		searchPanel.textField[n].setText("");
		searchPanel.cb[n+1].setSelectedIndex(0);
}
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}
	
	public void controlGUI(ActionEvent e, JButton but, Font font, String path){
		JFrame infoFrame = new JFrame("O programie");
		Dimension d = new Dimension(500,300);
		JTextArea textArea = new JTextArea("Mehehe");
		textArea.setFont(font);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		infoFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(path));
		infoFrame.getContentPane().setBackground(Color.white);
		infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		infoFrame.setLocation(350, 200);
		infoFrame.setMinimumSize(d);
		infoFrame.setMaximumSize(d);
		infoFrame.setPreferredSize(d);
		infoFrame.add(scrollPane);	
		
		infoFrame.pack();
		
		infoFrame.setResizable(false);
		infoFrame.setVisible(true);
	}

}
