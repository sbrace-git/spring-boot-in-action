package com.example.readinglist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void insertQueryBook() {
        Book book = new Book();
        book.setTitle("title");
        book.setReader("reader");
        book.setIsbn("isbn");
        book.setAuthor("author");
        book.setDescription("description");

        Book save = bookService.save(book);
        Book findBook = bookService.findById(save.getId());

        assertThat(book, samePropertyValuesAs(findBook));

    }
}
