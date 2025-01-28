package com.haven.gutenbergBooks.controler;

import com.haven.gutenbergBooks.model.Book;
import com.haven.gutenbergBooks.services.BookService;
import com.haven.gutenbergBooks.services.SambaService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:9000")
public class BookController {

    private final BookService bookService;
    private final SambaService sambaService;

    public BookController(BookService bookService, SambaService sambaService) {
        this.bookService = bookService;
        this.sambaService = sambaService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> fetchBook(@PathVariable String bookId) {
        Book book = bookService.fetchBook(bookId);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeBook(@RequestBody Map<String, String> request) {
        String bookId = request.get("bookId");

        Book book = bookService.fetchBook(bookId);

        String analysisResult = sambaService.sendRequest(book.getContent());

        return ResponseEntity.ok(Map.of("analysisResult", analysisResult));
    }
}

