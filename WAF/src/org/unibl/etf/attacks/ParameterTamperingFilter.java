package org.unibl.etf.attacks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.unibl.etf.security.WAF;

public class ParameterTamperingFilter {

	private static final String ParameterTamperingReport = System.getProperty("user.home") + File.separator
			+ "izvjestajNapadaParameterTempering.txt";
	private static String configPath;
	HttpServletRequest request;
	private static ArrayList<String> parameters = new ArrayList<>();
	static {

	}

	public ParameterTamperingFilter(HttpServletRequest request) {
		try {
			configPath = request.getServletContext().getRealPath(File.separator) + File.separator + ".."
					+ File.separator + ".." + File.separator + "wtpwebapps" + File.separator + "WAF" + File.separator
					+ "WEB-INF" + File.separator + "config" + File.separator + "configParameterTampering.properties";
			BufferedReader br = new BufferedReader(new FileReader(configPath));
			String s = "";
			while ((s = br.readLine()) != null) {
				parameters.add(s);
			}
			br.close();
			this.request = request;
			checkRequests();
			checkCookies();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		} catch (Exception e) {
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		}
	}

	private void checkRequests() throws Exception {
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			filterContent(paramName, request.getParameter(paramName), false);
		}
	}

	private void checkCookies() {
		Cookie cookiesArray[] = request.getCookies();

		if (cookiesArray != null) {

			Arrays.asList(cookiesArray).stream().forEach(e -> {
				String paramName = e.getName();
				String content;
				try {
					content = URLDecoder.decode(e.getValue(), "UTF-8");
					filterContent(paramName, content, true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					LoggerWrapper.getLogger().log(Level.INFO, e1.toString(), e1);
				}
			});
		}
	}

	private void filterContent(String paramName, String content, boolean isCookie) throws Exception {
		parameters.stream().forEach(e -> {
			String description = "";
			String split[]=e.split(":");
			if (split[0].equals(paramName)) {
				if (split[1].equals("int") || split[1].equals("Integer")) {
					try {
						Integer number = Integer.parseInt(content);
						if ((number < Integer.parseInt(split[2])
								|| number > Integer.parseInt(split[3]))) {
							description = "Opis: Parametar se nalazi izvan opsega [" + split[2] + ","
									+ split[3] + "].";
							if (isCookie)
								writeReport(paramName, description, "Cookie", content);
							else
								writeReport(paramName, description, "GET/POST", content);
						}
					} catch (NumberFormatException ne) {
						description = "Opis: NumberFormatException, ne moze se pretvoriti poslijedjena vrijednost parametra u broj.";
						if (isCookie)
							writeReport(paramName, description, "Cookie", content);
						else
							writeReport(paramName, description, "GET/POST", content);
					}
				} else if (split[1].equals("String") || split[1].equals("string")) {
					if (content.length() < Integer.parseInt(split[2])
							|| content.length() > Integer.parseInt(split[3])) {
						description = "Opis: Parametar se nalazi izvan opsega [" + split[2] + ","
								+ split[3] + "].";
						if (isCookie)
							writeReport(paramName, description, "Cookie", content);
						else
							writeReport(paramName, description, "GET/POST", content);
					}
				}

			}
		});
	}

	private void writeReport(String paramName, String description, String action, String value) {
		try {
			WAF.isAttack = true;
			File file = new File(ParameterTamperingReport);
			PrintWriter pw;
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			pw.println("Napad: ParameterTampering");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			pw.println("Datum i vrijeme: " + dtf.format(now));
			pw.println("Ip adresa napadaca: " + request.getRemoteAddr());
			pw.println("Akcija:" + action);
			pw.println("Parametar->" + paramName);
			pw.println("Vrijednost parametra->" + value);
			pw.println(description);
			pw.println("================================================");
			pw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			LoggerWrapper.getLogger().log(Level.INFO, e1.toString(), e1);
		}
	}

}
