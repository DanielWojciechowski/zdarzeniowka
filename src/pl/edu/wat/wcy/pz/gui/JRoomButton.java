package pl.edu.wat.wcy.pz.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
/**
 * 
 * Komponent graficzny reprezentujący drzwi do pokoju w schemacie Domu Studenckiego.
 * Umieszczone są na nim ikonki reprezentujące liczbę mieszkańców.
 * @author Anna Cierniewska
 * 
 */
class JRoomButton extends JButton{
	private static final long serialVersionUID = 7475937080439185047L;
	private String txt;
	private int width, height, usersInRoom;
	private Color[] color;
	private static ImageIcon imageIcon = new ImageIcon("icons/door.png");
	private JLabel label;
	private GridBagConstraints c;
	private ImageIcon[] icon;

	JRoomButton(String txt, Font font, int users){ 
		super(imageIcon);
		super.setHorizontalAlignment(SwingConstants.CENTER );
		super.setVerticalAlignment(SwingConstants.CENTER );
		super.setBorder(BorderFactory.createLineBorder(Color.black));
		super.setContentAreaFilled(false);
        super.setOpaque(false);
		initiate(txt, font, users);
	}
	
	private void initiate(String txt, Font font, int value){
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
		icon = new ImageIcon[3];
		icon = setIcons();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.width = getWidth(); 
		this.height = getHeight(); 
		if (usersInRoom > 0) {
			icon[0].paintIcon(this, g, width-15, height-14);
			if (usersInRoom > 1){
				icon[1].paintIcon(this, g, width-15, height-14-17);
				if (usersInRoom > 2){
					icon[2].paintIcon(this, g, width-15, height-13-2*17);
				}
			}

		}
	}
	
	void increaseUsersInRoom(){
		this.usersInRoom++;
	}
	
	void decreaseUsersInRoom(){
		this.usersInRoom--;
	}
	
	int getUsersInRoom(){
		return this.usersInRoom;
	}
	
	ImageIcon[] setIcons(){
		for (int i = 0; i < 3; i++){
			icon[i] = new ImageIcon("icons/available.png");
		}
		return icon;
	}
	
	Color[] setColor(){
		for (int i = 0; i < 3; i++){
			color[i] = new Color(0f,1f,0f, 0.9f);
		}
		return color;
	}
	
	@Override
	public String getText(){
		return txt;
	}
}

