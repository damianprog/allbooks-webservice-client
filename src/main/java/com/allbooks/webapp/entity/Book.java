package com.allbooks.webapp.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

	private int id;

	private byte[] bookPhoto;

	private String miniTitle;

	private String fullTitle;

	private String author;

	private String aboutAuthor;

	private byte[] authorPhoto;

	private String reviewAuthor;

	private String description;

	private String bookQuotes;

	private String coverType;

	private int pages;

	@DateTimeFormat(iso=ISO.DATE)
	private Date publishDate;

	private String publishCompany;

	private String buyBook;

	private String category;

	private String encodedBookPhoto;

	public String getEncodedBookPhoto() {
		return encodedBookPhoto;
	}

	public void setEncodedBookPhoto(String encodedBookPhoto) {
		this.encodedBookPhoto = encodedBookPhoto;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMiniTitle() {
		return miniTitle;
	}

	public void setMiniTitle(String miniTitle) {
		this.miniTitle = miniTitle;
	}

	public byte[] getBookPhoto() {
		return bookPhoto;
	}

	public void setBookPhoto(byte[] bookPhoto) {
		this.bookPhoto = bookPhoto;
	}

	public String getAboutAuthor() {
		return aboutAuthor;
	}

	public void setAboutAuthor(String aboutAuthor) {
		this.aboutAuthor = aboutAuthor;
	}

	public byte[] getAuthorPhoto() {
		return authorPhoto;
	}

	public void setAuthorPhoto(byte[] authorPhoto) {
		this.authorPhoto = authorPhoto;
	}

	public String getReviewAuthor() {
		return reviewAuthor;
	}

	public void setReviewAuthor(String reviewAuthor) {
		this.reviewAuthor = reviewAuthor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBookQuotes() {
		return bookQuotes;
	}

	public void setBookQuotes(String bookQuotes) {
		this.bookQuotes = bookQuotes;
	}

	public String getCoverType() {
		return coverType;
	}

	public void setCoverType(String coverType) {
		this.coverType = coverType;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishCompany() {
		return publishCompany;
	}

	public void setPublishCompany(String publishCompany) {
		this.publishCompany = publishCompany;
	}

	public String getBuyBook() {
		return buyBook;
	}

	public void setBuyBook(String buyBook) {
		this.buyBook = buyBook;
	}

	public Book() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getFullTitle() {
		return fullTitle;
	}

	public void setFullTitle(String fullTitle) {
		this.fullTitle = fullTitle;
	}

}
