package zdarzeniowka;

import org.apache.log4j.xml.DOMConfigurator;

public class Starter {
	public static void main (String[] args) {
		DOMConfigurator.configure("xml/log4j.xml");
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			GUI gui = new GUI();
			@Override
			public void run() {
				gui.showGUI();	
			}
			});
	}
}
