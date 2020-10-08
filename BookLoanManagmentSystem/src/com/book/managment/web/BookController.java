package com.book.managment.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.managment.dao.BookDao;
import com.book.managment.model.Book;
import com.book.managment.util.BookStatus;
import com.google.gson.Gson;

@WebServlet("/book")
public class BookController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(BookController.class.getName());

	private BookDao bookDao;

	private Gson gson;

	public BookController() {
		super();
		bookDao = new BookDao();
		gson = new Gson();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookId = request.getParameter("bookId");
		String bookTitle = request.getParameter("bookTitle");

		List<Book> books = new ArrayList<>();
		if (null != bookId && !bookId.isEmpty()) {

			log.info("Extract book by id");
			books.add(bookDao.findBookById(Integer.parseInt(bookId)));
		} else if (null != bookTitle && !bookTitle.isEmpty()) {

			log.info("Extract book by name");
			books.add(bookDao.findBookByName(bookTitle));
		} else {

			log.info("Extract all books");
			books.addAll(bookDao.findAllBooks());
		}

		if (books.isEmpty()) {

			response.setStatus(404);
		} else {

			String booksJsonString = gson.toJson(books);
			log.info(String.format("Exracted Books: %s", booksJsonString));

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

		String bookIdString = request.getParameter("bookId");
		Integer bookId = null;
		if (bookIdString != null && !bookIdString.isEmpty()) {

			bookId = Integer.parseInt(bookIdString);
		}
		String bookTitle = request.getParameter("bookTitle");
		String authors = request.getParameter("authors");
		String publisher = request.getParameter("publisher");
		int publishedYear = Integer.parseInt(request.getParameter("publishedYear"));
		int totalPagesOfTheBook = Integer.parseInt(request.getParameter("totalPagesOfTheBook"));
		BookStatus bookStatus = BookStatus.valueOf(request.getParameter("bookStatus"));

		final Book book;
		final Book updatedBook;

		if (null != bookId) {

			book = new Book(bookId, bookTitle, authors, publisher, publishedYear, totalPagesOfTheBook, bookStatus);
			log.info(String.format("Book %s is being updated", book.toString()));
			updatedBook = bookDao.updateBook(book);
		} else {

			book = new Book(bookTitle, authors, publisher, publishedYear, totalPagesOfTheBook, bookStatus);
			log.info(String.format("Book %s is being saved", book.toString()));
			updatedBook = bookDao.saveBook(book);
		}

		if (null != updatedBook) {

			log.info(String.format("Successfully updated %s", updatedBook.toString()));
			response.setStatus(200);
		} else {

			log.warning(String.format("Error while updating the %s", book));
			response.setStatus(500);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookId = request.getParameter("deleteBookId");

		if (null != bookId) {

			Book deletedBook = bookDao.deleteBookById(bookId);
			if (null != deletedBook) {

				log.info(String.format("Successfully deleted %s", deletedBook.toString()));
				response.setStatus(200);
				return;
			}
		}
		log.warning(String.format("Error while deleting book with id %s", bookId));
		response.setStatus(500);
	}

}
