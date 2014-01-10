package zdarzeniowka;

import java.util.Set;


public class DBPort {
	private int idPort;
	private int portNo;
	private double dataUse;
	private Set<DBHistory> histories;
	
	public DBPort() {}
	
	public DBPort(int portNo) {
		this.portNo = portNo;
	}

	public int getIdPort() {
		return idPort;
	}

	public void setIdPort(int idPort) {
		this.idPort = idPort;
	}

	public int getPortNo() {
		return portNo;
	}
	public void setPortNo(int portNo) {
		this.portNo = portNo;
	}
	public double getDataUse() {
		return dataUse;
	}
	public void setDataUse(double dataUse) {
		this.dataUse = dataUse;
	}
	public Set<DBHistory> getHistories() {
		return histories;
	}
	public void setHistories(Set<DBHistory> histories) {
		this.histories = histories;
	}
	
	
}
