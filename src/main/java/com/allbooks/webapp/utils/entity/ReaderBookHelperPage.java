package com.allbooks.webapp.utils.entity;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.ReaderBook;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReaderBookHelperPage extends PageImpl<ReaderBook> {

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ReaderBookHelperPage(@JsonProperty("content") List<ReaderBook> content,
                      @JsonProperty("number") int number,
                      @JsonProperty("size") int size,
                      @JsonProperty("totalElements") Long totalElements) {
        super(content, new PageRequest(number, size), totalElements);
    }
    
    
}