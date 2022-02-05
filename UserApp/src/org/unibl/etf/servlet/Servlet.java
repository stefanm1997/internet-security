package org.unibl.etf.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.dao.DAOUser;

import com.google.gson.Gson;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet() {
		super();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		DAOUser.listOfUser().stream().forEach(e -> System.out.println(e));
		HttpSession session = request.getSession();
		// session.setAttribute("listOfUser", DAOUser.listOfUser());
		String username = null, role = null;
		if (session != null) {
			username = (String) session.getAttribute("edu.yale.its.tp.cas.client.filter.user");
			role = DAOUser.getRoleFromUser(username);
			System.out.println(username);
			System.out.println(role);
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(DAOUser.listOfUser()));
			out.close();

		} else {
			System.out.println("Prazna sesija");

		}

		/**/

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
