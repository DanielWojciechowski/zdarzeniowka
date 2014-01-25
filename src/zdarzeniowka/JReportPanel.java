package zdarzeniowka;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
	private JPanel  topPane, chartPane, botPane;
	private JChart chart;
	private JButton saveButton;
	private GridBagConstraints c, ctop, cchart, cbot;
	private String[] comboBox0 = {"Zużycie sieci ogółem", "Zużycie sieci wg użytkowników"}, comboBox1 = {"Jakies dane", "Jakies dane"};
    private JComboBox<String> cb;
    private JSpinner[] spinner;
    private JLabel[] label;
	private JXTransformer t;
	private Insets insets1, insets0;
	private DBUtil dbUtil =  new DBUtil();
	private Date latestDate, earliestDate;

    public JReportPanel(Font font){
            super();
            paint(font);
            
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
                   
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
		botPane = new JPanel();
		topPane = new JPanel();
		chartPane = new JPanel();
		chart = new JChart(450, 240);
		
		saveButton = new JButton("Zapisz do pliku");
		c = new GridBagConstraints();
		ctop = new GridBagConstraints();
		cchart = new GridBagConstraints();
		cbot = new GridBagConstraints();
        cb = new JComboBox<String>(comboBox0);
        label = new JLabel[8];
    	insets1 = new Insets(0,20,10,0);
    	insets0 = new Insets(0,0,10,0);
        this.setLayout(new GridBagLayout());
        botPane.setLayout(new GridBagLayout());
        topPane.setLayout(new GridBagLayout());
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
        t.rotate(Math.toRadians(90)); 

        latestDate = dbUtil.getEarliestDate();
        earliestDate = dbUtil.getLatestDate();
        
		SpinnerModel model0 = new SpinnerDateModel(
		        latestDate,
		        new Date(latestDate.getTime() - 86400000), //start
		        earliestDate, //end
		        Calendar.DAY_OF_MONTH
		); 
		SpinnerModel model1 = new SpinnerDateModel(
		        earliestDate,
		        new Date(latestDate.getTime() - 86400000), //start
		        earliestDate, //end
		        Calendar.DAY_OF_MONTH
		);
	    spinner = new JSpinner[2];
		spinner[0] = new JSpinner(model0);
		spinner[1] = new JSpinner(model1);
		spinner[0].setEditor(new JSpinner.DateEditor(spinner[0], "dd.MM.yyyy"));
	    spinner[1].setEditor(new JSpinner.DateEditor(spinner[1], "dd.MM.yyyy"));
	    //spinner[0].;
	    spinner[0].setFont(normal);
	    spinner[1].setFont(normal);
	    spinner[0].setPreferredSize(new Dimension(130,22));
	    spinner[1].setPreferredSize(new Dimension(130,22));

        ctop.anchor = GridBagConstraints.LINE_END;
        ctop.insets = insets0;
        topPane.add(label[0], ctop);
        ctop.insets = insets1;
        ctop.gridx = 1;
        topPane.add(spinner[0], ctop);
        ctop.gridx = 2;
        topPane.add(label[1], ctop);
        ctop.gridx = 3;
        topPane.add(spinner[1], ctop);
        
        ctop.insets = insets0;
        ctop.gridy = 1;
        ctop.gridx = 0;
    	ctop.anchor = GridBagConstraints.LINE_END;
    	topPane.add(label[2], ctop);
    	ctop.insets = insets1;
    	ctop.anchor = GridBagConstraints.LINE_START;
    	ctop.gridx = 1;
    	ctop.gridwidth = 3;
    	topPane.add(cb, ctop);
		ctop.anchor = GridBagConstraints.LINE_END;
        ctop.gridy = 2;
        ctop.gridx = 3;
        ctop.gridwidth = 0;
        ctop.insets = new Insets(10, 10, 0, 0);
        topPane.add(saveButton, ctop);
        //crPane.setBorder(BorderFactory.createLineBorder(Color.black));
        
        cchart.insets = new Insets(30, 0, 0, 5);
        cchart.anchor = GridBagConstraints.FIRST_LINE_END;
        chartPane.add(label[3], cchart);
        cchart.insets = new Insets(0, 0, 0, 0);
        cchart.ipady = 0;
        cchart.gridx = 1;
        cchart.gridwidth = 3;
        chartPane.add(chart, cchart);
        cchart.gridwidth = 1;
        cchart.gridx = 1;
        cchart.gridy = 1;
        chartPane.add(label[4], cchart);
        cchart.gridx = 3;
        chartPane.add(label[5], cchart);
        
       
        cbot.anchor = GridBagConstraints.LINE_END;
        cbot.gridwidth = 1;
        cbot.insets = new Insets(0, 0, 0, 0);
        botPane.add(chartPane, cbot);
        cbot.anchor = GridBagConstraints.CENTER;
        cbot.gridx = 1;
        cbot.insets = new Insets(0, 0, 20, 0);
        botPane.add(t, cbot);
        cbot.gridx = 0;
        cbot.gridwidth = 0;
        cbot.gridy = 2;
        botPane.add(label[7], cbot);
        
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setPreferredSize(new Dimension(600,1));
        separator.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
        c.insets = new Insets(10,0,0,0);
        c.anchor = GridBagConstraints.CENTER;
        this.add(topPane, c);
        c.gridy = 1;
        c.insets = new Insets(20,0,0,0);
        this.add(botPane, c);
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
