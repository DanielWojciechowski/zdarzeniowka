package zdarzeniowka.db;

import java.util.Set;

public class DBNetworkDevice{
	private int idDevice;
	private String mac;
	private String ip;
	private boolean configuration;
	private char type;
	private String otherInfo;
	private Set<DBPort> ports;
	
	public DBNetworkDevice() {}
	public DBNetworkDevice(String mac, String ip, boolean configuration, char type, String otherInfo) {
		this.mac = mac;
		this.ip = ip;
		this.configuration = configuration;
		this.type = type;
		this.otherInfo = otherInfo;
	}

	public Set<DBPort> getPorts() {
		return ports;
	}
	public void setPorts(Set<DBPort> ports) {
		this.ports = ports;
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
}
