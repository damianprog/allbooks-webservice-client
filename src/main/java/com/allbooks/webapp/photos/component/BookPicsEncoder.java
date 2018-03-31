package com.allbooks.webapp.photos.component;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;

@Component
public class BookPicsEncoder {

	@Autowired
	Base64Encoder base64Encoder;
	
	@Autowired
	ResizePhoto resizePhoto;
	
	public List<Book> encode(List<Book> books) throws IOException{
		
		for(Book book:books) {
			
			byte[] bookPicResized =  resizePhoto.resize(book.getBookPhoto(), 115, 180);
			
			book.setEncodedBookPic(base64Encoder.getEncodedImage(bookPicResized));
			
		}
		
		return books;
	}
	
}
