package zdarzeniowka;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class hibTest {
    private static Transaction trans = null;
    
	public static void main(String[] args) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try{
        	trans = session.beginTransaction();
        	DBRoom room = new DBRoom();
            session.save(room);
	        //createRoom(session);
	        //createUserDevice(session);
	        trans.commit();
        } catch(Exception ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }
        System.exit(0);
	}
	
	public static void createRoom(Session session) {
        DBRoom room = new DBRoom();
        session.save(room);
    }
	
	public static void createUserDevice(Session session){
		DBUserDevice udv = new DBUserDevice();
		udv.setConfiguration(true);
		udv.setIdUser(1);
		udv.setIp("123");
		udv.setMac("234");
		udv.setOtherInfo("bla bla");
		udv.setType('k');
		udv.setIdDevice(1);
		session.save(udv);
	}
	
	/*public void doTransaction(){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();

        try{
	        createRoom(session);
	        //createUserDevice(session);
	        trans.commit();
        } catch(Exception ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        } finally{
        	session.close();
        }
	}*/
}
