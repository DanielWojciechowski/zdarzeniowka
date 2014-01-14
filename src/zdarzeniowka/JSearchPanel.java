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
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class JSearchPanel extends JPanel implements ItemListener, ActionListener{

	private static final long serialVersionUID = 4379112746462083318L;
	private final String OPTION1 = "Wyszukaj uzytkownika", OPTION2 = "Wyszukaj sprzet uzytkownika", 
			OPTION3 = "Wyszukaj sprzet sieciowy";
	private JPanel cardSearchPanel;
	private DefaultListModel<String>[] searchListModel;
	private Font normal;
	private JList<String>[] list;
	private JScrollPane[] scrollPane;
	private JPanel[] searchPanel, searchPane, resultPane, buttonPanel;
    private JLabel[] label, label2, result;
    private JTextField[] textField;
    private JButton[] searchButton, deleteButton, showButton;
	private GridBagConstraints c = new GridBagConstraints(), cbutton = new GridBagConstraints(), 
			cpane = new GridBagConstraints(), csearch = new GridBagConstraints(), 
			cresult = new GridBagConstraints();
	private Insets insets1, insets0;
    private JComboBox<String>[] cb;
	private String[] comboBoxItems = {OPTION1, OPTION2, OPTION3}, 
			comboBox1 = {"Imie", "Nazwisko", "Numer pokoju", "Numer albumu"}, 
			comboBox23 = {"Adres MAC", "Adres IP"};
	
	private DBUtil dbUtil = null;
	private String[] category = {"DBUser", "DBUserDevice", "DBNetworkDevice"},
			criterium1 = {"firstName","lastName", "roomNo", "albumNo"},
			criterium2 = {"mac", "ip"};
	private List<DBUser> userResultList = null;
	private List<DBUserDevice> userDeviceResultList = null;
	private List<DBNetworkDevice> networkDeviceResultList = null;
	
	public JSearchPanel(){
		super();
		list = new JList[3];
		scrollPane = new JScrollPane[3];
		searchPanel = new JPanel[3];
		searchPane = new JPanel[3];
		resultPane = new JPanel[3];
		buttonPanel = new JPanel[3];
		label = new JLabel[3];
		label2 = new JLabel[3];
		result = new JLabel[3];
		textField = new JTextField[3];
		searchButton = new JButton[3];
		deleteButton = new JButton[3];
		showButton = new JButton[3];
		c = new GridBagConstraints();
		cbutton = new GridBagConstraints();
		cpane = new GridBagConstraints();
		csearch = new GridBagConstraints();
		cresult = new GridBagConstraints();
		insets1 = new Insets(0,20,10,0);
		insets0 = new Insets(0,0,10,0);
		cb = new JComboBox[4];
		normal = new Font("Open sans", Font.PLAIN, 13);
		searchListModel = new DefaultListModel[3];
		cardSearchPanel = new JPanel(new CardLayout());
        cb[0]= new JComboBox<String>(comboBoxItems);
        cb[1] = new JComboBox<String>(comboBox1);
        cb[2] = new JComboBox<String>(comboBox23);
        cb[3] = new JComboBox<String>(comboBox23);
        cb[0].addItemListener(this);
        this.setLayout(new GridBagLayout());
        
        for (int i = 0; i < 4; i++){
        	cb[i].setFont(normal);
        	if (i > 0) cb[i].addActionListener(this);
        }        
        
        for (int i = 0; i < 3; i++){
        	searchPanel[i] = new JPanel();
    		searchPanel[i].setLayout(new GridBagLayout());
    		searchListModel[i] = new DefaultListModel<String>();
    		list[i] = new JList<String>(searchListModel[i]);
    		list[i].setPrototypeCellValue("Mam nadzieje, ze ta   "
    				+ " glupia funkcja zadziala mehehehhehe        ");
    		list[i].setFont(normal);
    		scrollPane[i] = new JScrollPane(list[i]);  
    		
        	label[i] = new JLabel("Wybierz:");
        	label2[i] = new JLabel("Wpisz:");
        	result[i] = new JLabel("Wyniki:");
        	textField[i] = new JTextField(30);
        	searchButton[i] = new JButton("Szukaj!");
        	searchButton[i].addActionListener(this);
        	deleteButton[i] = new JButton("Usun");
        	showButton[i] = new JButton("Wyświetl");

        	buttonPanel[i] = new JPanel();
        	searchPane[i] = new JPanel();
        	resultPane[i] = new JPanel();
        	buttonPanel[i].setLayout(new GridBagLayout());
        	searchPane[i].setLayout(new GridBagLayout());
        	resultPane[i].setLayout(new GridBagLayout());
        	
        	label[i].setFont(normal);
        	label2[i].setFont(normal);
        	result[i].setFont(normal);
        	deleteButton[i].setFont(normal);
        	searchButton[i].setFont(normal);
        	showButton[i].setFont(normal);
        	textField[i].setFont(normal);
        	       	
        	c.insets = new Insets(50,0,0,0);
        	c.gridx = 0;
        	c.gridy = 0;
        	c.anchor = GridBagConstraints.CENTER;
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
        	c.gridy = 1;
        	searchPanel[i].add(searchPane[i], c);        	
        	
        	c.insets = new Insets(0,18,0,0);
        	cresult.gridheight = 0;
        	cresult.ipadx = 0;
        	cresult.ipady = 0;
        	cresult.anchor = GridBagConstraints.LINE_END;
        	cresult.gridx = 0;
        	cresult.gridy = 0;
        	cresult.insets = new Insets(0, 0, 0, 0);
        	resultPane[i].add(result[i], cresult);
        	cresult.insets = insets1;
        	cresult.anchor = GridBagConstraints.LINE_START;
        	cresult.gridx = 1;
        	cresult.ipady = 75;
        	resultPane[i].add(scrollPane[i], cresult);
        	c.gridy = 2;
        	c.insets = new Insets(10,14,0,0);
        	searchPanel[i].add(resultPane[i], c);
        	
        	cbutton.anchor = GridBagConstraints.FIRST_LINE_END;
        	cbutton.gridy = 0;
        	cbutton.gridx = 0;
        	cbutton.ipadx = 0;
        	cbutton.insets = new Insets(0, 10, 0, 0);
        	buttonPanel[i].add(showButton[i], cbutton);
        	cbutton.gridx = 1;
        	cbutton.ipadx = 20;
        	buttonPanel[i].add(deleteButton[i], cbutton);
        	
        	c.insets = new Insets(0, 0, 0, 0);
        	c.gridy = 3;
        	c.anchor = GridBagConstraints.LINE_END;
         	searchPanel[i].add(buttonPanel[i], c);
        }
        
        cpane.anchor = GridBagConstraints.NORTH;
        cpane.weightx = 1;
    	cpane.weighty = 1;
        cpane.ipadx = 232;
        cpane.gridx = 0;
        cpane.gridy = 0;
        cpane.insets= new Insets(30, 0, 0, 0);
        this.add(cb[0], cpane);
        this.add(cardSearchPanel, cpane);
		
        cardSearchPanel.add(searchPanel[0], OPTION1);
        cardSearchPanel.add(searchPanel[1], OPTION2);
        cardSearchPanel.add(searchPanel[2], OPTION3);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cardSearchPanel.getLayout());
        cl.show(cardSearchPanel, (String)e.getItem());
        for(int i = 0; i < 3; i++){
                searchListModel[i].clear();
	}

}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JComboBox<?>){
			String name = (String)((JComboBox<?>) source).getSelectedItem();
			//System.out.println(name);
			searchListModel[1].addElement(name);
		}
		final int tmp = cb[0].getSelectedIndex();
		if (source == searchButton[tmp]){
			final int tmp2 = cb[tmp+1].getSelectedIndex();
			
			SwingWorker<String, Void> worker = new SwingWorker<String, Void>(){
	            @Override
	            protected String doInBackground() throws Exception {
	    			dbUtil = new DBUtil();
	    			if(tmp == 0)
	    				userResultList = (List<DBUser>) dbUtil.findUserOrDevice(category[tmp], criterium1[tmp2], textField[tmp].getText());
	    			else if(tmp == 1)
	    				userDeviceResultList = (List<DBUserDevice>) dbUtil.findUserOrDevice(category[tmp], criterium2[tmp2], textField[tmp].getText());
	    			else if(tmp == 1)
	    				networkDeviceResultList = (List<DBNetworkDevice>) dbUtil.findUserOrDevice(category[tmp], criterium2[tmp2], textField[tmp].getText());
	    			return category[tmp+1];
	            }
	            
	
	            @Override
	            protected void done() {
	            }
	       };
	       	worker.execute();
		}
	}
		
	}
