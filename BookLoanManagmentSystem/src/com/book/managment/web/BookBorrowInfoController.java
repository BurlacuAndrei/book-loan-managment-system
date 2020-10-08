package com.book.managment.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.managment.dao.BookDao;
import com.book.managment.model.Book;
import com.google.gson.Gson;

@WebServlet("/borrow_info")
public class BookBorrowInfoController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(BookBorrowInfoController.class.getName());

	private BookDao bookDao;

	private Gson gson;

	public BookBorrowInfoController() {
		super();
		bookDao = new BookDao();
		gson = new Gson();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userId = request.getParameter("userId");

		List<Book> books = bookDao.findBorrowedBooksByUserId(userId);
		if (books.isEmpty()) {

			response.setStatus(404);
		} else {

			String booksJsonString = gson.toJson(books);
			log.info(String.format("Extracted Borrowed Books: %s", booksJsonString));

			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(booksJsonString);
			out.flush();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
