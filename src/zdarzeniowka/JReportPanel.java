package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingWorker;

public class JReportPanel extends JPanel {
	private static final long serialVersionUID = -6125026078942430487L;
	private Font normal;
	private JPanel cbPane, crPane;
	private JChart chart;
	private JScrollPane scrollPane;
	private JButton saveButton;
	private GridBagConstraints crpane, cbp, csp;
	private String[] comboBox0 = {"Zużycie sieci ogółem", "Zużycie sieci wg użytkowników"}, comboBox1 = {"Jakies dane", "Jakies dane"};
    private JComboBox<String> cb;
    private JSpinner[] spinner;
    private JLabel[] label;
	private JXTransformer t;
	private Insets insets1, insets0;
	private DBUtil dbutil = null;

	public JReportPanel(Font font){
		super();
		paint(font);
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			DBUtil dbUtil = new DBUtil();
            @Override
            protected Void doInBackground() throws Exception {
            	int[] tab = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        		Generator g = new Generator(tab, chart);
        		g.run();
				return null;
            }
		};
		worker.execute();
	}
	
	private void paint(Font font){
		normal = font;
		cbPane = new JPanel();
		crPane = new JPanel();
		chart = new JChart(600, 250);
		scrollPane = new JScrollPane(chart);
		saveButton = new JButton("Zapisz do pliku");
		crpane = new GridBagConstraints();
		cbp = new GridBagConstraints();
		csp = new GridBagConstraints();
        cb = new JComboBox<String>(comboBox0);
        label = new JLabel[5];
    	insets1 = new Insets(0,20,10,0);
    	insets0 = new Insets(0,0,10,0);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500, 270));
        this.setLayout(new GridBagLayout());
        cbPane.setLayout(new GridBagLayout());
        crPane.setLayout(new GridBagLayout());
        saveButton.setFont(normal);
        cb.setFont(normal);
        cb.setPrototypeDisplayValue("Bla bla bla bla bla bla bla bla bla bla wow such bla");
        label[0] = new JLabel("Wybierz okres od:");
        label[1] = new JLabel("do:");
        label[2] = new JLabel("Wybierz dane: ");
        label[3] = new JLabel("Przepustowość");
        label[4] = new JLabel("Czas");
        label[0].setFont(normal);
        label[1].setFont(normal);
        label[2].setFont(normal);
        label[3].setFont(normal);
        label[4].setFont(normal);
    	t = new JXTransformer(label[3]);
        t.rotate(Math.toRadians(-90)); 
        
        //dbutil = new DBUtil();
        Date latestDate = new Date(0);//dbutil.getLatestDate();
        Date earliestDate = new Date(System.currentTimeMillis());//dbutil.getEarliestDate();
        SpinnerModel model0 = new SpinnerDateModel(
        		latestDate,
                latestDate, //start
                earliestDate, //end
                Calendar.DAY_OF_MONTH
        ); 
        SpinnerModel model1 = new SpinnerDateModel(
                earliestDate,
                latestDate, //start
                earliestDate, //end
                Calendar.DAY_OF_MONTH
        ); 
        spinner = new JSpinner[2];
        spinner[0] = new JSpinner(model0);
        spinner[1] = new JSpinner(model1);
        spinner[0].setFont(normal);
        spinner[1].setFont(normal);
        spinner[0].setPreferredSize(new Dimension(130,22));
        spinner[1].setPreferredSize(new Dimension(130,22));
        
        csp.anchor = GridBagConstraints.LINE_END;
        csp.insets = insets0;
        crPane.add(label[0], csp);
        csp.insets = insets1;
        csp.gridx = 1;
        crPane.add(spinner[0], csp);
        csp.gridx = 2;
        crPane.add(label[1], csp);
        csp.gridx = 3;
        crPane.add(spinner[1], csp);
        
        cbp.insets = insets0;
        cbp.gridy = 1;
        cbp.gridx = 0;
    	cbp.anchor = GridBagConstraints.LINE_END;
    	cbPane.add(label[2], cbp);
    	cbp.insets = insets1;
    	cbp.anchor = GridBagConstraints.LINE_START;
    	cbp.gridx = 1;
    	cbPane.add(cb, cbp);
		cbp.anchor = GridBagConstraints.LINE_END;
        cbp.gridy = 2;
        cbp.insets = new Insets(10, 10, 0, 0);
        cbPane.add(saveButton, cbp);
        
        crpane.insets = new Insets(10,0,0,40);
        crpane.anchor = GridBagConstraints.LINE_END;
        crpane.gridwidth = 2;
        this.add(crPane, crpane);
        crpane.gridy = 1;
        this.add(cbPane, crpane);
        crpane.gridy = 2;
        crpane.gridwidth = 1;
        this.add(t, crpane);
        crpane.gridx = 1;
        crpane.anchor = GridBagConstraints.CENTER;
        crpane.insets = new Insets(20, 0, 0, 0);
        this.add(scrollPane, crpane);

        crpane.gridwidth = 0;
        crpane.gridy = 3;
        this.add(label[4], crpane);
        crpane.gridy = 4;
	}

}
