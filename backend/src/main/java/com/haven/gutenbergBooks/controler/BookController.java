package com.haven.gutenbergBooks.controler;

import com.haven.gutenbergBooks.model.Book;
import com.haven.gutenbergBooks.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:9000")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> fetchBook(@PathVariable String bookId) {
        Book book = bookService.fetchBook(bookId);
        return ResponseEntity.ok(book);
    }
}

