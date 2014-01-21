package zdarzeniowka;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

public class Generator implements Runnable {
	private final static int TIME = 5;
	private final static int DB_REFRESH_TIME = 60;
	private final static int MAX_BANDWITH = 10*TIME;
	private final static int LIMIT = 1*TIME;
	Object[][] dataUse, du2;
	private JChart chart;
	
	private Logger  log = Logger.getLogger(Generator.class);
	
	/*public static void main (String[] args) {  
		int[] tab = {1,2,3,4,5,6,7,8,9,10};
		Generator g = new Generator(tab, new JChart(0, 0));
		g.run();
	}*/
	
	public Generator(List<Integer> userTable, JChart chart){
		dataUse = new Object[userTable.size()][2];
		du2 = new Object[userTable.size()][2];
		this.chart = chart;
		for(int i = 0; i < userTable.size(); i++){
			dataUse[i][0] = userTable.get(i);
			dataUse[i][1] = 0.0;
			du2[i][0] = userTable.get(i);
			du2[i][1] = 0.0;
		}
	}

	@Override
	public void run() {
		double min = 0.0, max=MAX_BANDWITH, limit=LIMIT;
		int activeUsers, usr = -1, timer = 0;
		Random r = new Random();
		double randomValue;
		LinkedList<Integer> selectedUsers = new LinkedList<Integer>();
		final DBUtil dbUtil = new DBUtil();
		while(true){
			while(timer<DB_REFRESH_TIME){
				activeUsers = r.nextInt(dataUse.length+1);
				log.info("Active "+activeUsers);
				for(int i = 0; i<activeUsers; i++){
					boolean ok = true;
					while(ok){
						usr = r.nextInt(dataUse.length);
						if(!selectedUsers.contains(usr)){
							selectedUsers.add(usr);
							ok = false;
						}
					}
					randomValue = min + (limit - min) * r.nextDouble();
					randomValue = Math.floor((randomValue*100))/100;
					max-=randomValue;
					dataUse[usr][1] = (double)dataUse[usr][1] + randomValue;
					if(limit>max)
						limit = max;
					//log.info(usr + " " +randomValue);
				}
				timer+=TIME;
				log.info("TIME: "+timer+ " Suma: " + (int)Math.round(MAX_BANDWITH-max));
				chart.setY1((int)Math.round(MAX_BANDWITH-max), TIME);
				log.info("=====================");
				max = MAX_BANDWITH;
				limit=LIMIT;
				selectedUsers.removeAll(selectedUsers);
				if(timer == DB_REFRESH_TIME){
					for(int i=0; i<dataUse.length; i++){
			    		du2[i][1]=dataUse[i][1];
						dataUse[i][1] = 0.0;
					}
					new Thread( new Runnable() {
					    @Override
					    public void run() {
					    	dbUtil.updateTransfer(du2);
					    }
					}).start();
					log.info("TIMER: "+timer);
					timer = 0;
					
				}
				try {
					Thread.sleep(TIME*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
