package com.haven.gutenbergBooks.controler;

import com.haven.gutenbergBooks.model.Book;
import com.haven.gutenbergBooks.services.BookService;
import com.haven.gutenbergBooks.services.SambaService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Map<String, Object>> analyzeText(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");

        if (userMessage == null || userMessage.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Message is required.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            Map<String, Object> response = sambaService.sendRequest(userMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

