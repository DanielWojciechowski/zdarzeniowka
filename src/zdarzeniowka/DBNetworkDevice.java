package zdarzeniowka;

public class DBNetworkDevice{
	private int idDevice;
	private String mac;
	private String ip;
	private boolean configuration;
	private char type;
	private String otherInfo;
	private boolean deviceStatus;
	
	public DBNetworkDevice() {}
	public DBNetworkDevice(String mac, String ip, boolean configuration, char type, 
			String otherInfo, boolean deviceStatus) {
		this.mac = mac;
		this.ip = ip;
		this.configuration = configuration;
		this.type = type;
		this.otherInfo = otherInfo;
		this.deviceStatus = deviceStatus;
	}
	public int getIdDevice() {
		return idDevice;
	}
	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isConfiguration() {
		return configuration;
	}
	public void setConfiguration(boolean configuration) {
		this.configuration = configuration;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public boolean isDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(boolean deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
}
