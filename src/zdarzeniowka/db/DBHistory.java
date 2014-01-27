package zdarzeniowka.db;

import java.sql.Date;
/**
 * Klasa przedstawiająca tabelę History z bazy danych.
 * @author Daniel Wojciechowski
 *
 */
public class DBHistory {
	private int idHistory;
	private Date date;
	private double dataUse;
	
	public DBHistory() {}
	
	public DBHistory(Date date, double userDataUse) {
		this.date = date;
		this.dataUse = userDataUse;
	}

	public int getIdHistory() {
		return idHistory;
	}
	public void setIdHistory(int idHistory) {
		this.idHistory = idHistory;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getDataUse() {
		return dataUse;
	}
	public void setDataUse(double userDataUse) {
		this.dataUse = userDataUse;
	}

}
