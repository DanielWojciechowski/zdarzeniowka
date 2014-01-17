package zdarzeniowka;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.hibernate.metamodel.domain.Superclass;

public class JRoomButton extends JButton{
	private static final long serialVersionUID = 7475937080439185047L;
	private String txt;
	private int width, height, usersInRoom;
	private Color[] color;
	private static ImageIcon imageIcon = new ImageIcon("icons/door.png");
	private JLabel label;
	private GridBagConstraints c;
	private ImageIcon[] icon;
	

	public JRoomButton(String txt, Font font){ 
		super(imageIcon);
		initiate(txt, font, 3);
	}
	
	public JRoomButton(String txt, Font font, int users){ 
		super(imageIcon);
		initiate(txt, font, users);
	}
	
	public void initiate(String txt, Font font, int value){
		super.setMinimumSize(new Dimension(90, 50));
		super.setPreferredSize(new Dimension(90, 50));
		super.setMaximumSize(new Dimension(90, 50));
		super.setRolloverIcon(imageIcon);
		this.setFont(font);
		this.txt = txt;
		
		usersInRoom = value;
		color = new Color[3];
		label = new JLabel(txt);
		label.setFont(font);
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		this.add(label,c);
		icon = new ImageIcon[value];
		icon = setIcons();
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.width = getWidth(); 
		this.height = getHeight(); 
		for (int i = 0; i < this.usersInRoom; i++){
			if (i == 2){
				icon[i].paintIcon(this, g, width-15, height-13-i*17);
			}
			else {
				icon[i].paintIcon(this, g, width-15, height-14-i*17);;
			}		
		}
	}
	
	public int setUsersInRoom(int users){
		return this.usersInRoom = users;
	}
	
	public ImageIcon[] setIcons(){
		for (int i = 0; i < this.usersInRoom; i++){
			icon[i] =new ImageIcon("icons/available.png");
		}
		return icon;
	}
	
	public Color[] setColor(){
		for (int i = 0; i < this.usersInRoom; i++){
			color[i] = new Color(0f,1f,0f, 0.9f);
		}
		return color;
	}
	
	public String getText(){
		return txt;
	}
	
}

