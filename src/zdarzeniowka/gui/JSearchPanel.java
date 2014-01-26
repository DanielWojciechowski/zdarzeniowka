package zdarzeniowka.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import zdarzeniowka.db.DBNetworkDevice;
import zdarzeniowka.db.DBUser;
import zdarzeniowka.db.DBUserDevice;

public class JSearchPanel extends JPanel implements ItemListener, ActionListener{

	private static final long serialVersionUID = 4379112746462083318L;
	private final String OPTION1 = "Wyszukaj uzytkownika", OPTION2 = "Wyszukaj sprzet uzytkownika", 
			OPTION3 = "Wyszukaj sprzet sieciowy";
	JPanel cardSearchPanel;
	private JPanel resultPane;
	private JPanel buttonPanel;;

	private Font normal;
	static JMyTable resultTable;
	private JScrollPane scrollPane;
	private JPanel[] searchPanel, searchPane;
    private JLabel[] label, label2;
    private JLabel result;
    JTextField[] textField;
    JButton[] searchButton;
    JButton deleteButton;
	JButton showButton;
	private GridBagConstraints c = new GridBagConstraints(), cbutton = new GridBagConstraints(), 
			cpane = new GridBagConstraints(), csearch = new GridBagConstraints(), 
			cresult = new GridBagConstraints();
	private Insets insets1, insets0;
    JComboBox<String>[] cb;
	String[] comboBoxItems = {OPTION1, OPTION2, OPTION3};
	private String[] comboBox1 = {"Imię", "Nazwisko", "Numer pokoju", "Numer albumu"};
	private String[] comboBox23 = {"Adres MAC", "Adres IP"};
	private String[] columnNames0 = {"Id", "Imie", "Nazwisko", "Pokój", "Nr Albumu"};
	private String[] columnNames1 = {"Id", "Adres IP", "Adres MAC", "Typ"};
	DefaultTableModel userModel;
	DefaultTableModel deviceModel;
	JDSPanel dsPanel;
	Controller cont = new Controller();
	
	String[] category = {"DBUser", "DBUserDevice", "DBNetworkDevice"};
	String[] criterium1 = {"firstName","lastName", "roomNo", "albumNo"};
	String[] criterium2 = {"mac", "ip"};
	List<DBUser> userResultList = null;
	List<DBUserDevice> userDeviceResultList = null;
	List<DBNetworkDevice> networkDeviceResultList = null;
	
	public JSearchPanel(Font font, JDSPanel dsPanel){
		super();
		initiate(font);
		this.dsPanel = dsPanel;
		paint();

	}

	public void initiate(Font font){
		userModel = new DefaultTableModel(columnNames0,0);
    	deviceModel = new DefaultTableModel(columnNames1, 0);
		resultTable = new JMyTable();
		resultTable.setModel(userModel);
		resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		resultTable.getTableHeader().setReorderingAllowed(false);
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
		cb = new JComboBox[4];
		normal = font;
		cardSearchPanel = new JPanel(new CardLayout());
    	deleteButton = new JButton("Usun");
    	deleteButton.addActionListener(this);
    	showButton = new JButton("Wyświetl");
    	showButton.addActionListener(this);
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
	}
	
	private void paint(){
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
        		csearch.ipadx = 245;
        	}
        	else {
        		csearch.ipadx = 272;
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
        	csearch.anchor = GridBagConstraints.LINE_START;
        	searchPane[i].add(textField[i], csearch);
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
        cpane.insets = new Insets(10,0,0,20);
    	this.add(resultPane, cpane);
    	cpane.gridy = 2;
    	cpane.insets = new Insets(0,0,50,20);
        this.add(buttonPanel, cpane);	
        cardSearchPanel.add(searchPanel[0], OPTION1);
        cardSearchPanel.add(searchPanel[1], OPTION2);
        cardSearchPanel.add(searchPanel[2], OPTION3);
	}
	
	public void setComponentsBackground(Color bg){
		this.setBackground(bg);
		cardSearchPanel.setBackground(bg);
		resultPane.setBackground(bg);
		buttonPanel.setBackground(bg);
		for (int i = 0; i < 3; i++){
			searchPanel[i].setBackground(bg);
			searchPane[i].setBackground(bg);
		}
	}
	
	public void showResultFrame(DBUser user){
		String frameLabel = "Użytkownik, id:"+String.valueOf(user.getIdUser());
		JResultFrame resultFrame = new JResultFrame(normal, frameLabel, user, dsPanel, resultTable);
		resultFrame.setComponentsBackground(this.getBackground());
		resultFrame.setVisible(true);
		resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		resultFrame.setLocation(400, 200);
		resultFrame.setResizable(false);
		resultFrame.pack();
		resultFrame.setVisible(true);
	}
	
	public void showResultFrame(DBUserDevice userDevice){
		String frameLabel = "Urządzenie użytkownika, id:"+String.valueOf(userDevice.getIdDevice());
		JResultFrame resultFrame = new JResultFrame(normal, frameLabel, userDevice, resultTable);
		resultFrame.setComponentsBackground(this.getBackground());
		resultFrame.setVisible(true);
		resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		resultFrame.setLocation(400, 200);
		resultFrame.setResizable(false);
		resultFrame.pack();
		resultFrame.setVisible(true);
	}	
	
	public void showResultFrame(DBNetworkDevice networkDevice){
		String frameLabel = "Urządzenie sieciowe, id:"+String.valueOf(networkDevice.getIdDevice());
		JResultFrame resultFrame = new JResultFrame(normal, frameLabel, networkDevice, resultTable);
		resultFrame.setComponentsBackground(this.getBackground());
		resultFrame.setVisible(true);
		resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		resultFrame.setLocation(400, 200);
		resultFrame.setResizable(false);
		resultFrame.pack();
		resultFrame.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		cont.contJSearchPanelISC(e, this);
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		cont.contJSearchPanelAL(e, this);
	}
}
