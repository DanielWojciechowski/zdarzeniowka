package pl.edu.wat.wcy.pz;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.xml.DOMConfigurator;

import pl.edu.wat.wcy.pz.db.DBUtil;
import pl.edu.wat.wcy.pz.gui.GUI;
import pl.edu.wat.wcy.pz.gui.LoadingFrame;
/**
 * 
 * Klasa startująca aplikacje: otwiera LoadingFrame w czasie ładowania bazy
 * danych, a następnie otwiera główną część programu.
 * @author Anna Cierniewska
 * @author Daniel Wojciechowski
 *
 */
public class Starter {
	public static void main (String[] args) {    
		try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		
		LoadingFrame lframe = new LoadingFrame();
		DOMConfigurator.configure("xml/log4j.xml");
		new DBUtil(true);
		lframe.setCond();
		lframe.dispose();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			GUI gui = new GUI();
			@Override
			public void run() {
				gui.showGUI();	
			}
		});
		
		
	}
}
