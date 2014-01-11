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
        System.exit(0);
	}
	public List<?> findUserOrDevice(String category, String criterium, String value){
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
        	ex.printStackTrace();
        }finally{
        	session.close();
        }
		return list;
	}
	
	public void addUser(){
		
	}
	
	
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}	
}
