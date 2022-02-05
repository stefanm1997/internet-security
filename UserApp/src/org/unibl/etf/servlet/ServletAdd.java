package org.unibl.etf.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.dao.DAOUser;
import org.unibl.etf.dto.DTOUser;

/**
 * Servlet implementation class ServletAdd
 */
@WebServlet("/ServletAdd")
public class ServletAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAdd() {
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
		DTOUser user = null;

		if (request.getParameter("username") != null && request.getParameter("password") != null
				&& request.getParameter("role") != null) {
			user = new DTOUser(0, request.getParameter("username"), DAOUser.sha256(request.getParameter("password")),
					request.getParameter("role"));
			DAOUser.insertUser(user);
		} else {
			System.out.println("Nepravilan unos!!! Neki od parametara nije unesen!");
		}

		HttpSession session = request.getSession();
		session.setAttribute("listOfUser", DAOUser.listOfUser());
		response.sendRedirect("index.jsp");

		// response.getWriter().append("Served at: ").append(request.getContextPath());
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
