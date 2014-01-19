package zdarzeniowka;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.apache.log4j.Logger;

public class Generator implements Runnable {
	Object[][] dataUse;
	private final static int TIME = 5;
	private final static int MAX_BANDWITH = (int) (10.0*TIME);
	private final static int LIMIT = 3;
	
	private Logger  log = Logger.getLogger(Generator.class);
	
	public static void main (String[] args) {  
		int[] tab = {1,2,3,4,5,6,7,8,9,10};
		Generator g = new Generator(tab);
		g.run();
	}
	
	public Generator(int[] userTable){
		dataUse = new Object[userTable.length][2];
		for(int i = 0; i < userTable.length; i++){
			dataUse[i][0] = userTable[i];
			dataUse[i][1] = 0.0;
		}
	}

	public Generator() {
	}

	@Override
	public void run() {
		double min = 0.0, max=MAX_BANDWITH, limit=LIMIT;
		int activeUsers, usr = -1;
		Random r = new Random();
		double randomValue;
		LinkedList<Integer> selectedUsers = new LinkedList<Integer>();
		while(true){
			activeUsers = r.nextInt(dataUse.length+1);
			log.info(activeUsers);
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
				if(limit>max)
					limit = max;
				log.info(usr + " " +randomValue);
			}
			log.info("Suma: " + (MAX_BANDWITH-max));
			log.info("=====================");
			max = MAX_BANDWITH;
			limit=LIMIT;
			selectedUsers.removeAll(selectedUsers);
			try {
				Thread.sleep(TIME*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
