package zdarzeniowka;

public class DBUser {
	private int idUser;
	private int roomNo;
	private int portNo;
	private String firstName;
	private String lastName;
	private String email;
	private int albumNo;
	
	public DBUser() {}
	public DBUser(int roomNo, int portNo, String firstName, String lastName, String email, 
			int albumNo) {
		this.roomNo = roomNo;
		this.portNo = portNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.albumNo = albumNo;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	public int getPortNo() {
		return portNo;
	}
	public void setPortNo(int portNo) {
		this.portNo = portNo;
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
