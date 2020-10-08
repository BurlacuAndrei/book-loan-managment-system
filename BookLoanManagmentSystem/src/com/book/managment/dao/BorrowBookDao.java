package com.book.managment.dao;

import org.hibernate.Session;

import com.book.managment.dto.BorrowedBookDTO;
import com.book.managment.model.BorrowedBook;
import com.book.managment.util.DaoUtil;

public class BorrowBookDao {

	public BorrowedBook saveBorrowedBook(BorrowedBook borrowedBook) {

		return DaoUtil.defaultDbOperation(borrowedBook, (Session session, BorrowedBook b) -> {
			session.save(b);
			return b;
		});
	}

	public BorrowedBook deleteBook(BorrowedBook borrowedBook) {

		return DaoUtil.defaultDbOperation(borrowedBook, (Session session, BorrowedBook b) -> {
			session.delete(b);
			return b;
		});
	}

	public BorrowedBook deleteBookById(BorrowedBookDTO borrowedBook) {

		return DaoUtil.defaultDbOperation(borrowedBook, (Session session, BorrowedBookDTO borrowedBookDTO) -> {

			BorrowedBook book = (BorrowedBook) session
					.createQuery("FROM BorrowedBook b WHERE b.bookId = :bookId AND b.userId = :userId")
					.setParameter("bookId", borrowedBookDTO.getBookId())
					.setParameter("userId", borrowedBookDTO.getUserId()).uniqueResult();
			if (null != book) {

				session.delete(book);
			}
			return book;
		});
	}
}
