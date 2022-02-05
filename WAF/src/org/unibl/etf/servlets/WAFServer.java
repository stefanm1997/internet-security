package org.unibl.etf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.unibl.etf.security.WAF;

public class WAFServer extends ValveBase {

	@Override
	public void invoke(Request arg0, Response arg1) throws IOException, ServletException {

		new WAF(arg0);

		if (!WAF.isAttack) {
			getNext().invoke(arg0, arg1);
		} else
			WAF.isAttack = false;

	}

}
