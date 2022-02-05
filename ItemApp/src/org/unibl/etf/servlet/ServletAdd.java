package org.unibl.etf.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.dao.DAOItem;
import org.unibl.etf.dto.DTOItem;

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
		if (request.getParameter("code") != null && request.getParameter("name") != null
				&& request.getParameter("producer") != null && request.getParameter("price") != null
				&& !request.getParameter("price").isEmpty()) {
			DTOItem item = new DTOItem(request.getParameter("code"), request.getParameter("name"),
					request.getParameter("producer"), Double.parseDouble(request.getParameter("price")), 0);
			DAOItem.insertItem(item);

		} else {
			System.out.println("Nepravilan unos!!! Neki od parametara nije unesen!");
		}
		HttpSession session = request.getSession();
		session.setAttribute("listOfArticle", DAOItem.listOfArticle());
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
