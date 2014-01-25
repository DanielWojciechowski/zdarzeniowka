package zdarzeniowka;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;


public class JReportPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -6125026078942430487L;
	private Font normal;
	private JPanel cbPane, crPane, chartPane;
	private JChart chart;
	private JButton saveButton;
	private GridBagConstraints crpane, cbp, csp, cp;
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
                List<Integer> tab = dbUtil.getAllUsersIds();
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
		chartPane = new JPanel();
		chart = new JChart(450, 240);
		
		saveButton = new JButton("Zapisz do pliku");
		crpane = new GridBagConstraints();
		cbp = new GridBagConstraints();
		csp = new GridBagConstraints();
		cp = new GridBagConstraints();
        cb = new JComboBox<String>(comboBox0);
        label = new JLabel[8];
    	insets1 = new Insets(0,20,10,0);
    	insets0 = new Insets(0,0,10,0);
        this.setLayout(new GridBagLayout());
        cbPane.setLayout(new GridBagLayout());
        crPane.setLayout(new GridBagLayout());
        chartPane.setLayout(new GridBagLayout());
        saveButton.setFont(normal);
        saveButton.addActionListener(this);
        cb.setFont(normal);
        cb.setPrototypeDisplayValue("Bla bla bla bla bla bla bla bla bla bla wow such bla");
        label[0] = new JLabel("Wybierz okres od:");
        label[1] = new JLabel("do:");
        label[2] = new JLabel("Wybierz dane: ");
        label[3] = new JLabel("20MB/s");
        label[4] = new JLabel("0");
        label[5] = new JLabel("90");
        label[6] = new JLabel("Transfer [MB]");
        label[7] = new JLabel("Czas [s]");
        label[0].setFont(normal);
        label[1].setFont(normal);
        label[2].setFont(normal);
        label[3].setFont(normal);
        label[4].setFont(normal);
        label[5].setFont(normal);
        label[6].setFont(normal);
        label[7].setFont(normal);
    	t = new JXTransformer(label[6]);
        t.rotate(Math.toRadians(-90)); 
        
        spinner = new JSpinner[2];

        Date latestDate = new Date(5000000);
        Date earliestDate =new Date(System.currentTimeMillis());
        	
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
		spinner[0] = new JSpinner(model0);
		spinner[1] = new JSpinner(model1);

	    spinner[0].setFont(normal);
	    spinner[1].setFont(normal);
	    spinner[0].setPreferredSize(new Dimension(130,22));
	    spinner[1].setPreferredSize(new Dimension(130,22));
	    spinner[0].setEditor(new JSpinner.DateEditor(spinner[0], "dd.MM.yyyy"));
	    spinner[1].setEditor(new JSpinner.DateEditor(spinner[1], "dd.MM.yyyy"));

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
        
        csp.insets = insets0;
        csp.gridy = 1;
        csp.gridx = 0;
    	csp.anchor = GridBagConstraints.LINE_END;
    	crPane.add(label[2], csp);
    	csp.insets = insets1;
    	csp.anchor = GridBagConstraints.LINE_START;
    	csp.gridx = 1;
    	csp.gridwidth = 3;
    	crPane.add(cb, csp);
		csp.anchor = GridBagConstraints.LINE_END;
        csp.gridy = 2;
        csp.gridx = 3;
        csp.gridwidth = 0;
        csp.insets = new Insets(10, 10, 0, 0);
        crPane.add(saveButton, csp);
        //crPane.setBorder(BorderFactory.createLineBorder(Color.black));
        
        cp.insets = new Insets(30, 10, 0, 0);
        cp.anchor = GridBagConstraints.FIRST_LINE_END;
        chartPane.add(label[3], cp);
        cp.insets = new Insets(0, 10, 0, 0);
        cp.ipady = 0;
        cp.gridx = 1;
        cp.gridwidth = 3;
        chartPane.add(chart, cp);
        cp.gridwidth = 1;
        cp.gridx = 1;
        cp.gridy = 1;
        chartPane.add(label[4], cp);
        cp.gridx = 3;
        chartPane.add(label[5], cp);
        
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setPreferredSize(new Dimension(600,1));
        separator.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
        crpane.gridwidth = 2;
        crpane.insets = new Insets(10,0,0,0);
        crpane.anchor = GridBagConstraints.LINE_END;
        this.add(crPane, crpane);
        crpane.gridy = 1;
        this.add(cbPane, crpane);
        crpane.gridy = 2;
        crpane.insets = new Insets(20,0,20,0);
        crpane.anchor = GridBagConstraints.CENTER;
        crpane.gridwidth = 4;
        this.add(separator, crpane);
        crpane.anchor = GridBagConstraints.LINE_END;
        crpane.gridy = 3;
        crpane.gridwidth = 1;
        crpane.insets = new Insets(0, 0, 0, 0);
        this.add(t, crpane);
        crpane.anchor = GridBagConstraints.CENTER;
        this.add(chartPane, crpane);
        crpane.gridwidth = 0;
        crpane.gridy = 4;
        crpane.insets = new Insets(0, 0, 10, 0);
        this.add(label[7], crpane);
        crpane.gridy = 5;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		Date start = (Date) spinner[0].getValue();
		Date end = (Date) spinner[1].getValue();
		char data;
		if (cb.getSelectedIndex() == 0){
			data = '0';
		}
		else {
			data = '1';
		}
		System.out.println(end.toString() + " " + start.toString() + " "+ data);
		if (source == saveButton){
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
            	String fileName = file.getPath();
				if(!fileName.endsWith(".txt")) {	
					file = new File(fileName + ".txt");
				}
                try {
                    FileWriter out = new FileWriter(file);
                    //String pom = textArea.getText();        
                    //out.write(pom.replaceAll("\n", System.getProperty("line.separator")));
                    out.close();
                } catch (IOException e1) {
                   JOptionPane.showMessageDialog(this, "Nie moge zapisac pliku " +file.getAbsolutePath());
                    return;
                }
			}
		}
		
	}

}
