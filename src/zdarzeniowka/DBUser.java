package zdarzeniowka;

import java.util.Set;

public class DBUser {
	private int idUser;
	private String firstName;
	private String lastName;
	private String email;
	private int roomNo;
	private int albumNo;
	private DBPort port;
	private Set<DBUserDevice> devices;
	
	public DBUser() {}

	public DBUser(String firstName, String lastName, String email, int albumNo, DBPort port, int roomNo) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.albumNo = albumNo;
		this.port = port;
		this.roomNo = roomNo;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public Set<DBUserDevice> getDevices() {
		return devices;
	}

	public void setDevices(Set<DBUserDevice> devices) {
		this.devices = devices;
	}

	public DBPort getPort() {
		return port;
	}
	public void setPort(DBPort port) {
		this.port = port;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAlbumNo() {
		return albumNo;
	}
	public void setAlbumNo(int albumNo) {
		this.albumNo = albumNo;
	}

}
