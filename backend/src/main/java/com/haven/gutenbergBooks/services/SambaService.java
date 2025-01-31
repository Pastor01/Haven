package com.haven.gutenbergBooks.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class SambaService {

    private final RestTemplate restTemplate;

    public SambaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map sendRequest(String userMessage) {
        String apiUrl = "https://api.sambanova.ai/v1/chat/completions";
        String apiKey = "153c1857-0d53-4a99-9949-c031c99f0efd";

        // Build the request body
        Map<String, Object> requestBody = Map.of(
                "stream", false,
                "model", "Meta-Llama-3.1-8B-Instruct",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a librarian"),
                        Map.of("role", "user", "content", "Can you give me the key characters from this story: " + userMessage)
                )
        );


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.getBody(), Map.class);
            } else {
                throw new RuntimeException("SambaNova API returned an error: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling SambaNova API: " + e.getMessage());
        }
    }
}
