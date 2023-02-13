package com.example.readinglist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/readingList")
public class ReadingListController {

    private static final String DEFAULT_READER = "test";

    private final ReadingListRepository readingListRepository;
    private final AmazonProperties amazonProperties;


    public ReadingListController(ReadingListRepository readingListRepository, AmazonProperties amazonProperties) {
        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String readingList(Model model) {
        List<Book> readingList = readingListRepository.findByReader(DEFAULT_READER);
        if (readingList != null) {
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(path = "/{reader}", method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model) {

        List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(path = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/readingList/{reader}";
    }

}
