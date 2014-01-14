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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class JSearchPanel extends JPanel implements ItemListener, ActionListener{

	private static final long serialVersionUID = 4379112746462083318L;
	private JPanel cardSearchPanel;
	private DefaultListModel<String>[] searchListModel;
	private Font normal;
	private JList<String>[] list = new JList[3];
	private JScrollPane[] scrollPane = new JScrollPane[3];
	private JPanel[] searchPanel = new JPanel[3], searchPane = new JPanel[3], 
			resultPane = new JPanel[3], buttonPanel = new JPanel[3];
    private JLabel[] label = new JLabel[3], label2 = new JLabel[3], result = new JLabel[3];
    private JTextField[] textField = new JTextField[3];
    private JButton[] searchButtonS = new JButton[3], 
    		 deleteButtonS = new JButton[3], showButtonS = new JButton[3];
	private GridBagConstraints c = new GridBagConstraints(), cbutton = new GridBagConstraints(), 
			cpane = new GridBagConstraints(), csearch = new GridBagConstraints(), 
			cresult = new GridBagConstraints();
	private Insets insets1 = new Insets(0,20,10,0), insets0 = new Insets(0,0,10,0);
	private final String OPTION1 = "Wyszukaj uzytkownika", OPTION2 = "Wyszukaj sprzet uzytkownika", 
			OPTION3 = "Wyszukaj sprzet sieciowy";
	private String[] comboBoxItems = {OPTION1, OPTION2, OPTION3}, 
			comboBox1 = {"Imie", "Nazwisko", "Numer pokoju", "Numer albumu"}, 
			comboBox23 = {"Adres MAC", "Adres IP"};
    private JComboBox<String>[] cb = new JComboBox[4];
	
	public JSearchPanel(){
		super();
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
        	searchButtonS[i] = new JButton("Szukaj!");
        	deleteButtonS[i] = new JButton("Usun");
        	showButtonS[i] = new JButton("Wyświetl");

        	buttonPanel[i] = new JPanel();
        	searchPane[i] = new JPanel();
        	resultPane[i] = new JPanel();
        	buttonPanel[i].setLayout(new GridBagLayout());
        	searchPane[i].setLayout(new GridBagLayout());
        	resultPane[i].setLayout(new GridBagLayout());
        	
        	label[i].setFont(normal);
        	label2[i].setFont(normal);
        	result[i].setFont(normal);
        	deleteButtonS[i].setFont(normal);
        	searchButtonS[i].setFont(normal);
        	showButtonS[i].setFont(normal);
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
        	searchPane[i].add(searchButtonS[i], csearch);
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
        	buttonPanel[i].add(showButtonS[i], cbutton);
        	cbutton.gridx = 1;
        	cbutton.ipadx = 20;
        	buttonPanel[i].add(deleteButtonS[i], cbutton);
        	
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
		Object source = e.getSource();
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
			System.out.println(name);
			searchListModel[1].addElement(name);
		}
	}
		
	}
