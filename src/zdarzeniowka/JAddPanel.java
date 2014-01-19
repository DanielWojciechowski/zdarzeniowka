package zdarzeniowka;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

public class JAddPanel extends JPanel implements ItemListener, ActionListener{
	
	private static final long serialVersionUID = -2513979177576640749L;
	private JPanel[] addingPanel, buttonPanel, panel;	
	private JPanel cardAddingPanel;
	private JComboBox<String> addingCB;
	private GridBagConstraints capane = new GridBagConstraints(), cbutton = new GridBagConstraints(),
			c = new GridBagConstraints();
	private JButton[] confirmButton = new JButton[3], clearButton = new JButton[3];
	private final String OPTION1 = "Dodaj użytkownika", OPTION2 = "Dodaj sprzęt użytkownika", 
			OPTION3 = "Dodaj sprzęt sieciowy";
	private String comboBoxItems[] = {OPTION1, OPTION2, OPTION3}; 
	private Font normal;
	
	private DBUtil dbUtil = null;
	private Logger  log = Logger.getLogger(JAddPanel.class);
	private char[] deviceTypes = {'k','p','r','a','i','s'};
	
	public JAddPanel(Font font){
		super();
		paint(font);
	}

	void paint(Font font){
		addingPanel = new JPanel[3];
		buttonPanel = new JPanel[3];
		panel = new JPanel[3];	
		capane = new GridBagConstraints();
		cbutton = new GridBagConstraints();
		c = new GridBagConstraints();
		confirmButton = new JButton[3];
		clearButton = new JButton[3];
		normal = font;
		addingCB = new JComboBox<String>(comboBoxItems);
		addingCB.addItemListener(this);
		addingCB.setFont(normal);
		cardAddingPanel = new JPanel(new CardLayout());
		this.setLayout(new GridBagLayout());
		panel[0] = new JUserPanel(font);
		panel[1] = new JUserDevicePanel(font);
		panel[2] = new JNetworkDevicePanel(font);
		
		for(int i = 0; i < 3; i++){
			addingPanel[i] = new JPanel();
			addingPanel[i].setLayout(new GridBagLayout());
			buttonPanel[i] = new JPanel();
			buttonPanel[i].setLayout(new GridBagLayout());
			confirmButton[i] = new JButton("Dodaj");
			confirmButton[i].addActionListener(this);
			clearButton[i] = new JButton("Wyczysc");
			clearButton[i].addActionListener(this);
			confirmButton[i].setFont(normal);
			clearButton[i].setFont(normal);
			
			cbutton.anchor = GridBagConstraints.LINE_END;
        	cbutton.gridy = 0;
        	cbutton.gridx = 0;
        	cbutton.ipadx = 12;
        	cbutton.insets = new Insets(0, 10, 0, 0);
        	buttonPanel[i].add(confirmButton[i], cbutton);
        	cbutton.gridx = 1;
        	cbutton.ipadx = 0;
        	buttonPanel[i].add(clearButton[i],cbutton);
        	if (i == 0) {
        		c.insets = new Insets(50,0,0,5);
        	}
        	else {
        		c.insets = new Insets(50,0,0,0);
        	}
         
        	c.gridx = 0;
        	c.gridy = 0;
        	c.anchor = GridBagConstraints.CENTER;	
			addingPanel[i].add(panel[i], c);
			
        	if (i == 0) {
        		c.insets = new Insets(20, 0, 152, 8);
        	}
        	else {
        		c.insets = new Insets(20, 30, 20, 0);
        	}
        	c.gridy = 2;
        	c.anchor = GridBagConstraints.LINE_END;
         	addingPanel[i].add(buttonPanel[i], c);
		}
		
        capane.anchor = GridBagConstraints.NORTH;
        capane.weightx = 1;
    	capane.weighty = 1;
        capane.ipadx = 253;
        capane.gridx = 0;
        capane.gridy = 0;
        capane.insets= new Insets(30, 0, 0, 0);
        this.add(addingCB, capane);
        this.add(cardAddingPanel, capane);
		
        cardAddingPanel.add(addingPanel[0], OPTION1);
        cardAddingPanel.add(addingPanel[1], OPTION2);
        cardAddingPanel.add(addingPanel[2], OPTION3);	
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getStateChange() == ItemEvent.DESELECTED){
			String item = (String) e.getItem();
			if(item == comboBoxItems[0])
				((JBasicPanel) panel[0]).clearForm(0);
			else if(item == comboBoxItems[1])
				((JBasicPanel) panel[1]).clearForm(1);
			else if(item == comboBoxItems[2])
				((JBasicPanel) panel[2]).clearForm(2);
		}
		
		Object source = e.getSource();
		if (source == addingCB){
			CardLayout cl = (CardLayout)(cardAddingPanel.getLayout());
	        cl.show(cardAddingPanel, (String)e.getItem());
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		final int tmp = addingCB.getSelectedIndex();
		if (source == confirmButton[tmp]){
			if(((JBasicPanel) panel[tmp]).checkForm(tmp)){
				SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>(){
		            @Override
		            protected Integer doInBackground() throws Exception {
		    			dbUtil = new DBUtil();
		    			if(tmp == 0){
			    			JTextField[] tf = ((JUserPanel)panel[tmp]).textFields;
			    			return dbUtil.addUser(tf[0].getText(), tf[1].getText(), tf[2].getText(), Integer.parseInt(tf[4].getText()), Integer.parseInt(tf[5].getText()), Integer.parseInt(tf[6].getText()));
		    			}
		    			else if(tmp == 1){
			    			JTextField[] tf = ((JUserDevicePanel)panel[tmp]).textFields;
			    			boolean conf = (((JUserDevicePanel)panel[tmp]).cb[0].getSelectedIndex() == 0);
			    			int typeInd = ((JUserDevicePanel)panel[tmp]).cb[0].getSelectedIndex();
			    			String s = ((JUserDevicePanel)panel[tmp]).textArea.getText();
			    			return dbUtil.addUserDevice(tf[0].getText(), tf[1].getText(), deviceTypes[typeInd], conf, s, Integer.parseInt(tf[3].getText()));
		    			}
		    			else if(tmp == 2){
			    			JTextField[] tf = ((JNetworkDevicePanel)panel[tmp]).textFields;
			    			boolean conf = (((JNetworkDevicePanel)panel[tmp]).cb[0].getSelectedIndex() == 0);
			    			int typeInd = ((JNetworkDevicePanel)panel[tmp]).cb[0].getSelectedIndex();  			
			    			String s = ((JNetworkDevicePanel)panel[tmp]).textArea.getText();
			    			return dbUtil.addNetworkDevice(tf[0].getText(), tf[1].getText(), deviceTypes[typeInd], conf, s);
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
		            		if(tmp == 0)
			            		((JUserPanel)panel[tmp]).textFields[3].setText(String.valueOf(id));
			            	else if(tmp == 1)
			            		((JUserDevicePanel)panel[tmp]).textFields[2].setText(String.valueOf(id));
			            	else if(tmp == 2)
			            		((JNetworkDevicePanel)panel[tmp]).textFields[2].setText(String.valueOf(id));
		            	}
		            	else{
		            		JOptionPane.showMessageDialog(cardAddingPanel, "Dodawanie nie powiodło się! Sprawdź poprawność danych.", 
		            				"Błąd dodawania", JOptionPane.ERROR_MESSAGE);
		        			log.error("Błąd dodawania");
		            	}
		            }
		       };
		       	worker.execute();
			}
			else{
				
			}
		}
		else if(source == clearButton[tmp]){
			log.info("Naciśnięto przycisk Wyczyść!");
			((JBasicPanel) panel[tmp]).clearForm(tmp);
		}
		
	}

}
