package zdarzeniowka;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

public class JSearchPanel extends JPanel implements ItemListener, ActionListener{

	private static final long serialVersionUID = 4379112746462083318L;
	private final String OPTION1 = "Wyszukaj uzytkownika", OPTION2 = "Wyszukaj sprzet uzytkownika", 
			OPTION3 = "Wyszukaj sprzet sieciowy";
	private JPanel cardSearchPanel, resultPane, buttonPanel;;
	private Font normal;
	private JTable resultTable;
	private JScrollPane scrollPane;
	private JPanel[] searchPanel, searchPane;
    private JLabel[] label, label2;
    private JLabel result;
    private JTextField[] textField;
    private JButton[] searchButton;
    private JButton deleteButton, showButton;
	private GridBagConstraints c = new GridBagConstraints(), cbutton = new GridBagConstraints(), 
			cpane = new GridBagConstraints(), csearch = new GridBagConstraints(), 
			cresult = new GridBagConstraints();
	private Insets insets1, insets0;
    private JComboBox<String>[] cb;
	private String[] comboBoxItems = {OPTION1, OPTION2, OPTION3}, 
			comboBox1 = {"Imie", "Nazwisko", "Numer pokoju", "Numer albumu"}, 
			comboBox23 = {"Adres MAC", "Adres IP"}, 
			columnNames0 = {"Id", "Imie", "Nazwisko", "Pokój", "Nr Albumu"}, 
			columnNames1 = {"Id", "Adres IP", "Adres MAC", "Typ"};
	private String[][] data0 = {{"", "", "", "", ""}}, data1 = {{"", "", "", ""}};
	private Dimension d;
	private DefaultTableModel userModel, deviceModel;
	
	private DBUtil dbUtil = null;
	private String[] category = {"DBUser", "DBUserDevice", "DBNetworkDevice"},
			criterium1 = {"firstName","lastName", "roomNo", "albumNo"},
			criterium2 = {"mac", "ip"};
	private List<DBUser> userResultList = null;
	private List<DBUserDevice> userDeviceResultList = null;
	private List<DBNetworkDevice> networkDeviceResultList = null;
    private Logger  log = Logger.getLogger(JSearchPanel.class);
	
	public JSearchPanel(){
		super();
    	userModel = new DefaultTableModel(columnNames0,0);
    	deviceModel = new DefaultTableModel(columnNames1, 0);
		resultTable = new JTable(userModel);
		scrollPane = new JScrollPane(resultTable);
		searchPanel = new JPanel[3];
		searchPane = new JPanel[3];
		resultPane = new JPanel();
		buttonPanel = new JPanel();
		label = new JLabel[3];
		label2 = new JLabel[3];
		result = new JLabel();
		textField = new JTextField[3];
		searchButton = new JButton[3];
		c = new GridBagConstraints();
		cbutton = new GridBagConstraints();
		cpane = new GridBagConstraints();
		csearch = new GridBagConstraints();
		cresult = new GridBagConstraints();
		insets1 = new Insets(0,20,10,0);
		insets0 = new Insets(0,0,10,0);
		d = new Dimension(330,120);
		cb = new JComboBox[4];
		normal = new Font("Open sans", Font.PLAIN, 13);
		cardSearchPanel = new JPanel(new CardLayout());
    	deleteButton = new JButton("Usun");
    	showButton = new JButton("Wyświetl");
    	result = new JLabel("Wyniki:");
    	result.setFont(normal);
    	buttonPanel.setLayout(new GridBagLayout());
    	resultPane.setLayout(new GridBagLayout());
    	deleteButton.setFont(normal);
    	showButton.setFont(normal);
        cb[0]= new JComboBox<String>(comboBoxItems);
        cb[1] = new JComboBox<String>(comboBox1);
        cb[2] = new JComboBox<String>(comboBox23);
        cb[3] = new JComboBox<String>(comboBox23);
        cb[0].addItemListener(this);
        this.setLayout(new GridBagLayout());
	    resultTable.setPreferredScrollableViewportSize(new Dimension(360, 130)); 
	    resultTable.setFillsViewportHeight(true); 
	    
        for (int i = 0; i < 4; i++){
        	cb[i].setFont(normal);
        	if (i > 0) cb[i].addActionListener(this);
        }        
        
        for (int i = 0; i < 3; i++){
        	searchPanel[i] = new JPanel();
    		searchPanel[i].setLayout(new GridBagLayout());
    		label[i] = new JLabel("Wybierz:");
        	label2[i] = new JLabel("Wpisz:");
        	textField[i] = new JTextField(30);
        	searchButton[i] = new JButton("Szukaj!");
        	searchButton[i].addActionListener(this);
        	searchPane[i] = new JPanel();
        	searchPane[i].setLayout(new GridBagLayout());
        	label[i].setFont(normal);
        	label2[i].setFont(normal);
        	searchButton[i].setFont(normal);
            textField[i].setFont(normal);
        	csearch.insets = insets0;
        	csearch.anchor = GridBagConstraints.LINE_END;
        	csearch.gridx = 0;
        	csearch.gridy = 0;
        	searchPane[i].add(label[i], csearch);
        	csearch.anchor = GridBagConstraints.LINE_START;
        	csearch.gridx = 1;
        	csearch.insets = insets1;
        	if(i == 0){
        		csearch.ipadx = 243;
        	}
        	else {
        		csearch.ipadx = 270;
        	}
        	searchPane[i].add(cb[i+1], csearch);
        	csearch.ipadx = 0;
        	csearch.gridx = 0;
        	csearch.gridy = 1;
        	csearch.insets = insets0;
        	csearch.anchor = GridBagConstraints.LINE_END;
        	searchPane[i].add(label2[i], csearch);
        	csearch.gridx = 1;
        	csearch.insets = insets1;
        	csearch.ipady = 6;
        	csearch.anchor = GridBagConstraints.LINE_START;
        	searchPane[i].add(textField[i], csearch);
        	csearch.ipadx = 0;
        	csearch.ipady = 0;
        	csearch.insets = insets0;
        	csearch.anchor = GridBagConstraints.LINE_END;
        	csearch.gridy = 2;
        	csearch.gridx = 1;
        	searchPane[i].add(searchButton[i], csearch);
        	
        	c.insets = new Insets(52,0,0,0);
        	c.gridx = 0;
        	c.gridy = 0;
        	c.anchor = GridBagConstraints.CENTER;
        	searchPanel[i].add(searchPane[i], c);        	
	    }
        
		cresult.gridheight = 0;
		cresult.fill = GridBagConstraints.BOTH;
    	cresult.ipadx = 0;
    	cresult.ipady = 0;
    	cresult.anchor = GridBagConstraints.LINE_END;
    	cresult.gridx = 0;
    	cresult.gridy = 0;
    	cresult.insets = new Insets(0, 0, 0, 0);
    	resultPane.add(result, cresult);
    	cresult.insets = insets1;
    	cresult.anchor = GridBagConstraints.LINE_START;
    	cresult.gridx = 1;
    	cresult.ipady = 75;
    	resultPane.add(scrollPane, cresult);

        cbutton.anchor = GridBagConstraints.FIRST_LINE_END;
        cbutton.gridy = 0;
        cbutton.gridx = 0;
        cbutton.ipadx = 0;
        cbutton.insets = new Insets(0, 10, 0, 0);
        buttonPanel.add(showButton, cbutton);
        cbutton.gridx = 1;
        cbutton.ipadx = 20;
        buttonPanel.add(deleteButton, cbutton);
          	
        cpane.anchor = GridBagConstraints.NORTH;
        cpane.weightx = 1;
    	cpane.weighty = 1;
        cpane.ipadx = 232;
        cpane.gridx = 0;
        cpane.gridy = 0;
        cpane.insets= new Insets(30, 0, 0, 0);
        this.add(cb[0], cpane);
        this.add(cardSearchPanel, cpane);
        cpane.gridy = 1;
        cpane.anchor = GridBagConstraints.LINE_END;
        cpane.insets = new Insets(10,0,0,15);
    	this.add(resultPane, cpane);
    	cpane.gridy = 2;
    	cpane.insets = new Insets(0,0,50,15);
        this.add(buttonPanel, cpane);
		
        cardSearchPanel.add(searchPanel[0], OPTION1);
        cardSearchPanel.add(searchPanel[1], OPTION2);
        cardSearchPanel.add(searchPanel[2], OPTION3);
	}
	
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cardSearchPanel.getLayout());
        cl.show(cardSearchPanel, (String)e.getItem());
        final int tmp = cb[0].getSelectedIndex();
		if (tmp != 0){
			resultTable.setModel(deviceModel);
		}
		else {
			resultTable.setModel(userModel);		
		}
	}

	public void displayColumns(int j, String[] columnNames, String[][] dataValues){   
	    this.revalidate();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JComboBox<?>){
		}
		final int tmp = cb[0].getSelectedIndex();
		if (source == searchButton[tmp]){
			log.info("Wciśnięto przycisk szukaj");
			final int tmp2 = cb[tmp+1].getSelectedIndex();
			
			SwingWorker<String, Void> worker = new SwingWorker<String, Void>(){
	            @Override
	            protected String doInBackground() throws Exception {
	    			dbUtil = new DBUtil();
	    			if(tmp == 0)
	    				userResultList = castList(DBUser.class, dbUtil.findUserOrDevice(category[tmp], criterium1[tmp2], textField[tmp].getText()));
	    			else if(tmp == 1)
	    				userDeviceResultList = castList(DBUserDevice.class, dbUtil.findUserOrDevice(category[tmp], criterium2[tmp2], textField[tmp].getText()));
	    			else if(tmp == 2)
	    				networkDeviceResultList = castList(DBNetworkDevice.class, dbUtil.findUserOrDevice(category[tmp], criterium2[tmp2], textField[tmp].getText()));
	    			return category[tmp];
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
	            	if(cat == category[0]){
	            		log.info("Listowanie Userów, liczba wyników: "+ userResultList.size());
	            		userModel.setRowCount(0);
	            		for(Iterator<DBUser> iter = userResultList.iterator(); iter.hasNext();){
	            			DBUser user = iter.next();
	            			userModel.addRow(new Object[]{String.valueOf(user.getIdUser()),
	            					user.getFirstName(),user.getLastName(),
	            					String.valueOf(user.getRoomNo()),
	            					String.valueOf(user.getAlbumNo())});
	            		}
	            	}
	            	else if(cat == category[1]){
	            		log.info("Listowanie Urządzeń Usera, liczba wyników: "+ userDeviceResultList.size());
	            		deviceModel.setRowCount(0);
	            		for(Iterator<DBUserDevice> iter = userDeviceResultList.iterator(); iter.hasNext();){
	            			DBUserDevice device = iter.next();
	            			deviceModel.addRow(new Object[]{String.valueOf(device.getIdDevice()),
	            					device.getIp(),device.getMac(),
	            					String.valueOf(device.getType()),});
	            		}
	            	}
	            	else if(cat == category[2]){
	            		log.info("Listowanie Urządzeń Sieciowych, liczba wyników: "+ networkDeviceResultList.size());
	            		deviceModel.setRowCount(0);
	            		for(Iterator<DBNetworkDevice> iter = networkDeviceResultList.iterator(); iter.hasNext();){
	            			DBNetworkDevice device = iter.next();
	            			deviceModel.addRow(new Object[]{String.valueOf(device.getIdDevice()),
	            					device.getIp(),device.getMac(),
	            					String.valueOf(device.getType()),});
	            		}
	            	}
	            	resultTable.repaint();
	            }
	       };
	       	worker.execute();
		}
	}
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}	
		
}
