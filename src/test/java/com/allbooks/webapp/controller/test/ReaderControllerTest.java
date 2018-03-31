package com.allbooks.webapp.controller.test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.allbooks.webapp.entity.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReaderControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void showBookTest() throws Exception {
		
		mockMvc.perform(get("/reader/showBook")
			.param("bookName", "The Ship Of The Dead"))
				.andExpect(status().isOk())
				.andExpect(view().name("book"))
				.andExpect(forwardedUrl("/view/book.jsp"))
				.andExpect(model().attribute("book",instanceOf(Book.class)))
				.andExpect(model().attribute("book",notNullValue()))
				.andExpect(model().attribute("quotesSplit",hasSize(2)));
		
		
		
	}
	
}
