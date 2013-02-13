package com.imstarter.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IMServer
 */
@WebServlet("/IMServer")
public class IMServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IMServer() {
		super();
		//logger.setLevel(Level.ALL);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doQuery(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doQuery(request, response);
	}
	protected void doQuery(HttpServletRequest request, HttpServletResponse response) {

		try {
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			String message = request.getParameter("message");
			//String oper = request.getParameter("oper");
			//logger.debug("FROM:" + from + " to " + to + " : " + message);
			
			if (from != null && from.length() > 0 && to != null && to.length() > 0 && message != null && message.length() > 0) {
				MessageManager.sendMessage(from, to, message);
			}

			String sOut = "";
			if (from != null && from.length() > 0) {
				sOut = MessageManager.readmsg(from);
			}

			//response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sOut);
			out.flush();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
