package com.haven.gutenbergBooks.controler;

import com.haven.gutenbergBooks.model.Book;
import com.haven.gutenbergBooks.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:9000")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Endpoint to fetch a book by ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> fetchBook(@PathVariable String bookId) {
        Book book = bookService.fetchBook(bookId);
        return ResponseEntity.ok(book);
    }

    // Endpoint to get all previously fetched books
    @GetMapping
    public ResponseEntity<Map<String, Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Endpoint to fetch a book by ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> fetchBookContent(@PathVariable String bookId) {
        Book book = bookService.fetchBook(bookId);
        return ResponseEntity.ok(book);
    }
}

