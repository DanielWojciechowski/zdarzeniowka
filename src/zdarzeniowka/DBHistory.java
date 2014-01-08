package zdarzeniowka;

import java.sql.Date;

public class DBHistory {
	private int idHistory;
	private Date date;
	private double userDataUse;
	
	public DBHistory() {}
	
	public DBHistory(Date date, double userDataUse) {
		this.date = date;
		this.userDataUse = userDataUse;
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
	public double getUserDataUse() {
		return userDataUse;
	}
	public void setUserDataUse(double userDataUse) {
		this.userDataUse = userDataUse;
	}
	
	
	

}
