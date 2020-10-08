package com.book.managment.dao;

import java.util.List;
import java.util.function.BiFunction;

import org.hibernate.Session;

import com.book.managment.model.Book;
import com.book.managment.util.DaoUtil;

public class BookDao {

	public Book saveBook(Book book) {

		return DaoUtil.defaultDbOperation(book, (Session session, Book b) -> {
			session.save(b);
			return b;
		});
	}

	public Book deleteBookById(String bookId) {

		return DaoUtil.defaultDbOperation(bookId, (Session session, String id) -> {

			Book book = session.load(Book.class, Integer.parseInt(id));
			if (null != book) {

				session.delete(book);
			}
			return book;
		});
	}

	public Book updateBook(Book book) {

		return DaoUtil.defaultDbOperation(book, (Session session, Book b) -> {
			session.update(b);
			return b;
		});
	}

	public List<Book> findAllBooks() {

		return DaoUtil.defaultDbOperation("id",
				(Session session, String id) -> session.createQuery("FROM Book b", Book.class).getResultList());
	}

	public Book findBookById(Integer bookId) {

		return DaoUtil.defaultDbOperation(bookId, (Session session, Integer id) -> session.find(Book.class, id));
	}

	public Book findBookByName(String bookTitle) {

		return DaoUtil.defaultDbOperation(bookTitle, selectBookByName());
	}

	private BiFunction<Session, String, Book> selectBookByName() {

		return (Session session, String title) -> (Book) session.createQuery("FROM Book b WHERE b.bookTitle = :title")
				.setParameter("title", title).uniqueResult();
	}

	public List<Book> findBorrowedBooksByUserId(String userId) {

		Integer user = Integer.parseInt(userId);
		return DaoUtil.defaultDbOperation(user,
				(Session session, Integer uId) -> session.createQuery(
						"SELECT b FROM Book b, BorrowedBook bb WHERE b.bookId = bb.bookId AND bb.userId = :userId",
						Book.class).setParameter("userId", uId).getResultList());

	}
}
