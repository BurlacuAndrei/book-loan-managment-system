package com.book.managment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.book.managment.util.BookStatus;

@Entity
@Table(name = "book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "book_id")
	private int bookId;

	@Column(name = "book_title")
	private String bookTitle;

	@Column(name = "authors")
	private String authors;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "published_year")
	private int publishedYear;

	@Column(name = "total_pages_of_the_book")
	private int totalPagesOfTheBook;

	@Column(name = "book_status")
	@Enumerated(EnumType.STRING)
	private BookStatus bookStatus;

	public Book() {
	}

	public Book(int bookId, String bookTitle, String authors, String publisher, int publishedYear,
			int totalPagesOfTheBook, BookStatus bookStatus) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.authors = authors;
		this.publisher = publisher;
		this.publishedYear = publishedYear;
		this.totalPagesOfTheBook = totalPagesOfTheBook;
		this.bookStatus = bookStatus;
	}

	public Book(String bookTitle, String authors, String publisher, int publishedYear, int totalPagesOfTheBook,
			BookStatus bookStatus) {
		super();
		this.bookTitle = bookTitle;
		this.authors = authors;
		this.publisher = publisher;
		this.publishedYear = publishedYear;
		this.totalPagesOfTheBook = totalPagesOfTheBook;
		this.bookStatus = bookStatus;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

	public int getTotalPagesOfTheBook() {
		return totalPagesOfTheBook;
	}

	public void setTotalPagesOfTheBook(int totalPagesOfTheBook) {
		this.totalPagesOfTheBook = totalPagesOfTheBook;
	}

	public BookStatus getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookTitle=" + bookTitle + ", authors=" + authors + ", publisher="
				+ publisher + ", publishedYear=" + publishedYear + ", totalPagesOfTheBook=" + totalPagesOfTheBook
				+ ", bookStatus=" + bookStatus + "]";
	}

}
