package com.book.managment.dto;

public class BorrowedBookDTO {

	private int bookId;

	private int userId;

	public BorrowedBookDTO(int bookId, int userId) {
		super();
		this.bookId = bookId;
		this.userId = userId;
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
}
