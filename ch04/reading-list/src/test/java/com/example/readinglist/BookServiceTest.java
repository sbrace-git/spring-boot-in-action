package com.example.readinglist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testService() {
        Book book = bookService.findById(1L);

        System.out.println(book.getId());
        System.out.println(book.getAuthor());
        System.out.println(book.getDescription());
        System.out.println(book.getIsbn());
        System.out.println(book.getReader());
        System.out.println(book.getTitle());


        assertEquals(book.getId(),1L);
        assertEquals(book.getAuthor(),"1");
        assertEquals(book.getDescription(),"1");
        assertEquals(book.getIsbn(),"1");
        assertEquals(book.getReader(),"test");
        assertEquals(book.getTitle(),"1");
    }
}
