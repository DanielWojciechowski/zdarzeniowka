package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JReportPanel extends JPanel {
	private static final long serialVersionUID = -6125026078942430487L;
	private Font normal;
	private JPanel cbPane, buttonPane;
	private JChart report;
	private JScrollPane scrollPane;
	private JButton generateButton, saveButton;
	private GridBagConstraints crpane, cbpane, cbutton;
	private String[] comboBox0 = {"Jakis okres ", "Jakis okres"},
			comboBox1 = {"Jakies dane", "Jakies dane"};
    private JComboBox<String>[] cb;
    private JLabel[] label;
	private Insets insets1, insets0;
	
	public JReportPanel(){
		normal = new Font("Open sans", Font.PLAIN, 13);
		cbPane = new JPanel();
		buttonPane = new JPanel();
		report = new JChart(500, 600);
		scrollPane = new JScrollPane(report);
		generateButton = new JButton("Generuj");
		saveButton = new JButton("Zapisz do pliku");
		crpane = new GridBagConstraints();
		cbpane = new GridBagConstraints();
		cbutton = new GridBagConstraints();
        cb = new JComboBox[2];
        label = new JLabel[2];
    	insets1 = new Insets(0,20,10,0);
    	insets0 = new Insets(0,0,10,0);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        this.setLayout(new GridBagLayout());
        cbPane.setLayout(new GridBagLayout());
        report.setLayout(new GridBagLayout());
        buttonPane.setLayout(new GridBagLayout());
        saveButton.setFont(normal);
        generateButton.setFont(normal);
        cb[0] = new JComboBox<String>(comboBox0);
        cb[1] = new JComboBox<String>(comboBox1);
        cb[0].setFont(normal);
        cb[1].setFont(normal);
        cb[0].setPrototypeDisplayValue("Bla bla bla bla bla bla bla bla bla bla bla blaBla bla bla bla bla b");
        cb[1].setPrototypeDisplayValue("Bla bla bla bla bla bla bla bla bla bla bla blaBla bla bla bla bla b");
        label[0] = new JLabel("Wybierz okres:");
        label[1] = new JLabel("Wybierz dane: ");
        label[0].setFont(normal);
        label[1].setFont(normal);
        
        for (int i = 0; i < 2; i++)
        	{
        	cbpane.insets = insets0;
        	cbpane.gridy = i;
        	cbpane.gridx = 0;
    		cbpane.anchor = GridBagConstraints.LINE_END;
    		cbPane.add(label[i], cbpane);
    		cbpane.insets = insets1;
    		cbpane.anchor = GridBagConstraints.LINE_START;
    		cbpane.gridx = 1;
    		cbPane.add(cb[i], cbpane);
        	}
		
        cbutton.insets = insets0;
        cbutton.ipadx = 30;
        buttonPane.add(generateButton, cbutton);
        cbutton.insets = insets1;
        cbutton.gridx = 1;
        cbutton.ipadx = 0;
        buttonPane.add(saveButton, cbutton);
        
        crpane.insets = new Insets(0, 0, 0, 0);
        crpane.anchor = GridBagConstraints.NORTH;
        this.add(cbPane, crpane);
        crpane.insets = new Insets(10,0,0,0);
        crpane.gridy = 1;
        crpane.anchor = GridBagConstraints.LINE_END;
        this.add(buttonPane, crpane);
        crpane.gridy = 2;
        //crpane.gridheight = 2;
        crpane.anchor = GridBagConstraints.CENTER;
        this.add(scrollPane, crpane);
	}

}
