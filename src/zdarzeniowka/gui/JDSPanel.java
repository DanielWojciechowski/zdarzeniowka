package zdarzeniowka.gui;
/**
 * 
 * Panel przedstawiający schemat Domu Studenckiego.
 * Przedstawia 
 * @author Anna Cierniewska
 * @author Daniel Wojciechowski
 * 
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import zdarzeniowka.db.DBUser;
import zdarzeniowka.db.DBUtil;

class JDSPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5329098283210850224L;
	private JPanel dsPanel0, dsPanel1, dsPanel2 = new JPanel();
	private GridBagConstraints c;
	private Insets cInsets1, cInsets2, cInsets3, cInsets4;
	private  List<JRoomButton> roomButton;
	private Border border;
	private Font normal;
	private DBUtil dbUtil = new DBUtil();
	private int[] countTab ;
	private Controller cont = new Controller();
	
	JDSPanel(Font font){
		super();
		initiate(font);
	}
	
	private void initiate(Font font){
		List<Object[]> countList = new LinkedList<Object[]>();
		SwingWorker<List<Object[]>, Void> worker = new SwingWorker<List<Object[]>, Void>(){

            @Override
            protected List<Object[]> doInBackground() throws Exception {
            	List<Object[]> list = dbUtil.countUsersForAllRooms();
				return list;
            }
		};
		worker.execute();
		try {
			countList = worker.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		countTab = new int[36];
		for(Object[] counter: countList){
			int n = (int) counter[0];
			if(n > 0 && n <=12)
				countTab[n-1] = (int)(long)counter[1];
			else if(n > 100 && n <=112)
				countTab[n-89] = (int)(long)counter[1];
			else if(n > 200 && n <=212)
				countTab[n-177] = (int)(long)counter[1];
	     }
		int roomIter = 0;
	    roomButton = new ArrayList<JRoomButton>();
		this.normal = font;
		dsPanel0 = new JPanel();
		dsPanel1 = new JPanel();
		dsPanel2 = new JPanel();
		border = BorderFactory.createEmptyBorder();
		dsPanel0.setLayout(new GridBagLayout());
		dsPanel1.setLayout(new GridBagLayout());
		dsPanel2.setLayout(new GridBagLayout());
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		//c.ipadx=60;
		//c.ipady=20;
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
				JRoomButton b = new JRoomButton(String.valueOf(stage+i), normal, countTab[roomIter]);
				roomButton.add(b);
				roomIter++;
				b.addActionListener(this);
				dsPanel0.add(b,c);
				c.gridx++;
			}
			stage+=6; 
			c.gridx=0;
			c.gridy++;
		}
		stage=100;
		for(int j=1;j<=2;j++){
			if (j == 2) {
				c.insets = cInsets2;
			}
			else {
				c.insets = cInsets1;
			}
			for(int i=1;i<=6;i++){
				JRoomButton b = new JRoomButton(String.valueOf(stage+i), normal, countTab[roomIter]);
				roomButton.add(b);
				roomIter++;
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
				JRoomButton b = new JRoomButton(String.valueOf(stage+i), normal, countTab[roomIter]);
				roomButton.add(b);
				roomIter++;
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
	

	void showRoomFrame(String buttonText, List<DBUser> userList){
		JRoomFrame roomFrame = new JRoomFrame(normal, "Pokój nr " + buttonText, userList, this);
		roomFrame.setComponentsBackground(this.getBackground());
		roomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		roomFrame.setLocation(400, 200);
		roomFrame.setResizable(false);
		roomFrame.pack();
		roomFrame.setVisible(true);
	}
	
	void setComponentsBackground(Color bg){
		this.setBackground(bg);
		dsPanel0.setBackground(bg);
		dsPanel1.setBackground(bg);
		dsPanel2.setBackground(bg);
	}

	List<JRoomButton> getRoomButton(){
		return this.roomButton;
	}
	
	int getCountTabAt(int index){
		return countTab[index];
	}
	
	void increaseCountTabAt(int index){
		if(index > 0 && index <=12)
			countTab[index-1]++;
		else if(index > 100 && index <=112)
			countTab[index-89]++;
		else if(index > 200 && index <=212)
			countTab[index-177]++;
     }
	
	void decreaseCountTabAt(int index){
		if(index > 0 && index <=12)
			countTab[index-1]--;
		else if(index > 100 && index <=112)
			countTab[index-89]--;
		else if(index > 200 && index <=212)
			countTab[index-177]--;
     }

	@Override
	public void actionPerformed(ActionEvent e) {
		cont.contDSPanelAL(e, this);
	}
	
}
