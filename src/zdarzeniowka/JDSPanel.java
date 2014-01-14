package zdarzeniowka;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class JDSPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5329098283210850224L;
	private JPanel dsPanel0, dsPanel1, dsPanel2 = new JPanel();
	private GridBagConstraints c;
	private Insets cInsets1, cInsets2, cInsets3, cInsets4;
	private Font normal;
	private Border border;
	
	public JDSPanel(){
		dsPanel0 = new JPanel();
		dsPanel1 = new JPanel();
		dsPanel2 = new JPanel();
		border = BorderFactory.createEmptyBorder();
		normal = new Font("Open sans", Font.PLAIN, 13);
		dsPanel0.setLayout(new GridBagLayout());
		dsPanel1.setLayout(new GridBagLayout());
		dsPanel2.setLayout(new GridBagLayout());
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.ipadx=60;
		c.ipady=20;
		c.fill = GridBagConstraints.BOTH;
		cInsets1 = new Insets(0,0,30,10);
		cInsets2 = new Insets(0,0,10,10);
		cInsets3 = new Insets(0,0,0,0);
		cInsets4 = new Insets(0,0,10,0);
		int stage=0;
		for(int j=1;j<=2;j++){
			if (j == 2) {
				c.insets = cInsets2;
			}
			else {
				c.insets = cInsets1;
			}
			for(int i=1;i<=6;i++){
				JButton b = new JButton(String.valueOf(stage+i));
				b.setFont(normal);
				b.addActionListener(this);
				dsPanel0.add(b,c);
				c.gridx++;
			}
			stage+=6; 
			c.gridx=0;
			c.gridy++;
		}
		c.ipadx=50;
		stage=100;
		
		for(int j=1;j<=2;j++){
			if (j == 2) {
				c.insets = cInsets2;
			}
			else {
				c.insets = cInsets1;
			}
			for(int i=1;i<=6;i++){
				JButton b = new JButton(String.valueOf(stage+i));
				b.setFont(normal);
				b.addActionListener(this);
				dsPanel1.add(b,c);
				c.gridx++;
			}
			stage+=6; 
			c.gridx=0;
			c.gridy++;
		}
		stage=200;
		for(int j=1;j<=2;j++){
			if (j == 2) {
				c.insets = cInsets2;
			}
			else {
				c.insets = cInsets1;
			}
			for(int i=1;i<=6;i++){
				JButton b = new JButton(String.valueOf(stage+i));
				b.setFont(normal);
				b.addActionListener(this);
				dsPanel2.add(b,c);
				c.gridx++;
			}
			stage+=6;
			c.gridx=0;
			c.gridy++;
		}
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx=0;
		c.ipady=0;
		c.insets = cInsets3;
		this.add(dsPanel0,c);
		c.gridy = 1;
		c.insets = cInsets4;
		this.add(new JSeparator(JSeparator.HORIZONTAL),c);
		c.gridy = 2;
		c.insets = cInsets3;
		this.add(dsPanel1,c);
		c.insets = cInsets4;
		c.gridy = 3;
		this.add(new JSeparator(JSeparator.HORIZONTAL),c);
		c.gridy = 4;
		c.insets = cInsets3;
		this.add(dsPanel2,c);
		c.gridy = 0;
		c.gridy = 0;
		c.insets = cInsets4;

		TitledBorder stageBorder = BorderFactory.createTitledBorder(border, "Parter");
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		stageBorder.setTitleFont(normal);
		dsPanel0.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piętro I");
		stageBorder.setTitleFont(normal);
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel1.setBorder(stageBorder);
		stageBorder = BorderFactory.createTitledBorder(border, "Piętro II");
		stageBorder.setTitleFont(normal);
		stageBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		dsPanel2.setBorder(stageBorder);
	}
	
	public void showRoomFrame(String buttonText){
		JRoomFrame roomFrame = new JRoomFrame("Pokój nr " + buttonText);
		roomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		roomFrame.setLocation(400, 200);
		roomFrame.setResizable(false);
		roomFrame.pack();
		roomFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JButton){
			String buttonText = ((JButton) source).getText();
			System.out.println(buttonText);
			showRoomFrame(buttonText);
		}
		
	}

}
