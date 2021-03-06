package pl.edu.wat.wcy.pz.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Klasa obsługi bazy danych
 * @author Daniel Wojciechowski
 * @author Anna Cierniewska
 *
 */
public class DBUtil {
    private static SessionFactory factory = null;
    private static Logger log;
    
    public static void main(String[] args){
    	/*DBUtil db = new DBUtil(true);
    	List<Object[]> list = db.report1("2014-01-18", "2014-01-22");
    	log.info("elementów "+list.size());
    	for(Object[] counter: list){
    		log.info(counter[0]+" "+ counter[1]);
    	}*/
    	/*Date d = db.getLatestDate();
    	log.info(d);*/
    	
    	
    }
    
    public DBUtil(){
    	log = Logger.getLogger(DBUtil.class);    	
    }
    public DBUtil(boolean startFactory){
    	factory = SessionFactoryUtil.getSessionFactory();
    	log = Logger.getLogger(DBUtil.class);
    	log.info("Połączono z bazą danych");

    }
	/**
	 * Funkcja wyszukuje użytkownika lub urządzenie sieciowe
	 * @param category kategoria do jakiej należy obiekt do wyszukania - DBUser, DBUserDevice, DBNetworkDevice
	 * @param criterium kryterium po jakim wyszukiwany będzie obiekt 
	 * dla DBUser: firstName, lastName, roomNo, albumNo; 
	 * dla DBUserDevice i DBNetworkDevice: mac, ip
	 * @param value wartość wpisana przez użytkownika w pole wyszukiwania
	 * @return funkcja zwraca listę typu DBUser, DBUserDevice, DBNetworkDevice, zawierającą wyniki wyszukiwania
	 */
	public List<?> findUserOrDevice(String category, String criterium, String value){
		log.info("Wykonywanie findUserOrDevice()");
		Session session = factory.openSession();
		Transaction trans = null;
		List <?> list = null;
		Criteria crit = null;
		try{
        	trans = session.beginTransaction();
        	
        	if(category == "DBUserDevice")
        		crit = session.createCriteria(DBUserDevice.class);
        	else if(category == "DBNetworkDevice")
        		crit = session.createCriteria(DBNetworkDevice.class);
        	else if(category == "DBUser")
        		crit = session.createCriteria(DBUser.class);
        	
        	if(criterium == "albumNo" || criterium == "roomNo")
        		crit.add(Restrictions.eq(criterium, Integer.parseInt(value)));
        	else
        		crit.add(Restrictions.eq(criterium, value));
        	
        	if(category == "DBUserDevice")
        		list = castList(DBUserDevice.class, crit.list());
        	else if(category == "DBNetworkDevice")
        		list = castList(DBNetworkDevice.class, crit.list());
        	else if(category == "DBUser")
        		list = castList(DBUser.class, crit.list());
        	trans.commit();
        }catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	log.error("Błąd w wykonywaniu findUserOrDevice()");
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		log.info("koniec wykonywania findUserOrDevice()");
		return list;
	}
	/**
	 * Funkcja usuwa wskazany element z bazy danych
	 * @param category kategoria do jakiej należy obiekt do usunięcia - DBUser, DBUserDevice, DBNetworkDevice
	 * @param idObject identyfikator obiektu do usunięcia
	 * @return funkcja zwraca true jeżli operacja powiedzie się, false w przeciwnym wypadku
	 */
	public boolean removeUserOrDevice(String category, int idObject){
		Session session = factory.openSession();
		Transaction trans = null;
		try{
        	trans = session.beginTransaction();
        	Object objctToRemove = null;
        	if(category == "DBUserDevice")
        		objctToRemove = session.get(DBUserDevice.class, idObject);
        	else if(category == "DBNetworkDevice")
        		objctToRemove = session.get(DBNetworkDevice.class, idObject);
        	else if(category == "DBUser")
        		objctToRemove = session.get(DBUser.class, idObject);
        	session.delete(objctToRemove);
        	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
    		return false;
        }finally{
        	session.close();
        }
		return true;
	}
	/**
	 * funkcja dodająca użytkownika do bazy danych
	 * @param firstName wartość pola imię
	 * @param lastName wartość pola nazwisko
	 * @param email wartość pola email
	 * @param roomNo wartość pola numer pokoju
	 * @param albumNo wartość pola numer albumu
	 * @param portNo wartość pola numer portu
	 * @return funkcja zwraca identyfikator utworzonego użytkownika, jeśli operacja nie powiedzie się zwraca NULL
	 */
	public Integer addUser(String firstName, String lastName, String email, int roomNo, int albumNo, int portNo){
		Session session = factory.openSession();
		Transaction trans = null;
		Integer userId = null;
		try{
        	trans = session.beginTransaction();
        	Criteria crit = session.createCriteria(DBPort.class);
        	crit.add(Restrictions.eq("portNo", portNo));
        	DBPort port = (DBPort) crit.uniqueResult();
        	DBUser user = new DBUser(firstName, lastName, email, albumNo, roomNo, port);
        	userId = (Integer) session.save(user);
        	
        	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return userId;
	}
	/**
	 * funkcja umożliwiąjąca edytowanie danych istniejącego użytkownika
	 * @param firstName wartość pola imię
	 * @param lastName wartość pola nazwisko
	 * @param email wartość pola email
	 * @param roomNo wartość pola numer pokoju
	 * @param albumNo wartość pola numer albumu
	 * @param portNo wartość pola numer portu
	 * @param idUser identyfikator edytowanego użytkownika
	 * @return funkcja zwraca true jeśli operacja powiedzie się, false w przeciwnym wypadku
	 */
	public boolean updateUser(String firstName, String lastName, String email, int roomNo, int albumNo, int portNo, 
			int idUser){
		Session session = factory.openSession();
		Transaction trans = null;
		try{
        	trans = session.beginTransaction();
        	DBUser user = (DBUser) session.byId(DBUser.class).getReference(idUser);
        	Criteria crit = session.createCriteria(DBPort.class);
        	crit.add(Restrictions.eq("portNo", portNo));
        	DBPort port = (DBPort) crit.uniqueResult();
        	user.setFirstName(firstName);
        	user.setLastName(lastName);
        	user.setEmail(email);
        	user.setRoomNo(roomNo);
        	user.setAlbumNo(albumNo);
        	user.setPort(port);
        	session.update(user);
        	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        	return false;
        }finally{
        	session.close();
        }
		return true;
	}	
	/**
	 * funkcja dodająca urządzenie użytkownika do bazy danych
	 * @param mac wartość pola mac
	 * @param ip wartość pola ip
	 * @param type wartość pola typ
	 * @param configuration wartość pola konfiguracja
	 * @param otherInfo wartość pola uwagi
	 * @param idUser wartość pola id użytkownika
	 * @return funkcja zwraca identyfikator utworzonego urządzenia użytkownika, jeśli operacja nie powiedzie się zwraca NULL
	 */
	public Integer addUserDevice(String mac, String ip, char type, boolean configuration, 
			String otherInfo, int idUser){
		Session session = factory.openSession();
		Transaction trans = null;
		Integer deviceId = null;
		try{
        	trans = session.beginTransaction();
        	DBUser user = (DBUser) session.byId(DBUser.class).getReference(idUser);
        	DBUserDevice device = new DBUserDevice(mac, ip, configuration, type, otherInfo);
        	user.getDevices().add(device);
        	session.saveOrUpdate(user);
        	trans.commit();
        	deviceId = device.getIdDevice();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return deviceId;
	}
	/**
	 * funkcja umożliwiająca edytowanie danych istniejącego urządzenia użytkownika
	 * @param mac wartość pola mac
	 * @param ip wartość pola ip
	 * @param type wartość pola typ
	 * @param configuration wartość pola konfiguracja
	 * @param otherInfo wartość pola uwagi
	 * @param idUser wartość pola id użytkownika
	 * @param idDevice identyfikator edytowanego urządzenia
	 * @return funkcja zwraca true jeśli operacja powiedzie się, false w przeciwnym wypadku
	 */
	public boolean updateUserDevice(String mac, String ip, char type, boolean configuration, String otherInfo, 
			int idUser, int idDevice){
		Session session = factory.openSession();
		Transaction trans = null;
		try{
        	trans = session.beginTransaction();
        	DBUserDevice device = (DBUserDevice) session.byId(DBUserDevice.class).getReference(idDevice);
        	device.setMac(mac);
        	device.setIp(ip);
        	device.setType(type);
        	device.setConfiguration(configuration);
        	device.setOtherInfo(otherInfo);
        	session.update(device);
        	String qs = "Update userdevice set idUser = :idUser where idDevice = :idDevice";
        	SQLQuery q = session.createSQLQuery(qs);
        	q.setParameter("idDevice", idDevice);
        	q.setParameter("idUser", idUser);
        	q.executeUpdate();
        	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        	return false;
        }finally{
        	session.close();
        }
		return true;
	}
	/**
	 * funkcja dodająca urządzenie sieciowe do bazy danych
	 * @param mac wartość pola mac
	 * @param ip warto pola ip
	 * @param type wartość pola typ
	 * @param configuration wartość pola konfiguracja
	 * @param otherInfo wartość pola uwagi
	 * @return funkcja zwraca identyfikator utworzonego urządzenia sieciowego, jeśli operacja nie powiedzie się zwraca NULL
	 */
	public Integer addNetworkDevice(String mac, String ip, char type, boolean configuration, String otherInfo){
		Session session = factory.openSession();
		Transaction trans = null;
		Integer deviceId = null;
		try{
        	trans = session.beginTransaction();
        	DBNetworkDevice device = new DBNetworkDevice(mac, ip, configuration, type, otherInfo);
        	session.save(device);
        	trans.commit();
        	deviceId = device.getIdDevice();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return deviceId;
	}
	/**
	 * funkcja umożliwiająca edytowanie danych istniejącego urządzenia sieciowego
	 * @param mac wartość pola mac
	 * @param ip wartość pola ip
	 * @param type wartość pola typ
	 * @param configuration wartość pola konfiguracja
	 * @param otherInfo wartość pola uwagi
	 * @param idDevice identyfikator edytowanego urządzenia
	 * @return funkcja zwraca true jeśli operacja powiedzie się, false w przeciwnym wypadku
	 */
	public boolean updateNetworkDevice(String mac, String ip, char type, boolean configuration, String otherInfo, 
			int idDevice){
		Session session = factory.openSession();
		Transaction trans = null;
		try{
        	trans = session.beginTransaction();
        	DBNetworkDevice device = (DBNetworkDevice) session.byId(DBNetworkDevice.class).getReference(idDevice);
        	device.setMac(mac);
        	device.setIp(ip);
        	device.setType(type);
        	device.setConfiguration(configuration);
        	device.setOtherInfo(otherInfo);
        	session.update(device);
        	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        	return false;
        }finally{
        	session.close();
        }
		return true;
	}
	/**
	 * funkcja umożliwiająca znalezienie użytkownika danego urządzenia
	 * @param idDevice identyfikator urządzenia którego użytkownika chcemy znaleźć
	 * @return funkcja zwraca identyfikator użytkownika będącego właścicielem podanego urządzenia
	 */
	public int getDeviceUser(int idDevice){
		Session session = factory.openSession();
		Transaction trans = null;
		int idUser = -1;
		try{
        	trans = session.beginTransaction();
	    	String qs = "Select idUser From userdevice where idDevice = :idDevice";
	    	SQLQuery q = session.createSQLQuery(qs);
	    	q.setParameter("idDevice", idDevice);
	    	List<Integer> list = castList(Integer.class, q.list());
	    	idUser = list.get(0);
	    	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return idUser;
	}
	/**
	 * Funkcja zwracająca liczbę osób w pokoju
	 * @param roomNo numer pokoju z którego zliczamy użytkowników
	 * @return liczba użytkownikó w pokoju
	 */
	public int countUsersInRoom(int roomNo){
		Session session = factory.openSession();
		Transaction trans = null;
		int count = 0;
		try{
        	trans = session.beginTransaction();
        	String hql = "SELECT count(idUser) FROM DBUser WHERE roomNo=:roomNo";
        	Query query = session.createQuery(hql);
        	query.setParameter("roomNo", roomNo);
        	List<Long> list = castList(Long.class, query.list());
	    	count = (int)(long)list.get(0);
	    	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return count;
	}
	
	public List<Object[]> countUsersForAllRooms(){
		Session session = factory.openSession();
		Transaction trans = null;
		List<Object[]> resultList = null;
		try{
        	trans = session.beginTransaction();
        	Query q = session.createQuery("select roomNo, count(idUser) from DBUser group by roomNo");
        	resultList = castList(Object[].class, q.list());
        	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return resultList;
	}
	
	public List<Integer> getAllUsersIds(){
		Session session = factory.openSession();
		Transaction trans = null;
		List<Integer> resultList = null;
		try{
        	trans = session.beginTransaction();
        	Query q = session.createQuery("select idUser from DBUser");
        	resultList = castList(Integer.class, q.list());
        	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return resultList;
	}
	
	public void updateTransfer(Object[][] dataUse){
		Session session = factory.openSession();
		Transaction trans = null;
		try{
        	DBUser user;
        	for(int i=0; i<dataUse.length; i++){
        		trans = session.beginTransaction();
        		int j = (int) dataUse[i][0];
        		user = (DBUser) session.byId(DBUser.class).getReference(j);
        		log.info("du "+dataUse[i][1]);
        		user.getPort().setDataUse((double)(user.getPort().getDataUse()+(double)dataUse[i][1]));
        		session.update(user);
            	trans.commit();
        	}
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	log.error("Błąd uaktualniania transferu!");
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		log.info("Zaaktualizowano transfer");
	}	
	
	/**
	 * funkcja zwracająca najwczesniejsza date w tabeli History
	 * @return najwczesniejsza data
	 */
	public Date getEarliestDate(){
		Session session = factory.openSession();
		Transaction trans = null;
		Date earliestDate = null;
		try{
        	trans = session.beginTransaction();
	    	String qs = "Select date From history order by date asc LIMIT 1";
	    	SQLQuery q = session.createSQLQuery(qs);
	    	earliestDate = (Date) q.uniqueResult();
	    	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		
		return earliestDate;
	}
	
	/**
	 * funkcja zwracająca najpozniejsza date w tabeli History
	 * @return najpozniejsza data
	 */
	public Date getLatestDate(){
		Session session = factory.openSession();
		Transaction trans = null;
		Date latestDate = null;
		try{
        	trans = session.beginTransaction();
	    	String qs = "Select date From history order by date desc LIMIT 1";
	    	SQLQuery q = session.createSQLQuery(qs);
	    	latestDate = (Date) q.uniqueResult();
	    	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return latestDate;
	}
	
	public List<Object[]> report1(String date1, String date2){
		Session session = factory.openSession();
		Transaction trans = null;
		List<Object[]> resultList = null;
		try{
        	trans = session.beginTransaction();
	    	String qs = "select CAST(history.date AS DATE), sum(dataUse) from history "
	    			+ "where history.date between :date1 and :date2 "
	    			+ "group by CAST(history.date AS DATE) ";
	    	SQLQuery q = session.createSQLQuery(qs);
	    	q.setParameter("date1", date1);
	    	q.setParameter("date2", date2);
	    	resultList = castList(Object[].class, q.list());
	    	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		log.info("Report1 wykonano "+resultList.size());
		return resultList;
	}
	
	public List<Object[]> report2(String date1, String date2){
		Session session = factory.openSession();
		Transaction trans = null;
		List<Object[]> resultList = null;
		try{
        	trans = session.beginTransaction();
	    	String qs = "select user.idUser, sum(dataUse) from history "
	    			+ "left join user on history.idPort=user.idPort "
	    			+ "where (history.date between :date1 and :date2) and user.idUser IS  NOT NULL "
	    			+ "group by history.idPort order by user.idUser";
	    	SQLQuery q = session.createSQLQuery(qs);
	    	q.setParameter("date1", date1);
	    	q.setParameter("date2", date2);
	    	resultList = castList(Object[].class, q.list());
	    	trans.commit();
		}catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		log.info("Report2 wykonano "+resultList.size());
		return resultList;
	}
	
	/**
	 * funkcja rzutująca listę dowolnego typu na listę zadanego typu
	 * @param clazz typ na jaki ma być rzutowana lista
	 * @param c lista dowolnego typu
	 * @return funkcja zwraca listę ze zrzutowanymi elementami
	 */
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}
}
