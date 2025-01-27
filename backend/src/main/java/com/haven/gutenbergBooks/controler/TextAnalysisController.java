package com.haven.gutenbergBooks.controler;

import com.haven.gutenbergBooks.services.SambaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TextAnalysisController {

    private final SambaService sambaNovaService;

    public TextAnalysisController(SambaService sambaNovaService) {
        this.sambaNovaService = sambaNovaService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeText(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");

        if (userMessage == null || userMessage.isEmpty()) {
            return ResponseEntity.badRequest().body("Message is required.");
        }

        try {
            String response = sambaNovaService.sendRequest(userMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
