package zdarzeniowka;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class JAddPanel extends JPanel implements ItemListener{
	
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
	
	public JAddPanel(){
		super();
		addingPanel = new JPanel[3];
		buttonPanel = new JPanel[3];
		panel = new JPanel[3];	
		capane = new GridBagConstraints();
		cbutton = new GridBagConstraints();
		c = new GridBagConstraints();
		confirmButton = new JButton[3];
		clearButton = new JButton[3];
		normal = new Font("Open sans", Font.PLAIN, 13);
		addingCB = new JComboBox<String>(comboBoxItems);
		addingCB.addItemListener(this);
		addingCB.setFont(normal);
		cardAddingPanel = new JPanel(new CardLayout());
		this.setLayout(new GridBagLayout());
		panel[0] = new JUserPanel();
		panel[1] = new JUserDevicePanel();
		panel[2] = new JNetworkDevicePanel();
		
		for(int i = 0; i < 3; i++){
			addingPanel[i] = new JPanel();
			addingPanel[i].setLayout(new GridBagLayout());
			buttonPanel[i] = new JPanel();
			buttonPanel[i].setLayout(new GridBagLayout());
			confirmButton[i] = new JButton("Dodaj");
			clearButton[i] = new JButton("Wyczysc");
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
        
        	c.insets = new Insets(50,0,0,0);
        	c.gridx = 0;
        	c.gridy = 0;
        	c.anchor = GridBagConstraints.CENTER;	
			addingPanel[i].add(panel[i], c);
			
        	if (i == 0) {
        		c.insets = new Insets(20, 0, 152, 0);
        	}
        	else {
        		c.insets = new Insets(20, 0, 20, 0);
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
		Object source = e.getSource();
		if (source == addingCB){
			CardLayout cl = (CardLayout)(cardAddingPanel.getLayout());
	        cl.show(cardAddingPanel, (String)e.getItem());
	        
		}
		
	}

}
