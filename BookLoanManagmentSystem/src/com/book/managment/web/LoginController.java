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
import com.book.managment.dto.UserDTO;
import com.book.managment.model.User;
import com.google.gson.Gson;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(LoginController.class.getName());

	private UserDao userDao;

	private Gson gson;

	public LoginController() {
		super();
		userDao = new UserDao();
		gson = new Gson();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("Login page has been requested !");
		response.sendRedirect("login.html");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserDTO userDTO = new UserDTO(username, password);

		if (Boolean.TRUE.equals(userDao.validate(userDTO))) {

			log.info("Login success");
			User user = userDao.findUserByUsername(username);
			String userJsonString = gson.toJson(user);

			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(userJsonString);
			out.flush();
		} else {

			log.warning("Login error !");
			response.setStatus(404);
		}
	}

}
