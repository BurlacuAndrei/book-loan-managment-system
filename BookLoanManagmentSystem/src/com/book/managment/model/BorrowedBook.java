package com.book.managment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "borrowed_book")
public class BorrowedBook implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "borrowed_book_id")
	private int borrowedBookId;

	@Column(name = "book_id")
	private int bookId;

	@Column(name = "user_id")
	private int userId;

//	@OneToOne
//	@JoinColumn(name = "book_id")
//	private Book book;
//	
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private User user;

	public BorrowedBook() {
	}

	public BorrowedBook(int borrowedBookId, int bookId, int userId) {
		super();
		this.borrowedBookId = borrowedBookId;
		this.bookId = bookId;
		this.userId = userId;
	}

	public BorrowedBook(int bookId, int userId) {
		super();
		this.bookId = bookId;
		this.userId = userId;
	}

	public int getBorrowedBookId() {
		return borrowedBookId;
	}

	public void setBorrowedBookId(int borrowedBookId) {
		this.borrowedBookId = borrowedBookId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BorrowedBook [borrowedBookId=" + borrowedBookId + ", bookId=" + bookId + ", userId=" + userId + "]";
	}
}
