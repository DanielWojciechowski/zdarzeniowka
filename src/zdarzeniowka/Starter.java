package zdarzeniowka;

import org.apache.log4j.xml.DOMConfigurator;

public class Starter {
	public static void main (String[] args) {
		LoadingFrame lframe = new LoadingFrame();
		
		DOMConfigurator.configure("xml/log4j.xml");
		new DBUtil(true);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			GUI gui = new GUI();
			@Override
			public void run() {
				gui.showGUI();	
			}
			});

	}
}
