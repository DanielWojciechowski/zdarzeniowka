package zdarzeniowka.gui;
/**
 * 
 * Tablica, której elementy nie mogą być edytowalne.
 * @author Anna Cierniewska
 * 
 */
import javax.swing.JTable;

public class JMyTable extends JTable{
	private static final long serialVersionUID = -3136653135458537297L;

	public boolean isCellEditable(int row, int col) {  
		 return false;  
	 }    
}
