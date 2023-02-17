package com.example.readinglist;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@WebMvcTest(ReadingListController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReadingListControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ReadingListRepository readingListRepository;

    @MockBean
    private AmazonProperties amazonProperties;

    @TestConfiguration
    static class AdditionalConfig {
        @Bean
        public MeterRegistry registry() {
            return new SimpleMeterRegistry();
        }
    }

    @Test
    @Order(1)
    public void getReadingList() throws Exception {
        when(readingListRepository.findByReader("craig"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/readingList"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", is(empty())));
    }

    @Test
    @Order(2)
    public void addToReadingList() throws Exception {
        mockMvc.perform(post("/readingList")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "title")
                        .param("author", "author")
                        .param("isbn", "isbn")
                        .param("description", "description"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/readingList"))
        ;
        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setReader("craig");
        expectedBook.setTitle("title");
        expectedBook.setAuthor("author");
        expectedBook.setIsbn("isbn");
        expectedBook.setDescription("description");

        when(readingListRepository.findByReader("craig"))
                .thenReturn(Collections.singletonList(expectedBook));


        mockMvc.perform(get("/readingList"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books",
                        contains(samePropertyValuesAs(expectedBook))));
    }
}
