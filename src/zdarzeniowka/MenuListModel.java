package zdarzeniowka;

import javax.swing.DefaultListModel;

public class MenuListModel extends DefaultListModel<String>{
	private static final long serialVersionUID = -3256759296340102196L;

	public MenuListModel(){
		super();
		addElement("Zarzadzanie uzytkownikami.");
		addElement("Zarzadzanie sprzetem uzytkownika.");
		addElement("Zarzadzanie sprzetem sieciowym.");
		addElement("Wyszukaj sprzet sieciowy.");
		addElement("Wyszukaj sprzet uzytkownika.");
		addElement("Wyszukaj uzytkownika.");
		addElement("Wygeneruj raporty.");
	}

}
