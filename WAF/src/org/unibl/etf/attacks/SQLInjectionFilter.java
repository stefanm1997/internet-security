package org.unibl.etf.attacks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.unibl.etf.security.WAF;

public class SQLInjectionFilter {

	private static final String SQLInjectionReport = System.getProperty("user.home") + File.separator
			+ "izvjestajNapadaSQLInjection.txt";
	private String configPath;

	HttpServletRequest request;

	public SQLInjectionFilter(HttpServletRequest request) {
		try {
			this.request = request;
			configPath = request.getServletContext().getRealPath(File.separator) + File.separator + ".."
					+ File.separator + ".." + File.separator + "wtpwebapps" + File.separator + "WAF" + File.separator
					+ "WEB-INF" + File.separator + "config" + File.separator + "configSQLInjection.properties";
			checkRequests();
			checkCookies();

		} catch (Exception e) {
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		}
	}

	private void checkRequests() throws Exception {
		Enumeration<String> parameterName = request.getParameterNames();

		while (parameterName.hasMoreElements()) {
			String paramName = parameterName.nextElement();
			String wordForCheck = request.getParameter(paramName);
			if (isExistKeyWord(wordForCheck.toLowerCase())) {
				writeReport("GET/POST", paramName, wordForCheck);
			}
		}
	}

	private void checkCookies() {
		Cookie cookiesArray[] = request.getCookies();

		if (cookiesArray != null) {
			Arrays.asList(cookiesArray).stream().forEach(e -> {
				String paramName = e.getName();
				String wordForCheck;
				try {
					wordForCheck = URLDecoder.decode(e.getValue(), "UTF-8");
					if (isExistKeyWord(wordForCheck.toLowerCase())) {
						writeReport("Cookie", paramName, wordForCheck);
					}
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					LoggerWrapper.getLogger().log(Level.INFO, e1.toString(), e1);
				}
			});
		}
	}

	private void writeReport(String action, String paramName, String value) {
		try {
			WAF.isAttack = true;
			File file = new File(SQLInjectionReport);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			pw.println("Napad: SQLInjection");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			pw.println("Datum i vrijeme: " + dtf.format(now));
			pw.println("Ip adresa napadaca: " + request.getRemoteAddr());
			pw.println("Akcija: " + action);
			pw.println("Parametar->" + paramName);
			pw.println("Vrijednost parametra->" + value);
			pw.println("================================================");
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		}
	}

	private boolean isExistKeyWord(String element) {

		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(configPath));
			String[] keyWords = prop.getProperty("keyword").split(",");
			for (String key : keyWords) {
				if (element.contains(key)) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		}
		return false;
	}

}
