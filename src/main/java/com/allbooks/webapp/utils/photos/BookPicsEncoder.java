package com.allbooks.webapp.utils.photos;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;

@Component
public class BookPicsEncoder {

	@Autowired
	private Base64Encoder base64Encoder;
	
	@Autowired
	private ResizePhoto resizePhoto;
	
	public List<Book> encode(List<Book> books,int width,int height) throws IOException{
		
		for(Book book:books) {
			
			byte[] bookPicResized =  resizePhoto.resize(book.getBookPhoto(), width, height);
			
			book.setEncodedBookPhoto(base64Encoder.getEncodedImage(bookPicResized));
			
		}
		
		return books;
	}
	
}
