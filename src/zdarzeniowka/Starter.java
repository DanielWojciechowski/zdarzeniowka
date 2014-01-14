package zdarzeniowka;

public class Starter {
	public static void main (String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			GUI gui = new GUI();
			@Override
			public void run() {
				gui.showGUI();	
			}
			});
	}
}
