package org.unibl.etf.security;

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import org.unibl.etf.attacks.*;

public class WAF {

	public static boolean isAttack=false;
	
	public WAF(HttpServletRequest request) {
		try {
			new SQLInjectionFilter(request);			
			DoSAttacksFilter.incrementNumberOfConnectionsFromIp(request);
			new ParameterTamperingFilter(request);
			new XSSFilter(request);
		} catch (Exception e) {
			LoggerWrapper.getLogger().log(Level.INFO, e.toString(), e);
		}
	}

}
