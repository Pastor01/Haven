package com.haven.gutenbergBooks.services;

import com.haven.gutenbergBooks.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {

    private final Map<String, Book> bookStore = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public Book fetchBook(String bookId) {
        String metadataUrl = "https://www.gutenberg.org/ebooks/" + bookId;
        String contentUrl = "https://www.gutenberg.org/files/" + bookId + "/" + bookId + "-0.txt";

        try {
            // Fetch content
            String content = restTemplate.getForObject(contentUrl, String.class);

            // Store the book in memory
            Book book = new Book(bookId, metadataUrl, content);
            bookStore.put(bookId, book);

            return book;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching book data: " + e.getMessage());
        }
    }

    public Map<String, Book> getAllBooks() {
        return bookStore;
    }
}
