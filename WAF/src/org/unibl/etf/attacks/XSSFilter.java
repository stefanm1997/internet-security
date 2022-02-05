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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.unibl.etf.security.WAF;

public class XSSFilter {

	private static final String XSSFilterReport = System.getProperty("user.home") + File.separator
			+ "izvjestajNapadaXSS.txt";
	private static String configPath;
	HttpServletRequest request;
	private static Pattern arrayPattern[];

	public XSSFilter(HttpServletRequest request) {
		try {
			configPath = request.getServletContext().getRealPath(File.separator) + File.separator + ".."
					+ File.separator + ".." + File.separator + "wtpwebapps" + File.separator + "WAF" + File.separator
					+ "WEB-INF" + File.separator + "config" + File.separator + "configXSS.properties";
			Properties prop = new Properties();
			prop.load(new FileInputStream(configPath));
			String keyword = prop.getProperty("keywords");
			String array[] = keyword.split(",");
			arrayPattern = new Pattern[array.length];
			for (int i = 0; i < array.length; i++) {
				arrayPattern[i] = Pattern.compile(array[i], Pattern.DOTALL);
			}
			this.request = request;
			checkRequests();
			checkCookie();
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

	private void checkCookie() throws Exception {
		Cookie cookiesArray[] = request.getCookies();

		if (cookiesArray != null) {

			Arrays.asList(cookiesArray).stream().forEach(e -> {
				String paramName = e.getName();
				String content;
				try {
					content = URLDecoder.decode(e.getValue(), "UTF-8");
					filterContent(paramName, content, true);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					LoggerWrapper.getLogger().log(Level.INFO, e1.toString(), e1);
				}
			});
		}
	}

	private void filterContent(String paramName, String content, boolean isCookie) {
		try {
			Arrays.asList(arrayPattern).stream().forEach(e -> {
				Matcher matcher = e.matcher(content);
				if (matcher.find()) {
					if (isCookie)
						writeReport(paramName, "Cookie", content);
					else
						writeReport(paramName, "GET/POST", content);
				}
			});
		} catch (Exception e) {
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		}

	}

	private void writeReport(String paramName, String action, String value) {
		try {
			WAF.isAttack = true;
			File file = new File(XSSFilterReport);
			PrintWriter pw;
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			pw.println("Napad: XSS");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			pw.println("Datum i vrijeme: " + dtf.format(now));
			pw.println("Ip adresa napadaca: " + request.getRemoteAddr());
			pw.println("Akcija:" + action);
			pw.println("Parametar->" + paramName);
			pw.println("Vrijednost parametra->" + value);
			pw.println("================================================");
			pw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			LoggerWrapper.getLogger().log(Level.INFO, e1.toString(), e1);
		}
	}

}
