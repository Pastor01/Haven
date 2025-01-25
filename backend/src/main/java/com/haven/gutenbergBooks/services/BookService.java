package com.haven.gutenbergBooks.services;

import com.haven.gutenbergBooks.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class BookService {

    private final RestTemplate restTemplate;

    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Book fetchBook(String bookId) {
        String contentUrl = String.format("https://www.gutenberg.org/files/%s/%s-0.txt", bookId, bookId);
        String metadataUrl = String.format("https://www.gutenberg.org/ebooks/%s", bookId);

        String content = restTemplate.getForObject(contentUrl, String.class);

        Book book = new Book();
        book.setContent(content);
        book.setMetadataUrl(metadataUrl);

        return book;
    }
}
