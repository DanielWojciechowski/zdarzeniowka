package zdarzeniowka;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public abstract class JBasicPanel extends JPanel{
	private static final long serialVersionUID = 5020407406483635930L;
	protected JPanel topPanel, botPanel;
	protected JScrollPane scrollPane;
	protected JTextArea textArea;
	protected JComboBox<String>[] cb;
	protected JLabel[] labels;
	protected JTextField[] textFields; 
	protected GridBagConstraints cpane, ctop, cbot;
	protected Insets insets1, insets0;
	protected Font normal;
	protected boolean editable;
	
	public JBasicPanel() {
		super();
		normal = new Font("Open sans", Font.PLAIN, 13);
		editable = true;
		
		topPanel = new JPanel();
		botPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		botPanel.setLayout(new GridBagLayout());
		cpane = new GridBagConstraints();
		ctop = new GridBagConstraints();
		cbot = new GridBagConstraints();
		insets1 = new Insets(0,20,10,0);
		insets0 = new Insets(0,0,10,0);
	}
	
	public void setTextField(int index, String text){ //ma rzucac wyjatek jezeli indeks przekracza tablice
		textFields[index].setText(text);		
	}
		
	public void editabling(boolean value){
		editable = value;
		for (int i = 0; i < textFields.length; i++){
			if (i != 3){
				textFields[i].setEditable(editable);
			}
			
		}
		if (cb != null){
			for (int i = 0; i < cb.length; i++){
				cb[i].setEnabled(editable);
			}
			
		}
		if (textArea != null)
			textArea.setEditable(editable);
	}

}
