package com.example.readinglist;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    private ReadingListRepository readingListRepository;

    public BookService(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }


    public Book findById(Long id) {
        return readingListRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return readingListRepository.save(book);
    }
}
