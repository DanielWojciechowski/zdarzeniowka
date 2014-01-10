package zdarzeniowka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class DBUtil {
    private static SessionFactory factory = null;
    
	public static void main(String[] args) {
		factory = SessionFactoryUtil.getSessionFactory();
		DBUtil ht = new DBUtil();
		ht.getUsersFromRoom(1);
		//System.out.println(ht.getUsersFromRoom(1));
        System.exit(0);
	}

	
	public DBRoom rooms(){
		Session session = factory.openSession();
		Transaction trans = null;
		DBRoom room = null;
		try{
        	trans = session.beginTransaction();
        	int stage=0;
        	for(int j=1;j<=3;j++){
        		for(int i=1;i<=12;i++){
        			room = new DBRoom(stage+i);
                	session.save(room);
        		}
        		stage+=100;
        	}
        	
        	trans.commit();
        }catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return room;
	}
	
	public List<DBUser> getUsersFromRoom(int no){
		Session session = factory.openSession();
		Transaction trans = null;
		List <DBUser> list = null;
		try{
        	trans = session.beginTransaction();
			Criteria crit1 = session.createCriteria(DBRoom.class);
			crit1.add(Restrictions.eq("roomNo", no));
			DBRoom room = (DBRoom) crit1.uniqueResult();
			System.out.println(room.getIdRoom()+ " "+ room.getRoomNo());
        	Criteria crit = session.createCriteria(DBUser.class);
        	crit.add(Restrictions.eq("room.idRoom", room.getIdRoom()));
        	list = castList(DBUser.class, crit.list());
        	trans.commit();
        }catch(HibernateException ex){
        	if(trans != null) trans.rollback();
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}
	
}
