package com.book.managment.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.managment.dao.BookDao;
import com.book.managment.dao.BorrowBookDao;
import com.book.managment.dao.UserDao;
import com.book.managment.dto.BorrowedBookDTO;
import com.book.managment.model.Book;
import com.book.managment.model.BorrowedBook;
import com.book.managment.model.User;
import com.book.managment.util.BookStatus;

/**
 * Servlet implementation class BookBorrowController
 */
@WebServlet("/borrow")
public class BookBorrowController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(BookBorrowController.class.getName());

	private BorrowBookDao borrowBookDao;

	private UserDao userDao;

	private BookDao bookDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookBorrowController() {
		super();
		borrowBookDao = new BorrowBookDao();
		userDao = new UserDao();
		bookDao = new BookDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int userId = Integer.parseInt(request.getParameter("userId"));

		User user = userDao.findUserById(userId);
		Book book = bookDao.findBookById(bookId);

		if (null != user && null != book) {

			book.setBookStatus(BookStatus.LOAN);
			bookDao.updateBook(book);
			int booksBorrowed = user.getBooksBorrowed();
			if (booksBorrowed < 6) {

				user.setBooksBorrowed(booksBorrowed + 1);
			}
			userDao.updateUser(user);
			BorrowedBook bBook = new BorrowedBook(bookId, userId);
			borrowBookDao.saveBorrowedBook(bBook);
			response.setStatus(200);
			log.info(String.format("The book %s was borrowed !", book.toString()));
		} else {

			log.warning(String.format("The book %s was not borrowed !", bookId));
			response.setStatus(500);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int userId = Integer.parseInt(request.getParameter("userId"));

		User user = userDao.findUserById(userId);
		Book book = bookDao.findBookById(bookId);

		BorrowedBookDTO borrowedBookDTO = new BorrowedBookDTO(bookId, userId);

		if (null != user && null != book) {

			book.setBookStatus(BookStatus.SHELF);
			bookDao.updateBook(book);
			int booksBorrowed = user.getBooksBorrowed();
			if (booksBorrowed > 0) {

				user.setBooksBorrowed(booksBorrowed - 1);
			}
			userDao.updateUser(user);
			BorrowedBook deletedBook = borrowBookDao.deleteBookById(borrowedBookDTO);

			if (null != deletedBook) {

				log.info(String.format("Successfully deleted %s", deletedBook.toString()));
				response.setStatus(200);
			} else {

				log.warning(String.format("Error while deleting borrowed book with id %s", bookId));
				response.setStatus(500);
			}
		} else {

			log.warning(String.format("The book %s was not returned !", bookId));
			response.setStatus(500);
		}
	}

}
