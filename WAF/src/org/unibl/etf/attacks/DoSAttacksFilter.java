package org.unibl.etf.attacks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.unibl.etf.security.WAF;

public class DoSAttacksFilter {

	public static HashMap<String, Integer> ipAddresAndNumberOfConnections = new HashMap<>();
	private static String configPath;
	private static final String SlowHTTPReport = System.getProperty("user.home") + File.separator
			+ "izvjestajNapadaDoS.txt";
	public static int maxTime;
	public String ipOfUser;
	public static int maxNumberOfConnections;

	public DoSAttacksFilter(HttpServletRequest request) {
		configPath = request.getServletContext().getRealPath(File.separator) + File.separator + ".." + File.separator
				+ ".." + File.separator + "wtpwebapps" + File.separator + "WAF" + File.separator + "WEB-INF"
				+ File.separator + "config" + File.separator + "configDoSAttacks.properties";
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(configPath));
			maxTime = Integer.parseInt(prop.getProperty("Interval"));
			maxNumberOfConnections = Integer.parseInt(prop.getProperty("MaxNumberOfConnection"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		}

		ipOfUser = request.getRemoteAddr();
		if (!isExist(ipOfUser)) {
			setInterval();
			ipAddresAndNumberOfConnections.put(ipOfUser, 1);
		}

	}

	public static boolean isExist(String userIp) {
		if (ipAddresAndNumberOfConnections.containsKey(userIp))
			return true;
		return false;
	}

	public static void incrementNumberOfConnectionsFromIp(HttpServletRequest request) {

		if (isExist(request.getRemoteAddr())) {
			String address = ipAddresAndNumberOfConnections.keySet().stream()
					.filter(e -> e.equals(request.getRemoteAddr())).findFirst().get();
			Integer currentNumber = ipAddresAndNumberOfConnections.get(address);
			ipAddresAndNumberOfConnections.replace(address, currentNumber, currentNumber + 1);
			if (currentNumber >= maxNumberOfConnections) {

				File file = new File(SlowHTTPReport);
				PrintWriter pw;
				try {
					WAF.isAttack = true;
					pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
					pw.println("Napad: DosAttack(SlowHTTP)");
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					pw.println("Datum i vrijeme: " + dtf.format(now));
					pw.println("Ip adresa napadaca: " + request.getRemoteAddr());
					pw.println("Broj konekcija : " + currentNumber + ", u vremenskom intevalu od :" + maxTime
							+ " sekundi, limit konekcija: " + maxNumberOfConnections + ".");
					pw.println("================================================");
					pw.close();
					ipAddresAndNumberOfConnections.remove(request.getRemoteAddr());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					LoggerWrapper.getLogger().log(Level.INFO, e1.toString(), e1);
				}
			}
		} else
			new DoSAttacksFilter(request);
	}

	private void setInterval() {
		new Thread() {
			public void run() {
				try {
					int time = 0;
					while ((time < maxTime) && isExist(ipOfUser)) {
						time++;
						Thread.sleep(1000);
					}
					if (isExist(ipOfUser)) {
						ipAddresAndNumberOfConnections.remove(ipOfUser);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
				}
			}
		}.start();

	}

}
