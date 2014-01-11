package zdarzeniowka;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RoomJFrame extends JFrame {
	private static final long serialVersionUID = -8038078091109185534L;
	private JTabbedPane tabbedPane;
	private JPanel[] pane;
	private Font normal;
	
	public RoomJFrame(String text, int usersInRoom){
		super(text);
		normal = new Font("Open sans", Font.PLAIN, 13);
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(normal);
		pane = new JPanel[usersInRoom];
		for (int i = 0; i < usersInRoom; i++){
			pane[i] = new JPanel();
			tabbedPane.add("Osoba",pane[i]);	
		}
		this.add(tabbedPane);
	}
	
	public RoomJFrame(String text){
		super(text);
		normal = new Font("Open sans", Font.PLAIN, 13);
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(normal);
		pane = new JPanel[3];
		for (int i = 0; i < 3; i++){
			pane[i] = new JPanel();
			tabbedPane.add("Osoba",pane[i]);	
		}
		this.add(tabbedPane);
	}
	
	

}
