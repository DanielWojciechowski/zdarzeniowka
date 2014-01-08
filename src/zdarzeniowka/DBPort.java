package zdarzeniowka;

public class DBPort {
	private int portNo;
	private int idHistory;
	private int idUser;
	private int idDevice;
	private double dataUse;
	
	public DBPort() {}
	public DBPort(int idHistory, int idUser, int idDevice, double dataUse) {
		this.idHistory = idHistory;
		this.idUser = idUser;
		this.idDevice = idDevice;
		this.dataUse = dataUse;
	}
	public int getPortNo() {
		return portNo;
	}
	public void setPortNo(int portNo) {
		this.portNo = portNo;
	}
	public int getIdHistory() {
		return idHistory;
	}
	public void setIdHistory(int idHistory) {
		this.idHistory = idHistory;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdDevice() {
		return idDevice;
	}
	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}
	public double getDataUse() {
		return dataUse;
	}
	public void setDataUse(double dataUse) {
		this.dataUse = dataUse;
	}
	
	
}
