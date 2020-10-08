package com.book.managment.util;

public enum BookStatus {
	LOAN("loan"), REQUESTED("requested"), SHELF("shelf");

	private final String synonym;

	BookStatus(String status) {

		this.synonym = status.toLowerCase();
	}

	public String getSynonym() {

		return synonym;
	}
}
