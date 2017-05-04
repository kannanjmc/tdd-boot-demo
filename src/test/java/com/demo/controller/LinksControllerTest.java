package com.demo.controller;

import com.demo.model.Link;
import com.demo.service.UrlShortener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(LinksController.class)
public class LinksControllerTest {

    private static final String SHORT_URL = "abc123";
    private static final String FULL_URL = "http://www.averylongurl.com";
    private Link mockLink = new Link(SHORT_URL, FULL_URL);

    @Autowired
    private LinksController linksController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UrlShortener urlShortener;

    @Test
    public void shorten_createsShortUrl() throws Exception {
        //Arrange
        when(urlShortener.shorten(anyString())).thenReturn(mockLink);

        //Act
        ResultActions result = mvc.perform(get("/shorten").param("fullUrl", FULL_URL));

        //Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("short_url").value(SHORT_URL))
                .andExpect(jsonPath("full_url").value(FULL_URL));
    }
}

