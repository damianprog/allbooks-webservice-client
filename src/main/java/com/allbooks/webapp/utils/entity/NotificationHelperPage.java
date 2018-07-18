package com.allbooks.webapp.utils.entity;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.allbooks.webapp.entity.Notification;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationHelperPage extends PageImpl<Notification> {

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public NotificationHelperPage(@JsonProperty("content") List<Notification> content,
                      @JsonProperty("number") int number,
                      @JsonProperty("size") int size,
                      @JsonProperty("totalElements") Long totalElements) {
        super(content, new PageRequest(number, size), totalElements);
    }
    
    
}
