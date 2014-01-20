package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

public class JReportPanel extends JPanel {
	private static final long serialVersionUID = -6125026078942430487L;
	private Font normal;
	private JPanel cbPane, crPane;
	private JChart chart;
	private JScrollPane scrollPane;
	private JButton saveButton;
	private GridBagConstraints crpane, cbpane, cbutton;
	private String[] comboBox0 = {"Jakis okres ", "Jakis okres"},
			comboBox1 = {"Jakies dane", "Jakies dane"};
    private JComboBox<String>[] cb;
    private JLabel[] label;
	JXTransformer t;
	private Insets insets1, insets0;

	public JReportPanel(Font font){
		super();
		paint(font);
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			DBUtil dbUtil = new DBUtil();
            @Override
            protected Void doInBackground() throws Exception {
            	int[] tab = {1,2,3,4,5,6,7,8,9,10};
        		Generator g = new Generator(tab, chart);
        		g.run();
				return null;
            }
		};
		worker.execute();
		
		
	}
	
	public void paint(Font font){
		normal = font;
		cbPane = new JPanel();
		crPane = new JPanel();
		chart = new JChart(600, 240);
		scrollPane = new JScrollPane(chart);
		saveButton = new JButton("Zapisz do pliku");
		crpane = new GridBagConstraints();
		cbpane = new GridBagConstraints();
		cbutton = new GridBagConstraints();
        cb = new JComboBox[2];
        label = new JLabel[4];
    	insets1 = new Insets(0,20,10,0);
    	insets0 = new Insets(0,0,10,0);
    	

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500, 260));
        this.setLayout(new GridBagLayout());
        cbPane.setLayout(new GridBagLayout());
        crPane.setLayout(new GridBagLayout());
        saveButton.setFont(normal);
        cb[0] = new JComboBox<String>(comboBox0);
        cb[1] = new JComboBox<String>(comboBox1);
        cb[0].setFont(normal);
        cb[1].setFont(normal);
        cb[0].setPrototypeDisplayValue("Bla bla bla bla bla bla bla bla bla bla bla blaBla bla bla bla bla bla bla");
        cb[1].setPrototypeDisplayValue("Bla bla bla bla bla bla bla bla bla bla bla blaBla bla bla bla bla bla bla");
        label[0] = new JLabel("Wybierz okres:");
        label[1] = new JLabel("Wybierz dane: ");
        label[2] = new JLabel("Przepustowość");
        label[3] = new JLabel("Czas");
        label[0].setFont(normal);
        label[1].setFont(normal);
        label[2].setFont(normal);
        label[3].setFont(normal);
    	t = new JXTransformer(label[2]);
        t.rotate(Math.toRadians(-90));
        
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

		cbpane.anchor = GridBagConstraints.LINE_END;
        cbpane.gridy = 2;
        cbPane.add(saveButton, cbpane);


        crpane.insets = new Insets(10,10,0,0);
        crpane.anchor = GridBagConstraints.LINE_END;
        crpane.gridwidth = 2;
        this.add(cbPane, crpane);
        crpane.gridy = 1;
        this.add(crPane, crpane);
        crpane.gridy = 2;
        crpane.gridwidth = 1;
        this.add(t, crpane);
        crpane.gridx = 1;
        crpane.anchor = GridBagConstraints.CENTER;
        this.add(scrollPane, crpane);
        crpane.gridy = 3;
        this.add(label[3], crpane);
        crpane.gridy = 4;
	}
	
	public JChart getJChart(){
		return this.chart;
	}

}
