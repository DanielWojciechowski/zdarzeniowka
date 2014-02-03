package pl.edu.wat.wcy.pz.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
/**
 * Klasa łącząca się z serwerem ntp oraz wyświetlająca aktualny czas
 * @author Anna Cierniewska
 * @author Daniel Wojciechowski
 *
 */
public class TimeLookup implements Runnable{
	final static private String TIME_SERVER = "de.pool.ntp.org";   
	private NTPUDPClient timeClient;
	private InetAddress inetAddress;
	private TimeInfo timeInfo;
	private Time time;
	private JLabel label;
	
	public TimeLookup(JLabel label) {
		this.label = label;
		timeClient = new NTPUDPClient();
		 try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	void setTime() throws Exception{
       timeInfo = timeClient.getTime(inetAddress);
       time = new Time(timeInfo.getReturnTime());
       System.out.println(time);
   }

	@Override
	public void run() {
		boolean flg = true;
		try {
			setTime();
		} catch (Exception e1) {
			e1.printStackTrace();
			label.setText("Brak połączenia z ntp");
			flg = false;
		}
		while(flg){
		       DateFormat df = new SimpleDateFormat("HH:mm:ss");
		       String labelTime = df.format(time);
		       label.setText(" Czas " + labelTime);
			try {
				Thread.sleep(1000);
				time.setTime(time.getTime() + 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}