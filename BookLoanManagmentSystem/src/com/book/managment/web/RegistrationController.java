package com.book.managment.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.managment.dao.UserDao;
import com.book.managment.model.User;
import com.google.gson.Gson;

@WebServlet("/register")
public class RegistrationController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(RegistrationController.class.getName());

	private UserDao userDao;

	private Gson gson;

	public RegistrationController() {
		super();
		userDao = new UserDao();
		gson = new Gson();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("Register page has been requested !");
		response.sendRedirect("register.html");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User(username, password, 0, false);

		User savedUser = userDao.saveUser(user);

		if (null != savedUser) {

			log.info(String.format("User %s registered successfully !", savedUser.toString()));
			String userJsonString = gson.toJson(savedUser);

			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(userJsonString);
			out.flush();
		} else {

			log.warning(String.format("Error doring registering user %s", user));
			response.setStatus(400);
		}
	}
}
