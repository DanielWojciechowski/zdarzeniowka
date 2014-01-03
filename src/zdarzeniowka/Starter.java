package zdarzeniowka;

public class Starter {
	public static void main (String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI gui = new GUI();
				gui.showGUI();	
			}
			});

	}
}
