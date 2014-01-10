package zdarzeniowka;

public class DBRoom {
	private int idRoom;
	private int roomNo;
	
	public DBRoom() {}
	public DBRoom(int roomNo) {
		this.roomNo = roomNo;
	}
	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	

}
